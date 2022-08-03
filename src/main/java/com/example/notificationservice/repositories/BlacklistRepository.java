package com.example.notificationservice.repositories;

import com.example.notificationservice.entities.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlacklistRepository extends JpaRepository<Blacklist,String> {
}
