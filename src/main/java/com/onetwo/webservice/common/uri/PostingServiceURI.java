package com.onetwo.webservice.common.uri;

public class PostingServiceURI {

    public static final String POSTING_SERVICE_ROOT = "/posting-service";

    public static final String POSTING_ROOT = POSTING_SERVICE_ROOT + "/postings";

    public static final String POSTING_FILTER = POSTING_ROOT + "/filter";

    public static final String PATH_VARIABLE_POSTING_ID = "posting-id";
    public static final String PATH_VARIABLE_POSTING_ID_WITH_BRACE = "/{" + PATH_VARIABLE_POSTING_ID + "}";
}
