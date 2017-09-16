package org.zendesk.testing;

import com.comcast.zucchini.TestContext;
import cucumber.api.java.en.Given;

/**
 * Created by v-ozelenov on 9/16/17.
 */
public class ZenSteps {
    @Given("^Zendesk API is accessible$")
    public void zendeskAPIIsAccessible() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        ZendeskClient zc = TestContext.getCurrent().get("API");
        zc.setBasePath("users/me.json").getAsync();
    }
}
