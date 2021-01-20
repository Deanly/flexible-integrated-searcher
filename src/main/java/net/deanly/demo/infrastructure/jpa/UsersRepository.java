package net.deanly.demo.infrastructure.jpa;

import net.deanly.demo.infrastructure.jpa.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsersRepository extends JpaRepository<UserDTO, Long>, JpaSpecificationExecutor<UserDTO> {
}
