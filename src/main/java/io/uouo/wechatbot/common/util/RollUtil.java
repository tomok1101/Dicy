package io.uouo.wechatbot.common.util;

public class RollUtil {

    //1到i随机数；
    public static int iRoll(int i) {
        return (int) (Math.random() * i + 1);
    }

    public static int hundredRoll() {
        return (int) (Math.random() *100 + 1);
    }
}
