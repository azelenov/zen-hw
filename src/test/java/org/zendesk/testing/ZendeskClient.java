package org.zendesk.testing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * REST client implementation with Unirest
 */
public class ZendeskClient extends Thread  {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZendeskClient.class);

    private String token;
    private String url;
    private String username;
    private String principal;



    public ZendeskClient(String url, String username, String token) {
        this.url = url;
        this.username = username;
        this.principal = username + "/token";
        this.token = token;
        Unirest.setDefaultHeader("Accept", "application/json");
        Unirest.setDefaultHeader("Content-Type", "application/json");
        Unirest.setConcurrency(2, 2);
    }

    public String getUrl() {
        return url;
    }

    public ZendeskClient setBasePath(String path) {
        this.url = url + path;
        return this;
    }

    public String getAsync() {
        try {
            final Future<HttpResponse<JsonNode>> response = Unirest.get(this.url)
                    .basicAuth(this.principal, this.token)
                    .asJsonAsync();
            HttpResponse<JsonNode> resp = response.get();
            verifyResponseCode(resp.getStatus(), 200);
            return resp.getBody().toString();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Request failed!");

        }
        finally {
            shutdown();
        }
    }

    public String postAsync(String payload) {
        try {
            final Future<HttpResponse<JsonNode>> response = Unirest.post(this.url)
                    .basicAuth(this.principal, this.token)
                    .body(payload)
            .asJsonAsync();
            HttpResponse<JsonNode> resp = response.get();
            verifyResponseCode(resp.getStatus(), 201);
            return resp.getBody().toString();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Request failed!");

        }
        finally {
            shutdown();
        }
    }

    public String get() {
        try {
            HttpResponse<JsonNode> resp = Unirest.get(this.url)
                    .basicAuth(this.principal, this.token)
                    .asJson();
            verifyResponseCode(resp.getStatus(), 200);
            return resp.getBody().toString();
        } catch (UnirestException e) {
            throw new RuntimeException("Request failed!");
        }
        finally {
            shutdown();
        }
    }

    public void delete() {
        try {
            HttpResponse<JsonNode> resp = Unirest.delete(this.url)
                    .basicAuth(this.principal, this.token)
                    .asJson();
            verifyResponseCode(resp.getStatus(), 204);
        } catch (UnirestException e) {
            throw new RuntimeException("Request failed!");
        }
        finally {
            shutdown();
        }
    }

    public void put(String payload) {
        try {
            HttpResponse<JsonNode> resp = Unirest.put(this.url)
                    .basicAuth(this.principal, this.token)
                    .body(payload)
                    .asJson();
            verifyResponseCode(resp.getStatus(), 200);
        } catch (UnirestException e) {
            throw new RuntimeException("Request failed!");
        }
        finally {
            shutdown();
        }
    }

    public String post(String payload) {
        try {
            HttpResponse<JsonNode> resp = Unirest.post(this.url)
                    .basicAuth(this.principal, this.token)
                    .body(payload)
                    .asJson();
            verifyResponseCode(resp.getStatus(), 201);
            return resp.getBody().toString();
        } catch (UnirestException e) {
            throw new RuntimeException("Request failed!");
        } finally {
            shutdown();
        }
    }

    private void verifyResponseCode(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException("Service responded with an error code: " + actual + " for url: " + this.url);
        }
        else {
            LOGGER.info("Request successful" + Thread.currentThread().getId());
        }
    }

    private void shutdown() {
        try {
            LOGGER.warn("Exiting REST client#" + Thread.currentThread().getId());
            Unirest.shutdown();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
