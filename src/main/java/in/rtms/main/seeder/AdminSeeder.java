package in.rtms.main.seeder;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import in.rtms.main.dto.UserRequestDTO;
import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.RoleEnum;
import in.rtms.main.entities.UserEntity;
import in.rtms.main.repositories.RoleRepo;
import in.rtms.main.repositories.UserRepo;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	public AdminSeeder(UserRepo userRepo, RoleRepo roleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.createSuperAdmin();

	}

	private void createSuperAdmin() {
		UserRequestDTO userDto = new UserRequestDTO();
		userDto.setUsername("hd");
		userDto.setPassword("123");

		Optional<RoleEntity> optionalRole = roleRepo.findByRoleName(RoleEnum.SUPER_ADMIN);
		Optional<UserEntity> optionalUser = userRepo.getByusername(userDto.getUsername());
		
		if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }
		
		UserEntity user = UserEntity.builder()
				.username(userDto.getUsername())
				.password(userDto.getPassword())
				.role(optionalRole.get())
				.build();
		
		userRepo.save(user);

	}

}
