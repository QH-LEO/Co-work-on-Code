package controller;

import dao.JdbcPool;
import utils.userinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//我参与的合集
public class myinvolved {
    public userinfo involved(userinfo user) throws SQLException {
        Connection conn = JdbcPool.getConnection();
        String sql = "select pname from involved where uname="  +"'"+user.getUname()+"'";

        // System.out.println("sql=" + sql);

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
//                System.out.println(rs.next());
//                boolean t=true;
            int i=0;
            ArrayList<userinfo> select =new ArrayList<userinfo>();
            while (rs.next()){
                String temp=rs.getString(1);
                userinfo u=new userinfo();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
