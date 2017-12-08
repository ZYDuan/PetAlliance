package socket;


public class MyBeans {
	private String userId;
	private String toUserId;
	private String chatContent;
	private String time;
	
	public void setUserId(String userId){
		this.userId=userId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setToUserId(String toUserId){
		this.toUserId=toUserId;
	}
	
	public String getToUserId(){
		return toUserId;
	}
	
	public void setChatContent(String chatContent){
		this.chatContent=chatContent;
	}
	
	public String getChatContent(){
		return chatContent;
	}
	
	public void setTime(String time){
		this.time=time;
	}
	
	public String getTime(){
		return time;
	}
}
