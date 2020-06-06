package controller;

/**
 * @Author: Hao Qin
 * @Date: 19-7-25  下午10:36
 * @Version 1.0
 */

import dao.JdbcPool;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import utils.userinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

//被调用的，ctrl点进去就行
public class login{
    public login(){}

    static Logger LOGGER=Logger.getLogger(login.class);

    public static userinfo Login(userinfo user) throws SQLException {
        System.out.println("前端传入的手机号是："+user.getPho());
        String number="";
        Jedis jedis=new Jedis("127.0.0.1",6379);
        LOGGER.info("登陆redis");
//        Set ss=jedis.keys(user.getPho());
//        Iterator iterator=ss.iterator();
//        while(iterator.hasNext()){
//            number= (String) iterator.next();
//        }
        if(jedis.exists(user.getPho())){
            System.out.println("从redis内匹配到用户");
            LOGGER.info("redis Login");
            user.setRs(1);
//            jedis.close();
            System.out.println("开始查询sql");
            LOGGER.info("MYSQL login");
            Connection conn = JdbcPool.getConnection();
            String sql = "select pho,uname,area from codeinfo where pho=" +"'"+user.getPho()+"'";
            // System.out.println("sql=" + sql);
            LOGGER.debug("this is in search mysql" + sql);
            try {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
//                System.out.println(rs.next());
//                boolean t=true;
                if(rs.next()){
                    LOGGER.info("mysql 成功匹配");
                    user.setUname(rs.getString(2));
                    user.setArea(rs.getString(3));
                }
                JdbcPool.returnConnection(conn);
            } catch (SQLException e) {
                LOGGER.debug("...老哥干啥玩意呢？？？", e);
            }

        }else {
            System.out.println("JEDIS没有匹配到");
            System.out.println("开始查询sql");
            LOGGER.info("MYSQL login");
            Connection conn = JdbcPool.getConnection();
            String sql = "select pho,uname,area from codeinfo where pho="  +"'"+user.getPho()+"'";
            // System.out.println("sql=" + sql);
            LOGGER.debug("this is in search mysql" + sql);
            try {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
//                System.out.println(rs.next());
//                boolean t=true;
                if(rs.next()){
                    LOGGER.info("mysql 成功匹配");
                    user.setRs(1);
                    jedis.set(user.getPho(),String.valueOf(1) );
                    jedis.expire(user.getPho(), 30 * 24 * 60);
                    //设置失效时间
                    System.out.println("redis 更新了一条数据  key=" + user.getPho());
                    jedis.close();
                    user.setUname(rs.getString(2));
                    user.setArea(rs.getString(3));
                }
                else{
                    user.setRs(0);
                    LOGGER.info("查无此人");
                }
            } catch (SQLException e) {
                LOGGER.debug("...老哥干啥玩意呢？？？", e);
            }
            JdbcPool.returnConnection(conn);
        }
        return user;
    }
}
