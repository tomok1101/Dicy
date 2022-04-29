package io.uouo.wechatbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YysMemberTemp {
    /**
     * 群聊id
     */
    private String room_id;

    /**
     * 地址
     */
    private String address;

    /**
     * 成员
     */
    private List<String> member;

}
