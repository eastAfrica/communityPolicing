package com.example.nyismaw.communitypolicing.ApiWrapper;

import android.graphics.Bitmap;

import java.io.InputStream;

/**
 * Created by nyismaw on 12/2/2017.
 */

public interface UploadFileInterface {
    public void uploadAudio(InputStream path, String fileName);
    public void UploadImage(Bitmap bitmap, String filename);
}
