package edu.miu.sujan.cs545lab.aspect;

import edu.miu.sujan.cs545lab.domain.Exception;
import edu.miu.sujan.cs545lab.domain.Logger;
import edu.miu.sujan.cs545lab.domain.User;
import edu.miu.sujan.cs545lab.repository.ExceptionRepository;
import edu.miu.sujan.cs545lab.repository.LoggerRepository;
import edu.miu.sujan.cs545lab.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
public class LoggerAspect {

  private LoggerRepository loggerRepository;
  private ExceptionRepository exceptionRepository;
  private UserRepository userRepository;

  @Autowired
  public void setLoggerRepository(LoggerRepository loggerRepository) {
    this.loggerRepository = loggerRepository;
  }

  @Autowired
  public void setExceptionRepository(ExceptionRepository exceptionRepository) {
    this.exceptionRepository = exceptionRepository;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @After("execution(* edu.miu.sujan.cs545lab.controller.*.*(..))")
  public void logAllOperations(JoinPoint joinPoint) {
    Logger logger = new Logger();
    logger.setTransactionId(ThreadLocalRandom.current().nextLong(10000));
    logger.setOperation(joinPoint.getSignature().getName());
    logger.setDateTime(LocalDateTime.now());
    logger.setUser(getUser());
    loggerRepository.save(logger);
  }

  @Pointcut("@annotation(edu.miu.sujan.cs545lab.aspect.annotation.ExecutionTime)")
  public void executionTimeAnnotation() {}

  @Around("executionTimeAnnotation()")
  public Object calculateTimeTaken(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    LocalDateTime startTime = LocalDateTime.now();
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      LocalDateTime endTime = LocalDateTime.now();
      System.out.println(
          "Time taken to complete the method : "
              + Duration.between(startTime, endTime).getNano()
              + " nano seconds");
    }
  }

  @AfterThrowing(
      pointcut = "execution(* edu.miu.sujan.cs545lab.service.*.*(..))",
      throwing = "exception")
  public void logThrowing(JoinPoint joinPoint, java.lang.Exception exception) {
    Exception ex = new Exception();
    ex.setTransactionId(ThreadLocalRandom.current().nextLong(10000));
    ex.setOperation(joinPoint.getSignature().getName());
    ex.setDateTime(LocalDateTime.now());
    ex.setUser(getUser());
    ex.setException(exception.getMessage());
    exceptionRepository.save(ex);
  }

  private User getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = "";
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return userRepository.findByEmail(username);
  }
}
