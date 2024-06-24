// Purpose: Contains the request for renaming a group.

package com.axis.team4.codecrafters.message_service.request;

public class RenameGroupRequest {

	private String groupName;
	private String groupImage;

	public RenameGroupRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public RenameGroupRequest(String groupName) {
		super();
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}
	
}
