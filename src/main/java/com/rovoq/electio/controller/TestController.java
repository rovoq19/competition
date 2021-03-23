package com.rovoq.electio.controller;

import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.Test;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.repos.UserRepo;
import com.rovoq.electio.service.TestService;
import com.rovoq.electio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("create")
    public String getTest() {
        return "createTest";
    }

    @PostMapping("create")
    public String testAdd(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Timestamp start,
            @RequestParam Timestamp stop) {

        testService.addTest(user, name, description, start, stop);

        return "redirect:/test/list";
    }

    @GetMapping("list")
    public String testList(Model model) {
        model.addAttribute("tests", testService.findAll());
        return "testList";
    }

    @GetMapping("{testId}")
    public String getTest(@AuthenticationPrincipal User user, @PathVariable("testId") Test test, Model model) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(timestamp.getTime() >= test.getStart().getTime() & timestamp.getTime() <= test.getStop().getTime() | test.getCreator().equals(user.getId())){
            model.addAttribute("test",test);
            model.addAttribute("user", user);
            model.addAttribute("tasks", test.getTaskSubscribers());
            model.addAttribute("results", testService.findResults(test.getId()));
            return "test";
        }else{
            model.addAttribute("res","Тест еще не начался или уже закончился");
            return "timeOutTest";
        }
    }

    @PostMapping("{testId}")
    public String getTestResult(@AuthenticationPrincipal User user, @PathVariable("testId") Test test,
                                @RequestParam String taskID, @RequestParam String taskCode, Model model) {

        return "redirect:/test/" + test.getId();
    }

    @GetMapping("{testId}/subscribe")
    public String subscribeTest(@AuthenticationPrincipal User user, @PathVariable("testId") Test test) {
        testService.subscribe(user, test);

        return "redirect:/test/" + test.getId();
    }

    @GetMapping("{testId}/edit")
    public String getEditTest(Model model, @PathVariable("testId") Test test) {
        model.addAttribute("name", test.getName());
        model.addAttribute("description", test.getDescription());
        model.addAttribute("testId", test.getId());
        model.addAttribute("start", test.getStart());
        model.addAttribute("stop", test.getStop());

        return "editTest";
    }

    @PostMapping("{testId}/edit")
    public String editTest(
            @AuthenticationPrincipal User user,
            @PathVariable("testId") Test test,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Timestamp start,
            @RequestParam Timestamp stop) {

        testService.updateTest(user, test, name, description, start, stop);

        return "redirect:/test/" + test.getId();
    }

    @GetMapping("{testId}/edit/addtask")
    public String getEditTask() {
        return "editTask";
    }

    @PostMapping("{testId}/edit/addtask")
    public String addTask(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String testText,
            @RequestParam String inputTask,
            @RequestParam String answer,
            @PathVariable("testId") Test test) {

        Task task = new Task(name, testText, inputTask, answer);
        testService.addTask(task, test);

        return "redirect:/test/" + test.getId();
    }

}
