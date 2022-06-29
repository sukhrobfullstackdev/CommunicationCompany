package uz.sudev.communicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.Role;
import uz.sudev.communicationcompany.entity.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName roleName);
}
