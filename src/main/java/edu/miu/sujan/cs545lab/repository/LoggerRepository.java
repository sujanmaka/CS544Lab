package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<Logger, Long> {}
