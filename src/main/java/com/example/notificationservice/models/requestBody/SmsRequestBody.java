package com.example.notificationservice.models.requestBody;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsRequestBody {
    private String phoneNumber;
    private String message;
}
