package in.rtms.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rtms.main.dao.RoleDao;
import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.RoleEnum;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
//  <--------------------------------------------------------------------------------------------------->
	
	public RoleEntity getRole(RoleEnum roleEnum) {
		
		RoleEntity role = roleDao.roleByRoleName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Role not found: " + RoleEnum.TENENT));
		
		return role;
		
	}

//  <--------------------------------------------------------------------------------------------------->
	
}
