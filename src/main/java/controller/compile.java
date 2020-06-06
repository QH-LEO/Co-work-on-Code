package controller;

import dao.JdbcPool;
import utils.UUIDGenerator;
import utils.userinfo;
import compile.CustomStringJavaCompiler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//代码编译返回信息
public class compile {
    public userinfo codeparse(userinfo u) throws SQLException {
        CustomStringJavaCompiler compiler = new CustomStringJavaCompiler(u.getPc());
        boolean res = compiler.compiler();
        if (res) {
            System.out.println("编译成功");
            System.out.println("compilerTakeTime：" + compiler.getCompilerTakeTime());
            try {
                compiler.runMainMethod();
                System.out.println("runTakeTime：" + compiler.getRunTakeTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            u.setTs(compiler.getRunResult());
            u.setRs(1);
        } else {
            System.out.println("编译失败");
            u.setRs(0);
            u.setTs(compiler.getCompilerMessage());
        }
        Connection conn= JdbcPool.getConnection();
        String sql = "INSERT INTO involved values (" + "'" +u.getUname()+ "'" + "," + "'" +u.getPname()+ "'" +")";
        Statement statement = conn.createStatement();
        statement.execute(sql);
        conn.setAutoCommit(false);
        conn.commit();
        JdbcPool.returnConnection(conn);
        return u;
    }


}
