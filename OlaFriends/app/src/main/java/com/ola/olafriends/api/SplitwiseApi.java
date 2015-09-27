package com.ola.olafriends.api;

import com.ola.olafriends.AppConstants;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * Created by thecodegame on 26-09-2015.
 */

public class SplitwiseApi extends DefaultApi10a {

    @Override
    public String getAccessTokenEndpoint() {
        return AppConstants.SPLITWISE_ACCESS_TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(Token arg0) {
        return AppConstants.SPLITWISE_AUTHORIZE_URL + arg0.getToken();
    }

    @Override
    public String getRequestTokenEndpoint() {
        return AppConstants.SPLITWISE_REQUEST_TOKEN_URL;
    }

}
