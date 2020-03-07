package com.ssm.codec;

import com.ssm.domain.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Package: com.ssm.codec
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/5 16:54
 */

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, ResponseData msg, ByteBuf out) throws Exception {
		out.writeInt(msg.getIntValue());
	}
}
