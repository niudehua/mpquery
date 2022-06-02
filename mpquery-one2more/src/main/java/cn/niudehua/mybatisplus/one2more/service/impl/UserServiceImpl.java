package cn.niudehua.mybatisplus.one2more.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.niudehua.mybatisplus.one2more.domain.User;
import cn.niudehua.mybatisplus.one2more.mapper.UserMapper;
import cn.niudehua.mybatisplus.one2more.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
