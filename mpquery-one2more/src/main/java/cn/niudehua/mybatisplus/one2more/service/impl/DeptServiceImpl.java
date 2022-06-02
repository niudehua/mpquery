package cn.niudehua.mybatisplus.one2more.service.impl;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.niudehua.mybatisplus.one2more.domain.Dept;
import cn.niudehua.mybatisplus.one2more.domain.User;
import cn.niudehua.mybatisplus.one2more.entity.vo.DeptVo;
import cn.niudehua.mybatisplus.one2more.mapper.DeptMapper;
import cn.niudehua.mybatisplus.one2more.mapper.UserMapper;
import cn.niudehua.mybatisplus.one2more.service.IDeptService;
import cn.niudehua.mybatisplus.one2more.service.IUserService;
import cn.niudehua.mybatisplus.one2more.util.EntityConvertUtils;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    private final UserMapper userMapper;

    private final IUserService userService;

    public DeptServiceImpl(UserMapper userMapper, IUserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /**
     * 查询单个部门（其中一个部门有多个用户）
     */
    @Override
    public DeptVo getOne(Integer deptId) {
        // 查询部门基础信息
        LambdaQueryWrapper<Dept> wrapper = Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptId, deptId);
        DeptVo deptVo = EntityConvertUtils.toObj(getOne(wrapper), DeptVo::new);
        ofNullable(deptVo).ifPresent(this::addUserInfo);
        return deptVo;
    }

    /**
     * 查询多个部门（其中一个部门有多个用户）
     */
    @Override
    public List<DeptVo> getList() {
        // 按条件查询部门信息
        List<DeptVo> deptVos = EntityConvertUtils.toList(list(Wrappers.emptyWrapper()), DeptVo::new);
        if (deptVos.size() > 0) {
            addUserInfo(deptVos);
        }
        return deptVos;
    }

    /**
     * 分页查询部门的信息（其中一个部门有多个用户）
     */
    @Override
    public IPage<DeptVo> getPage(Page<Dept> page) {
        // 按条件查询部门信息
        IPage<DeptVo> deptVoPage = EntityConvertUtils.toPage(page(page, Wrappers.emptyWrapper()), DeptVo::new);
        if (deptVoPage.getRecords().size() > 0) {
            addUserInfo(deptVoPage);
        }
        return deptVoPage;
    }

    private void addUserInfo(DeptVo deptVo) {
        // 根据部门deptId查询用户列表
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).eq(User::getDeptId, deptVo.getDeptId());
        List<User> users = userMapper.selectList(wrapper);
        deptVo.setUsers(users);
    }

    private void addUserInfo(List<DeptVo> deptVos) {
        // 准备deptId方便批量查询用户信息
        Set<Integer> deptIds = deptVos.stream().map(Dept::getDeptId).collect(toSet());
        // 用批量deptId查询用户信息
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).in(User::getDeptId, deptIds);
        Map<Integer, List<User>> hashMap = userService.list(wrapper).stream().collect(groupingBy(User::getDeptId));
        // 合并结果，构造Vo，添加集合列表
        deptVos.forEach(e -> e.setUsers(hashMap.get(e.getDeptId())));
    }

    private void addUserInfo(IPage<DeptVo> deptVoPage) {
        // 准备deptId方便批量查询用户信息
        Set<Integer> deptIds = EntityConvertUtils.collectList(deptVoPage.getRecords(), Dept::getDeptId, toSet());
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).in(User::getDeptId, deptIds);
        Map<Integer, List<User>> hashMap = userService.list(wrapper).stream().collect(groupingBy(User::getDeptId));
        // 合并结果，构造Vo，添加集合列表
        deptVoPage.convert(e -> e.setUsers(hashMap.get(e.getDeptId())));
    }
}
