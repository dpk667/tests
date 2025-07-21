package tests.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: добавить базовые проверки и написать javadoc для класса
public class AssertionsHelper {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(AssertionsHelper.class);

    @Step("Проверка статуса ответа (status code = 200)")
    public void validateSuccessResponse(Response response, String operationName) {
        assertEquals(200, response.getStatusCode(), "Операция " + operationName + " вернула неожиданный статус-код");
    }

    @Step("Проверка статуса ответа (status code = 500)")
    public void validateServerErrorResponse(Response response, String operationName) {
        assertEquals(200, response.getStatusCode(), "Операция " + operationName + " вернула неожиданный статус-код");
    }

    private static JsonNode normalize(JsonNode node) {
        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;
            obj.fieldNames().forEachRemaining(field -> {
                JsonNode child = obj.get(field);
                obj.set(field, normalize(child));
            });
            return obj;
        } else if (node.isArray()) {
            ArrayNode arr = (ArrayNode) node;
            List<JsonNode> sorted = new ArrayList<>();
            arr.forEach(child -> sorted.add(normalize(child)));

            if (!sorted.isEmpty() && sorted.get(0).isObject()) {
                sorted.sort(Comparator.comparing(JsonNode::toString));
            }

            ArrayNode newArr = arr.arrayNode();
            sorted.forEach(newArr::add);
            return newArr;
        } else {
            return node;
        }
    }

    //TODO вывести в allure либо diff либо json-ы
    @Step("Проверка соответствия отправленного и полученного тела json в запросах")
    public void assertJsonEquals(Object expected, Object actual, String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //для сортировки ключей и вывода только не null Значений
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

            //преобразование объектов в нормальные JsonNode
            JsonNode expectedJson = normalize(mapper.readTree(mapper.writeValueAsString(expected)));
            JsonNode actualJson = normalize(mapper.readTree(mapper.writeValueAsString(actual)));

            // вывод в sout для diff двух json (упрощенный дебаг)
            if (!expectedJson.equals(actualJson)) {
                System.out.println("=====EXPECTED JSON=====");
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedJson));
                System.out.println("=====ACTUAL JSON=====");
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(actualJson));

                Assertions.fail(message + "Excpected: \n" + expectedJson.toPrettyString() + "\n\nActual:\n" + actualJson.toPrettyString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Ошибка при сравнении JSON " + e.getMessage(), e);
        }
    }

    @Step("Проверка, что поле moduleOwner = {expectedOwner}")
    public void assertModuleOwner(Response response, String expectedOwner) {
        String actualOwner = response.jsonPath().getString("datahubList[0].moduleOwner");
        Assertions.assertEquals(expectedOwner.toLowerCase(), actualOwner.toLowerCase(),
                "Поле moduleOwner не совпадает с ожидаемым значением");
    }

}
