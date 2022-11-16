package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yys_lottery")
public class YysLottery {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 日期
     */
    private Date date;

    /**
     * 微信id
     */
    private String wxid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 狗海报
     */
    private Integer bingo;

}
