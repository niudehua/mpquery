package cn.niudehua.mybatisplus.more2more.entity.bo;

import cn.niudehua.mybatisplus.more2more.domain.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectBo extends Subject {
    /**
     * 分数
     */
    private Integer score;

    public SubjectBo(Subject subject) {
        super(subject);
    }
}
