package com.masai.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.masai.global.GlobalData;
import com.masai.model.Role;
import com.masai.model.User;
import com.masai.repository.RoleRepo;
import com.masai.repository.UserRepo;
@Controller
public class LoginController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@GetMapping("/register")
	public String registerGet() {
		
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request, HttpSession session)throws ServletException {
		
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findById(2).get());
		
		user.setRoles(roles);
		
		userRepo.save(user);
		
		request.login(user.getEmail(), password);
		session.setAttribute("message","Register Successfully...");
		return "redirect:/";
	}
	

}
