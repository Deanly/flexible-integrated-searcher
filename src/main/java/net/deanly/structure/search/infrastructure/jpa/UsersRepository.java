package net.deanly.structure.search.infrastructure.jpa;

import net.deanly.structure.search.infrastructure.jpa.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsersRepository extends JpaRepository<UserDTO, Long>, JpaSpecificationExecutor<UserDTO> {
}
