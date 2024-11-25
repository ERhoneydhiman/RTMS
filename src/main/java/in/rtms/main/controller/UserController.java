package in.rtms.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rtms.main.dto.UserRequestDTO;
import in.rtms.main.dto.UserResponceDTO;
import in.rtms.main.services.JWTUserService;
import in.rtms.main.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTUserService jwtUserService;

//  <--------------------------------------------------------------------------------------------------->
//	http://localhost:1000/api/user/user-detail/abc
	@GetMapping("/user-detail/{username}")
	public ResponseEntity<UserResponceDTO> getUser(@PathVariable String username) {
		return new ResponseEntity<UserResponceDTO>(userService.getUser(username), HttpStatus.OK);
	}

//  <--------------------------------------------------------------------------------------------------->

	// Update user api
	@PutMapping("/update/{username}")
	public ResponseEntity<Object> updateUser(@PathVariable String username,
	                                                  @RequestBody UserRequestDTO userRequestDTO) throws Exception {
	    if (jwtUserService.canModifyUser(username)) {
	        UserResponceDTO userResponceDTO = userService.updateUser(username, userRequestDTO);
	        return new ResponseEntity<>(userResponceDTO, HttpStatus.OK);
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("You do not have permission to update this user.");
	    }
	}

//  <--------------------------------------------------------------------------------------------------->

	// remove user
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<String> removeUser(@PathVariable String username) {
	    if (jwtUserService.canModifyUser(username)) {
	        Integer removeUser = userService.removeUser(username);
	        if (removeUser == 1) {
	            return ResponseEntity.ok("User with username " + username + " has been successfully removed.");
	        } else {
	            return ResponseEntity.status(500).body("Failed to remove user with username " + username + ".");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("You do not have permission to delete this user.");
	    }
	}

//  <--------------------------------------------------------------------------------------------------->

}
