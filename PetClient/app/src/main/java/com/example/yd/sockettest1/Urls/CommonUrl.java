package com.example.yd.sockettest1.Urls;

/**
 * Created by 张YD on 2017/8/30.
 */

public class CommonUrl {
    //public static String parent_url="http://110.64.89.190:8080/ChatServer";
    public static String parent_url="http://172.20.10.2:8080/ChatServer";
    public static String chat_url=parent_url+"/ChatView";
    public static String chat_list_url=parent_url+"/ChatListServlet";
    public static String trans_url=parent_url+"/Transaction";
    public static String home_url=parent_url+"/Home";
    public static String home_register_url=parent_url+"/pet_home";
    public static String pet_register_url=parent_url+"/PetRegister";
    public static String pet_info_url=parent_url+"/Pet_Info";
    public static String order_url=parent_url+"/OrderList";
    public static String login_url=parent_url+"/Login?";
    public static String register_url=parent_url+"/Register";
    public static String URL_COMMENT = parent_url+"/CommentServlet";
    public static String URL_SENDCOMMENT = parent_url+"/SendCommentServlet";
    public static String URL_SENDTOPIC = parent_url+"/ExpressTopicServlet";
    public static String URL_TOPIC = parent_url+"/TopicServlet";
    public static String feed_url = parent_url+"/FeedBack";
    public static String eva_url=parent_url+"/Evaluation";
    public static String family_url=parent_url+"/FamilyInfo";
}
