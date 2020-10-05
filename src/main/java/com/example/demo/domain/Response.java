package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    private int code;
    private String response;
    private String message;
    private Object data;

    public Response(int code, String response, String message, Object data) {
        this.code = code;
        this.response = response;
        this.message = message;
        this.data = data;
    }

}