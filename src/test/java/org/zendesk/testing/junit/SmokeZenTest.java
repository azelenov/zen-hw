package org.zendesk.testing.junit;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.zendesk.testing.cucumber.Helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assume.assumeThat;


/**
 * Created by v-ozelenov on 9/15/17.
 *
 * 1. Verify you are able to create a ticket+
 * 2. Verify you are able to add a comment to the ticket+
 * 3. Verify you are able to list all tickets for your Zendesk +
 * 4. Verify you are able to delete a ticket+
 */
@Ignore
public class SmokeZenTest {

    static Properties config;

    @BeforeClass
    public static void loadConfig() {
        config = new Properties();
        InputStream is = Helper.getResourceAsStream("/user/admin.properties");
        try {
            config.load(is);
        } catch (IOException e) {
            throw new RuntimeException("can't load properties file" + e);
        }
        assumeThat("No username found!", config.getProperty("username"), notNullValue());
        assumeThat("No token found!", config.getProperty("token"), notNullValue());
        assumeThat("No URL found!", config.getProperty("org"), notNullValue());
    }
    

    private ZendeskClient getClient(String path) {
        return new ZendeskClient(path, config.getProperty("username"), config.getProperty("token"));
    }
    
    public String getUrl() {
        return String.format("https://%s.zendesk.com/api/v2/", config.getProperty("org"));
    }

    @Test
    public void authenticate() {
        getClient(getUrl() + "users/me.json").getAsync();
    }


    @Test
    public void createTicket() {
        String respBody = getClient(getUrl() + "tickets.json")
                .postAsync((Helper.readFile("sample/ticket.json")));
        String url = JsonPath.from(respBody).getString("ticket.url");
        getClient(url).getAsync();
    }

    @Test
    public void updateTicket() {
        String respBody = getClient(getUrl() + "tickets.json")
                .postAsync(Helper.readFile("sample/ticket.json"));
        String url = JsonPath.from(respBody).getString("ticket.url");
        getClient(url).getAsync();
        getClient(url).put(Helper.readFile("sample/updated_ticket.json"));
        getClient(url).delete();
    }

    @Test
    public void deleteTicket() {
        String respBody = getClient(getUrl() + "tickets.json")
                .postAsync((Helper.readFile("sample/ticket.json")));
        String url = JsonPath.from(respBody).getString("ticket.url");
        getClient(url).get();
        getClient(url).delete();
    }


    @Test
    public void checkTicketsList() {
        String respBody = getClient(getUrl() + "tickets.json").getAsync();
        List<Map> tickets = JsonPath.from(respBody).getList("tickets");
        Assert.assertTrue(tickets.size() > 0);
    }


}
