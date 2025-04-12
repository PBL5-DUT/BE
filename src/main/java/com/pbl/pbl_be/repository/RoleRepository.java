package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Sửa lại kiểu trả về từ Optional<Object> thành Optional<Role>
    Optional<Role> findByRoleName(Role.RoleName roleName);
}
