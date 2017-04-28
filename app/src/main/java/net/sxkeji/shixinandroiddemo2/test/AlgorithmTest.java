package net.sxkeji.shixinandroiddemo2.test;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class AlgorithmTest {
    public static double sqrt(int value, double error){
        if (value <= 0){
            return 0;
        }
        int result = value / 2;
        while (result > 0){ //太大了
            if ((result * result - value) < error  && (result * result - value) >= 0){
                break;
            }else if ((value - result * result) > error) {  // 太小了
                result = (result - result / 2 ) / 2 + result / 2;
            }else {
                result = result / 2;
            }
        }
        return result;
    }
}
