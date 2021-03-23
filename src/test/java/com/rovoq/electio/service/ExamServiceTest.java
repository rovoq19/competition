package com.rovoq.electio.service;

import com.rovoq.electio.domain.Exam;
import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.repos.ExamRepo;
import com.rovoq.electio.repos.TaskRepo;
import com.rovoq.electio.repos.UserRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamServiceTest {
    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private ExamRepo examRepo;

    @MockBean
    private TaskRepo taskRepo;

    @Test
    public void addExam() {
        Exam exam = new Exam();
        User user = new User();
        java.sql.Timestamp date1 = new java.sql.Timestamp(2);
        java.sql.Timestamp date2 = new java.sql.Timestamp(2);

       boolean isExamCreated = examService.addExam(user, "exam1","examDescription1", date1, date2);

        Assert.assertTrue(isExamCreated);
        Mockito.verify(examRepo, Mockito.times(0)).save(exam);
    }

    @Test
    public void addTask() {
        Exam exam = new Exam();
        Task currentTask = new Task();

        boolean isTaskCreated = examService.addTask(currentTask, exam);

        Assert.assertTrue(isTaskCreated);
        Mockito.verify(taskRepo, Mockito.times(1)).save(currentTask);
        Mockito.verify(examRepo, Mockito.times(1)).save(exam);
    }

}