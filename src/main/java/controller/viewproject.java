package controller;

import dao.JdbcPool;
import utils.userinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//项目查看详情
public class viewproject {
    public static userinfo details(userinfo user) throws SQLException {
        Connection conn = JdbcPool.getConnection();
        String sql = "select ptime,uname,area,pname,pd,love from project where pname="  +"'"+user.getPname()+"'";
        // System.out.println("sql=" + sql);

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
//                System.out.println(rs.next());
//                boolean t=true;
            if(rs.next()){
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

        Connection conn1 = JdbcPool.getConnection();
        String sql1 = "select ptime,uname,onchat from comment where pname="  +"'"+user.getPname()+"'";
        // System.out.println("sql=" + sql);

        try {
            Statement statement1 = conn1.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);
//                System.out.println(rs.next());
//                boolean t=true;
            int i=0,j=0,k=0;
            ArrayList<String> select =new ArrayList<String>();
            ArrayList<String> select1 =new ArrayList<String>();
            ArrayList<String> select2 =new ArrayList<String>();
            while(rs1.next()){
                select.add(rs1.getString(1));
                select1.add(rs1.getString(2));
                select2.add(rs1.getString(3));

            }
            String[] price = new String[select.size()];
            String[] price1 = new String[select1.size()];
            String[] price2 = new String[select2.size()];
            for(i=0;i<select.size();i++)
            {
                price[i]=select.get(i);
            }
            for(i=0;i<select1.size();i++)
            {
                price1[i]=select1.get(i);
            }
            for(i=0;i<select2.size();i++)
            {
                price2[i]=select2.get(i);
            }
            user.time=price;
            user.unames=price1;
            user.onchats=price1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setRs(1);
        JdbcPool.returnConnection(conn);
        JdbcPool.returnConnection(conn1);
        return user;
    }
}
