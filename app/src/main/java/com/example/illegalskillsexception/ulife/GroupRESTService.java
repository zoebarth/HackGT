package com.example.illegalskillsexception.ulife;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author valetolpegin
 * @version 1.0
 */
final class GroupRESTService {

    private static final String PUSH_ENDPOINT_URL = "https://y5p1nc89p6.execute-api.us-east-1.amazonaws.com/api/v1/game/POST/info/{key}/{value}";

    private static final String GET_ENDPOINT_URL = "https://y5p1nc89p6.execute-api.us-east-1.amazonaws.com/api/v1/game/GET/info/{key}";

    private static String buildPUSHUrl(String key, String value) {
        return PUSH_ENDPOINT_URL.replace("{key}", key).replace("{value}", value);
    }

    /**
     *
     * @param key ID of the group participating in the competition
     * @param value the updated status for the game
     * @return true/false if the request was successful
     */
    public static boolean pushKeyValue(String key, String value) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(buildPUSHUrl(key, value));

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        Response response = request.get();

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            System.out.println("Success! " + response.getStatus());

            return true;
        } else {
            System.out.println("ERROR! " + response.getStatus());
        }

        return false;
    }

    private static String buildGETUrl(String key) {
        return GET_ENDPOINT_URL.replace("{key}", key);
    }

    /**
     * Get's game status from the cloud servers.
     *
     * @param key ID of the group participating in the competition
     * @return the status of the game ("" otherwise)
     */
    public static String getValue(String key) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(buildGETUrl(key));

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        Response response = request.get();

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            System.out.println("Success! " + response.getStatus());

            return response.readEntity(String.class);
        } else {
            System.out.println("ERROR! " + response.getStatus());
        }

        return "";
    }
}
