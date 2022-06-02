package cn.niudehua.mybatisplus.more2more.service;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.niudehua.mybatisplus.more2more.domain.StuSubRelation;

public interface IStuSubRelationService extends IService<StuSubRelation> {

    /**
     * 查询学生与科目的关联关系
     * 
     * @param  id id
     * @return    StuSubRelation
     */
    @Override
    default StuSubRelation getById(Serializable id) {
        return IService.super.getById(id);
    }

}
