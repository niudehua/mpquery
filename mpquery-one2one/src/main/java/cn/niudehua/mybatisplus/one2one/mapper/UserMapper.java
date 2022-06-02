package cn.niudehua.mybatisplus.one2one.mapper;

import cn.niudehua.mybatisplus.one2one.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
