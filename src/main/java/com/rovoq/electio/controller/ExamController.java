package com.rovoq.electio.controller;

import com.rovoq.electio.domain.Task;
import com.rovoq.electio.domain.Exam;
import com.rovoq.electio.domain.User;
import com.rovoq.electio.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    ExamService examService;

    @GetMapping("create")
    public String getExam() {
        return "createExam";
    }

    @PostMapping("create")
    public String examAdd(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Timestamp start,
            @RequestParam Timestamp stop) {

        examService.addExam(user, name, description, start, stop);

        return "redirect:/exam/list";
    }

    @GetMapping("list")
    public String examList(Model model) {
        model.addAttribute("exams", examService.findAll());
        return "examList";
    }

    @GetMapping("{examId}")
    public String getExam(@AuthenticationPrincipal User user, @PathVariable("examId") Exam exam, Model model) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(timestamp.getTime() >= exam.getStart().getTime() & timestamp.getTime() <= exam.getStop().getTime() | exam.getCreator().equals(user.getId())){
            model.addAttribute("exam",exam);
            model.addAttribute("user", user);
            model.addAttribute("tasks", exam.getTaskSubscribers());
            model.addAttribute("results", examService.findResults(exam.getId()));
            return "exam";
        }else{
            model.addAttribute("res","Тест еще не начался или уже закончился");
            return "timeOutExam";
        }
    }

    @PostMapping("{examId}")
    public String getExamResult(@AuthenticationPrincipal User user, @PathVariable("examId") Exam exam,
                                @RequestParam String taskID, @RequestParam String taskCode, Model model) {

        return "redirect:/exam/" + exam.getId();
    }

    @GetMapping("{examId}/subscribe")
    public String subscribeExam(@AuthenticationPrincipal User user, @PathVariable("examId") Exam exam) {
        examService.subscribe(user, exam);

        return "redirect:/exam/" + exam.getId();
    }

    @GetMapping("{examId}/edit")
    public String getEditExam(Model model, @PathVariable("testId") Exam exam) {
        model.addAttribute("name", exam.getName());
        model.addAttribute("description", exam.getDescription());
        model.addAttribute("examId", exam.getId());
        model.addAttribute("start", exam.getStart());
        model.addAttribute("stop", exam.getStop());

        return "editExam";
    }

    @PostMapping("{examId}/edit")
    public String editExam(
            @AuthenticationPrincipal User user,
            @PathVariable("testId") Exam exam,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Timestamp start,
            @RequestParam Timestamp stop) {

        examService.updateExam(user, exam, name, description, start, stop);

        return "redirect:/exam/" + exam.getId();
    }

    @GetMapping("{examId}/edit/addtask")
    public String getEditTask() {
        return "editTask";
    }

    @PostMapping("{examId}/edit/addtask")
    public String addTask(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String examText,
            @RequestParam String inputTask,
            @RequestParam String answer,
            @PathVariable("examId") Exam exam) {

        Task task = new Task(name, examText, inputTask, answer);
        examService.addTask(task, exam);

        return "redirect:/exam/" + exam.getId();
    }

}
