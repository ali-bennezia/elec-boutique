package fr.alib.elec_boutique.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.elec_boutique.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByUsernameOrEmail(String username, String email);
}
