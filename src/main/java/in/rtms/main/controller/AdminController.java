package in.rtms.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rtms.main.dto.UserRequestDTO;
import in.rtms.main.dto.UserResponceDTO;
import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.RoleEnum;
import in.rtms.main.exceptionHandlers.UsernameNotFoundException;
import in.rtms.main.services.RoleService;
import in.rtms.main.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
//  <--------------------------------------------------------------------------------------------------->
	
//	http://localhost:1000/api/admin/add-admin
	@PostMapping("/add-admin")
	public ResponseEntity<UserResponceDTO> signInAdmin(@RequestBody UserRequestDTO userRequestDTO) {
		RoleEntity role = roleService.getRole(RoleEnum.ADMIN);
		return new ResponseEntity<UserResponceDTO>(userService.saveUser(userRequestDTO, role), HttpStatus.OK);
	}
	
//  <--------------------------------------------------------------------------------------------------->
	
//	http://localhost:1000/api/admin/get-all-users
	@GetMapping("/get-all-users")
	public ResponseEntity<List<UserResponceDTO>> getAllUsers() {
		return new ResponseEntity<List<UserResponceDTO>>(userService.getAllUsers(), HttpStatus.OK);
	}
	
//  <--------------------------------------------------------------------------------------------------->
	
//	http://localhost:1000/api/admin/remove-admin/abc
	@DeleteMapping("/remove-admin/{username}")
	public ResponseEntity<String> removeAdmin(@PathVariable String username) {
		Integer removeAdmin =  userService.removeUser(username);
		if (removeAdmin == 1) {
		    return ResponseEntity.ok("Admin with username " + username + " has been successfully removed.");
		} else {
		    throw new UsernameNotFoundException("User with username " + username + " not found.");
		}

	}
	
//  <--------------------------------------------------------------------------------------------------->
	
	
}
