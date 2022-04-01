package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Exception;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionRepository extends JpaRepository<Exception, Long> {
}
