package org.mehmood.blogapplicationbackendproject.security;


import lombok.NonNull;
import org.mehmood.blogapplicationbackendproject.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return this.userRepo.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("user not found with email: " + username));
    }

}
