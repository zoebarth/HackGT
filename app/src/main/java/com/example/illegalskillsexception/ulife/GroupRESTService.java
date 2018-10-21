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

    private static final String PUSH_ENDPOINT_URL = "https://y5p1nc89p6.execute-api.us-east-1.amazonaws.com/api/v1/game/POST/info/{group_id}/{game_status}";

    private static final String GET_ENDPOINT_URL = "https://y5p1nc89p6.execute-api.us-east-1.amazonaws.com/api/v1/game/GET/info/{group_id}";

    private static String buildPUSHUrl(String groupID, String gameStatus) {
        return PUSH_ENDPOINT_URL.replace("{group_id}", groupID).replace("{game_status}", gameStatus);
    }

    /**
     *
     * @param groupID ID of the group participating in the competition
     * @param gameStatus the updated status for the game
     * @return true/false if the request was successful
     */
    public static boolean pushGameStatus(String groupID, String gameStatus) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(buildPUSHUrl(groupID, gameStatus));

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

    private static String buildGETUrl(String groupID) {
        return GET_ENDPOINT_URL.replace("{group_id}", groupID);
    }

    /**
     * Get's game status from the cloud servers.
     *
     * @param groupID ID of the group participating in the competition
     * @return the status of the game ("" otherwise)
     */
    public static String getGameStatus(String groupID) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(buildGETUrl(groupID));

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
