package uz.sudev.communicationcompany.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.Role;
import uz.sudev.communicationcompany.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Page<User>> findUsersByRolesNotLike(Set<Role> roles);
}
