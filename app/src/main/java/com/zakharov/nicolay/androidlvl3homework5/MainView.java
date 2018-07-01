package com.zakharov.nicolay.androidlvl3homework5;

/**
 * Created by 1 on 01.07.2018.
 */

public interface MainView {
    SQLiteHelper getSQLiteHelper();
    void appendIntoTextView(String str);
    void setTextIntoTextView(String str);
    void setVisibilityProgressBar(boolean visibility);
    void makeToast(String string);
    boolean getNetworkInfo();
}
