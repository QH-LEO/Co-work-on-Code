package utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class httpclient {
    public static userinfo post(JsonObject json,String url,userinfo user) throws IOException {


        //       String url = "http://localhost:55983/recieve/rec";
//        String json = "{\"head\":{\"qn\":\"mydirectqueue\",\"id\":\"uuid\",\"ty\":0,\"h\":0}}";
//        JsonObject jsonContainer =new JsonObject();
//        //为当前的json对象添加键值对
//        jsonContainer.addProperty("pc", "nba");
//        jsonContainer.addProperty("pname", "lakers");
//        jsonContainer.addProperty("td", 1);
//        jsonContainer.addProperty("tc", 1);
//        System.out.println(jsonContainer.toString());
        //       String json ="{\"pc\":\"qqqqqq\",\"tc\":1,\"td\":8,\"pname\":\"qqqqqq\"}";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost;
        httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
        stringEntity.setContentEncoding("UTF-8");

        httpPost.setEntity(stringEntity);

        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

        //执行请求操作，并拿到结果（同步阻塞）


            CloseableHttpResponse response = null;
            String body = "";

            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
            System.out.println(body);
        Gson gson = new Gson();
        userinfo u = gson.fromJson(body, userinfo.class);


            if (!user.getPd().equals("up")) {
                JSONObject jsonObj = new JSONObject(body);
                JSONArray jsonObj1 = jsonObj.getJSONArray("tcs");
                JSONArray jsonObj2 = jsonObj.getJSONArray("tds");
                List idsList = jsonObj1.toList();
                u.setTcs(idsList);
                List idsList1 = jsonObj2.toList();
                u.setTds(idsList1);

            }
            return u;


    }}

