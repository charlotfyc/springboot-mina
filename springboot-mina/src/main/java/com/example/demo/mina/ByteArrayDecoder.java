package com.example.demo.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.he.constant.Constant;
import com.he.util.FrameUtil;
import com.he.util.Hex;

/**
 * @author fyc 字节数组解码器
 */
public class ByteArrayDecoder extends CumulativeProtocolDecoder {
	
	protected static final Logger LOG = LoggerFactory.getLogger(ByteArrayDecoder.class);


	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() > 16) {// 前4字节是包头
			// 标记当前position的快照标记mark，以便后继的reset操作能恢复position位置
			in.mark();
			byte[] bytes = new byte[4];
			in.get(bytes);
			// 包体数据长度
			int len = FrameUtil.bytes2Int(bytes, 2, 3);
			if (Constant.OUTER_HEAD[0] == bytes[0] && Constant.OUTER_HEAD[1] == bytes[1]) {// 验证包头
				// 注意上面的get操作会导致下面的remaining()值发生变化
				if (in.remaining() + 4 < len) {
					// 如果消息内容不够，则重置恢复position位置到操作前,进入下一轮, 接收新数据，以拼凑成完整数据
					in.reset();
					return false;
				} else {// 消息内容足够
					in.reset();// 重置恢复position位置到操作前
					byte[] data = new byte[len];
					in.get(data);
					LOG.warn("收到 ： " + Hex.bytesToHex(data));
					out.write(data);
					if (in.remaining() > 0) // 如果读取一个完整包内容后还粘了包，就让父类再调用一次，进行下一次解析
						return true;
				}
			} else {
				in.reset();
				int limit = in.remaining();
				byte[] errorBytes = new byte[limit];
				in.get(errorBytes);
				LOG.warn("丢弃非法数据 ： " + Hex.bytesToHex(errorBytes, 0, limit));
				LOG.error("丢弃非法数据 ： " + Hex.bytesToHex(errorBytes, 0, limit));
				System.out.println("丢弃非法数据 ： " + Hex.bytesToHex(errorBytes, 0, limit));
				return false;
			}
		} else {// 注册帧不处理
			int len = in.remaining();
			byte[] bytes = new byte[len];
			in.get(bytes);
			LOG.warn("收到 ： " + Hex.bytesToHex(bytes));
			out.write(bytes);
		}
		return false;// 处理成功，让父类进行接收下个包
	}
}