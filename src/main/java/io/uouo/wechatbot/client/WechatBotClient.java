package io.uouo.wechatbot.client;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import io.uouo.wechatbot.WechatBotApplication;
import io.uouo.wechatbot.common.WechatBotCommon;
import io.uouo.wechatbot.common.WechatBotConfig;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.domain.WechatReceiveMsg;
import io.uouo.wechatbot.entity.Activity;
import io.uouo.wechatbot.service.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    private UserService userService;
    @Autowired
    private ShakeService shakeService;
    @Autowired
    private GameService gameService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private FoodService foodService;


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
    public void onMessage(String msg) {
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

        //骰娘拿到消息
        WechatReceiveMsg wechatReceiveMsg = (WechatReceiveMsg) JSONObject.parseObject(msg, WechatReceiveMsg.class);
        if (!WechatBotCommon.HEART_BEAT.equals(wechatReceiveMsg.getType()) && wechatReceiveMsg.getWxid() != null) {
            //闭群
//            if (wechatReceiveMsg.getWxid() != null){
//                if (!wechatReceiveMsg.getWxid().equals("24355601674@chatroom")){
//                    return;
//                }
//            }
//            System.out.println("微信中收到了消息:" + msg);

            String rContent = wechatReceiveMsg.getContent();

            //有人@骰娘了
            if (rContent.contains("@骰娘")) {
                rContent = rContent.replace(" ", "");
                // @ 先看看是不是男桐
                if (rContent.contains("男桐指数")) {
                    Boolean isNt = false;
                    String ntName = "";
                    //在男桐列表吗
                    for (String s :
                            nantongList) {
                        if (rContent.contains(s)) {
                            isNt = true;
                            ntName = s;
                            break;
                        }
                    }
                    //在
                    if (isNt) {
                        //骰子模块
                        try {
                            String result = ntName + "男桐指数飙到了...";
                            result = result + (int) (Math.random() * 10 + 100) + " " + "!!!";
                            WechatMsg wechatMsg = new WechatMsg();
                            wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                            wechatMsg.setContent(result);
                            wechatMsg.setType(TXT_MSG);
                            sendMsgUtil(wechatMsg);
                            return;
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    //不在
                    else {
                        //骰子模块
                        try {
                            String result = ntName + "男桐指数为：";
                            result = result + (int) (Math.random() * 100 + 1.0D);
                            WechatMsg wechatMsg = new WechatMsg();
                            wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                            wechatMsg.setContent(result);
                            wechatMsg.setType(TXT_MSG);
                            sendMsgUtil(wechatMsg);
                            return;
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }

                // @ 有没有炒饭元素
                else if (rContent.contains("秋秋")) {
                    if (rContent.contains("饿了")) {
                        List<String> exChaofan = Arrays.asList(
                                "史诗", "绝版", "联名", "食堂",
                                "芒果现炒", "男桐", "群主现炒");
                        List<String> midChaofan = Arrays.asList(
                                "蛋", "拔十圆", "饭", "咖喱",
                                "胡萝卜蛋", "咸鸭蛋", "虾球鸡肉丁", "韩国泡菜", "扬州");
                        String chaofan = exChaofan.get((int) (Math.random() * exChaofan.size())) +
                                midChaofan.get((int) (Math.random() * midChaofan.size())) +
                                "炒饭！！！";
                        WechatMsg wechatMsg = new WechatMsg();
                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                        wechatMsg.setContent(chaofan);
                        wechatMsg.setType(TXT_MSG);
                        sendMsgUtil(wechatMsg);
                        return;
                    } else {
                        WechatMsg wechatMsg = new WechatMsg();
                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                        wechatMsg.setContent("哪里有秋秋？？秋秋辣妹！！[玫瑰][玫瑰][玫瑰]");
                        wechatMsg.setType(TXT_MSG);
                        sendMsgUtil(wechatMsg);
                        return;
                    }
                }

                // @ .rc丢骰子
                else if (rContent.contains(".rc")) {
                    try {
                        String[] results = rContent.split(" ");
                        String event = results[1];
                        Integer point = Integer.valueOf(results[2]);
                        String diceResult = "";
                        int dice = (int) (Math.random() * 100 + 1.0D);
                        if (point < 100) {
                            if (point <= dice) {
                                if (dice >= 95) {
                                    //大失败
                                    diceResult = "大失败";
                                } else {
                                    //失败
                                    diceResult = "失败";
                                }
                            } else if (point == dice) {
                                diceResult = "勉强成功";
                            } else if (point > dice) {
                                if (point / 5 > dice) {
                                    if (dice <= 5) {
                                        //大成功
                                        diceResult = "大成功";
                                    } else {
                                        //极难成功
                                        diceResult = "史诗成功";
                                    }
                                } else if (point / 2 > dice) {
                                    //困难成功
                                    diceResult = "稀有成功";
                                } else {
                                    //成功
                                    diceResult = "成功";
                                }
                            }
                            //蜜糖彩蛋
                            if (rContent.contains("蜜糖")){
                                diceResult = "蜜糖级成功";
                                dice = 1;
                            }
                            String result = "进行" + event + "判定:\n";
                            result = result + "D100 = " + dice + "/" + point + " " + diceResult;
                            WechatMsg wechatMsg = new WechatMsg();
                            wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                            wechatMsg.setContent(result);
                            wechatMsg.setType(TXT_MSG);
                            sendMsgUtil(wechatMsg);
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

                // @ 抽签
                else if (rContent.contains("抽签")) {
                    int rNum = activityService.countAll();
                    int chouQianNum = listRoll(rNum);
                    int chouQianNumNum = listRoll(rNum);
                    while (chouQianNum == chouQianNumNum) {
                        chouQianNumNum = listRoll(rNum);
                    }
                    Activity activity = activityService.selectByid(chouQianNum);
                    Activity activity1 = activityService.selectByid(chouQianNumNum);
                    String result = new SimpleDateFormat("yyyy年MM月dd日").format(new Date()) + "\n" +
                            " 宜：" + activityService.selectByid(chouQianNum).getActivity() + "、" + activityService.selectByid(chouQianNumNum).getActivity() + "\n" +
                            "今日有缘游戏：《" + gameService.selectByid(listRoll(gameService.countAll())).getGame() + "》来，试试看吧！";
                    WechatMsg wechatMsg = new WechatMsg();
                    wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                    wechatMsg.setContent(result);
                    wechatMsg.setType(TXT_MSG);
                    sendMsgUtil(wechatMsg);
                    return;
                }

                // @ 吃饭
                else if (rContent.contains("吃什么") || rContent.contains("吃撒子")) {
                    String result = "";
                    if (listRoll(10) > 5){
                        result = foodList.get(listRoll(foodList.size()-1));
                    }else {
                        result = "骰娘推荐恰：" + foodService.selectByid(listRoll(foodService.countAll())).getFood();
                    }

                    WechatMsg wechatMsg = new WechatMsg();
                    wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                    wechatMsg.setContent(result);
                    wechatMsg.setType(TXT_MSG);
                    sendMsgUtil(wechatMsg);
                    return;
                }

                // @ 记住这个人
                else if (rContent.contains("我是")) {
                    if (rContent.contains("爸") || rContent.contains("爹")||rContent.contains("father")||rContent.contains("dad")){
                        String result = "你是猪";
                        WechatMsg wechatMsg = new WechatMsg();
                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                        wechatMsg.setContent(result);
                        wechatMsg.setType(TXT_MSG);
                        sendMsgUtil(wechatMsg);
                        return;
                    }
                    String[] results = rContent.split("我是");
                    String nickname = results[1];
                    String wxid = wechatReceiveMsg.getId1();
                    userService.sayMyaName(wxid,nickname);
                    String result = nickname + "你好！";
                    WechatMsg wechatMsg = new WechatMsg();
                    wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                    wechatMsg.setContent(result);
                    wechatMsg.setType(TXT_MSG);
                    sendMsgUtil(wechatMsg);
                    return;
                }

                // @ 发车
                else if (rContent.contains(".fc")) {
                    try {
                        String[] results = rContent.split(" ");
                        String game = results[1];
                        Integer num = Integer.valueOf(results[2].replace("天",""));
                        shakeService.shake(game,num,7);

                        String result = "骰娘已记录";
                        WechatMsg wechatMsg = new WechatMsg();
                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
                        wechatMsg.setContent(result);
                        wechatMsg.setType(TXT_MSG);
                        sendMsgUtil(wechatMsg);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

                // @ 上车
//                else if (rContent.contains(".sc")) {
//                    try {
//                        String[] results = rContent.split(" ");
//                        String game = results[1];
//                        Integer num = Integer.valueOf(results[2].replace("天",""));
//                        shakeService.shake(game,num,7);
//
//                        String result = "骰娘已记录";
//                        WechatMsg wechatMsg = new WechatMsg();
//                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
//                        wechatMsg.setContent(result);
//                        wechatMsg.setType(TXT_MSG);
//                        sendMsgUtil(wechatMsg);
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
//                }


                // @ 查车
//                else if (rContent.contains(".cc")) {
//                    try {
//                        String[] results = rContent.split(" ");
//                        String game = results[1];
//                        Integer num = Integer.valueOf(results[2].replace("天",""));
//                        shakeService.shake(game,num,7);
//
//                        String result = "骰娘已记录";
//                        WechatMsg wechatMsg = new WechatMsg();
//                        wechatMsg.setWxid(wechatReceiveMsg.getWxid());
//                        wechatMsg.setContent(result);
//                        wechatMsg.setType(TXT_MSG);
//                        sendMsgUtil(wechatMsg);
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
//                }


                // @ 普通的丢骰子
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
                                return;
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                    }
                }
            }


            //群主的任务罢了1.0
            else if (wechatReceiveMsg.getWxid() != null){
                if (wechatReceiveMsg.getWxid().equals("wxid_e8rr05qphvq221")) {
                    String mission = wechatReceiveMsg.getContent();
                    WechatMsg wechatMsg = new WechatMsg();
                    wechatMsg.setWxid("18929140647@chatroom");
                    wechatMsg.setContent(mission);
                    wechatMsg.setType(TXT_MSG);
                    sendMsgUtil(wechatMsg);
                }
            }
        }
    }

    private int listRoll(int i) {
        int dice = (int) (Math.random() * i +1);
        return dice;
    }

    //DLC 男桐名单.tom
    private static List<String> nantongList = Arrays.asList(
            "士力架", "歪西", "李雨轩", "柯基",
            "富贵", "游零", "东宝", "汉堡王",
            "丝袜", "tom", "PMD", "AMD", "黑总"

    );

    //DLC 抽签事项.群主
//    private static List<String> chouQianList = Arrays.asList(
//            "打游戏", "玩桌游", "买游戏", "搞男酮", "吃炒饭",
//            "吃火锅", "吃烧烤", "吃肥宅快乐餐", "吃甜食", "喝酒",
//            "聚会", "上班摸鱼", "请假在家", "带薪拉屎", "上班聊微信",
//            "调戏骰娘", "出警emo", "出警二郎腿", "运动", "冲动消费",
//            "淘宝购物", "吸猫吸狗", "谈恋爱", "联系旧友", "买彩票",
//            "参与抽奖"
//    );

    //DLC 抽签游戏.群主
//    private static List<String> gameList = Arrays.asList(
//            "英雄联盟", "只狼", "黑暗之魂3", "仁王", "血源",
//            "女神异闻录5", "饥荒", "缺氧", "异星探险家", "无人深空",
//            "Grounded", "恐鬼症", "文明6", "巫师3", "死亡搁浅",
//            "极乐迪斯科", "以撒的结合", "空洞骑士", "DOTA2", "动物园之星",
//            "牧场物语", "动物森友会", "火焰纹章", "宝可梦", "猎天使魔女2",
//            "地平线4", "风来之国", "如龙", "审判之眼", "炉石传说", "塞尔达传说",
//            "生化危机", "怪物猎人", "双人成行", "你画我猜", "节奏医生", "超级马力欧创作家2",
//            "糖豆人", "荒野大镖客", "胡闹厨房", "戴森球计划", "黑帝斯", "对马岛之魂", "最终幻想",
//            "ORI", "十三机兵防卫圈", "天外世界", "健身环大冒险", "异度神剑", "勇者斗恶龙", "命运",
//            "无主之地", "魔兽世界", "鬼泣", "地铁", "逆转裁判", "任天堂明星大乱斗", "古剑奇谭",
//            "奥伯拉丁的回归", "杀戮尖塔", "艾迪芬奇的记忆", "神界原罪2", "锈湖系列", "死亡细胞", "八方旅人",
//            "密教模拟器", "战神", "底特律：成为人类", "蔚蓝", "刺客信条", "茶杯头", "心跳文学部", "奇异人生",
//            "柴堆", "巧克力与香子兰", "小小梦魇", "逃生", "异域镇魂曲", "博德之门", "泰坦天降2", "生化奇兵",
//            "赛博朋克酒保行动", "赛博朋克2077", "弹丸论破", "彩虹六号", "晶体管","博德之门", "给他爱5", "最后生还者"
//    );

    //DLC 吃什么.plus
    private static List<String> foodList = Arrays.asList(
            "可乐饼啦可乐饼！骰娘可以吃一盘！", "骰娘倾情推荐的拔丝地瓜！炸得酥脆，享用时蘸点糖浆更好吃。", "大西瓜！又甜又脆的冰镇大西瓜！真的会有人夏天不吃西瓜吗～", "是好吃的生煎……骰娘做梦梦到的生煎……", "来一碗粘稠又香浓的碗仔翅。",
            "理智应合剂功能饮料，将脑海中沉余杂质一扫而空！买不到可以用大瓶芥末替代，效果更加好！", "柔软膨松的舒芙蕾~骰娘我也很喜欢。", "柔情似蜜甜甜圈，爱的魔力转圈圈。", "香烤全鸡！好吃到骰娘流泪！整只火鸡放进烤箱做成的料理，有它立刻变大餐～", "牛肉乌冬面，精心烹饪的牛肉乌冬面，是骰娘十公里之外就能闻到的香气！",
            "炎热夏天带来凉爽口感的芒果绵绵牛奶冰，芒果的甜和牛奶冰的脆爽混合得恰到好处～", "湿润浓郁的鲷鱼烧，口感松软又富有弹性，是暖暖的红豆馅哦～", "椰子鸡怎么样，椰子鸡", "表面酥脆的菠萝油，刚出炉的热气合上中间夹着冰冻的黄油，冷与热，甜与咸，感受冰火两重天的美味。", "色泽金黄明亮，香气诱人，多少碗饭都吃得下，就选骰娘咖喱！",
            "嗦螺蛳粉，十分美味，十二分回味，够爽，够辣，够实惠！", "路边糖水铺买的杨枝甘露，骰娘吃完感觉整只鸽子都快乐了～", "色泽金黄明亮，香气诱人，多少碗饭都吃得下，就选骰娘咖喱！","小巷子口的鸭血粉丝汤，都说了不要放辣油啦！","骰娘倾情推荐的拔丝地瓜！炸得酥脆，享用时蘸点糖浆更好吃。",
            "干炒牛河！好吃到骰娘打嗝！","炸鸡块和柠檬汁，骰娘说反正我是只骰子这有什么不可以吃的～","来碗热腾腾的烧鹅濑，烧鹅皮脆肉嫩，汤汁鲜美，务必要尝尝哦。","芝士牛丼饭，软硬适中的牛肉上盖满了上品芝士。","让人想爆灯的美味烧鹅饭，路过的喜鹊吃到都要落泪了。"


            );


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