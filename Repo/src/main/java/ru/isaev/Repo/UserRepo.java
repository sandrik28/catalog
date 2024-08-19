package ru.isaev.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Domain.Users.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
