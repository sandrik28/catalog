package ru.isaev.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isaev.domain.users.User;
import ru.isaev.repo.IUserRepo;
import ru.isaev.service.utilities.Exceptions.UserNotFoundException;

@Service
public class MyUserDetailsService implements UserDetailsService  {
    @Autowired
    private IUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("Not found user with email = " + username));
        
        return new MyUserDetails(user);
    }
}
