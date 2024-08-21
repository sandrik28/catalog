package ru.isaev.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public List<User> findByRole(Roles role);

    public Optional<User> findByEmail(String email);
}
