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
@TableName("shake")
public class Shake {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String game;
    private Integer num;
    private Integer joinNum;
    private Date endTime;
    private Date creatTime;
}
