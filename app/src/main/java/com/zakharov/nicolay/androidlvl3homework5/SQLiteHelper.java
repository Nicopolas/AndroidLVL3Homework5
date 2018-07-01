package com.zakharov.nicolay.androidlvl3homework5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zakharov.nicolay.androidlvl3homework5.DbSchema.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 01.07.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "gitUsers";
    private SQLiteDatabase mDataBase;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UserTable.NAME + " ("
                + UserTable.Cols.userId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserTable.Cols.login + " TEXT, " + UserTable.Cols.avatarUrl + " TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserTable.NAME);
        }
    }

    public void saveUserInDateBase(Model model) {
        ContentValues editedModel = new ContentValues();
        editedModel.put(UserTable.Cols.userId, model.getUserId());
        editedModel.put(UserTable.Cols.login, model.getLogin());
        editedModel.put(UserTable.Cols.avatarUrl, model.getAvatar());
        mDataBase.insert(UserTable.NAME, null, editedModel);
    }

    public List<Model> getAllUsersFromDataBase() {
        List<Model> mModel = new ArrayList<Model>();
        Cursor cursor = mDataBase.query(UserTable.NAME,
                UserTable.Cols.userAllColumn, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Model model = cursorToPhrase(cursor);
            mModel.add(model);
            cursor.moveToNext();
        }
        return mModel;
    }

    public void deleteAllUsersFromDataBase() {
        mDataBase.delete(UserTable.NAME,null, null);
    }

    private Model cursorToPhrase(Cursor cursor) {
        Model model = new Model();
        model.setUserID(cursor.getString(0));
        model.setLogin(cursor.getString(1));
        model.setAvatarUrl(cursor.getString(2));
        return model;
    }


}
