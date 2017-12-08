package com.example.yd.sockettest1.Activity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Home on 2017/8/12.
 */

public class Topic {
    private String name;
    private String content;
    //构造函数
    public Topic(String name, String content) {
        this.name = name;
        this.content = content;

    }
    // 返回一个Teacher的列表
    public static List<Topic> getAllTopic() {
        List<Topic> teachers = new ArrayList<Topic>();
        teachers.add(new Topic("CNdota", "2017.8.12"));
        teachers.add(new Topic("CNlol",  "2017.8.12"));
        teachers.add(new Topic("OW", "2017.8.12"));

        return teachers;
    }
    // 以下都是访问内部属性的getter和setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // 以下都是访问内部属性的getter和setter

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
