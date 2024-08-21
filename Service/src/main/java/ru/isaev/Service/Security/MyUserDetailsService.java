package ru.isaev.Service.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Utilities.Exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService  {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("Not found user with email = " + username));
        
        return new MyUserDetails(user);
    }
}
