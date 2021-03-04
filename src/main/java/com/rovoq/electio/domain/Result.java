package com.rovoq.electio.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "results")
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long test;
    private String task;
    private String userName;
    private String result;

    public Result(Long test, String task, String userName, String result) {
        this.test = test;
        this.task = task;
        this.userName = userName;
        this.result = result;
    }

    public Result() {}
}
