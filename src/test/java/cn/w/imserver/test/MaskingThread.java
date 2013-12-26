package cn.w.imserver.test;

/**
 * Creator: JackieHan
 * DateTime: 13-12-23 下午5:28
 */
public class MaskingThread extends Thread {

    private boolean stop = false;
    private int index;
    private String initial;

    public MaskingThread(String initial) {
        this.initial = initial;
    }

    public void run() {
        while (!stop) {
            try {
                this.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (!stop) {
                System.out.print("\r" + initial + "\r" + initial);
            }
            System.out.flush();
        }
    }

    public void stopMasking() {
        this.stop = true;
    }
}
