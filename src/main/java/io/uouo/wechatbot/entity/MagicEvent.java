package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("magic_event")
public class MagicEvent {
    /**
     * id
     */
    private Integer id;

    /**
     * type
     */
    private String type;

    /**
     * 马猴事件
     */
    private String fishEvent;

    /**
     * 伤害上限
     */
    private Integer max;

    /**
     * 伤害下限
     */
    private Integer min;
}
