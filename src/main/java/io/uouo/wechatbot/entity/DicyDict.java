package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dicy_dict")
public class DicyDict {
    /**
     * id
     */
    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 编码
     */
    private String code;

    /**
     * 事件值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;
}
