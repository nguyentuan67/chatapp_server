package com.vn.yochatapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private int statusCode;
    private String message;
    private T output;
    private String error;
}
