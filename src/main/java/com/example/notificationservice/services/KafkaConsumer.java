package com.example.notificationservice.services;

import com.example.notificationservice.entities.SmsRequest;
import com.example.notificationservice.models.requestBody.ThirdPartyRequestBody;
import com.example.notificationservice.models.responseBody.ThirdPartyResponseBody;
import com.example.notificationservice.repositories.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.example.notificationservice.constants.Constant.KEYHEADER;

@Service
public class KafkaConsumer {

    @Autowired
    RestTemplate restTemplate;
    private NotificationService notificationService;
    private BlacklistService blacklistService;
    private SmsRepository smsRepository;

    @Autowired
    public KafkaConsumer(
            NotificationService notificationService,BlacklistService blacklistService,SmsRepository smsRepository) {
        this.notificationService = notificationService;
        this.blacklistService = blacklistService;
        this.smsRepository = smsRepository;
    }

    @KafkaListener(topics = "notification.send_sms",groupId = "groupId")
    private void consume(Integer requestId){
        Optional<SmsRequest> smsRequest = notificationService.getSmsDetails(requestId);

        boolean isBlacklisted = blacklistService.isBlacklisted(smsRequest.get().getPhoneNumber());

        ArrayList<ThirdPartyRequestBody> requestBody = new ArrayList<>();

        ThirdPartyRequestBody thirdPartyRequestBody = buildRequestBody(smsRequest.get());
        requestBody.add(thirdPartyRequestBody);
        System.out.println("req body : " + requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("key",KEYHEADER);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<ArrayList<ThirdPartyRequestBody>> request = new HttpEntity<>(requestBody,headers);

        if(!isBlacklisted){
            ThirdPartyResponseBody thirdPartyResponseBody = restTemplate
                    .exchange("https://api.imiconnect.in/resources/v1/messaging", HttpMethod.POST,
                    request, ThirdPartyResponseBody.class).getBody();
            if(thirdPartyResponseBody.getResponses().get(0).getCode().equals("1001")){
                smsRequest.get().setStatus("SUCCESS");
                smsRepository.save(smsRequest.get());
            }
            else{
                smsRequest.get().setStatus("FAILURE");
                smsRepository.save(smsRequest.get());
            }
        }
    }

    private ThirdPartyRequestBody buildRequestBody(SmsRequest smsRequest) {

        ArrayList<String> msisdn = new ArrayList<>();
        msisdn.add(smsRequest.getPhoneNumber());

        ArrayList<ThirdPartyRequestBody.Destination> destinations = new ArrayList<>();
        destinations.add(ThirdPartyRequestBody.Destination.builder()
                .msisdn(msisdn)
                .correlationId(smsRequest.getId().toString())
                .build());

        ThirdPartyRequestBody body = ThirdPartyRequestBody
                .builder()
                .deliveryChannel("sms")
                .channel(ThirdPartyRequestBody.Channel.builder()
                        .sms(ThirdPartyRequestBody.Channel.Sms.builder().text(smsRequest.getMessage()).build())
                        .build())
                .destination(destinations)
                .build();
        return body;
    }
}
