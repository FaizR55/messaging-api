package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String sender;

    @NotNull
    private String subject;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String body;

    @NotNull
    private String receiver;
    
    Date date = new Date();

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    public String getSubject() {
        return subject;
    }
    public void setSubject(String header) {
        this.subject = header;
    }
    
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
