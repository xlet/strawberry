package cn.w.im.utils.netty.future;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 下午1:57
 * Summary:
 */
public interface WriteFutureListener<T> {

    void operationComplete(WriteFuture<T> future) throws Exception;

}
