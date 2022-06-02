package cn.niudehua.mybatisplus.one2one.service.impl;

import cn.niudehua.mybatisplus.one2one.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import cn.niudehua.mybatisplus.one2one.domain.Dept;
import cn.niudehua.mybatisplus.one2one.mapper.DeptMapper;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
