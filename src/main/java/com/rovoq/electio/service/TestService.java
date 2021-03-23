package com.rovoq.electio.service;

import com.rovoq.electio.domain.Result;
import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.Test;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.repos.ResultRepo;
import com.rovoq.electio.repos.TaskRepo;
import com.rovoq.electio.repos.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TestService {
    @Autowired
    private TestRepo testRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultRepo resultRepo;

    public void addTest(User user, String name, String description, Timestamp start, Timestamp stop){
        Test test = new Test();

        test.setName(name);
        test.setDescription(description);
        test.setCreator(user.getId());
        test.setStart(start);
        test.setStop(stop);
        test.setCreationDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));

        testRepo.save(test);
    }

    public void updateTest(User user, Test test, String name, String description, Timestamp start, Timestamp stop){
        if(user.getId().equals(test.getCreator())){
            test.setName(name);
            test.setDescription(description);
            test.setStart(start);
            test.setStop(stop);

            testRepo.save(test);
        }
    }

    public List<Test> findAll() {
        return testRepo.findAll();
    }

    public void subscribe(User currentUser, Test test) {
        Set<User> subscribers = test.getSubscribers();

        if (!subscribers.contains(currentUser)){
            subscribers.add(currentUser);
        }

        testRepo.save(test);
    }

    public void addTask(Task currentTask, Test test){
        taskRepo.save(currentTask);

        Set<Task> task_subscribers = test.getTaskSubscribers();

        task_subscribers.add(currentTask);

        testRepo.save(test);
    }

    public boolean findByUserName(Long testId, String userName){
        List<Result> results = resultRepo.findAll();
        for(Result result:results){
            if (result.getTest().equals(testId) & result.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public void addResult(Test test, Task task, User user, String result){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(!findByUserName(test.getId(),user.getUsername())){
            if(timestamp.getTime() >= test.getStart().getTime() & timestamp.getTime() <= test.getStop().getTime()){
                Result res = new Result(test.getId(), task.getName(), user.getUsername(), result);
                resultRepo.save(res);
            }
        }
    }

    public List<Result> findResults(Long testId){
        return resultRepo.findByTest(testId);
    }

}
