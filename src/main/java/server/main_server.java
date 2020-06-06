package server;

import dao.JdbcPool;
import docker.dockerport;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import org.apache.log4j.Logger;



import java.util.concurrent.Executors;


/**
 * @Author: Hao Qin
 * @Date: 19-7-25  下午7:46
 * @Version 1.0
 */
public class main_server {


    private static Logger log = Logger.getLogger(main_server.class);
    public void startinbound(int port) throws Exception {
        EventLoopGroup bossGroup = new EpollEventLoopGroup(); //mainReactor    1个线程
        EventLoopGroup workerGroup = new EpollEventLoopGroup( );   //subReactor       线程数量等价于cpu个数+1
        try {
            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            b.group(bossGroup, workerGroup).channel(EpollServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            ch.pipeline().addLast(new HttpRequestDecoder());//有两次FIle操作
                            ch.pipeline().addLast(new HttpObjectAggregator(65535));//把上一句的两次File操作聚合在一起
                            ch.pipeline().addLast(new ChunkedWriteHandler());//Chunked是一种报文，处理后返回去，报文回去查一下
                            //  ch.pipeline().addLast(new Handle1());
                            // ch.pipeline().addLast(new CheckHandler());
//                            ch.pipeline().addLast( new OutHandler());
                            ch.pipeline().addLast( new InHandler());
                            // 就会有两次File处理（一次头处理，一次体处理）
                        }

                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)     //重用地址
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)// heap buf 's better
                    .childOption(ChannelOption.SO_RCVBUF, 1048576)
                    .childOption(ChannelOption.SO_SNDBUF, 1048576);
            //ChannelFuture f = b.bind(port).sync();
            ChannelFuture f=b.bind("0.0.0.0",port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        JdbcPool connPool = new JdbcPool("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/smart"
                , "leo", "123456");
        try {
            connPool.createPool();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        main_server serverIn = new main_server();
        serverIn.startinbound(8005);
        log.info("sever is starting..........wait");


    }

}
