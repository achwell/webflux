package com.vinsguru.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Response {

    private LocalDateTime date;
    private int output;

    public Response(int output) {
        this.output = output;
        date = LocalDateTime.now();
    }
}
