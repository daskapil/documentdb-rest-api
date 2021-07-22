package com.kapil.aws;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void handleRequest_shouldReturnConstantValue() throws IOException {
        App function = new App();
        Object result = function.handleRequest("echo", null);
        assertEquals("echo", result);
    }
}
