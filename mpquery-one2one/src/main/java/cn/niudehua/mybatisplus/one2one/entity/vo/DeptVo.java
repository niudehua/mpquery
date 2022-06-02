package cn.niudehua.mybatisplus.one2one.entity.vo;

import cn.niudehua.mybatisplus.one2one.domain.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import cn.niudehua.mybatisplus.one2one.domain.User;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class DeptVo extends Dept {

    private List<User> users;
    
    public DeptVo(Dept dept) {
        super(dept);
    }
}
