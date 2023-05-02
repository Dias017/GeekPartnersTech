package kz.tech.demo.repository;

import kz.tech.demo.model.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<CodeEntity, Integer> {
    Optional<CodeEntity> findTopByOrderByCodeDesc();
}
