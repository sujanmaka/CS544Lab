package edu.miu.sujan.cs545lab.domain;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Exception extends Logger {
    private String exception;
}
