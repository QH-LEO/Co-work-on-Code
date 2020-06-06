package controller;


import dao.JdbcPool;
import redis.clients.jedis.Jedis;
import utils.userinfo;

import java.sql.*;
import java.util.ArrayList;

import static controller.login.LOGGER;
//关键词搜索
public class search {
    public userinfo seek(userinfo user) throws SQLException {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        LOGGER.info("登陆redis");
        if(jedis.zscore("keyranks",user.getPname())==null){
            jedis.zadd("keyranks",1,user.getPname());
        }else{
            jedis.zincrby("keyranks",1,user.getPname());
        }
        Connection conn = JdbcPool.getConnection();
        String sql = "select pname from project where pname like ?";

        // System.out.println("sql=" + sql);

        try {
//            PreparedStatement statement = (PreparedStatement) conn.createStatement();
//            ResultSet rs = statement.executeQuery(sql);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + user.getPname() + "%");
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            ArrayList<userinfo> select =new ArrayList<userinfo>();
            while (rs.next()) {
                String temp = rs.getString(1);
                userinfo u = new userinfo();
                u.setPname(temp);
                select.add(u);
            }
            userinfo[] price = new userinfo[select.size()];
            for(i=0;i<select.size();i++)
            {
                price[i]=select.get(i);
            }
            user.info=price;
            for(int j=0;j<=price.length-1;j++){
                previewproject.simpledetail(user.info[j]);

            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        user.setRs(1);
     return  user;
    }
}
