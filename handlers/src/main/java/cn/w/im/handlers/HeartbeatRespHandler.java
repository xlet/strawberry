package cn.w.im.handlers;

import cn.w.im.domains.messages.heartbeat.Heartbeat;
import cn.w.im.domains.messages.heartbeat.HeartbeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 上午10:10
 * Summary: heartbeat response handler
 */
public class HeartbeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Heartbeat) {
            Heartbeat heartbeat = (Heartbeat) msg;
            if (heartbeat.isReply()) {
                HeartbeatResponse response = new HeartbeatResponse();
                response.setSeq(heartbeat.getSeq());
                ctx.writeAndFlush(response);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }
}
