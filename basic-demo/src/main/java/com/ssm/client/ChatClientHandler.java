package com.ssm.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Package: com.ssm.client
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/10 14:43
 */

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("服务端发来的："+msg);
        System.out.println("kkkkk");
	}
}
