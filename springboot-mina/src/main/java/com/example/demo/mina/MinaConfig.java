package com.example.demo.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author fyc
 *
 */
@Configuration
public class MinaConfig {

	protected static final Logger LOG = LoggerFactory.getLogger(MinaConfig.class);

	// socket占用端口
	@Value("${mina.port}")
	private int port;

	@Bean
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}

	@Bean
	public IoHandler ioHandler() {
		return new MyHandler();
	}

	@Bean
	public InetSocketAddress inetSocketAddress() {
		return new InetSocketAddress(port);
	}

	@Bean
	public ProtocolCodecFactory protocolCodecFactory() {
		return new MyCodeFactory();
	}

	@Bean
	public IoAcceptor ioAcceptor() throws Exception {

		IoAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("logger", loggingFilter());

		// 使用自定义编码解码工厂类
		chain.addLast("codec", new ProtocolCodecFilter(protocolCodecFactory()));
		// acceptor.getFilterChain().addLast("coderc", new ProtocolCodecFilter(new
		// SocketFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(ioHandler());

		acceptor.getSessionConfig().setReadBufferSize(2048);
		// acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		acceptor.bind(inetSocketAddress());
		LOG.warn("tcp服务开启_端口 ： " + port);
		return acceptor;
	}

}