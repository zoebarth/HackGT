package com.example.illegalskillsexception.ulife;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class RESTAPIUnitTests {
    @Test
    public void push_requestIsRight() throws Exception {
        boolean response = GroupRESTService.pushGameStatus("test1", "whoopsie");

        assertTrue(response);
    }

    @Test
    public void get_requestIsRight() throws Exception {
        GroupRESTService.pushGameStatus("test1", "whoopsie3331");
        String response = GroupRESTService.getGameStatus("test1");

        assertTrue(response.equals("whoopsie3331"));
    }
}