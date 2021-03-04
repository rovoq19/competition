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

import java.util.List;
import java.util.Optional;
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

//    private Test test;

    public void addTest(User user, String name, String description){
        Test test = new Test();

        test.setName(name);
        test.setDescription(description);
        test.setCreator(user.getId());

        testRepo.save(test);
    }

    public void updateTest(User user, Test test, String name, String description){
//        test = findById(testId).get();
        if(user.getId().equals(test.getCreator())){
            test.setName(name);
            test.setDescription(description);

            testRepo.save(test);
        }
    }

    public List<Test> findAll() {
        return testRepo.findAll();
    }
    public Optional<Test> findById(Long id) {

        return testRepo.findById(id);
    }

//    public Optional<Task> findTask(Long id){
//
////        Set<Task> tasks = test.getTaskSubscribers();
////        System.out.println(tasks);
////        return tasks;
//        return taskRepo.findById(id);
//    }

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

    public void addResult(Test test, Task task, User user, String result){

        Result res = new Result(test.getId(), task.getName(), user.getUsername(), result);

        resultRepo.save(res);
    }

    public List<Result> findResults(Long testId){
        return resultRepo.findByTest(testId);
    }

}
