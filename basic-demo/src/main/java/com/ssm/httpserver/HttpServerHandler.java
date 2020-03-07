package com.ssm.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.URI;

/**
 * @Package: com.ssm.httpserver
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/8 00:13
 */

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		if (msg instanceof HttpRequest) {
			System.out.println("msg.getClass(): "+msg.getClass());
			System.out.println("remoteAddress(): "+ctx.channel().remoteAddress());
			HttpRequest httpRequest = (HttpRequest) msg;
			URI uri = new URI(httpRequest.uri());
			if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico");
                return;
            }
			System.out.println("请求方法名:"+httpRequest.method().name());
			ByteBuf byteBuf = Unpooled.copiedBuffer("HelloWorld!", CharsetUtil.UTF_8);
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
			ctx.writeAndFlush(response);
		}
	}

	   /**
     * 处理顺序如下：
     * handlerAdded
     * channelRegistered
     * channelActive
     * 请求方法名:GET（channelRead0）
     * （下面的表示的是断开连接后）
     * 1.如果是使用curl ：连接会立刻关闭
     * 2.如果是浏览器访问，http1.0：它是短连接，会立刻关闭。http1.1，是长连接，连接保持一段时间
     * channelInactive
     * channelUnregistered
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
}
