package ru.isaev.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    public List<User> findByRole(Roles role);
}
