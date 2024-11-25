package in.rtms.main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.RoleEnum;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long>{
	Optional<RoleEntity> findByRoleName(RoleEnum name);
}
