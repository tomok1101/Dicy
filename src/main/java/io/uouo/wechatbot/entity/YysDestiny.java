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
@TableName("yys_destiny")
public class YysDestiny {
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
     * 运势
     */
    private String destiny;

    /**
     * 宜1
     */
    private String rise1;

    /**
     * 宜2
     */
    private String rise2;

    /**
     * 宜3
     */
    private String rise3;

    /**
     * 忌1
     */
    private String fall1;

    /**
     * 忌2
     */
    private String fall2;

    /**
     * 忌3
     */
    private String fall3;

    /**
     * 忌3
     */
    private String game;

}
