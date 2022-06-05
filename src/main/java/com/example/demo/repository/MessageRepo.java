package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
	
    @Query(value="SELECT * from message where receiver=?1 ORDER BY date DESC",nativeQuery = true)
	List<Message> findMsg(String email);

}