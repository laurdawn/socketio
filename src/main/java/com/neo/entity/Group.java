package com.neo.entity;

public class Group extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;          //群id
    private String name;   //群的名字
    private String ownerId;     //创建的人 id
    private String ownerName;   //创建人的名字，群主
    private String iconUrl;      //群的头像
    private String createTime;  //创建时间

    public String getId() {
        return id;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
