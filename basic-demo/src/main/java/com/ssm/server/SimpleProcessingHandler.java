package com.ssm.server;

import com.ssm.domain.RequestData;
import com.ssm.domain.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Package: com.ssm.server
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/5 14:14
 */

public class SimpleProcessingHandler extends ChannelInboundHandlerAdapter {

	private ByteBuf tmp;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Handler add");
		tmp = ctx.alloc().buffer(4);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Handler removed");
		tmp.release();
		tmp = null;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf m = (ByteBuf) msg;
		tmp.writeBytes(m);
		m.release();
		if (tmp.readableBytes()>=4) {
			RequestData requestData = new RequestData();
			requestData.setIntValue(tmp.readInt());
			ResponseData responseData = new ResponseData();
            responseData.setIntValue(requestData.getIntValue() * 2);
			ChannelFuture future = ctx.writeAndFlush(requestData);
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
