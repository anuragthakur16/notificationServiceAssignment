package com.example.notificationservice.repositories;

import com.example.notificationservice.entities.SmsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<SmsRequest,Integer> {

}
