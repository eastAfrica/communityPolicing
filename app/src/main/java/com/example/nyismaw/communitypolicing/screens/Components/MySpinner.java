package com.example.nyismaw.communitypolicing.screens.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by nyismaw on 12/9/2017.
 */
public class MySpinner extends Spinner {

    OnItemSelectedListener listener;

    public MySpinner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position)
    {
        super.setSelection(position);

        if (position == getSelectedItemPosition())
            listener.onItemSelected(null, null, position, 0);
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener)
    {
        this.listener = listener;
    }
}
