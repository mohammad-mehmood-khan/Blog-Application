package org.mehmood.blogapplicationbackendproject;

import lombok.RequiredArgsConstructor;
import org.mehmood.blogapplicationbackendproject.Repository.RoleRepo;
import org.mehmood.blogapplicationbackendproject.config.AppConstants;
import org.mehmood.blogapplicationbackendproject.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogApplicationBackendProjectApplication implements CommandLineRunner {
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplicationBackendProjectApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) {
        System.out.println(this.passwordEncoder.encode("mehmood2022"));
        try {
            Role role = new Role();
            role.setId(AppConstants.ROLE_ADMIN);
            role.setName("ROLE_ADMIN");
            Role role1 = new Role();
            role1.setId(AppConstants.ROLE_USER);
            role1.setName("ROLE_USER");
            List<Role> roles = List.of(role, role1);
            List<Role> result = this.roleRepo.saveAll(roles);
            result.forEach(r -> {
                System.out.println(r.getName());
            });

        } catch (Exception e) {

        }
    }

}