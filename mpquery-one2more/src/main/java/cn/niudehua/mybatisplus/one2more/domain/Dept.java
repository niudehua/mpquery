package cn.niudehua.mybatisplus.one2more.domain;

import java.util.Optional;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("tb_dept")
public class Dept extends Model<Dept> {

    @TableId
    private Integer deptId;
    private String deptName;

    public Dept(Dept dept) {
        Optional.ofNullable(dept).ifPresent(e -> {
            this.deptId = e.getDeptId();
            this.deptName = e.getDeptName();
        });
    }
}
