package in.rtms.main.seeder;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import in.rtms.main.entities.RoleEntity;
import in.rtms.main.entities.RoleEnum;
import in.rtms.main.repositories.RoleRepo;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private final RoleRepo roleRepo;

    public RoleSeeder(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.TENENT,RoleEnum.OWNER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN };
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
            RoleEnum.TENENT, "Tenent user role",
            RoleEnum.OWNER, "Owner user role",
            RoleEnum.ADMIN, "Administrator role",
            RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );
        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<RoleEntity> optionalRole = roleRepo.findByRoleName(roleName);

            optionalRole.ifPresentOrElse(
                System.out::println, 
                () -> {
                	RoleEntity roleToCreate = new RoleEntity();

                    roleToCreate.setRoleName(roleName);
                    roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                    roleRepo.save(roleToCreate); 
                }
            );
        });
    }
}
