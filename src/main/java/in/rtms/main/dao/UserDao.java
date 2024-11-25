package in.rtms.main.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.rtms.main.entities.UserEntity;
import in.rtms.main.exceptionHandlers.UserNameAllreadyExistException;
import in.rtms.main.exceptionHandlers.UsernameNotFoundException;
import in.rtms.main.repositories.UserRepo;
import jakarta.transaction.Transactional;

@Repository
public class UserDao {

	@Autowired
	private UserRepo userRepo;

//  <--------------------------------------------------------------------------------------------------->

	public Boolean userNameAlreadyExist(String username) {
		return userRepo.existsByUsername(username);
	}

//  <--------------------------------------------------------------------------------------------------->

//	save user in db 
	public UserEntity saveUserEntity(UserEntity userEntity) throws UserNameAllreadyExistException {
		return userRepo.save(userEntity);
	}

//  <--------------------------------------------------------------------------------------------------->

//	check user by username
	public Optional<UserEntity> userByUsername(String username) throws UsernameNotFoundException {
		return userRepo.getByusername(username);
	}

//  <--------------------------------------------------------------------------------------------------->

//	get all users
	public List<UserEntity> getAllUsers() {
		return userRepo.findAll();
	}

//  <--------------------------------------------------------------------------------------------------->

//	delete user
	@Transactional
	public Integer deleteUser(String username) {
	    if (!userRepo.existsByUsername(username)) {
	        throw new UsernameNotFoundException("User with username " + username + " not found.");
	    }
	    return userRepo.deleteByUsername(username);
	}


//  <--------------------------------------------------------------------------------------------------->

//	update user
	public UserEntity updateUser(UserEntity user) {
		return userRepo.save(user);
	}

//  <--------------------------------------------------------------------------------------------------->
}
