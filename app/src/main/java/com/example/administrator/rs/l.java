package com.example.administrator.rs;

import android.util.Log;

/**
 * Created by mao on 16-8-5.
 */

public class l {
    public static String s = "";
    private static int i = 3;
    private enum LogState {
        INFO, ERROR, ALL;
    }

    public static final LogState CURRENT_STATE = LogState.ALL;

    public static void i(final String msg) {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        if (elements.length < 5) {
            Log.v("test", "myLog error elements.length < 5");
        } else {
            s = "at " + elements[i].getClassName() + "." + elements[i].getMethodName() + "(" + elements[i].getClassName().substring(elements[i].getClassName().lastIndexOf(".") + 1, elements[i].getClassName().length()) +
                    ".java:"
                    + elements[i].getLineNumber() + ")";
        }
        switch (CURRENT_STATE) {
            case ALL:
            case INFO:

                Log.v("test", s + "    " + msg);
        }
    }
    public static void e(final String msg) {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        if (elements.length < 5) {
            Log.v("test", "myLog error elements.length < 5");
        } else {
            s = "at " + elements[i].getClassName() + "." + elements[i].getMethodName() + "(" + elements[i].getClassName().substring(elements[i].getClassName().lastIndexOf(".") + 1, elements[i].getClassName().length()) +
                    ".java:"
                    + elements[i].getLineNumber() + ")";
        }
        switch (CURRENT_STATE) {
            case ALL:
            case INFO:

                Log.e("test", s + "    " + msg);
        }
    }

}

