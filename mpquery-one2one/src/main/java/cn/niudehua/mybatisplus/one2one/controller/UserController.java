package cn.niudehua.mybatisplus.one2one.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.niudehua.mybatisplus.one2one.service.IUserService;

@RestController
@RequestMapping("user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/detail/{userId}")
    public CommonResult detail(@PathVariable Integer userId) {
        return CommonResult.success(userService.getOne(userId));
    }

    @GetMapping("/list")
    public CommonResult list() {
        return CommonResult.success(userService.getList());
    }

    @GetMapping("/page")
    public CommonResult page() {
        return CommonResult.success(userService.getPage(new Page<>()));
    }

}
