package cn.niudehua.mybatisplus.one2more.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.niudehua.mybatisplus.one2more.domain.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
