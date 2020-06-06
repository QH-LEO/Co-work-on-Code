package controller;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import utils.userinfo;

import java.sql.SQLException;

/**
 * @Author: Hao Qin
 * @Date: 19-7-30  上午2:39
 * @Version 1.0
 */
//判断是否可以登录，包括校验验证码和登录失效
public class Judge_Login {
    static Logger LOGGER=Logger.getLogger(Judge_Login.class);
    public userinfo judgelogin(userinfo u)throws SQLException {
//        CountDownLatch begin = new CountDownLatch(2);
//        SendSms s=new SendSms();
//        int i=s.send(u);
//        begin.countDown();
        Jedis jedis=new Jedis("127.0.0.1",6380);
        LOGGER.info("登陆redis");
        String temp="false";
        if(jedis.get(u.getPho())!=null) {
            temp = jedis.get(u.getPho());
        }
        System.out.println(temp);
        System.out.println(u.getCode());
        if(temp.equals(u.getCode())){
            System.out.println("666");
            jedis.del(u.getPho());
            login.Login(u);
            u.setRs(1);

        }
        return u;
    }

}