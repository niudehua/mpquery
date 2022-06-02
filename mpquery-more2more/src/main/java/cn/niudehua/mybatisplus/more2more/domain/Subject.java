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
 * 课程表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "tb_subject")
public class Subject extends Model<Subject> {

    private static final long serialVersionUID = 1L;
    /**
     * 课程ID
     */
    @TableId(type = IdType.AUTO)
    private Integer subId;
    /**
     * 课程名
     */
    private String subName;

    public Subject(Subject subject) {
        this.subId = subject.subId;
        this.subName = subject.subName;
    }
}
