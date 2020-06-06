package controller;

import redis.clients.jedis.Jedis;
import utils.userinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static controller.login.LOGGER;

//刷新首页，热门关键词推荐和热门博客推荐
public class indexinfo {
    public userinfo index(userinfo user) throws SQLException {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        LOGGER.info("登陆redis");
        Set<String> set=jedis.zrevrange("keyranks", 0, 4);
        Iterator<String> iter = set.iterator();
        ArrayList<String> select =new ArrayList<String>();
        int i = 0;
        while(iter.hasNext()) {
           select.add(iter.next());
        }
        String[] price = new String[select.size()];
        for(i=0;i<select.size();i++)
        {
            price[i]=select.get(i);
        }
        user.pks=price;


        Set<String> set1=jedis.zrevrange("scoreranks", 0, 15);
        Iterator<String> iter1 = set1.iterator();
        ArrayList<userinfo> select1 =new ArrayList<userinfo>();
        while(iter1.hasNext()) {
            String temp=iter1.next();
            System.out.println(temp);
            userinfo u=new userinfo();
            u.setPname(temp);

            select1.add(u);
        }
        userinfo[] price1 = new userinfo[select1.size()];
        for(int j=0;j<select1.size();j++)
        {
            price1[j]=select1.get(j);
            String SS=price1[j].getPname();
            System.out.println(SS);
        }
        user.info=price1;
        for(int k=0;k<=price1.length-1;k++){
            previewproject.simpledetail(user.info[k]);
        }
        return user;
    }
}
