package org.zendesk.testing.cucumber;

import com.comcast.zucchini.AbstractZucchiniTest;
import com.comcast.zucchini.TestContext;
import cucumber.api.CucumberOptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Test runner for Zucchini test
 */
@CucumberOptions(
        features = { "src/test/resources/feature" },
        glue = { "org.zendesk.testing.cucumber" }
)
public class TestRunner extends AbstractZucchiniTest {
    @Override
    public List<TestContext> getTestContexts() {
        List<TestContext> testContexts = new ArrayList<>();

        TestContext apiClient = new TestContext("API");
        testContexts.add(apiClient);

        return testContexts;
    }
}
