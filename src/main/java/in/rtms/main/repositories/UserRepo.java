package in.rtms.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.rtms.main.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>{

}
