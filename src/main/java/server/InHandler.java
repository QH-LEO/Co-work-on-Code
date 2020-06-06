package server;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import server.Global;
import server.transaction;
import utils.message;
import utils.userinfo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Logger;


import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * @Author: Hao Qin
 * @Date: 19-8-15  下午2:09
 * @Version 1.0
 */
public class InHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger
            .getLogger(WebSocketServerHandshaker.class.getName());

    private WebSocketServerHandshaker handshaker;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 添加
        Global.group.add(ctx.channel());

        System.out.println("客户端与服务端连接开启"+ctx.channel().id());


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        // 移除
        Global.group.remove(ctx.channel());

        System.out.println("客户端与服务端连接关闭");

    }
//shutdown

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException, UnsupportedEncodingException {
        String json = "";
        ChannelHandlerContext ctx1 = ctx;
        Object msg1 = msg;
        logger.info("===================进入=======================");
        if (msg instanceof FullHttpRequest) {
            logger.info("http");

            json=handleHttpRequest(ctx, (FullHttpRequest) msg);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8)));
            response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN,"*");
//			response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS,"Origin, X-Requested-With, Content-Type, Accept");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

//            response = new DefaultFullHttpResponse(HTTP_1_1,
//                    OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "application/json");
//            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

//            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,"*");

            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.writeAndFlush(response);


        } else if (msg instanceof WebSocketFrame) {
            logger.info("websocket");
            json = handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
            //judgement_action(((TextWebSocketFrame) o).text(),ctx.channel());
        }
        logger.info("返回数据" + json);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private String handlerWebSocketFrame(ChannelHandlerContext ctx,
                                         WebSocketFrame frame) {


        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
                    .retain());
        }

        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return frame.content().retain().toString();
        }
        if (!(frame instanceof TextWebSocketFrame)) {


            System.out.println("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }

        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();

        System.out.println("服务端收到：" + request);

        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString()
                + ctx.channel().id() + "：" + request);

        // 群发
        Global.group.writeAndFlush(tws);
        return request;

    }

    private String handleHttpRequest(ChannelHandlerContext ctx,
                                     FullHttpRequest req) throws InterruptedException, UnsupportedEncodingException {
        String url = req.uri();
//		String URLFLAG=url.split("/")[0];
        if (url.equals("/websocket")) {
            if (!req.decoderResult().isSuccess()
                    || (!"websocket".equals(req.headers().get("Upgrade")))) {

                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                        HTTP_1_1, HttpResponseStatus.BAD_REQUEST));


            }

            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    "ws://0.0.0.0:8006" + url, null, false);
            handshaker = wsFactory.newHandshaker(req);

            if (handshaker == null) {
                WebSocketServerHandshakerFactory
                        .sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
            }

            return "ok";

        } else {
            message message = new message();
            System.out.println("read");
            FullHttpRequest fhr = req;
            System.out.println("请求的URL：" + fhr.uri());
            message.setUrl(fhr.uri());
            message.setFhr(fhr);
            ByteBuf buf = fhr.content();
            HttpHeaders head = fhr.headers();
//            System.out.println( head.toString());
            byte[] result1 = new byte[buf.readableBytes()];
            buf.readBytes(result1);
            String data = new String(result1, "utf8");
            System.out.println("读取的数据 :" + data);
            //  log.error("读取的数据:  "+data);
            message.setData(data);
            userinfo u= transaction.doServlet(message);
            String res=new Gson().toJson(u);
            System.out.println("res-----------"+res);
            FullHttpResponse response = null;
//            response = new DefaultFullHttpResponse(HTTP_1_1,
//                    OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
//            response.headers().set(CONTENT_TYPE, "application/json");
//            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
//            //kua yu 请求的URL：/PictureCheck/picturecheck
//            //读取的数据 :{"userid":123456,"url":"Picture/PM56czScDh.jpg"}
//            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
//            //  if (HttpHeaderUtil.isKeepAlive(this.message.getFhr())) {
//            response.headers().set(CONNECTION, KEEP_ALIVE);
//            ctx.flush();
//            ctx.write(message);
//            System.out.println("write");
            return res;
        }

    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {

        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static boolean isKeepAlive(FullHttpRequest req) {

        return false;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        cause.printStackTrace();
        ctx.close();

    }


}


