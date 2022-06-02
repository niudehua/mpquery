package cn.niudehua.mybatisplus.one2more.entity.vo;

import cn.niudehua.mybatisplus.one2more.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserVo extends User {

    private String deptName;

    public UserVo(User user) {
        super(user);
    }
}