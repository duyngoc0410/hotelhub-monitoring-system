package com.hotelhub.backend.config.Seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PermissionSeeder permissionSeeder;

    private final RoleSeeder roleSeeder;

    private final AdminSeeder adminSeeder;

    @Override
    public void run(String... args){
        permissionSeeder.seed();
        roleSeeder.seed();
        adminSeeder.seed();
    }
}
