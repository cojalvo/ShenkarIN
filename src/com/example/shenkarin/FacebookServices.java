package com.example.shenkarin;

import java.util.LinkedList;
import java.util.List;

import android.app.ProgressDialog;

import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.ParseFacebookUtils;

public class FacebookServices
{
	public static List<GraphUser> getFriendsList()
	{
		final List<GraphUser> returnList=new LinkedList<GraphUser>();
		Session session=ParseFacebookUtils.getSession();
		if(session.isOpened())
		{
			Request friendRequest=Request.newMyFriendsRequest(session, new GraphUserListCallback() {
				
				@Override
				public void onCompleted(List<GraphUser> users, Response response) 
				{
					for (GraphUser graphUser : users) {
						returnList.add(graphUser);
					}
				}
			});
			friendRequest.executeAsync();
		}
		return returnList;
	}

}
