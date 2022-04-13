package com.example.our_chat_app.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
 public class ApiResponse {
    private String message;

    private boolean isSuccess;

    private Object data;

    public ApiResponse(Object data) {
        this.data = data;
    }


    public ApiResponse(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
