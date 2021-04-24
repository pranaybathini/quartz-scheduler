package com.pranay.quartz.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private Object response;
    private int statusCode;
    private String message;
    private boolean success;

    public  ApiResponse(String message, int statusCode, boolean success){
        this.message = message;
        this.statusCode = statusCode;
        this.success = success;
    }

    public ApiResponse(Object response, int statusCode, boolean success){
        this.response = response;
        this.statusCode = statusCode;
        this.success = success;
    }

}
