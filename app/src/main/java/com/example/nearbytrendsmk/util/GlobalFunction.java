package com.example.nearbytrendsmk.util;

import android.content.Context;
import android.content.Intent;

public class GlobalFunction {

    public static void tranferab(Context a, Class b)
    {
        Intent in = new Intent(a,b);
        a.startActivity(in);
    }

}
