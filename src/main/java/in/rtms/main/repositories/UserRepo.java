package in.rtms.main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.rtms.main.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>{
	
//	method to check if username already exist in db or not.
	public boolean existsByUsername(String username);
	
//	method to find user by username.
	public Optional<UserEntity> getByusername(String username);
	
//	method to delete user by username in user
	public Integer deleteByUsername(String username);

}
