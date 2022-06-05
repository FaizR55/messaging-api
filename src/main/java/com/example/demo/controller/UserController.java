package com.example.demo.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.UserRepo;
import com.example.demo.service.LoginService;
import com.example.demo.repository.MessageRepo;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.common.Response;


@SuppressWarnings({"unchecked", "rawtypes"})
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	MessageRepo msgRepo;
	
    @Autowired
    private LoginService loginServ;
	
	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@GetMapping("/login/")
	public ResponseEntity<Response> loginUser(@RequestParam("email")String email, @RequestParam("password") String password) {
		
        Response response = new Response();

		List<String> auth = loginServ.login(email, password);

		if (auth.get(0).equals("sukses")) {

		String user = auth.get(1);
		String[] ary = user.split(",");
		Map<String, Object> msg = new HashMap<>();
		
		msg.put("id", ary[0]);
        msg.put("nama", ary[2]);
        msg.put("email", ary[1]);

		response.setStatus("Sukses");
        response.setMessage("Selamat datang user");
        response.setData(msg);

		return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
		}else {
		response.setStatus("Gagal");
		response.setMessage("Autentikasi salah");
		response.setData("-");

		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
		}
	}

	@GetMapping("/searchby/{type}/{value}")
	public List<User> getSearchBy(@PathVariable("type")String type, @PathVariable("value") String value) {
		return userRepo.findBySearchBy(type, value);
	}

	@GetMapping("/name/{value}")
	public User getByName(@PathVariable("value") String value) {
		return userRepo.findByName(value);
	}

	@PostMapping("/register/")
	public String addUser(@RequestBody User user) {
		try {
			userRepo.save(user);
			return "User baru berhasil dibuat";
		} catch (Exception e) {
			return "Terjadi kesalahan parameter";
		}
	}
	
	@PostMapping("/register/{id}")
	public String updateUser(@PathVariable String id, @RequestBody User user) {
		user.setId(Long.parseLong(id));
		userRepo.save(user);
		return "Update user berhasil";
	}

	@GetMapping("/inbox/")
	public ResponseEntity<Response> inboxUser(@RequestParam("email")String email, @RequestParam("password") String password) {
		Response response = new Response();

		List<String> auth = loginServ.login(email, password);

		if (auth.get(0).equals("sukses")) {
		// JSONArray inbox = new JSONArray(msgRepo.findMsg(name));
		// Map<String, Object> msg = new HashMap<>();

		// for (int i = 0; i < inbox.length(); i++) { 	
		// 	JSONObject row = inbox.getJSONObject(i);
		// 	 msg.put("id", row.getInt("id"));
		// 	 msg.put("header", row.getString("header"));
		// 	 msg.put("body", row.getString("body"));
		// 	 msg.put("sender", row.getString("sender"));
		// }  

		response.setStatus("Sukses");
        response.setMessage("Inbox email anda :");
        response.setData(msgRepo.findMsg(email));

		return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
		}else {
		response.setStatus("Gagal");
		response.setMessage("Autentikasi salah");
		response.setData("-");

		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
		}
	}

}