package com.ssm.codec;

import com.ssm.domain.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Package: com.ssm.codec
 * @Description:
 * @Author: Sammy
 * @Date: 2020/3/5 14:35
 */

public class RequestDecoder extends ReplayingDecoder<RequestData> {
	private final Charset charset = Charset.forName("UTF-8");

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
		RequestData data = new RequestData();
		data.setIntValue(in.readInt());
		int strLen = in.readInt();
		data.setStringValue(
				in.readCharSequence(strLen, charset).toString());
		out.add(data);
	}
}
