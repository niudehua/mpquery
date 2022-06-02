package cn.niudehua.mybatisplus.one2one.service;

import cn.niudehua.mybatisplus.one2one.domain.User;
import cn.niudehua.mybatisplus.one2one.entity.vo.UserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUserService extends IService<User> {
    /**
     * 查询单个用户信息（一个用户对应一个部门）
     *
     * @param userId userId
     * @return UserVo
     */
    UserVo getOne(Integer userId);

    /**
     * 批量查询用户信息（一个用户对应一个部门）
     *
     * @return List<UserVo>
     */
    List<UserVo> getList();

    /**
     * 分页查询用户信息（一个用户对应一个部门）
     *
     * @param page page
     * @return IPage<UserVo>
     */
    IPage<UserVo> getPage(Page<User> page);
}
