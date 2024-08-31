package ru.isaev.repo_made_by_isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {
    public List<User> findByRole(Roles role);
    public Optional<User> findByEmail(String email);
}
