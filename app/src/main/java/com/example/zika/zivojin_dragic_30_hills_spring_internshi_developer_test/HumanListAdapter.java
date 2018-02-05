package com.example.zika.zivojin_dragic_30_hills_spring_internshi_developer_test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zika on 05-Feb-18.
 */

public class HumanListAdapter extends BaseAdapter {

    private ArrayList<HumanObject> mHumanList;
    LayoutInflater mInflater;
    private Context mContext;
    private int mRecourceId;

    public HumanListAdapter(ArrayList<HumanObject> list, Context context, int recourceId) {

        this.mHumanList = list;
        this.mContext = context;
        this.mRecourceId = recourceId;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mHumanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHumanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        HolderClass holder;

        if (row == null) {
            row = mInflater.inflate(mRecourceId, parent, false);

            holder = new HolderClass();

            holder.txtName = row.findViewById(R.id.txtViewName);
            holder.txtMyFriends = row.findViewById(R.id.friendsView);
            holder.txtFriendsOfFriends = row.findViewById(R.id.friendsOfFriendsView);

            row.setTag(holder);

        } else {
            holder = (HolderClass) row.getTag();
        }

        HumanObject human = mHumanList.get(position);

        holder.txtName.setText(human.mName);
        holder.txtMyFriends.setText(human.mFriendsList);
        holder.txtFriendsOfFriends.setText(human.mFriendsOfFriendsList);

        if (position%2 == 0) {
            row.setBackgroundResource(R.color.bcgLightGray);
        } else {
            row.setBackgroundResource(R.color.bcgWhite);
        }


        return row;
    }

    public class HolderClass {

        public TextView txtName, txtFriendsOfFriends, txtMyFriends;
    }
}
