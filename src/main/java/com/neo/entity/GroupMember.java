package com.neo.entity;


//群成员
public class GroupMember extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
    
	private String groupId;    // 群 id
	
	private String groupName;	//群名称
    
	private String userId;     //群成员的id
    
	private String username;    //群成员的名字
	
    // 冗余数据，不想关联查询
//  private String avatar;      //群成员的头像
//  private String sign;        //签名
//  private String createTime;   //加入时间

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
