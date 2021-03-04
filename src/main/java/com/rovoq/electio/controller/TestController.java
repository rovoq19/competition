package com.rovoq.electio.controller;

import com.rovoq.electio.domain.Role;
import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.Test;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.service.TestService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public String testAdd(@AuthenticationPrincipal User user, @RequestParam String name,
                          @RequestParam String description) {

        testService.addTest(user, name, description);

        return "redirect:/test/list";
    }

    @GetMapping("list")
    public String testList(Model model) {
        model.addAttribute("tests", testService.findAll());
        return "testList";
    }

    @GetMapping("{testId}")
    public String getTest(@AuthenticationPrincipal User user, @PathVariable("testId") Test test, Model model) {
//        Test tst = testService.findById(testId).get();

        model.addAttribute("test",test);
        model.addAttribute("user", user);
        model.addAttribute("tasks", test.getTaskSubscribers());

        model.addAttribute("results", testService.findResults(test.getId()));
//        System.out.println(testService.findResults(test.getId()));

        return "test";
    }

    @PostMapping("{testId}")
    public String getTestResult(@AuthenticationPrincipal User user, @PathVariable("testId") Test test,
                                @RequestParam String taskID, @RequestParam String taskCode, Model model) {

//        System.out.println(taskID);
//        System.out.println(taskCode);

        return "redirect:/test/" + test.getId();
    }

//    @GetMapping("compile")
//    public String compile() {
//        return "codeCompiling";
//    }

    @GetMapping("{testId}/subscribe")
    public String subscribeTest(@AuthenticationPrincipal User user, @PathVariable("testId") Test test) {
//        Test tst = testService.findById(testId).get();
////        System.out.println(tst);
        testService.subscribe(user, test);

        return "redirect:/test/" + test.getId();
    }

    @GetMapping("{testId}/edit")
    public String getEditTest(Model model, @PathVariable("testId") Test test) {
        model.addAttribute("name", test.getName());
        model.addAttribute("description", test.getDescription());
        model.addAttribute("testId", test.getId());
        return "editTest";
    }

    @PostMapping("{testId}/edit")
    public String editTest(@AuthenticationPrincipal User user, @PathVariable("testId") Test test, @RequestParam String name,
                           @RequestParam String description) {

        testService.updateTest(user, test, name, description);

        return "redirect:/test/" + test.getId();
    }

    @GetMapping("{testId}/edit/addtask")
    public String getEditTask() {
        return "editTask";
    }

    @PostMapping("{testId}/edit/addtask")
    public String addTask(@AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String testText,
            @RequestParam String inputTask,
            @RequestParam String answer,
            @PathVariable("testId") Test test) {
        Task task = new Task(name, testText, inputTask, answer);
        testService.addTask(task, test);

        return "redirect:/test/" + test.getId() + "/edit";
    }

}
