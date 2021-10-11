package io.uouo.wechatbot.entity;

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
    private Integer carId;
    private String wxid;
    private Integer nickname;
    private Date creatTime;
}
