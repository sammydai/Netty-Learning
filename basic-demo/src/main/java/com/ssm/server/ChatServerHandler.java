package com.ssm.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Package: com.ssm.server
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/9 09:57
 */

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

	/**
	 * 定义一个channelGroup
	 * 一个用户是一个channel，连接进来，加入channelGroup
	 *
	 */
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel channel = ctx.channel();
		for (Channel ch : channelGroup) {
			if (channel != ch) {
				ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息--" + msg + "\n");
			} else {
                ch.writeAndFlush("[自己]"+"发送了消息"+msg+"\n");
			}
		}
	}

	/**
	 * @description channelgroup对循环里面的每一个channel进行输出
	 * 假如A上线，会通知channelGroup其他channel，但是不会通知A，因为A还未假如group
	 * @Exception
	 *
	 */

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]-"+channel.remoteAddress()+"加入\n");
		channelGroup.add(channel);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]-"+channel.remoteAddress()+"离开\n");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端: " + ctx.channel().remoteAddress() + "上线了!");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端: " + ctx.channel().remoteAddress() + "离线了..");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
