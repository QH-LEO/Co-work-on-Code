package controller;

import dao.JdbcPool;
import utils.UUIDGenerator;
import utils.userinfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
//博客评论

public class comment {
    public userinfo insertcomment(userinfo u) throws SQLException {
        Connection conn= JdbcPool.getConnection();
        String UUID= UUIDGenerator.getUUID();
        String sql = "INSERT INTO comment values (" + "'"+UUID+ "'" + "," + "'" +u.getPname()+ "'" + "," + "'" +u.getOnchat()+ "'" + "," + "'" +u.getUname()+ "'" + "," + "'" +u.getPtime()+ "'" +")";
        Statement statement = conn.createStatement();
        statement.execute(sql);
        conn.setAutoCommit(false);
        conn.commit();
        u.setRs(1);
        JdbcPool.returnConnection(conn);
        return u;
    }

}
