package com.example.zika.zivojin_dragic_30_hills_spring_internshi_developer_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private final String DATA_HUMAN = "http://zindra.rs/data/data.json";
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ArrayList<HumanObject> mListHuman = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, DATA_HUMAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //Create list of Humans from JSON file - parsing
                mListHuman = parsing(response);


                //Catching Friends of friends
                mListHuman = calculateFriendsOfFriends(mListHuman);


                //Setting up adapter and get names to frends and frends of friends lists of Integers - ID's
                mListHuman = setNames(mListHuman);

                HumanListAdapter adapter = new HumanListAdapter(mListHuman, getApplicationContext(), R.layout.list_human_item);

                mListView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Glupost neka se desila, proverite internet konekciju", Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(mStringRequest);
    }

    //Method for parsing JSON data into HumanItem list

    private ArrayList<HumanObject> parsing(String json) {

        try {
            JSONObject JSONObjectHuman = new JSONObject(json);

            JSONArray pomHumanListJSON = JSONObjectHuman.getJSONArray("humans");


            for (int i = 0; i < pomHumanListJSON.length(); i++) {
                JSONObjectHuman = pomHumanListJSON.getJSONObject(i);

                HumanObject human = new HumanObject();

                human.setName(JSONObjectHuman.getString("firstName") + " " + JSONObjectHuman.getString("surname"));

                ArrayList<Integer> pomListofFriends = new ArrayList<>();

                JSONArray JSONListOfFriends = JSONObjectHuman.getJSONArray("friends");

                for (int j = 0; j < JSONListOfFriends.length(); j++) {

                    //setting list of my friends
                    if (JSONListOfFriends != null) {

                        pomListofFriends.add((Integer) JSONListOfFriends.get(j));
                        human.setMyFriends(pomListofFriends);
                    }
                }
                mListHuman.add(human);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return mListHuman;
    }

    //setting up names for lists that contains integers
    private ArrayList<HumanObject> setNames(ArrayList<HumanObject> humanList) {

        ArrayList<HumanObject> list = new ArrayList<>();

        for (int i = 0; i < humanList.size(); i++) {
            HumanObject human = new HumanObject();

            human.mName = humanList.get(i).getName();

            human.mFriendsList = "";

            human.mFriendsOfFriendsList = "";

            //set names to Friends list

            for (int j = 0; j < humanList.get(i).getMyFriends().size(); j++) {

                int friendID = humanList.get(i).getMyFriends().get(j);

                //examing if this is last item in list
                if (j == humanList.get(i).getMyFriends().size() - 1) {
                    human.mFriendsList = human.mFriendsList + humanList.get(friendID - 1).getName() + ".";
                } else {
                    human.mFriendsList = human.mFriendsList + humanList.get(friendID - 1).getName() + ", ";
                }

            }

            //set names to Friends of Friends listsw
            for (int j = 0; j < humanList.get(i).getFriendsOfFriends().size(); j++) {

                int friendID = humanList.get(i).getFriendsOfFriends().get(j);

                //examing if this is last item in list
                if (j == humanList.get(i).getFriendsOfFriends().size() - 1) {
                    human.mFriendsOfFriendsList = human.mFriendsOfFriendsList + humanList.get(friendID - 1).getName() + ".";
                } else {
                    human.mFriendsOfFriendsList = human.mFriendsOfFriendsList + humanList.get(friendID - 1).getName() + ", ";
                }
            }

            list.add(human);
        }
        return list;
    }

    private ArrayList<HumanObject> calculateFriendsOfFriends(ArrayList<HumanObject> humanList) {


        ArrayList<HumanObject> list = new ArrayList<>();

        for (int i = 0; i < humanList.size(); i++) {
            HumanObject human = new HumanObject();

            // creating temp HashSet list to avoid duplicate items in friends of friends list

            HashSet<Integer> fofList = new HashSet<>();

            for (int j = 0; j < humanList.get(i).getMyFriends().size(); j++) {

                //get ID of my direct friend
                int friendID = humanList.get(i).getMyFriends().get(j);


                for (int k = 0; k < humanList.get(friendID - 1).getMyFriends().size(); k++) {

                    //get ID of a friend's friend
                    int fofID = humanList.get(friendID - 1).getMyFriends().get(k);

                    //dont put mine id in friends of friends list
                    if (fofID - 1 != i) {
                        fofList.add(fofID);

                    }
                }
            }

            //removing my direct friends from friends of friends list
            for (int j = 0; j < humanList.get(i).getMyFriends().size(); j++) {
                fofList.remove(humanList.get(i).getMyFriends().get(j));
            }

            //Make ArrayList of temp HashSet
            ArrayList<Integer> fofArrayList = new ArrayList<>(fofList);

            //setting up Name, Frends List and FoF list
            human.setFriendsOfFriends(fofArrayList);
            human.setMyFriends(humanList.get(i).getMyFriends());
            human.setName(humanList.get(i).getName());

            list.add(human);

        }
        return list;
    }
}
