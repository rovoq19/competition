package com.rovoq.electio.controller;

import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
        }
        if (!task.getAnswer().equals(output)){
            testService.addResult(task.getTestSubscriptions().stream().findFirst().get(), task, user, "Не выполнено");
        }
    }

}
