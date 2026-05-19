package com.telemetry.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alert {
    private String message;
    private String severity; // INFO, WARNING, CRITICAL
    private LocalDateTime timestamp;

    public Alert(String message, String severity) {
        this.message = message;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] " + severity + ": " + message;
    }
}