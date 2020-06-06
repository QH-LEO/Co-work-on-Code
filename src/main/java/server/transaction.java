package server;

import com.google.gson.Gson;

import controller.*;
import org.apache.log4j.Logger;
import utils.message;
import utils.userinfo;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Hao Qin
 * @Date: 19-8-15  下午3:40
 * @Version 1.0
 */
public class transaction {

    public static userinfo doServlet(message message) {
        Logger LOGGER=Logger.getLogger(utils.message.class);
        String url = message.getUrl();
        LOGGER.info("this is message get url"+url);
        String gson = message.getData();
        String response = "{\"state\":0}";
        userinfo u=new userinfo();
        String[] result = url.split("/");
        Class cls = null;
        Gson g = new Gson();
        Constructor con = null;
        try {

            LOGGER.info("请求的资源： " + result[1] + "===" + result[2]);
            //result[1]=============类名
            //result[2]==============方法名

            //1登陆=========================================================================================================
            switch (result[1]) {
                case "Judge_Login":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    Judge_Login l= (Judge_Login)con.newInstance();
                    Method doj=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) doj.invoke(l,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "acceptsms":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    acceptsms a= (acceptsms)con.newInstance();
                    Method doa=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) doa.invoke(a,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "comment":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    comment i= (comment)con.newInstance();
                    Method doi=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) doi.invoke(i,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "compile":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    compile j=(compile) con.newInstance();
                    Method doj1=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) doj1.invoke(j,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "createproject":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    createproject lll= (createproject)con.newInstance();
                    Method ddd=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) ddd.invoke(lll,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "indexinfo":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    indexinfo qqq= (indexinfo)con.newInstance();
                    Method dqq=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dqq.invoke(qqq,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "infoupload":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    infoupload lllq= (infoupload)con.newInstance();
                    Method dddq=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddq.invoke(lllq,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "love":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    love lllw= (love)con.newInstance();
                    Method dddw=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddw.invoke(lllw,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "mycreate":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    mycreate llls= (mycreate)con.newInstance();
                    Method ddds=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) ddds.invoke(llls,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "myinvolved":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    myinvolved lllu= (myinvolved)con.newInstance();
                    Method dddu=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddu.invoke(lllu,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "mylove":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    mylove llli= (mylove)con.newInstance();
                    Method dddi=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddi.invoke(llli,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "previewproject":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    previewproject llliq= (previewproject)con.newInstance();
                    Method dddiq=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddiq.invoke(llliq,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "viewproject":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    viewproject llliqw= (viewproject)con.newInstance();
                    Method dddiqw=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddiqw.invoke(llliqw,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "search":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    search llliwqw= (search)con.newInstance();
                    Method dddiwqw=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddiwqw.invoke(llliwqw,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "commit":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    commit llliwqw1= (commit)con.newInstance();
                    Method dddiwqw1=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddiwqw1.invoke(llliwqw1,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "continuing":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    continuing llliwqw11= (continuing)con.newInstance();
                    Method dddiwqw11=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddiwqw11.invoke(llliwqw11,new Gson().fromJson(gson,userinfo.class));
                    return u;
                case "select":
                    cls=Class.forName("controller."+result[1]);
                    con=cls.getConstructor();
                    select llliwqw121= (select)con.newInstance();
                    Method dddiwqw121=cls.getMethod(result[2], userinfo.class);
                    u= (userinfo) dddiwqw121.invoke(llliwqw121,new Gson().fromJson(gson,userinfo.class));
                    return u;
//                case "":
//                    return u;
//                case "":
//                    return u;
            }
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return u;
    }

}