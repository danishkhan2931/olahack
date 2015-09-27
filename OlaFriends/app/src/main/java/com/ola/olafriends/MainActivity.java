package com.ola.olafriends;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ola.olafriends.utils.Debug;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<SplitwiseUser> friendList;
    ArrayList<String> friends;
    ListView listView;
    ArrayAdapter<String> adapter;
    Button btn;

    ProgressBar pb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.list_view_selectable);

        pb = (ProgressBar) findViewById(R.id.pbLoading);



        // Turn it on
        setProgressBarIndeterminateVisibility(true);
        listView = (ListView) findViewById(R.id.list);
        btn = (Button) findViewById(R.id.testbutton);
        btn.setOnClickListener(this);
        friends = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, friends);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        Debug.check();

        pb.setVisibility(ProgressBar.VISIBLE);


        new AsyncTaskNew().execute();
        Debug.check();

        /*
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConstants.SPLITWISE_CURRENT_USER,
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
                                Debug.toastLong("Error: "+error.toString(), getApplicationContext(), true);
                                Debug.i(error.toString());
                            }
                        }



                );*//* {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("SPLITWISE_oauth_consumer_key", "Yahoo");
                //headers.put("ANOTHER_CUSTOM_HEADER", "Google");
                return headers;
            }
        };*/
        // OlaFriendsApplication.getInstance().getRequestQueue().add(request);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    class AsyncTaskNew extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            Debug.check();

/*
            OAuthService service = OlaFriendsApplication.getOAuthService();
            Token requestToken = service.getRequestToken();

            //token
            Debug.i(requestToken.getToken());

            Debug.i("TOKEN: " + requestToken.getToken());
            Debug.i("SECRET: "+requestToken.getSecret());
            final String authorizationUrl = service.getAuthorizationUrl(requestToken);
            Debug.i("Authorization Url: "+authorizationUrl);



            //hard code
            Verifier v = new Verifier("r5dv6QOCVTe7hJywtkuG");
            Debug.check();


            Token newAccessToken = new Token(requestToken.getToken(), requestToken.getSecret(), null);
            final OAuthRequest request = new OAuthRequest(Verb.GET,AppConstants.SPLITWISE_AUTHORIZE);
            service.signRequest(newAccessToken, request);

            Token accessToken = service.getAccessToken(requestToken, v);
            Debug.i(accessToken.toString());

            */

            OAuthService service = OlaFriendsApplication.getOAuthService();
            Token newAccessToken = new Token(AppConstants.SPLITWISE_USERNAME, AppConstants.SPLITWISE_PASSWORD);
            final OAuthRequest request = new OAuthRequest(Verb.GET, AppConstants.SPLITWISE_FRIEND_LIST);
            service.signRequest(newAccessToken, request);
            Response response = request.send();
            String body = response.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String body) {
            Debug.e(body);
            friendList = SplitwiseUser.getFriendList(body);

            for (SplitwiseUser s : friendList) {
                friends.add(s.getFirstName() + " " + s.getLastName());
                Debug.i(s.toString());
            }

            Debug.check();
            adapter.notifyDataSetChanged();

            // Turn it on
            setProgressBarIndeterminateVisibility(false);

            // run a background job and once complete
            pb.setVisibility(ProgressBar.INVISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItemsIds = new ArrayList<String>();
        ArrayList<String> selectedItemsName = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i)) {
                SplitwiseUser spu = friendList.get(position);
                selectedItemsIds.add(spu.getId());
                selectedItemsName.add(spu.getFirstName()+" "+spu.getLastName());
            }
        }

        String[] outputStrArr = new String[selectedItemsIds.size()];
        String[] outputStrArr2 = new String[selectedItemsIds.size()];
        for (int i = 0; i < selectedItemsIds.size(); i++) {
            outputStrArr[i] = selectedItemsIds.get(i);
            outputStrArr2[i] = selectedItemsName.get(i);
        }

        Intent intent = new Intent(getApplicationContext(),
                SelectedActivity.class);

        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedItemsIds", outputStrArr);
        b.putStringArray("selectedItemsName", outputStrArr2);
        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        startActivity(intent);
    }
}
