package cn.niudehua.mybatisplus.more2more.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 学生表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "tb_student")
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    /**
     * 学号ID
     */
    @TableId(type = IdType.AUTO)
    private Integer stuId;
    /**
     * 姓名
     */
    private String stuName;

    public Student(Student student) {
        this.stuId = student.stuId;
        this.stuName = student.stuName;
    }
}
