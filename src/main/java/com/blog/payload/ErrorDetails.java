package com.blog.payload;

import java.util.Date;
//it helps when ever error ocurres .it gives to user in message what problem occurs. in details of error.
//resource not found exception triggers and handles
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }
}
