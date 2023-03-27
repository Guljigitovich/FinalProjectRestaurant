package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.dto.response.UserResponse;
import project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select new project.dto.response.UserResponse " +
            "(u.id,u.firstName,u.lastName,u.dateOfBirth,u.phoneNumber,u.experience) from User u where u.accepted = true ")
    List<UserResponse> getAllUsers();

    Optional<UserResponse> getUserById(Long userId);
    @Query("select new project.dto.response.UserResponse (u.id," +
            "u.firstName,u.lastName,u.dateOfBirth,u.phoneNumber,u.experience) " +
            "from User u where u.accepted = false ")
    List<UserResponse> getAllApplication();

    boolean existsByEmail(String email);
    Optional<User> findUsersByEmail(String email);
}