package com.example.android.news_insider.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hp on 20-07-2018.
 */

public class TopicDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "newstopics";
    private static final Integer DATABASE_VERSION =1;
    public TopicDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String CREATE_TABLE_STMT = "CREATE TABLE " + TopicContract.TopicEntry.TABLE_NAME + " ("
            + TopicContract.TopicEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TopicContract.TopicEntry.COLUMN_TOPIC_NAME + " TEXT NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STMT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS topics");
        db.execSQL(CREATE_TABLE_STMT);
    }
}
