package cn.w.im.netty.handlers;

import cn.w.im.netty.heartbeat.Heartbeat;
import cn.w.im.netty.heartbeat.HeartbeatContext;
import cn.w.im.netty.heartbeat.HeartbeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 上午10:21
 * Summary:
 */
public class HeartbeatReqHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile ScheduledFuture<?> heartbeatFuture;

    private boolean reply;

    private int interval = 60;

    private HeartbeatContext context;

    private ScheduledExecutorService executor;

    private AtomicInteger errorCount = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HeartbeatResponse) {
            context.setResult((HeartbeatResponse) msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }


    public HeartbeatReqHandler(int intervalSeconds) {
        this(false, intervalSeconds);
    }

    public HeartbeatReqHandler(boolean reply, int intervalSeconds) {
        this.reply = reply;
        this.interval = intervalSeconds;
        //使用netty线程池会阻塞其他IO操作
        this.executor = Executors.newScheduledThreadPool(1, new DefaultThreadFactory("HeartbeatThread"));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("starting heartbeat...");
        this.context = new HeartbeatContext(ctx);

        this.heartbeatFuture = this.executor.scheduleAtFixedRate(new HeartbeatTask(this), 0, interval, TimeUnit.SECONDS);
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        stopHeartbeat();
        destory();
        super.channelInactive(ctx);
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    private static class HeartbeatTask implements Runnable {
        private static final Logger logger = LoggerFactory.getLogger(HeartbeatTask.class);
        private static final long timeout = 5 * 1000;

        private final HeartbeatReqHandler handler;

        private HeartbeatTask(final HeartbeatReqHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                HeartbeatResponse response = (HeartbeatResponse) handler.getContext().writeSync(new Heartbeat(true), timeout);
                logger.debug("Server is alive...[" + response.getSeq() + "]");
                handler.getErrorCount().set(0);
                //context.writeAsync(new Heartbeat(true));
            } catch (Exception e) {

                int times = handler.getErrorCount().incrementAndGet();
                logger.error(e.getMessage() + "[" + times + "]");
                if (times > 5) {
                    //超过次数断开连接 等待重连
                    logger.debug("closing...");
                    handler.getContext().dispose();
                }
            }
        }


    }

    public void stopHeartbeat() {
        logger.debug("cancelling heartbeat...");
        if (this.heartbeatFuture != null) {
            this.heartbeatFuture.cancel(true);
            logger.debug("heartbeat cancelled!");
        }
    }

    public void destory() {
        if (this.executor != null && !this.executor.isShutdown()) {
            this.executor.shutdownNow();
        }
    }

    public AtomicInteger getErrorCount() {
        return errorCount;
    }

    public HeartbeatContext getContext() {
        return context;
    }
}
