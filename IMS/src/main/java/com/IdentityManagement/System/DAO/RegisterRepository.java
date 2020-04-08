package com.IdentityManagement.System.DAO;

import org.springframework.data.repository.CrudRepository;

import com.IdentityManagement.System.model.UserEntity;

public interface RegisterRepository extends CrudRepository<UserEntity, Integer >{

	UserEntity findByName (String name) ;
}
