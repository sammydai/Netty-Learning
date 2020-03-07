package com.ssm.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Package: com.ssm.codec
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/7 21:30
 */

public class TimeDecoder extends ByteToMessageDecoder{
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes()<4) {
			return;
		}
		out.add(in.readBytes(4));
	}
}
