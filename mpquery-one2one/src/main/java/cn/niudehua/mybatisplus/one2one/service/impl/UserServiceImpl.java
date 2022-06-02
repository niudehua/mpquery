package cn.niudehua.mybatisplus.one2one.service.impl;

import cn.niudehua.mybatisplus.one2one.domain.User;
import cn.niudehua.mybatisplus.one2one.entity.vo.UserVo;
import cn.niudehua.mybatisplus.one2one.mapper.UserMapper;
import cn.niudehua.mybatisplus.one2one.service.IUserService;
import cn.niudehua.mybatisplus.one2one.util.EntityConvertUtils;
import cn.niudehua.mybatisplus.one2one.domain.Dept;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final DeptServiceImpl deptService;

    public UserServiceImpl(DeptServiceImpl deptService) {
        this.deptService = deptService;
    }

    /**
     * 查询单个用户信息（一个用户对应一个部门）
     */
    @Override
    public UserVo getOne(Integer userId) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).eq(User::getUserId, userId);
        UserVo userVo = EntityConvertUtils.toObj(getOne(wrapper), UserVo::new);

        // 从其它表查询信息再封装到Vo
        Optional.ofNullable(userVo).ifPresent(this::addDeptNameInfo);
        return userVo;
    }

    /**
     * 批量查询用户信息（一个用户对应一个部门）
     */
    @Override
    public List<UserVo> getList() {
        // 先查询用户信息（表现形式为列表）
        List<User> user = list(Wrappers.emptyWrapper());
        List<UserVo> userVos = user.stream().map(UserVo::new).collect(toList());
        // 此步骤可以有多个
        addDeptNameInfo(userVos);
        return userVos;
    }

    /**
     * 分页查询用户信息（一个用户对应一个部门）
     */
    @Override
    public IPage<UserVo> getPage(Page<User> page) {
        // 先查询用户信息
        IPage<User> xUserPage = page(page, Wrappers.emptyWrapper());
        // 初始化Vo
        IPage<UserVo> userVoPage = xUserPage.convert(UserVo::new);
        if (userVoPage.getRecords().size() > 0) {
            addDeptNameInfo(userVoPage);
        }
        return userVoPage;
    }

    /**
     * 补充部门名称信息
     */
    private void addDeptNameInfo(UserVo userVo) {
        LambdaQueryWrapper<Dept> wrapper = Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptId, userVo.getDeptId());
        Dept dept = deptService.getOne(wrapper);
        Optional.ofNullable(dept).ifPresent(e -> userVo.setDeptName(e.getDeptName()));
    }

    private void addDeptNameInfo(IPage<UserVo> userVoPage) {
        // 提取用户userId，方便批量查询
        Set<Integer> deptIds = userVoPage.getRecords().stream().map(User::getDeptId).collect(toSet());
        // 根据deptId查询deptName
        List<Dept> dept = deptService.list(Wrappers.lambdaQuery(Dept.class).in(Dept::getDeptId, deptIds));
        // 构造映射关系，方便匹配deptId与deptName
        Map<Integer, String> hashMap = dept.stream().collect(toMap(Dept::getDeptId, Dept::getDeptName));
        // 将查询补充的信息添加到Vo中
        userVoPage.convert(e -> e.setDeptName(hashMap.get(e.getDeptId())));
    }

    private void addDeptNameInfo(List<UserVo> userVos) {
        // 提取用户userId，方便批量查询
        Set<Integer> deptIds = userVos.stream().map(User::getDeptId).collect(toSet());
        EntityConvertUtils.toMap(deptService.listByIds(deptIds), Dept::getDeptId, Dept::getDeptName);
        Map<Integer, String> hashMap = EntityConvertUtils.toMap(deptService.listByIds(deptIds), Dept::getDeptId, Dept::getDeptName);
        // 封装Vo，并添加到集合中(关键内容)
        userVos.forEach(e -> e.setDeptName(hashMap.get(e.getDeptId())));
    }
}
