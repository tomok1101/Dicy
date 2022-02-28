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
@TableName("spellcasting_log")
public class SpellcastingLog {
    /**
     * 日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 法术编号
     */
    private Integer spellId;

    /**
     * 坏人id
     */
    private String badmanWxid;

    /**
     * 受害人id
     */
    private String poormanWxid;

    /**
     * 案发时间
     */
    private Date createTime;
}
