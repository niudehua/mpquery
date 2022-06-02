package cn.niudehua.mybatisplus.one2more.entity.vo;

import java.util.List;

import cn.niudehua.mybatisplus.one2more.domain.Dept;
import cn.niudehua.mybatisplus.one2more.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class DeptVo extends Dept {

    private List<User> users;

    public DeptVo(Dept dept) {
        super(dept);
    }
}
