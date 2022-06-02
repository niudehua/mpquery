package cn.niudehua.mybatisplus.one2one.entity.vo;

import cn.niudehua.mybatisplus.one2one.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class UserVo extends User {

    private String deptName;

    public UserVo(User user) {
        super(user);
    }
}