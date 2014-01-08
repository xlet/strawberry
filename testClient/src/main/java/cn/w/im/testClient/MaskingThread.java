package cn.w.im.testClient;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-23 下午5:28.
 * Summary:MaskingThread.
 */
public class MaskingThread extends Thread {

    /**
     * 是否停止.
     */
    private boolean stop = false;

    /**
     * index.
     */
    private int index;

    /**
     * 输出字符串.
     */
    private String initial;

    /**
     * 构造函数.
     * @param initial 输出命令行字符串.
     */
    public MaskingThread(String initial) {
        this.initial = initial;
    }

    /**
     * run.
     */
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

    /**
     * 停止Masking.
     */
    public void stopMasking() {
        this.stop = true;
    }
}
