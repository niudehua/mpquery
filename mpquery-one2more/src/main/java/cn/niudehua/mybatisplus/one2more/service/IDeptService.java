package cn.niudehua.mybatisplus.one2more.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.niudehua.mybatisplus.one2more.domain.Dept;
import cn.niudehua.mybatisplus.one2more.entity.vo.DeptVo;

public interface IDeptService extends IService<Dept> {
    /**
     * 查询单个部门（其中一个部门有多个用户）
     *
     * @param  deptId deptId
     * @return        DeptVo
     */
    DeptVo getOne(Integer deptId);

    /**
     * 查询多个部门（其中一个部门有多个用户）
     *
     * @return List<DeptVo>
     */
    List<DeptVo> getList();

    /**
     * 分页查询部门的信息（其中一个部门有多个用户）
     *
     * @param  page page
     * @return      IPage<DeptVo>
     */
    IPage<DeptVo> getPage(Page<Dept> page);
}
