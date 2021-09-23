package io.uouo.wechatbot.client;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import io.uouo.wechatbot.WechatBotApplication;
import io.uouo.wechatbot.common.WechatBotCommon;
import io.uouo.wechatbot.common.WechatBotConfig;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.domain.WechatReceiveMsg;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * websocket机器人客户端
 *
 * @author: [青衫] 'QSSSYH@QQ.com'
 * @Date: 2021-03-16 18:20
 * @Description: < 描述 >
 */
public class WechatBotClient extends WebSocketClient implements WechatBotCommon {
    private static List<String> nantongList = Arrays.asList(
            "士力架", "歪西", "李雨轩", "柯基",
            "富贵", "游零", "东宝", "汉堡王",
            "丝袜","tom","PMD","AMD");

    /**
     * 描述: 构造方法创建 WechatBotClient对象
     *
     * @param url WebSocket链接地址
     * @return
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-26
     */
    public WechatBotClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    /**
     * 描述: 在websocket连接开启时调用
     *
     * @param serverHandshake
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.err.println("已发送尝试连接到微信客户端请求");
    }

    /**
     * 描述: 方法在接收到消息时调用
     *
     * @param msg
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onMessage(String msg){
        // 由于我的机器人是放在某个小服务器上的, 就将接收数据后的处理交给了另外一个服务器(看群里好多群友也这么干的)所以我这里就加了这几行代码,这根据自己的想法进行自定义

        // 这里也可以不进行转换 直接将微信中接收到的消息交给服务端, 提高效率,但是浪费在网络通信上的资源相对来说就会变多(根据自己需求自信来写没什么特别的)
//        System.out.println("微信中收到了消息:" + msg);

        // 是否开启远程处理消息功能
//        if (WechatBotConfig.wechatMsgServerIsOpen) {
//            // 不等于心跳包
//            WechatReceiveMsg wechatReceiveMsg = JSONObject.parseObject(msg, WechatReceiveMsg.class);
//            if (!WechatBotCommon.HEART_BEAT.equals(wechatReceiveMsg.getType())) {
//                HttpUtil.post(WechatBotConfig.wechatMsgServerUrl, msg);
//            }
//        }

        WechatReceiveMsg wechatReceiveMsg = (WechatReceiveMsg) JSONObject.parseObject(msg, WechatReceiveMsg.class);
        if (!WechatBotCommon.HEART_BEAT.equals(wechatReceiveMsg.getType())) {
            System.out.println("微信中收到了消息:" + msg);
            String rContent = wechatReceiveMsg.getContent();
            rContent = rContent.replace(" ", "");
            //有人@骰娘了
            if (rContent.contains("@骰娘")) {

                //先看看是不是男桐
                if (rContent.contains("男桐")) {
                    Boolean isNt = false;
                    String ntName = "";
                    for (String s :
                            nantongList) {
                        if (rContent.contains(s)){
                            isNt = true;
                            ntName = s;
                            break;
                        }
                    }
                    //在男桐名单
                    if (isNt){
                        String[] split = rContent.split("@骰娘");
                        String text = split[1];
                        //骰子模块
                        if (text.contains("d")) {
                            String[] ds = text.split("d");
                            if (ds.length == 2)
                                try {
                                    Integer i1 = Integer.valueOf(ds[0]);
                                    Integer i2 = Integer.valueOf(ds[1]);
                                    String result = ntName + "男桐指数飙到了...";

                                    for (int i = 0; i < i1.intValue(); i++) {
                                        result = result +  (int)(Math.random() * 10 + i2) + " ";
                                    }
                                    result = result + "!!!";
                                    WechatMsg wechatMsg = new WechatMsg();
                                    wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                                    wechatMsg.setContent(result);
                                    wechatMsg.setType(TXT_MSG);
                                    sendMsgUtil(wechatMsg);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                        }
                    }
                    //不在
                    else {

                        String[] split = rContent.split("@骰娘");
                        String text = split[1];
                        //骰子模块
                        if (text.contains("d")) {
                            String[] ds = text.split("d");
                            if (ds.length == 2)
                                try {
                                    Integer i1 = Integer.valueOf(ds[0]);
                                    Integer i2 = Integer.valueOf(ds[1]);
                                    String result = ntName + "男桐指数为：";

                                    for (int i = 0; i < i1.intValue(); i++) {
                                        result = result + (int) (Math.random() * i2.intValue() + 1.0D) + " ";
                                    }
                                    WechatMsg wechatMsg = new WechatMsg();
                                    wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                                    wechatMsg.setContent(result);
                                    wechatMsg.setType(TXT_MSG);
                                    sendMsgUtil(wechatMsg);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                        }
                    }



                }

                //有没有炒饭元素
                else if (rContent.contains("秋秋")){
                    List<String> exChaofan = Arrays.asList(
                            "史诗", "绝版", "联名", "食堂",
                            "芒果现炒","男桐","群主现炒");
                    List<String> midChaofan = Arrays.asList(
                            "蛋", "拔十圆", "饭", "咖喱",
                            "胡萝卜蛋","咸鸭蛋","虾球鸡肉丁","韩国泡菜","扬州");

                    String chaofan = exChaofan.get((int) (Math.random() * exChaofan.size()))+
                                     midChaofan.get((int) (Math.random() * midChaofan.size()))+
                                     "炒饭！！！";
                    WechatMsg wechatMsg = new WechatMsg();
                    wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                    wechatMsg.setContent(chaofan);
                    wechatMsg.setType(TXT_MSG);
                    sendMsgUtil(wechatMsg);
                }

                //.rc丢骰子
                else if(rContent.contains(".rc")){
                    try {
                        String[] results = rContent.split(" ");
                        String event = results[1];
                        Integer point = Integer.valueOf(results[2]);
                        String diceResult = "";
                        int dice = (int) (Math.random() * 100 + 1.0D);
                        if (point < 100){
                            if (point <= dice){
                                if (dice >= 95){
                                    //大失败
                                    diceResult = "大失败";
                                }else {
                                    //失败
                                    diceResult = "失败";
                                }
                            }
                            else if (point > dice){
                                if (point/5 > dice){
                                    if (dice <= 5){
                                        //大成功
                                        diceResult = "大成功";
                                    }else {
                                        //极难成功
                                        diceResult = "极难成功";
                                    }
                                }
                                else if (point/2 > dice){
                                    //困难成功
                                    diceResult = "困难成功";
                                }else {
                                    //成功
                                    diceResult = "成功";
                                }
                            }
                        }
                        String result = "进行" + event + "判定:\n";
                        result = result + "D100 = " + dice + "/" + point + " " +diceResult;
                        WechatMsg wechatMsg = new WechatMsg();
                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                        wechatMsg.setContent(result);
                        wechatMsg.setType(TXT_MSG);
                        sendMsgUtil(wechatMsg);
                    }catch (Exception e){
                        System.out.println(e);
                    }


                }

                //普通的丢骰子
                else {
                    String[] split = rContent.split("@骰娘");
                    String text = split[1];
                    //骰子模块
                    if (text.contains("d")) {
                        String[] ds = text.split("d");
                        if (ds.length == 2)
                            try {
                                Integer i1 = Integer.valueOf(ds[0]);
                                Integer i2 = Integer.valueOf(ds[1]);
                                String result = "";
                                if ((i1.intValue() > 99) || (i2.intValue() > 9999) || i1 <= 0 || i2 <= 0) {
                                    result = "不许乱骰！";
                                } else {
                                    result = "骰出了:";
                                    for (int i = 0; i < i1.intValue(); i++) {
                                        result = result + (int) (Math.random() * i2.intValue() + 1.0D) + " ";
                                    }
                                }
                                WechatMsg wechatMsg = new WechatMsg();
                                wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                                wechatMsg.setContent(result);
                                wechatMsg.setType(TXT_MSG);
                                sendMsgUtil(wechatMsg);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                    }
                }




            }
            //群主的任务罢了
            else if(wechatReceiveMsg.getWxid().equals("wxid_e8rr05qphvq221")){
                String mission = wechatReceiveMsg.getContent();

                WechatMsg wechatMsg = new WechatMsg();
                wechatMsg.setWxid("18929140647@chatroom");
                wechatMsg.setContent(mission);
                wechatMsg.setType(TXT_MSG);
                sendMsgUtil(wechatMsg);

            }
        }
    }


    /**
     * 描述: 方法在连接断开时调用
     *
     * @param i
     * @param s
     * @param b
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("已断开连接... ");
    }

    /**
     * 描述: 方法在连接出错时调用
     *
     * @param e
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onError(Exception e) {
        System.err.println("通信连接出现异常:" + e.getMessage());
    }

    /**
     * 描述: 发送消息工具 (其实就是把几行常用代码提取出来 )
     *
     * @param wechatMsg 消息体
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-18
     */
    public void sendMsgUtil(WechatMsg wechatMsg) {
        if (!StringUtils.hasText(wechatMsg.getExt())) {
            wechatMsg.setExt(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getNickname())) {
            wechatMsg.setNickname(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getRoomid())) {
            wechatMsg.setRoomid(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getContent())) {
            wechatMsg.setContent(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getWxid())) {
            wechatMsg.setWxid(NULL_MSG);
        }
        // 消息Id
        wechatMsg.setId(String.valueOf(System.currentTimeMillis()));
        // 发送消息
        String string = JSONObject.toJSONString(wechatMsg);
        System.err.println(":" + string);
        send(JSONObject.toJSONString(wechatMsg));
    }
}