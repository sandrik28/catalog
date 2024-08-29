package ru.isaev.service.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isaev.domain.Users.User;
import ru.isaev.repo.UserRepo;
import ru.isaev.service.Utilities.Exceptions.UserNotFoundException;

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
