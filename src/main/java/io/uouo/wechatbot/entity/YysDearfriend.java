package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yys_dearfriend")
public class YysDearfriend {
    /**
     * 微信id
     */
    private String wxid;

    /**
     * 昵称
     */
    private String nickname;

}
