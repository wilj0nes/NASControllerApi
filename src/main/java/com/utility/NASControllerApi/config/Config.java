package com.utility.NASControllerApi.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Configuration
public class Config {

    private ProcessBuilder processBuilder;
    private ExecutorService executorService;

    @Bean
    public static ProcessBuilder processBuilder() {
        return new ProcessBuilder();
    }

    @Bean
    public ExecutorService executorService() {
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        return executorService;
    }

}
