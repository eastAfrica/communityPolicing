package com.example.nyismaw.communitypolicing.exceptions;

import android.content.Context;

/**
 * Created by nyismaw on 12/2/2017.
 */

public abstract  class FIreBaseApiException extends Exception{

    public abstract  void fix(Context context, Object o);
}
