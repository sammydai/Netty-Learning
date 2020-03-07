package com.ssm.client;

import com.ssm.server.MyServerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Package: com.ssm.httpserver
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/8 00:09
 */

public class MyClient {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workerGroup)
					.channel(NioSocketChannel.class)
					.handler(new MyClientInitializer());
			ChannelFuture future = bootstrap.connect("localhost",8899).sync();
			future.channel().closeFuture().sync();
		}finally {
			workerGroup.shutdownGracefully();
		}
	}
}
