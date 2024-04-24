package com.utility.NASControllerApi.service;

import com.utility.NASControllerApi.utils.StreamGobbler;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
public class CommandRunnerService {

    private String homeDirectory = System.getProperty("user.home");
    private ProcessBuilder processBuilder;
    private ExecutorService executorService;

    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /IM ";

//    @Autowired
    public CommandRunnerService(ProcessBuilder processBuilder, ExecutorService executorService) {
        this.processBuilder = processBuilder;
        this.executorService = executorService;
    }

    public String gobblerTest() {
        Process process = null;

        Consumer<String> resultConsumer = null;

        try {
//            process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory));
            process = Runtime.getRuntime().exec(String.format("cmd.exe /c taskkill  \"Sonarr.exe\" /im /F %s", homeDirectory));

            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);

            Future<?> future = executorService.submit(streamGobbler);

            int exitCode = process.waitFor();
            future.get(10, TimeUnit.SECONDS);
            return String.valueOf(exitCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.executorService.shutdown();
        }

        return "ERROR";
    }

    public static boolean isProcessRunning(String serviceName) throws Exception {
        try {
            Process process = Runtime.getRuntime().exec(TASKLIST);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(serviceName)) {
                    return true;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String killWindowsProcess(String serviceName) throws Exception {
        try {
            Runtime.getRuntime().exec(KILL + serviceName + " /F");
            System.out.println(serviceName + " killed successfully");
            return serviceName + " killed successfully";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    public String testCommand() {
//        processBuilder.command("taskkill /IM \"Sonarr.exe\" /F");
//        processBuilder.command("cmd.exe", "dir");
        processBuilder.command("dir");

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
//            output.append(reader.readLine());

            int exitValue = process.waitFor();

            if (exitValue == 0) {
                System.out.println(output);
                return output.toString();
            }
            else {
                return String.valueOf(exitValue);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }
}
