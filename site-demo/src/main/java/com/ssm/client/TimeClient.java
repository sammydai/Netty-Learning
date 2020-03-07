package com.ssm.client;

import com.ssm.server.TimeServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Package: com.ssm.client
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/7 17:57
 */

public class TimeClient {
	public static void main(String[] args) throws InterruptedException {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new TimeClientHandler());
						}
					});
			//是connect 不是bind
			ChannelFuture future = b.connect(host, port).sync();
			System.out.println("Time Client Start...");
			future.channel().closeFuture().sync();
		} finally{
			workerGroup.shutdownGracefully();
		}
	}
}
