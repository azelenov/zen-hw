package org.zendesk.testing;

import com.comcast.zucchini.AbstractZucchiniTest;
import com.comcast.zucchini.TestContext;
import cucumber.api.CucumberOptions;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by v-ozelenov on 9/16/17.
 */
@CucumberOptions(
        features = { "src/test/resources" },
        glue = { "org.zendesk.testing" }
)
public class TestRunner extends AbstractZucchiniTest {
    @Override
    public List<TestContext> getTestContexts() {
        List<TestContext> testContexts = new ArrayList<>();
        Properties config = new Properties();
        InputStream is = Helper.getResourceAsStream("/zendesk-test.properties");
        try {
            config.load(is);
        } catch (IOException e) {
            throw new RuntimeException("can't load properties file" + e);
        }

        TestContext apiClient = new TestContext("API");
        apiClient.set("API", new ZendeskClient(config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("token")));
        testContexts.add(apiClient);
        return testContexts;
    }
}
