package com.example.notificationservice.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "sms_requests")
public class SmsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

//    @NotNull         -- did not work rn , will see later
    private String phoneNumber;

//    @Field(type = FieldType.Text, name = "message")
    private String message;
    private String status;
    private Integer failureCode;
    private String failureComments;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    public SmsRequest() {

    }
}
