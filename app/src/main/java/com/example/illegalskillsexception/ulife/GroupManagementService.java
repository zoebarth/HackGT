package com.example.illegalskillsexception.ulife;

public class GroupManagementService {

    private static final String USER_DATA_DELIMETER = "_";

    private static final int USER_ID_LENGTH = 3;

    /**
     *
     * @param groupID ID for the group
     * @param userID ID for this user in the group
     * @param numFails # of times the user has picked up their device
     * @return true/false if the update was successful
     */
    public static boolean updateUserInfo(String groupID, String userID, int numFails) {
        String[] userInfo = GroupManagementService.getUserInfo(groupID);

        String updatedUserInfo = "";
        boolean isUserInInfo = false;
        for (String user : userInfo) {
            String individualUserID = user.substring(0, USER_ID_LENGTH);

            if (individualUserID.equals(userID)) {
                isUserInInfo = true;

                updatedUserInfo += userID + numFails + USER_DATA_DELIMETER;
            } else {
                updatedUserInfo += user + USER_DATA_DELIMETER;
            }
        }

        if (!isUserInInfo) {
            updatedUserInfo += userID + numFails + USER_DATA_DELIMETER;
        }

        return GroupRESTService.pushKeyValue(groupID, updatedUserInfo);
    }

    /**
     *
     * @param groupID ID for the group
     * @return String array of the user information data
     */
    public static String[] getUserInfo(String groupID) {
        String userInfo = GroupRESTService.getValue(groupID);

        return userInfo.split(USER_DATA_DELIMETER);
    }
}
