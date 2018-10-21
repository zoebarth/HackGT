package com.example.illegalskillsexception.ulife;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GroupManagementServiceTest {

    @Test
    public void getUserInfo_isRight() throws Exception {
        GroupRESTService.pushKeyValue("test1", "abc12_bcf442_apz30");
        String[] responseData = GroupManagementService.getUserInfo("test1");

        assertEquals(responseData.length, 3);
        assertTrue(responseData[0].equals("abc12"));
        assertTrue(responseData[1].equals("bcf442"));
        assertTrue(responseData[2].equals("apz30"));
    }

    @Test
    public void updateUserInfo_isRight() throws Exception {
        GroupRESTService.pushKeyValue("test1", "abc12_bcf442_apz30");
        boolean response = GroupManagementService.updateUserInfo("test1", "bcf", 333);

        String[] responseData = GroupManagementService.getUserInfo("test1");

        assertTrue(response);
        assertTrue(responseData[1].equals("bcf333"));
    }
}