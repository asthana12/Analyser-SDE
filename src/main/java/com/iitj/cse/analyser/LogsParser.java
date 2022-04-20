package com.iitj.cse.analyser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.InputStream;
import java.util.*;


public class LogsParser {

    @Autowired
    private LogRepository logRepository;

    @Scheduled(fixedDelay = 1000)
    public List<String> readFile() {
        StringBuilder resultStringBuilder = new StringBuilder();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .toUriString();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new InputStream(fileDownloadUri)))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
            resolveDbLog(line);
            lines.add(line);
        }
        return lines;
    }

    public LogObject resolveLog(String logLine) {
        String[] logBreakDown = logLine.split(" ");
        LogObject logObject = new LogObject();
        if (logBreakDown[0].matches("[0-9]")) {
            logObject.setTimeStamp(logBreakDown[0] + logBreakDown[1]);
            logObject.setLogLevel(logBreakDown[2]);
            logObject.setApplicationName(logBreakDown[3].split(",")[0]);
            logObject.setUniqueId(logBreakDown[3].split(",")[1]);
            logObject.setClassName(logBreakDown[7]);
            logObject.setErrorMessage(logBreakDown[9]);
        }
        if (logObject.getErrorMessage().matches(ApplicationConstants.PASS_REGEX) || logObject.getErrorMessage().matches(ApplicationConstants.UUID_REGEX) || logObject.getErrorMessage().contains(ApplicationConstants.ID_KEY) || logObject.getErrorMessage().toLowerCase(Locale.ROOT).contains(ApplicationConstants.PASSWORD_KEY)) {
            System.out.println(logObject.getErrorMessage());
        }
        return logObject;
    }


    public LogObject resolveDbLog(String logLine) {
        String[] logBreakDown = logLine.split(" ");
        com.cse.iitj.application.LogDbObject logObject = new LogDbObject();
        if (logBreakDown[0].matches("[0-9]")) {
            logObject.setLogLevel(logBreakDown[2]);
            logObject.setApplicationName(logBreakDown[3].split(",")[0]);
            logObject.setTraceId(logBreakDown[3].split(",")[1]);
            logObject.setClassName(logBreakDown[7]);
            logObject.setErrorMessage(logBreakDown[9]);
        }
        logRepository.save(logObject);
        return logObject;
    }

    public Map<String, LogObject> groupByRequest(List<String> logLines) {
        Map<String, LogObject> logMap = new HashMap<>();
        List<LogObject> logList = new ArrayList<>();
        for (String logLine : logLines) {
            LogObject resolvedLogObject = resolveLog(logLine);
            logList.add(resolvedLogObject);
        }
        Map<String, Integer> logCountMap = new HashMap<>();
        for (LogObject logLine : logList) {
            if (logCountMap.containsKey(logLine.getUniqueId())) {
                Integer value = logCountMap.get(logLine.getUniqueId());
                logCountMap.put(logLine.getUniqueId(), value++);
            } else {
                logCountMap.put(logLine.getUniqueId(), 1);
            }
        }
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : logCountMap.entrySet()) {
            if (maxEntry == null || entry.getValue()
                    .compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        for (LogObject logLine : logList) {
            if (logLine.getUniqueId().equals(maxEntry.getKey())) {
                logMap.put(maxEntry.getKey(), logLine);
            }
        }
        return logMap;
    }

    @Scheduled(fixedDelay = 1000)
    public void fetchLogs() {
        groupByRequest(readFile());
    }


}
