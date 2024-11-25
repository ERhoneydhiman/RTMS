package in.rtms.main.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.RoleEnum;
import in.rtms.main.repositories.RoleRepo;

@Repository
public class RoleDao {

	@Autowired
	private RoleRepo roleRepo;
	
//  <--------------------------------------------------------------------------------------------------->
	
	public Optional<RoleEntity> roleByRoleName(RoleEnum roleEnum) {
		return roleRepo.findByRoleName(roleEnum);
		
	}
	
//  <--------------------------------------------------------------------------------------------------->
	
}
