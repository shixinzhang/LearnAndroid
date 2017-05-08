package net.sxkeji.shixinandroiddemo2.test.concurrent;

import java.util.concurrent.Semaphore;

/**
 * Description:
 * <br> 信号量，互斥访问
 * <p>
 * <br> Created by shixinzhang on 17/5/8.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SemaphoreTest {
    public static class CarPark{
        private Semaphore mPosition;

        public CarPark(int position) {
            mPosition = new Semaphore(position);
        }

        public void park(String carName) throws InterruptedException {
            mPosition.acquire();

            System.out.println(carName +" 正在停车....");
            Thread.sleep(2000);
            System.out.println(carName + " 开走了....");

            mPosition.release();
        }
    }

    public static void main(String[] args) {
        final CarPark carPark = new CarPark(3);
        for (int i = 0; i < 8; i++) {
            final String carName = "宝马 " + i + " 系";
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        carPark.park(carName);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, carName + "_Thread");
            thread.start();
        }

    }

}
