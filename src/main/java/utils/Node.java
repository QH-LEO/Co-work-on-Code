package utils;

import java.util.ArrayList;
import java.util.List;


public class Node {
    public String id;          //结点id
    public String name;//结点名称显示层级和子节点个数“2/1”
    public String code;
    public List<Node> sonList=new ArrayList<>(5);//该结点的 子结点集合


    public String getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getSonList() {
        return sonList;
    }

    public void setSonList(List<Node> sonList) {
        this.sonList = sonList;
    }
}
