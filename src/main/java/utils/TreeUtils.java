package utils;

import java.util.ArrayList;
import java.util.List;

//进行项目版本控制的多叉树
public class TreeUtils {

    /**
     * 插入新结点          输入父结点id进行定位 ，将新结点 插入到父结点的 sonList 中
     * @param changeNode  传入根结点,传入前需判断:若根结点不存在，待插入结点成为根结点，不必进入此方法
     * @param fatherId    新结点的 父结点id
     * @param newNode     新结点
     */
    public static void insert(Node changeNode, String fatherId, Node newNode) {
        if (fatherId.equals(changeNode.getId())) {
            changeNode.getSonList().add(newNode);
            return;
        }

        List<Node> sonList = changeNode.getSonList();
        if (sonList == null || sonList.isEmpty()) {
            return;                            //若该结点 的子结点集合为空 返回
        } else {

            for (int i = 0; i < sonList.size(); i++) {
                insert(sonList.get(i), fatherId, newNode);
            }
        }

    }

    /**
     * 遍历结点 并打印. 同时按每个结点所在深度 在结点前打印不同长度的空格
     * @param changeNode    根结点
     * @param depth        结点深度：1
     */
    public static  void queryAll(Node changeNode, int depth){
        List<Node> sonList = changeNode.getSonList();
        String space = generateSpace(depth);    //根据深度depth,返回(depth*3)长度的空格

        if (sonList==null||sonList.isEmpty()){
            return;
        }

        for (int i = 0; i <sonList.size() ; i++) {
            System.out.println(space+"---"      //打印空格 和结点id，name
                    +"<"+sonList.get(i).getId()+">"
                    +sonList.get(i).getName());

            if(i==0){
                depth = depth+1;  //结点深度+1，每个for循环仅执行一次。作为子结点sonList.get(i)的深度
            }
            queryAll(sonList.get(i),depth);
        }

    }

    /**
     * 删除结点   注意:先判断 是否在删除 根结点. 若删除根结点,不必进入此方法 直接为null即可
     * @param changeNode 根结点
     * @param id         待删除结点id
     */
    public static void delete(Node changeNode, String id) {
        List<Node> sonList = changeNode.getSonList();

        if (sonList == null || sonList.isEmpty()) {
            return;
        } else {

            for (int i = 0; i < sonList.size(); i++) {

                if(id.equals(sonList.get(i).getId())){
                    sonList.remove(i);
                    delete(new Node(),id);
                    break;
                }else{
                    delete(sonList.get(i), id);
                }

            }
        }

    }

    /**
     * 根据结点id  修改结点 name       //同理可根据结点name修改结点id
     * @param changeNode 根结点
     * @param id         结点id
     * @param name       修改后的 新name
     */
    public static void update(Node changeNode, String id, String name) {
        if (changeNode.getId().equals(id)){
            changeNode.setName(name);
            return;
        }

        List<Node> sonList = changeNode.getSonList();
        if (sonList == null || sonList.isEmpty()) {
            return;
        } else {

            for (int i = 0; i < sonList.size(); i++) {
                update(sonList.get(i), id,name);
            }
        }

    }

    /**
     * 查询 某个结点 到根结点的路径
     * @param changeNode 根结点
     * @param name       待查找的结点 name
     * @param wayList    路径
     */
    public static void queryWayById(Node changeNode, String name, ArrayList<String> wayList) {
        List<Node> sonList = changeNode.getSonList();

        wayList.add(changeNode.getName());
        if (sonList == null || sonList.isEmpty()) {
            return;
        } else {
            for (int i = 0; i < sonList.size(); i++) {

                if(name.equals(sonList.get(i).getName())){
                    for (int j = 0; j < wayList.size(); j++) {
                        System.out.print(wayList.get(j)+"->");
                    }
                    System.out.println(sonList.get(i).getName());
                    break;
                }
                queryWayById(sonList.get(i), name, wayList);

            }
        }

    }

    //打印空格
    public static String generateSpace(int count) {
        count = count*3;
        char[] chs = new char[count];
        for(int i = 0; i < count; i++) {
            chs[i] = ' ';
        }
        return new String(chs);
    }
    //返回当前子节点的子节点集合长度，前端判断小于五开放一切功能，等于五关闭续写功能
    public static int judge(Node root,int x){
        int i=0;
        return i;


    }

}
