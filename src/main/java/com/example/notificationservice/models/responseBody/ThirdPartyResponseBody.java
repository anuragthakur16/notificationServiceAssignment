package com.example.notificationservice.models.responseBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyResponseBody {

    @JsonProperty("response")
    private ArrayList<Response> responses;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String code;
        @JsonProperty("transid")
        private String transId;
        @JsonProperty("description")
        private String description;
        @JsonProperty("correlationid")
        private String correlationId;
    }

}
