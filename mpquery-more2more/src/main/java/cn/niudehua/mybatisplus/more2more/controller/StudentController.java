package cn.niudehua.mybatisplus.more2more.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.niudehua.mybatisplus.more2more.service.IStudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/detail/{stuId}")
    public CommonResult detail(@PathVariable("stuId") Integer id) {
        return CommonResult.success(studentService.getStudent(id));
    }

    @GetMapping("/list")
    public CommonResult list() {
        return CommonResult.success(studentService.getStudentList());
    }

    @GetMapping("/page")
    public CommonResult page() {
        return CommonResult.success(studentService.getStudentPage(new Page<>()));
    }
}
