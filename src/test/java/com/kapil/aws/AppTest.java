package com.kapil.aws;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void handleRequest_shouldReturnConstantValue() {
        App function = new App();
        Object result = function.handleRequest("echo", null);
        assertEquals("echo", result);
    }
}
