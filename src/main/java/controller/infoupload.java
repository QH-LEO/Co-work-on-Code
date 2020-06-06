package controller;

import dao.JdbcPool;
import utils.UUIDGenerator;
import utils.userinfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//个人信息上传
public class infoupload {
    public userinfo upload(userinfo u) throws SQLException {
        Connection conn= JdbcPool.getConnection();
        String sql = "INSERT INTO codeinfo(uname,pho,area) values (" + "'"+u.getUname()+ "'" + "," + "'" +u.getPho()+ "'" + "," + "'" +u.getArea()+ "'" +")";
        Statement statement = conn.createStatement();
        statement.execute(sql);
        conn.setAutoCommit(false);
        conn.commit();
        u.setRs(1);
        JdbcPool.returnConnection(conn);
        return u;

    }
}
