package edu.miu.sujan.cs545lab.domain;

import edu.miu.sujan.cs545lab.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  public Role(RoleType roleType) {
    this.roleType = roleType;
  }
}
