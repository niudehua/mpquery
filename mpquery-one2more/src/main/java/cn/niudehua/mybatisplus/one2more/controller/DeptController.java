package cn.niudehua.mybatisplus.one2more.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.niudehua.mybatisplus.one2more.service.IDeptService;

@RestController
@RequestMapping("dept")
public class DeptController {

    private final IDeptService deptService;

    public DeptController(IDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/detail/{deptId}")
    public CommonResult detail(@PathVariable Integer deptId) {
        return CommonResult.success(deptService.getOne(deptId));
    }

    @GetMapping("list")
    public CommonResult list() {
        return CommonResult.success(deptService.getList());
    }

    @GetMapping("page")
    public CommonResult page() {
        return CommonResult.success(deptService.getPage(new Page<>()));
    }
}