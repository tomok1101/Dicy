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
@TableName("yys_fish_daily")
public class YysFishDaily {
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
     * 鱼级
     */
    private Integer fishLv;

    /**
     * 附加级
     */
    private Integer bonusLv;

    /**
     * 除你fish
     */
    private Integer expellifish;

    /**
     * 阿瓦达香蕉
     */
    private Integer avadabanana;

}
