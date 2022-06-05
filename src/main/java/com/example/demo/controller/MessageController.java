package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.MessageRepo;
import com.example.demo.service.LoginService;
import com.example.demo.common.Response;
import com.example.demo.entity.Message;


@SuppressWarnings({"unchecked", "rawtypes"})
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
	MessageRepo msgRepo;
	
    @Autowired
    private LoginService loginServ;

    @PostMapping("/send/")
	public ResponseEntity<Response> sendMessage(@RequestBody Message msg , @RequestParam String email, @RequestParam String password, @RequestParam String[] receiver) {

		Response response = new Response();

		List<String> auth = loginServ.login(email, password);
		if (auth.get(0).equals("sukses")) {
			String subject = msg.getSubject();
			String body = msg.getBody();
		if (receiver.length > 1){
			for (String i : receiver) {
				Message msgloop = new Message();
				msgloop.setSender(email);
				msgloop.setSubject(subject);
				msgloop.setBody(body);
				msgloop.setReceiver(i.toString());
				msgRepo.save(msgloop);
			  }
			  Map<String, Object> map = new HashMap<String, Object>();
				map.put("sender", email);
				map.put("subject", subject);
				map.put("body", body);
				map.put("receiver", receiver.toString());
				
				response.setStatus("Sukses");
				response.setMessage("Pesan berhasil dikirim");
				response.setData(map);
			return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
			}else{
				msg.setSender(email);
				msg.setReceiver(receiver[0].toString());
				msgRepo.save(msg);
				response.setStatus("Sukses");
				response.setMessage("Pesan berhasil dikirim");
				response.setData(msg);
			return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
			}
		}else {
			response.setStatus("Gagal");
			response.setMessage("Autentikasi gagal");
			response.setData("-");
	
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.contentType(MediaType.APPLICATION_JSON)
					.body(response);
			}
		
	}
}
