package com.example.demo.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
    private int statusCode;
    private String error;
    // mesage có thể là String hoặc Arraylisst
    private Object mesage;
    private T data;



}
