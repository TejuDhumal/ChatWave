// Purpose: Contains the UserIdsRequest class which is used to get the list of userIds from the request body.

package com.axis.team4.codecrafters.message_service.request;
import java.util.List;

public class UserIdsRequest {
    private List<Integer> userIds;

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
