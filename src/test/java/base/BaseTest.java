package base;

import api.client.ApiClient;
import config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import api.assertion.AssertionsHelper;

public abstract class BaseTest {

    protected ApiClient apiClient;
    protected AssertionsHelper assertionsHelper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        apiClient = new ApiClient(Config.getBaseUrl());
        assertionsHelper = new AssertionsHelper();
    }
}
