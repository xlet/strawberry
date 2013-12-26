package cn.w.imserver.test;

/**
 * Creator: JackieHan
 * DateTime: 13-12-25 下午2:51
 */
public class ShutDownListener implements Runnable{
    @Override
    public void run() {
       System.out.println("logout!");
    }
}
