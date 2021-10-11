package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("shake")
public class Shake {
    private String game;
    private Integer num;
    private Integer joinNum;
    private Date endTime;
    private Date creatTime;
}
