package com.IdentityManagement.System.DAO;

import org.springframework.data.repository.CrudRepository;

public interface RegisterRepository extends CrudRepository<UserEntity, Integer >{

	UserEntity findByName (String name) ;
}
