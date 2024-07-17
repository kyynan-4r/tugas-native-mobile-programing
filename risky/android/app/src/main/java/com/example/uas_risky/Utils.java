package com.example.uas_risky;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

public class Utils {
    public static EditText createEditText(Context context, String hint) {
        EditText et = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        et.setLayoutParams(params);
        et.setHint(hint);
        return et;
    }
    public static SwitchCompat createSwitch(Context context, String hint) {
        SwitchCompat sc = new SwitchCompat(context);
        sc.setText(hint);
        return sc;
    }
    public static TextView createTextView(Context context) {
        TextView tv = new TextView(context);
        return tv;
    }
}
