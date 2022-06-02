package cn.niudehua.mybatisplus.more2more.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.niudehua.mybatisplus.more2more.domain.Subject;
import cn.niudehua.mybatisplus.more2more.mapper.SubjectMapper;
import cn.niudehua.mybatisplus.more2more.service.ISubjectService;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

}
