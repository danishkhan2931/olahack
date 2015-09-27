package com.ola.olafriends;

import com.ola.olafriends.utils.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by thecodegame on 27-09-2015.
 */
public class app {

    private String firstName, lastName, id;


    public app(String firstName, String lastName, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static ArrayList<app> getFriendList(String body) {
        ArrayList<app> list = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(body);
            JSONArray friendArray = jObject.getJSONArray("friends");
            int len = friendArray.length();

            Debug.e("Len : " + len);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                JSONObject item = friendArray.getJSONObject(i);
                try {
                    String id = item.get("id").toString();
                    String firstName = item.get("first_name").toString();
                    String lastName = item.get("last_name").toString();
                    list.add(new app(firstName, lastName, id));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String toString() {
        return "(" + firstName + " " + lastName + "," + id + ")";
    }
}
