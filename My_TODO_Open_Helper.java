package com.example.lenovo.my_todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 9/22/2017.
 */
public class My_TODO_Open_Helper extends SQLiteOpenHelper {

    public static My_TODO_Open_Helper instance;

    public static My_TODO_Open_Helper getinstance(Context context) {

        if (instance == null) {
            instance = new My_TODO_Open_Helper(context);

        }
        return instance;

    }


    public My_TODO_Open_Helper(Context context) {
        super(context, "MY_TODO_DB1", null, 1);
    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + Contract.MY_TODO_Table_Name + " (" +
                Contract.MY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.MY_TODO_Title + " TEXT, " +
                Contract.MY_TODO_Rank + " INTEGER )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
