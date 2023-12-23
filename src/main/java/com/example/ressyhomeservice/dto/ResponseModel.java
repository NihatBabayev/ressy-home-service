package com.example.ressyhomeservice.dto;

import lombok.Data;

@Data
public class ResponseModel<T> {
    T data;
    String message;
}