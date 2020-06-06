package controller;

import dao.JdbcPool;
import docker.dockerport;
import utils.userinfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

//创建项目并为期创建容器分配交互端口
public class createproject {
    public userinfo create(userinfo u) throws SQLException, InterruptedException, IOException {
        Connection conn= JdbcPool.getConnection();
        String sql = "INSERT INTO project(pname,pd,area,uname,ptime) values (" + "'"+u.getPname()+ "'" + "," + "'" +u.getPd()+ "'" + "," + "'" +u.getArea()+ "'" + "," + "'" +u.getUname()+ "'" + "," + "'" +u.getPtime()+ "'" +")";
        Statement statement = conn.createStatement();
        statement.execute(sql);
        conn.setAutoCommit(false);
        conn.commit();
        JdbcPool.returnConnection(conn);
        Runtime rt = Runtime.getRuntime();
        int top=1;
        dockerport.put(top);
        while(dockerport.exist(top)) {
            Random random = new Random();
            top = random.nextInt(65535) % (65535 - 49152 + 1) + 49152;//私有端口
        }
        Process proc = rt.exec("docker run --name="+u.getPname()+"-d -p "+top+":8004 leo1");
//        int exitVal = 0;
//        try {
//            exitVal = proc.waitFor();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("ExitValue: " + exitVal);
        Connection conn1= JdbcPool.getConnection();
        String sql1 = "update project set port="+top+" where pname =" +"'"+u.getPname()+"'";
        Statement statement1 = conn1.createStatement();
        statement1.execute(sql1);
        conn1.setAutoCommit(false);
        conn1.commit();
        u.setRs(1);
        JdbcPool.returnConnection(conn1);
        return u;
    }
}




