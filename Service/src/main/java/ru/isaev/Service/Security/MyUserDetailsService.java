package ru.isaev.Service.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isaev.DAO.IOwnerDAO;
import ru.isaev.Domain.Users.User;
import ru.isaev.Entities.Owners.Owner;
import ru.isaev.Repo.UserRepo;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService  {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findById(Long.valueOf(username));

        return new MyUserDetails(user.get());
    }
}
