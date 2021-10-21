package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("shakeman")
public class Shakeman {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer carId;
    private String wxid;
    private Integer nickname;
    private Date creatTime;
}
