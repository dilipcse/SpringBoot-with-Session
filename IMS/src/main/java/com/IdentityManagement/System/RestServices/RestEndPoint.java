package com.IdentityManagement.System.RestServices;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.IdentityManagement.System.DAO.RegisterRepository;
import com.IdentityManagement.System.DAO.UserAuth;
import com.IdentityManagement.System.DAO.UserEntity;

@RestController
public class RestEndPoint {

	private RegisterRepository regRepo ;
	
	@Autowired
	public RestEndPoint(RegisterRepository regRepo) {
		this.regRepo = regRepo;
	}
	
	@GetMapping("/")
	public String checkMethod() {
		return "Program is running Succesfully";
	}
	
	@PostMapping("/register")
	public void registerUser(@RequestBody UserEntity theUser) {
		System.out.println("cached data: " +theUser);
		this.regRepo.save(theUser);
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody UserAuth theUser,HttpSession session) {

		String name= theUser.getUserName();
		String Password = theUser.getPassword();
		
		System.out.println("name :"+name+" password:"+Password);
		UserEntity UserData = regRepo.findByName(name);
		
		if(Password.equals(UserData.getPassword())) {
			 
			session.setAttribute("User_name", name);
			return "Successfully Logged in";
		}
		
		return "redirect:/" ;
	}
	
	@GetMapping("/login")
	public String Relogin(HttpSession session) {
		String username = (String) session.getAttribute("User_name");
		if(username == null)
			return "login first";
		
		return "logged in as "+username ;
	}
	
	@GetMapping("/logout")
	public String Userlogout(HttpSession session) {
		session.invalidate();
		return "Successfully log out";
	}
}
