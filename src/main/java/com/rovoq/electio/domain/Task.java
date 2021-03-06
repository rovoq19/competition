package com.rovoq.electio.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String examText;
    private String inputTask;
    private String answer;

    @ManyToMany
    @JoinTable(
            name = "task_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private Set<Exam> ExamSubscriptions = new HashSet<>();

    public Task() {}

    public Task(String name, String examText, String inputTask, String answer) {
        this.name = name;
        this.examText = examText;
        this.inputTask = inputTask;
        this.answer = answer;
    }
}
