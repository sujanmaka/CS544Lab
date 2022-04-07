package edu.miu.sujan.cs545lab.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Logger {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long transactionId;
  private LocalDateTime dateTime;
  @OneToOne
  private User user;
  private String operation;
}
