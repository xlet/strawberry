package cn.w.im.utils.netty.channel;

import cn.w.im.utils.netty.Sequential;
import cn.w.im.utils.netty.future.WriteFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 下午2:14
 * Summary:
 */
public class NettyChannel {
    private ChannelHandlerContext ctx;
    private static AtomicInteger seq = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, WriteFuture> futures = new ConcurrentHashMap<Integer, WriteFuture>();


    public NettyChannel(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public WriteFuture<?> writeAsync(Sequential packet) {
        final WriteFuture writeFuture = new WriteFuture();
        final int seq = getSeq();
        packet.setSeq(seq);
        futures.put(seq, writeFuture);
        ctx.writeAndFlush(packet);
        return writeFuture;
    }

    public Object writeSync(Sequential packet, long timeoutMillis) {
        WriteFuture<?> writeFuture = writeAsync(packet);
        return writeFuture.getResult(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    private int getSeq() {
        return seq.getAndIncrement();
    }

    public WriteFuture<Sequential> getFuture(int seq){
        if(futures.containsKey(seq)){
            return futures.get(seq);
        }
        return null;
    }

    public void setResult(Sequential response){
        WriteFuture<Sequential> future = getFuture(response.getSeq());
        if(future!=null){
            future.setResult(response);
        }
    }

    public void dispose(){
        if(ctx!=null && ctx.channel().isActive()){
            ctx.close();
        }
    }
}
