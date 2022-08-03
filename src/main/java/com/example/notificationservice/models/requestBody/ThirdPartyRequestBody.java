package com.example.notificationservice.models.requestBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

//import javax.print.attribute.standard.Destination;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyRequestBody {

    @JsonProperty("deliverychannel")
    private String deliveryChannel;

    @JsonProperty("channels")
    private Channel channel;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Channel{

        @JsonProperty("sms")
        private Sms sms;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Sms{

            @JsonProperty("text")
            private String text;

        }
    }

    @JsonProperty("destination")
    private ArrayList<Destination> destination;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Destination{

        @JsonProperty("msisdn")
        private ArrayList<String> msisdn;

        @JsonProperty("correlationid")
        private String correlationId;

    }
}
