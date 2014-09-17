package cn.w.im.utils.netty.future;


import cn.w.im.utils.netty.exception.TimeoutException;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 下午1:54
 * Summary:
 */
public class WriteFuture<V> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected V result;
    protected AtomicBoolean done = new AtomicBoolean(false);
    protected AtomicBoolean success = new AtomicBoolean(false);
    protected Semaphore semaphore = new Semaphore(0);
    protected Throwable cause;
    protected ChannelHandlerContext ctx;
    protected List<WriteFutureListener> listeners = new ArrayList<WriteFutureListener>();

    public WriteFuture(){}

    public void addListener(WriteFutureListener<V> listener){
        if(listener == null){
            throw new NullPointerException("listener can not be null");
        }

        listeners.add(listener);
    }

    private void notifyListeners() {
        if (isDone()) {
            for (WriteFutureListener<V> listener : listeners) {
                try {
                    listener.operationComplete(this);
                } catch (Exception e) {
                    logger.error("call listener error:"+e.getMessage());
                }
            }
        }
    }

    private void notifyListener(WriteFutureListener<V> listener) {
        if (listener == null) {
            throw new NullPointerException("listener can not be null.");
        }
        //
        if (isDone()) {
            try {
                listener.operationComplete(this);
            } catch (Exception e) {
                logger.error("call listener error:"+e.getMessage());
            }
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return done.get();
    }

    public V getResult(){
        if (!isDone()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // check exception
        if (cause != null) {
            throw new RuntimeException(cause);
        }
        //
        return this.result;
    }

    public void setResult(V result) {
        this.result = result;
        done.set(true);
        success.set(true);

        semaphore.release(Integer.MAX_VALUE - semaphore.availablePermits());
        notifyListeners();
    }

    public V getResult(long timeout, TimeUnit unit) {
        if (!isDone()) {
            try {
                if (!semaphore.tryAcquire(timeout, unit)) {
                    setCause(new TimeoutException("time out."));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // check exception
        if (cause != null) {
            throw new RuntimeException(cause);
        }
        //
        return this.result;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
        done.set(true);
        success.set(false);
        semaphore.release(Integer.MAX_VALUE - semaphore.availablePermits());
        notifyListeners();
    }

    public boolean isSuccess() {
        return success.get();
    }

    public Throwable getCause() {
        return cause;
    }
}
