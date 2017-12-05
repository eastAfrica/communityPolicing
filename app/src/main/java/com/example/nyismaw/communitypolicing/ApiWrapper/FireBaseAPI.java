package com.example.nyismaw.communitypolicing.ApiWrapper;

import com.example.nyismaw.communitypolicing.controller.location.AppLocationListener;

/**
 * Created by nyismaw on 12/2/2017.
 */

public class FireBaseAPI
        extends FireBaseProxy
        implements DownloadFileInterface, ImageBrowseInterface,
        UploadFileInterface, ReprotedIssuesInterface {
    public FireBaseAPI(AppLocationListener appLocationListener) {
        super(appLocationListener);
    }

    public FireBaseAPI() {
        super();
    }
}
