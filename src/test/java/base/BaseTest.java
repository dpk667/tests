package base;

import api.client.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import api.assertion.AssertionsHelper;

public abstract class BaseTest {

    protected final ApiClient apiClient = new ApiClient();
    protected final AssertionsHelper assertionsHelper = new AssertionsHelper();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
