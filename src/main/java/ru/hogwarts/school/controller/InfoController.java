package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Autowired
    Environment environment;

    @GetMapping("port")
    public ResponseEntity<String> getPort() {
        return ResponseEntity.ok(environment.getProperty("local.server.port"));

    }
}