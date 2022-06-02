package cn.niudehua.mybatisplus.one2one.domain;

import java.util.Optional;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author explore
 */
@Data
@NoArgsConstructor
@TableName("tb_user")
public class User extends Model<User> {

    @TableId
    private Integer userId;
    private String userName;
    private Integer deptId;

    public User(User user) {
        Optional.ofNullable(user).ifPresent(e -> {
            this.userId = user.getUserId();
            this.userName = user.getUserName();
            this.deptId = user.getDeptId();
        });
    }
}
