package org.mehmood.blogapplicationbackendproject;

import org.junit.jupiter.api.Test;
import org.mehmood.blogapplicationbackendproject.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationBackendProjectApplicationTests {
    @Autowired
    private UserRepo userRepo;
    @Test
    void contextLoads() {
    }
    @Test
    public void repoTest(){
        String className=this.userRepo.getClass().getName();
        String pakageName=this.getClass().getPackageName();
        System.out.println(className);
        System.out.println(pakageName);
    }

}
