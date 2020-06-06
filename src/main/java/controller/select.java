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
import java.util.*;

public class select {
    public userinfo con(userinfo u) throws SQLException {
        JsonObject jsonContainer =new JsonObject();
        jsonContainer.addProperty("pname", u.getPname());
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
        String url="http://localhost:"+port+"/push/find";
        System.out.println(url);
        String body=null;
        userinfo user=new userinfo();
        try {
            user.setPd("select");
             user=httpclient.post(jsonContainer,url,user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List t1=user.getTcs();
        List t2=user.getTds();



        int tmp = (Integer) t1.get(0);
        for (Object value : t1) {
            if ((Integer)value > tmp) {
                tmp = (Integer)value;
            }
        }//max
//        ArrayList<Integer> select =new ArrayList<Integer>();
//        ArrayList<Integer> select1 =new ArrayList<Integer>();
        Map<Integer, List<Integer>> tar1 = new HashMap<Integer, List<Integer>>();
        for(int i=2;i<=tmp+1;i++){
            ArrayList<Integer> select =new ArrayList<Integer>();
            ArrayList<Integer> select1 =new ArrayList<Integer>();
            //i-1层进select
            for(int d=0;d<=t1.size()-1;d++){
                if((int)t1.get(d)==i-1){
                    int q=(int)t2.get(d);
                    select.add((int)t2.get(d));
                }
            }
            //max of i-1
            int tmp1 = select.get(0);
            for (int k = 0; k <=select.size()-1; k++) {
                if (select.get(k) > tmp1) {
                    tmp1 = select.get(k);
                }
            }
            //i 进 select1
            for(int d=0;d<=t1.size()-1;d++){
                if((int)t1.get(d)==i){
                    int q=(int)t2.get(d);
                    select1.add((int)t2.get(d));
                }
            }
            Iterator it = select1.iterator();
            while(it.hasNext()){

                System.out.println(it.next());
            }

            List<Integer> list = new ArrayList<Integer>();
            for (int e =1; e <= 5*tmp1; e++) {
                System.out.println(!select1.contains(e));
                if (!select1.contains(e)) {
                    int q=e;
                    list.add(e);
                }
            }
            tar1.put(i,list);
        }
        List<Integer> select2 =new ArrayList<Integer>();
        List<Integer> select3 =new ArrayList<Integer>();
        for(Map.Entry<Integer, List<Integer>> entry : tar1.entrySet()){
            int mapKey = entry.getKey();
            List<Integer> mapValue = entry.getValue();
            for (int integer : mapValue) {
                select2.add(mapKey);
                select3.add(integer);
            }
        }
        Iterator it = select2.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        Iterator it1 = select2.iterator();
        while(it1.hasNext()){
            System.out.println(it1.next());
        }
        int [] price = new int[select2.size()];
        int [] price1 = new int[select3.size()];
        for(int i=0;i<select2.size();i++)
        {
            price[i]=select2.get(i);
        }
        for(int i=0;i<select3.size();i++)
        {
            price1[i]=select3.get(i);
        }
        userinfo u1=new userinfo();
        u1.setTcss(price);
        u1.setTdss(price1);

        return u1;
    }
}
