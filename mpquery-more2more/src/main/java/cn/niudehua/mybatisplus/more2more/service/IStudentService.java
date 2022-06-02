package cn.niudehua.mybatisplus.more2more.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.niudehua.mybatisplus.more2more.domain.Student;
import cn.niudehua.mybatisplus.more2more.entity.vo.StudentVo;

public interface IStudentService extends IService<Student> {

    /**
     * 查询单个学生
     *
     * @param  stuId stuId
     * @return       StudentVo
     */
    StudentVo getStudent(Integer stuId);

    /**
     * 查询多个学生
     *
     * @return List<StudentVo>
     */
    List<StudentVo> getStudentList();

    /**
     * 分页查询学生的信息
     *
     * @param  page page
     * @return      IPage<StudentVo>
     */
    IPage<StudentVo> getStudentPage(IPage<Student> page);
}
