package com.ola.olafriends;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ola.olafriends.api.SplitwiseApi;
import com.ola.olafriends.utils.Debug;
import com.ola.olafriends.utils.Utils;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

/**
 * Created by thecodegame on 26-09-2015.
 */
public class OlaFriendsApplication extends Application {
    private static OlaFriendsApplication mInstance;
    private static OAuthService service = new ServiceBuilder()
            .provider(twitter.class)
            .apiKey(AppConstants.SPLITWISE_oauth_consumer_key_value)
            .apiSecret(AppConstants.SPLITWISE_oauth_consumer_secret_value)
            .build();
    ;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);

        mInstance = this;

        Debug.check();


        Debug.check();

        Utils.printKeyHash(this);
    }

    public static OlaFriendsApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static OAuthService getOAuthService() {
        return service;
    }
}
