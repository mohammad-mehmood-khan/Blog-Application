package org.mehmood.blogapplicationbackendproject.Repository;


import org.mehmood.blogapplicationbackendproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}
