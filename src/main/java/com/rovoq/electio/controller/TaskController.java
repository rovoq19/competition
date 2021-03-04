package com.rovoq.electio.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.Test;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.service.TestService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TestService testService;

    @GetMapping("{taskId}/{output}")
    public void getTask(@PathVariable("taskId") Task task, @PathVariable("output") String output, @AuthenticationPrincipal User user) {

        if (task.getAnswer().equals(output)){
            testService.addResult(task.getTestSubscriptions().stream().findFirst().get(), task, user, "Выполнено");
//            System.out.println(true);
//            System.out.println(task.getTestSubscriptions().stream().findFirst().get().getId());
        }

//        Test tst = testService.findById(testId).get();

//        System.out.println(task);
//        System.out.println(output);

//        model.addAttribute("test",test);
//        model.addAttribute("user", user);
//        model.addAttribute("tasks", test.getTaskSubscribers());
//        return "test";
    }

}
