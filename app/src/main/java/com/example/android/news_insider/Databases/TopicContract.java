package com.example.android.news_insider.Databases;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hp on 20-07-2018.
 */

public final class TopicContract {
    private TopicContract(){}
    public static final String CONTENT_AUTHORITY = "com.example.android.news_insider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TOPICS = "topics";
    public static final class TopicEntry implements BaseColumns {
        public static final Uri CONTENT_URI_TOPIC = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TOPICS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOPICS;
        /**
         * The MIME type of the {CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOPICS;
        public static final String TABLE_NAME = "topics";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TOPIC_NAME = "name";

    }
}
