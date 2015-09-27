package com.ola.olafriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ola.olafriends.utils.Debug;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.google.gson.stream.JsonReader;

public class AvailableCab extends AppCompatActivity {

    double lat, longg;

    class Booking {
        int crn;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cab);

        Debug.check();
        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        longg = b.getDouble("long");
        Debug.bundle(b);
        Debug.check();


        new Thread() {
            @Override
            public void run() {
                Debug.check();


                String fetchUrl = "http://sandbox-t.olacabs.com/v1/bookings/create?pickup_lat="+lat+"&pickup_lng="+longg+"&category=sedan&pickup_mode=NOW";

                Debug.e(fetchUrl);
                HttpURLConnection myURLConnection = null;
                try {
                    myURLConnection = (HttpURLConnection) new URL(fetchUrl).openConnection();
                    myURLConnection.setRequestProperty("Authorization", "Bearer c3ea2bee92fb4464ace21dc9fed2dff4");
                    myURLConnection.setRequestProperty("X-APP-Token", "85d0bc63a42844efbcb4009f7b679527");
                    myURLConnection.setRequestMethod("GET");
                    myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    myURLConnection.setRequestProperty("Content-Language", "en-US");
                    myURLConnection.setUseCaches(false);
                    myURLConnection.setDoInput(true);
                    myURLConnection.setDoOutput(true);

                    myURLConnection.connect();
                    InputStream in = new BufferedInputStream(myURLConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    System.out.println(out.toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                } finally {
                    myURLConnection.disconnect();
                }
            }
        }.start();

        //new AsyncTaskNew().execute();




    /*

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                AppConstants.OLA_BOOKING_URL,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Debug.toastLong(response.toString(), getApplicationContext(), true);
                        Debug.i(response.toString());
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Debug.toastLong("Error: " + error.toString(), getApplicationContext(), true);
                        Debug.i(error.toString() + error.getMessage()+" "+error.networkResponse);
                    }
                }


        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Debug.check();
                //Map headers = new HashMap();
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("X-APP-Token", "85d0bc63a42844efbcb4009f7b679527");
                headers.put("Authorization", "Bearer c3ea2bee92fb4464ace21dc9fed2dff4");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Debug.check();
                Map<String, String> mParams = new HashMap<String, String>();
                mParams.put("pickup_lat", Double.toString(lat));
                mParams.put("pickup_lng", Double.toString(longg));
                mParams.put("pickup_mode", "NOW");
                mParams.put("category", "sedan");

                return mParams;
            }
        };
        OlaFriendsApplication.getInstance().getRequestQueue().add(request);
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_available_cab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /*class AsyncTaskNew extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            Debug.check();


            String fetchUrl = "http://sandbox-t.olacabs.com/v1/bookings/create?pickup_lat="+lat+"&pickup_lng="+longg+"&category=sedan&pickup_mode=NOW";

            Debug.e(fetchUrl);
            HttpURLConnection myURLConnection = null;
            try {
                myURLConnection = (HttpURLConnection) new URL(fetchUrl).openConnection();
                myURLConnection.setRequestProperty("Authorization", "Bearer c3ea2bee92fb4464ace21dc9fed2dff4");
                myURLConnection.setRequestProperty("X-APP-Token", "85d0bc63a42844efbcb4009f7b679527");
                myURLConnection.setRequestMethod("GET");
                myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                myURLConnection.setUseCaches(false);
                myURLConnection.setDoInput(true);
                myURLConnection.setDoOutput(true);

                myURLConnection.connect();
                InputStream in = new BufferedInputStream(myURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                System.out.println(out.toString());
            }
            catch(Exception e){
                e.printStackTrace();
            } finally {
                myURLConnection.disconnect();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String body) {
            Debug.e(body);
            Debug.toastLong(body, getApplicationContext(),true);
*//*            friendList = appUser.getFriendList(body);

            for (appUser s : friendList) {
                friends.add(s.getFirstName() + " " + s.getLastName());
                Debug.i(s.toString());
            }

            Debug.check();
            adapter.notifyDataSetChanged();

            // Turn it on
            setProgressBarIndeterminateVisibility(false);

            // run a background job and once complete
            pb.setVisibility(ProgressBar.INVISIBLE);*//*
        }


    }
*/

}
