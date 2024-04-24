package com.utility.NASControllerApi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nas")
public class NasController {

    @GetMapping("/test")
    public String testCommand() {
        return "whatever";
    }

}
