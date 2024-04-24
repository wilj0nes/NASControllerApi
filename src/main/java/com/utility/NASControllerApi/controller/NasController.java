package com.utility.NASControllerApi.controller;

import com.utility.NASControllerApi.service.CommandRunnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nas")
public class NasController {

    private final CommandRunnerService commandRunnerService;

    public NasController(CommandRunnerService commandRunnerService) {
        this.commandRunnerService = commandRunnerService;
    }

    @GetMapping("/os")
    public String checkOs() {
        return System.getProperty("os.name");
    }

    @GetMapping("/kill")
    public String testCommand() throws Exception {
        String response = "";
        String sonarr = "Sonarr.exe";
//        return this.commandRunnerService.gobblerTest();
//        return commandRunnerService.killWindowsProcess("Sonarr.exe");

        if (CommandRunnerService.isProcessRunning(sonarr)) {
            response = this.commandRunnerService.killWindowsProcess(sonarr);
        }
        else {
            response = "Failed to kill process: + " + sonarr;
        }

        return response;
    }

}
