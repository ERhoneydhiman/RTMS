package in.rtms.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rtms.main.dao.UserDao;
import in.rtms.main.dto.UserRequestDTO;
import in.rtms.main.dto.UserResponceDTO;
import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.UserEntity;
import in.rtms.main.exceptionHandlers.BadCredentialsException;
import in.rtms.main.exceptionHandlers.UserNameAllreadyExistException;
import in.rtms.main.exceptionHandlers.UsernameNotFoundException;
import in.rtms.main.translater.UserTranslater;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserTranslater userTranslater;

//  <--------------------------------------------------------------------------------------------------->

	public Boolean checkUserExist(String username) {
		return userDao.userNameAlreadyExist(username);
	}

//  <--------------------------------------------------------------------------------------------------->

	public UserResponceDTO getUser(String username) {
		Optional<UserEntity> userOptional = userDao.userByUsername(username);
		UserEntity userEntity = userOptional.orElseThrow(() -> new UsernameNotFoundException("user not found..."));

		return userTranslater.userEntityToDto(userEntity);
	}

//  <--------------------------------------------------------------------------------------------------->

	public UserResponceDTO saveUser(UserRequestDTO userRequestDTO, RoleEntity roleEntity)
			throws UserNameAllreadyExistException {

		UserEntity user = UserEntity.builder().firstName(userRequestDTO.getFirstName())
				.lastName(userRequestDTO.getLastName()).username(userRequestDTO.getUsername())
				.phoneNumber(userRequestDTO.getPhoneNumber()).email(userRequestDTO.getEmail())
				.password(userRequestDTO.getPassword()).role(roleEntity).build();

		if (checkUserExist(user.getUsername())) {
			throw new UserNameAllreadyExistException("user with same username allready exist...");
		}

		UserEntity savedUser = userDao.saveUserEntity(user);
		UserResponceDTO userResponceDTO = userTranslater.userEntityToDto(savedUser);

		return userResponceDTO;

	}

//  <--------------------------------------------------------------------------------------------------->

	public UserResponceDTO loginUser(String username, String password) throws UsernameNotFoundException {
		Optional<UserEntity> userOptional = userDao.userByUsername(username);

		UserEntity userEntity = userOptional.orElseThrow(() -> new UsernameNotFoundException("user not found..."));

		if (!userEntity.getPassword().equals(password)) {
			throw new BadCredentialsException("Incorrect password.");
		}
		UserResponceDTO createrResDTO = userTranslater.userEntityToDto(userEntity);

		return createrResDTO;

	}

//  <--------------------------------------------------------------------------------------------------->

	public List<UserResponceDTO> getAllUsers() {

		List<UserEntity> userEntities = userDao.getAllUsers();
		return userTranslater.userListEntityToDto(userEntities);
	}

//  <--------------------------------------------------------------------------------------------------->

	public Integer removeUser(String username) throws UsernameNotFoundException {
		Integer deletedUser = userDao.deleteUser(username);

		if (deletedUser == 0) {
		    throw new UsernameNotFoundException("User with username " + username + " not found.");
		}

		return deletedUser;
	}

//  <--------------------------------------------------------------------------------------------------->

	public UserResponceDTO updateUser(String username, UserRequestDTO userRequestDTO) throws Exception {

		UserEntity user = userDao.userByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("username not found..."));

		user.setFirstName(userRequestDTO.getFirstName());
		user.setLastName(userRequestDTO.getLastName());
		user.setPhoneNumber(userRequestDTO.getPhoneNumber());
		user.setEmail(userRequestDTO.getEmail());

		UserEntity updatedUser = userDao.updateUser(user);

		return userTranslater.userEntityToDto(updatedUser);

	}

//  <--------------------------------------------------------------------------------------------------->

}
