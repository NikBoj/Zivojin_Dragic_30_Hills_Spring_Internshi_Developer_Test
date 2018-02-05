package com.example.zika.zivojin_dragic_30_hills_spring_internshi_developer_test;

import java.util.ArrayList;

/**
 * Created by Zika on 05-Feb-18.
 */

public class HumanObject {

    String name;

    ArrayList<Integer> myFriends;
    ArrayList<Integer> friendsOfFriends;

    public String mName, mFriendsList, mFriendsOfFriendsList;

    public HumanObject(String name, ArrayList<Integer> friends, ArrayList<Integer> friendsOfFriends) {
        this.myFriends = friends;
        this.friendsOfFriends = friendsOfFriends;
        this.name = name;
    }

    public HumanObject() {
    }

    public void setMyFriends(ArrayList<Integer> myFriends) {
        this.myFriends = myFriends;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<Integer> getMyFriends() {
        return myFriends;
    }

    public String getName() {
        return name;
    }

    public void setFriendsOfFriends(ArrayList<Integer> friendsOfFriends) {
        this.friendsOfFriends = friendsOfFriends;
    }

    public ArrayList<Integer> getFriendsOfFriends() {
        return friendsOfFriends;
    }
}
