package com.santd.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

    @org.junit.jupiter.api.Test
    byte[] readBytes(String file) throws URISyntaxException, IOException {
        return Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("invalid_req.json").toURI().getPath()));
    }
}
