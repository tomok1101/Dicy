package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("expellifish_event")
public class ExpellifishEvent {
    /**
     * id
     */
    private Integer id;

    /**
     * 除你fish事件
     */
    private String fishEvent;

    /**
     * 伤害上限
     */
    private Integer max;
}
