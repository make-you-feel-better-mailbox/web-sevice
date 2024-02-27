package com.onetwo.webservice.common.uri;

public class UserServiceURI {

    public static final String USER_SERVICE_ROOT = "/user-service";

    public static final String USER_ROOT = USER_SERVICE_ROOT + "/users";
    public static final String USER_LOGIN = USER_ROOT + "/login";
    public static final String USER_ID = USER_ROOT + "/id";
    public static final String TOKEN = USER_SERVICE_ROOT + "/token";
    public static final String TOKEN_REFRESH = TOKEN + "/refresh";
}
