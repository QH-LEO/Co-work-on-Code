package controller;

import Alicloud_service.SendSms;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import utils.userinfo;

/**
 * @Author: Hao Qin
 * @Date: 19-7-30  上午10:27
 * @Version 1.0
 *
 */
//阿里云短信发送接口
public class acceptsms {
    static Logger LOGGER=Logger.getLogger(acceptsms.class);
    public acceptsms(){}
    public userinfo send1(userinfo u){
        SendSms s=new SendSms();
        int i=s.send(u);
        Jedis jedis=new Jedis("127.0.0.1",6380);
        LOGGER.info("登陆redis");
        jedis.set(u.getPho(),String.valueOf(i));
        u.setRs(1);
        return u;
    }
}
