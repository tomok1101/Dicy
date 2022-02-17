package io.uouo.wechatbot.common.util;

public class RollUtil {

    //1到i随机数；
    public static int iRoll(int i) {
        return (int) (Math.random() * i + 1);
    }

    //1到i随机数；
    public static int a2bRoll(int a ,int b) {
        return (int) (Math.random() * (b-a+1)  + a);
    }

    //1-100随机数；
    public static int hundredRoll() {
        return (int) (Math.random() *100 + 1);
    }
}
