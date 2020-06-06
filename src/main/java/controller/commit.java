package controller;


import com.google.gson.JsonObject;
import dao.JdbcPool;
import utils.httpclient;
import utils.userinfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class commit {
    public userinfo con(userinfo u) throws SQLException {
        JsonObject jsonContainer =new JsonObject();
        jsonContainer.addProperty("pc", u.getPc());
        jsonContainer.addProperty("pname", u.getPname());
        jsonContainer.addProperty("td", u.getTd());
        jsonContainer.addProperty("tc", u.getTc());
        String port=null;
        Connection conn = JdbcPool.getConnection();
        String sql = "select port from project where pname=" + "'" + u.getPname() + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
//                System.out.println(rs.next());
//                boolean t=true;
            if (rs.next()) {
                 port=rs.getString(1);
                System.out.println(port);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url="http://localhost:"+port+"/recieve/rec";
        System.out.println(url);
        userinfo user=new userinfo();
        try {
            user.setPd("up");
            user=httpclient.post(jsonContainer,url,user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
