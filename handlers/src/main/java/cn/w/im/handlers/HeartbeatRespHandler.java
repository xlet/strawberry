package cn.w.im.handlers;

import cn.w.im.domains.messages.heartbeat.Heartbeat;
import cn.w.im.domains.messages.heartbeat.HeartbeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 上午10:10
 * Summary: heartbeat response handler
 */
public class HeartbeatRespHandler extends SimpleChannelInboundHandler<Heartbeat> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Heartbeat heartbeat) throws Exception {
        if (heartbeat.isReply()) {
            HeartbeatResponse response = new HeartbeatResponse();
            response.setSeq(heartbeat.getSeq());
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active!");
    }
}
