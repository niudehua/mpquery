package cn.niudehua.mybatisplus.more2more.entity.vo;

import java.util.List;

import cn.niudehua.mybatisplus.more2more.domain.Student;
import cn.niudehua.mybatisplus.more2more.entity.bo.SubjectBo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentVo extends Student {
    /**
     * 多门课程
     */
    private List<SubjectBo> subList;

    public StudentVo(Student student) {
        super(student);
    }
}
