package com.rovoq.electio.service;

import com.rovoq.electio.domain.Result;
import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.Exam;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.repos.ResultRepo;
import com.rovoq.electio.repos.TaskRepo;
import com.rovoq.electio.repos.ExamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ExamService {
    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultRepo resultRepo;

    public boolean addExam(User user, String name, String description, Timestamp start, Timestamp stop){
        Exam exam = new Exam();

        exam.setName(name);
        exam.setDescription(description);
        exam.setCreator(user.getId());
        exam.setStart(start);
        exam.setStop(stop);
        exam.setCreationDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));

        examRepo.save(exam);
        return true;
    }

    public void updateExam(User user, Exam exam, String name, String description, Timestamp start, Timestamp stop){
        if(user.getId().equals(exam.getCreator())){
            exam.setName(name);
            exam.setDescription(description);
            exam.setStart(start);
            exam.setStop(stop);

            examRepo.save(exam);
        }
    }

    public List<Exam> findAll() {
        return examRepo.findAll();
    }

    public void subscribe(User currentUser, Exam exam) {
        Set<User> subscribers = exam.getSubscribers();

        if (!subscribers.contains(currentUser)){
            subscribers.add(currentUser);
        }

        examRepo.save(exam);
    }

    public boolean addTask(Task currentTask, Exam exam){
        taskRepo.save(currentTask);

        Set<Task> task_subscribers = exam.getTaskSubscribers();

        task_subscribers.add(currentTask);

        examRepo.save(exam);
        return true;
    }

    public boolean findByUserName(Long examId, String userName){
        List<Result> results = resultRepo.findAll();
        for(Result result:results){
            if (result.getResult().equals(examId) & result.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public void addResult(Exam exam, Task task, User user, String result){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(!findByUserName(exam.getId(),user.getUsername())){
            if(timestamp.getTime() >= exam.getStart().getTime() & timestamp.getTime() <= exam.getStop().getTime()){
                Result res = new Result(exam.getId(), task.getName(), user.getUsername(), result);
                resultRepo.save(res);
            }
        }
    }

    public List<Result> findResults(Long examId){
        return resultRepo.findByTest(examId);
    }

}
