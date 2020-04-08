package com.IdentityManagement.System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IdentityManagement.System.DAO.RegisterRepository;
import com.IdentityManagement.System.model.UserEntity;

@Service
public class UserRegistrationService {
	
	@Autowired
	private RegisterRepository regRepo;

	public UserEntity findUserByName(String name) {
		return regRepo.findByName(name);
	}
	
	public void saveUser(UserEntity user) {
		 regRepo.save(user);
	}
	
}
