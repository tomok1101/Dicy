package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("suggestion")
public class Suggestion {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 微信id
     */
    private String wxid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 意见
     */
    private String suggestion;

}
