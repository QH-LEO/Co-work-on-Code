package controller;

import dao.JdbcPool;
import utils.userinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//符合条件项目的不需要评论的展示
public class previewproject {
    public static userinfo simpledetail(userinfo user) throws SQLException {
        Connection conn = JdbcPool.getConnection();
        String sql = "select ptime,uname,area,pname,pd,love from project where pname=" + "'" + user.getPname() + "'";
        // System.out.println("sql=" + sql);
        System.out.println(user.getPname());
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
//                System.out.println(rs.next());
//                boolean t=true;
            if (rs.next()) {
                user.setPtime(rs.getString(1));
                user.setUname(rs.getString(2));
                user.setArea(rs.getString(3));
                user.setPname(rs.getString(4));
                user.setPd(rs.getString(5));
                user.setLove(rs.getInt(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setRs(1);
        return user;
    }
}
