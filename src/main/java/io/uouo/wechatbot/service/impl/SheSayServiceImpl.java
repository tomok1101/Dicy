package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.domain.WechatReceiveMsg;
import io.uouo.wechatbot.entity.YysDearfriend;
import io.uouo.wechatbot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SheSayServiceImpl implements SheSayService {

    @Autowired
    private WechatBotService wechatBotService;

    @Autowired
    private IEventService iEventService;

    @Autowired
    private IGameService iGameService;

    @Autowired
    private IFoodService iFoodService;

    @Autowired
    private IMerryChristmasService iMerryChristmasService;

    @Autowired
    private IYysDearfriendService iYysDearfriendService;

    @Autowired
    private IYysFishDailyService iYysFishDailyService;

    @Override
    public void sheReplying(WechatReceiveMsg wechatReceiveMsg) {

        //图灵测试
//        if (!wechatReceiveMsg.getWxid().equals("24355601674@chatroom")) {
//            return;
//        }

        String rContent = wechatReceiveMsg.getContent();//收到消息串
        WechatMsg replyMsg = new WechatMsg();//回复消息实体
        replyMsg.setWxid(wechatReceiveMsg.getWxid());//回复到群
        String result = "";//回复信息串

        // .d 掷骰
        if (Pattern.compile("^.(\\d+)d(\\d+)").matcher(rContent).find()) {
            Matcher matcher = Pattern.compile("^.(\\d+)d(\\d+)").matcher(rContent);
            matcher.find();
            Integer times = Integer.valueOf(matcher.group(1));
            Integer points = Integer.valueOf(matcher.group(2));
            if ((times.intValue() > 99) || (points.intValue() > 9999) || times <= 0 || points <= 0) {
                result = "不许乱骰！";
            } else {
                result = "点数 -> ";
                for (int i = 0; i < times.intValue(); i++) {
                    if (i != times.intValue() - 1) {
                        result = result + (int) (Math.random() * points.intValue() + 1.0D) + ", ";
                    } else {
                        result = result + (int) (Math.random() * points.intValue() + 1.0D);
                    }
                }
            }
        }

        // .ra 事件判定
        else if (Pattern.compile("^.rc\\s*([a-zA-Z0-9_\\u4e00-\\u9fa5]+)\\s*(\\d+)").matcher(rContent).find()) {
            Matcher matcher = Pattern.compile("^.rc\\s*([a-zA-Z0-9_\\u4e00-\\u9fa5]+)\\s*(\\d{2}$)").matcher(rContent);
            matcher.find();
            String event = matcher.group(1);
            Integer point = Integer.valueOf(matcher.group(2));
            String diceResult = "";
            int rate = RollUtil.hundredRoll();

            if (100 > point) {
                if (rate >= point) {
                    diceResult = rate >= 95 ? "大失败" : "失败";
                } else if (rate == point) {
                    diceResult = "勉强成功";
                } else if (rate < point) {
                    if (rate < point / 5) {
                        diceResult = rate <= 5 ? "大！成！功！" : "极难成功";
                    } else if (rate < point / 2) {
                        diceResult = "困难成功";
                    } else {
                        diceResult = "成功";
                    }
                }
                result = "进行" + event + "判定:\n";
                result = result + "D100 = " + rate + "/" + point + " " + diceResult;
            }
            else {
                return;
            }
        }

        // .吃
        else if (Pattern.compile("^.吃什么|^.吃撒子|^.恰啥").matcher(rContent).find()) {
            if (RollUtil.iRoll(10) > 5) {
                result = foodList.get(RollUtil.iRoll(foodList.size() - 1));
            } else {
                result = "骰娘推荐恰：" + iFoodService.selectByid(RollUtil.iRoll(iFoodService.countAll())).getFood();
            }
        }

        //  .抽签
        else if (Pattern.compile("^.抽签").matcher(rContent).find()) {
            int rNum = iEventService.countAll();
            int chouQianNum = RollUtil.iRoll(rNum);
            int chouQianNumNum = RollUtil.iRoll(rNum);
            while (chouQianNum == chouQianNumNum) {
                chouQianNumNum = RollUtil.iRoll(rNum);
            }
            result = new SimpleDateFormat("yyyy年MM月dd日").format(new Date()) + "\n" +
                    " 宜：" + iEventService.selectByid(chouQianNum).getEvent() + "、" + iEventService.selectByid(chouQianNumNum).getEvent() + "\n" +
                    "今日有缘游戏：《" + iGameService.selectByid(RollUtil.iRoll(iGameService.countAll())).getGame() + "》来，试试看吧！";
        }

        //  .圣诞快乐
        else if (Pattern.compile("^mc.|圣诞快乐").matcher(rContent).find()) {
            if (Pattern.compile("reset").matcher(rContent).find()){
                iMerryChristmasService.reset();
            }

            else if (Pattern.compile("del\\s*([\\u4e00-\\u9fa5]+)").matcher(rContent).find()){
                Matcher matcher = Pattern.compile("del\\s*([\\u4e00-\\u9fa5]+)").matcher(rContent);
                matcher.find();
                String del = matcher.group(1);
                iMerryChristmasService.del(del);
            }

            else if (Pattern.compile("add\\s*([\\u4e00-\\u9fa5]+)").matcher(rContent).find()){
                Matcher matcher = Pattern.compile("del\\s*([\\u4e00-\\u9fa5]+)").matcher(rContent);
                matcher.find();
                String add = matcher.group(1);
                iMerryChristmasService.add(add);
            }

            else if (Pattern.compile("圣诞快乐").matcher(rContent).find()){
                String gift = iMerryChristmasService.get(wechatReceiveMsg.getId1());
                result = gift + "的圣诞礼物请收下~";
            }

            else {
                return;
            }
        }

        // .你的名字
        else if (Pattern.compile("^.login\\s*([a-zA-Z0-9_\\u4e00-\\u9fa5]+)").matcher(rContent).find()) {
            Matcher matcher = Pattern.compile("^.set\\s*([a-zA-Z0-9_\\u4e00-\\u9fa5]+$)").matcher(rContent);
            matcher.find();
            String nickname = matcher.group(1);
            YysDearfriend dearfriend = new YysDearfriend();
            dearfriend.setWxid(wechatReceiveMsg.getId1());
            dearfriend.setNickname(nickname);
            boolean add = iYysDearfriendService.add(dearfriend);
            if (add){
                result = "ヾ(•ω•`)o HI";
            }else {
                result = "( ⓛ ω ⓛ *)想改吗？但 是 我 拒 绝";
            }

        }

        // .摸lv
        else if (Pattern.compile("^.摸$").matcher(rContent).find()) {

            Integer lv = iYysFishDailyService.touchLv(wechatReceiveMsg.getId1());
            if (lv <= 10){
                result = "检测到摸鱼级别为Lv_" + lv + "，建议多摸哦";
            }
            else if (lv > 10){
                result = "检测到摸鱼级别为Lv_" + lv + "，什么嘛，这不是很会摸吗";
            }
        }

        //  .日摸量
        else if (Pattern.compile("^.日摸量").matcher(rContent).find()) {
            Map<String, Object> param = iYysFishDailyService.touchToday();
            Integer tt = (Integer) param.get("TT");
            String tk = (String) param.get("TK");
            Integer tm = (Integer) param.get("TM");

            result = "今天的摸鱼量：" + tt + " | 摸鱼人数：" + tm + " | 摸鱼king是......" + tk + "！！！";

        }

//        else if (Pattern.compile("^.\\s*(\\d+)\\s*d\\s*(\\d+)").matcher(rContent).find()) {
//            Matcher matcher = Pattern.compile("(\\D*)(\\d+)(.*)").matcher(rContent);
//            matcher.find();
//            String s = matcher.group(1);
//
//        }

        else {
            return;
        }

        replyMsg.setContent(result);
        wechatBotService.sendTextMsg(replyMsg);

    }

    @Override
    public void sheCounting(WechatReceiveMsg msg) {
        //yys-cd 摸鱼lv up
        if (msg.getWxid().equals("18929140647@chatroom")) {
            iYysFishDailyService.touch(msg.getId1());
        }
        return;
    }





    //DLC 吃什么.plus
    private static List<String> foodList = Arrays.asList(
            "可乐饼啦可乐饼！骰娘可以吃一盘！",
            "骰娘倾情推荐的拔丝地瓜！炸得酥脆，享用时蘸点糖浆更好吃。",
            "大西瓜！又甜又脆的冰镇大西瓜！真的会有人夏天不吃西瓜吗～",
            "是好吃的生煎……骰娘做梦梦到的生煎……",
            "来一碗粘稠又香浓的碗仔翅。",
            "理智应合剂功能饮料，将脑海中沉余杂质一扫而空！买不到可以用大瓶芥末替代，效果更加好！",
            "柔软膨松的舒芙蕾~骰娘我也很喜欢。",
            "柔情似蜜甜甜圈，爱的魔力转圈圈。",
            "香烤全鸡！好吃到骰娘流泪！整只火鸡放进烤箱做成的料理，有它立刻变大餐～",
            "牛肉乌冬面，精心烹饪的牛肉乌冬面，是骰娘十公里之外就能闻到的香气！",
            "炎热夏天带来凉爽口感的芒果绵绵牛奶冰，芒果的甜和牛奶冰的脆爽混合得恰到好处～",
            "湿润浓郁的鲷鱼烧，口感松软又富有弹性，是暖暖的红豆馅哦～",
            "椰子鸡怎么样，椰子鸡",
            "表面酥脆的菠萝油，刚出炉的热气合上中间夹着冰冻的黄油，冷与热，甜与咸，感受冰火两重天的美味。",
            "色泽金黄明亮，香气诱人，多少碗饭都吃得下，就选骰娘咖喱！",
            "嗦螺蛳粉，十分美味，十二分回味，够爽，够辣，够实惠！",
            "路边糖水铺买的杨枝甘露，骰娘吃完感觉整只骰子都快乐了～",
            "色泽金黄明亮，香气诱人，多少碗饭都吃得下，就选骰娘咖喱！",
            "小巷子口的鸭血粉丝汤，都说了不要放辣油啦！",
            "骰娘倾情推荐的拔丝地瓜！炸得酥脆，享用时蘸点糖浆更好吃。",
            "干炒牛河！好吃到骰娘打嗝！",
            "炸鸡块和柠檬汁，骰娘说反正我是只骰子这有什么不可以吃的～",
            "来碗热腾腾的烧鹅濑，烧鹅皮脆肉嫩，汤汁鲜美，务必要尝尝哦。", "芝士牛丼饭，软硬适中的牛肉上盖满了上品芝士。",
            "让人想爆灯的美味烧鹅饭，路过的喜鹊吃到都要落泪了。",
            "酸菜白肉炖粉条，五花三层的肥肉快刀切薄，爽口酸菜中和了它的油腻，只留下香滑软嫩的口感...(゜ρ゜*)",
            "豆角土豆炖排骨！食材新鲜，土豆豆角炖的绵密，排骨软烂的一下就能抽出骨头……再配上香香软软的大米饭……",
            "涮点羊肉吃吧，几片良姜，一捆小葱，滚水烧开下入现切的内蒙羊肉片，再放点白萝卜娃娃菜解腻，沾上点芝麻酱，吃他个大汗淋漓……",
            "试试羊汤馅饼吧，羊腿和羊杂熬一大锅雪白浓郁的汤，配上几张饼皮薄韧肉香四溢的羊肉馅饼，香香暖暖最适合冬天！",
            "烤冷面！小摊老板刚做好热乎乎冒着气的烤冷面！Q弹甜酸带点辣的烤冷面！老板——麻烦加两个蛋！",
            "色泽金黄的老式锅包肉！一口咬下去，微酸微甜，酥脆焦香……( ´ρ`)骰娘说请不要放番茄酱！",
            "为什么不能是泡面呢？偶尔偷懒泡个泡面打发一顿，等待3分钟就能享受俗套的色香味，再开一罐肥宅快乐水，骰娘也无法拒绝！",
            "鳗鱼饭吧——香软的白米饭上放上烤至金黄的厚切鳗鱼,浇上酱汁之后就是人间绝味！",
            "海边名小吃海蛎煎，大小刚好的海蛎均匀分布在金黄的煎蛋底座中，蘸着酱汁送入口中，能充分感受到海蛎肚子的滑、煎蛋的韧与绵和海蛎头有点焦脆的口感。",
            "热乎乎的西红柿炖牛腩，酸甜的汤汁浸透配菜，牛肉炖得软烂可口，还有奶香味~",
            "一般路过骰子也吃过的糖醋排骨，从南到北的骰子都知道的经典菜肴！酥脆的炸排骨外壳下是被锁住的肉汁，糖和醋的完美结合与稻香混响，骰娘能就着排骨吃三大碗！",
            "新鲜出炉的枣泥拉糕！软糯Q弹，让芬芳枣香伴随着甜蜜一同融化！即使放到第二天也依然保持嚼劲的秘诀是……?",
            "虾——滑——！刚从火锅里捞出来还在冒蒸汽的虾滑！咬一口有微微的弹牙感，虾肉的鲜甜与烟火气的完美融合！",
            "文火慢炖的笋干老鸭煲，两年以上江南麻鸭，配上金华火腿与天目山笋干，加上几颗鲜香菇，无需更多调料，鲜美多汁吸饱了油的笋干甚至比鸭肉本身更好吃！吃不完的汤还可以留着第二天下一碗面~吸溜",
            "油润而不腻的响油鳝糊……热油胡椒粉鲜美的鳝丝……是什么让一只骰子的眼泪从两点里流出……",
            "来一碗热气腾腾皮薄馅大晶莹剔透咬一口爆汁的虾饺皇！",
            "当然是粉蒸肉啊！不会有人不喜欢吃粉蒸肉吧！",
            "清蒸大螃蟹！我骰娘，哪怕被夹，被蟹钳暴打，也要恰到最好吃的那一口蟹！",
            "温度适宜的莼菜鲈鱼羹，入口能尝到莼菜的滑和鲈鱼的鲜，骰娘也有莼鲈之思了～"
    );

}

