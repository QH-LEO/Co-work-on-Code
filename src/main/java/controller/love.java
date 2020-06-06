package controller;

import dao.JdbcPool;
import redis.clients.jedis.Jedis;
import utils.UUIDGenerator;
import utils.userinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static controller.login.LOGGER;


//点赞，并计入排名
public class love {
    public userinfo love(userinfo u) throws SQLException {
        Connection conn2= JdbcPool.getConnection();
        String sql2 = "select love from project  where pname="  +"'"+u.getPname()+"'";
        Statement statement2 = conn2.createStatement();
        ResultSet rs = statement2.executeQuery(sql2);
        int i=0;
        Jedis jedis=new Jedis("127.0.0.1",6379);
        LOGGER.info("登陆redis");
        if (rs.next()){
            int temp=rs.getInt(1);
            System.out.println(temp);
            if(temp==0){
                Connection conn3 = JdbcPool.getConnection();
                String sql3 = "update project set love=1 where pname =" +"'"+u.getPname()+"'";
                Statement statement3 = conn3.createStatement();
                statement3.execute(sql3);
                conn3.setAutoCommit(false);
                conn3.commit();
                JdbcPool.returnConnection(conn3);
                jedis.zadd("scoreranks", 1, u.getPname());
            }else {
                    Connection conn4 = JdbcPool.getConnection();
                    String sql4 = "update project set love=love+1 where pname =" +"'"+u.getPname()+"'";
                    Statement statement4 = conn4.createStatement();
                    statement4.execute(sql4);
                    conn4.setAutoCommit(false);
                    conn4.commit();
                    JdbcPool.returnConnection(conn4);
                    jedis.zincrby("scoreranks",1,u.getPname());
            }


            u.setRs(1);

        }

        JdbcPool.returnConnection(conn2);
        return u;
    }
}
