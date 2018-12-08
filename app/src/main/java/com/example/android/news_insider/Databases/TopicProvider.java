package com.example.android.news_insider.Databases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by hp on 20-07-2018.
 */

public class TopicProvider extends ContentProvider {
    private static final int TOPIC =100;
    private static final int TOPIC_ID =101;
    private TopicDbHelper mDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI(TopicContract.CONTENT_AUTHORITY, TopicContract.PATH_TOPICS, TOPIC);
        sUriMatcher.addURI(TopicContract.CONTENT_AUTHORITY, TopicContract.PATH_TOPICS + "/#", TOPIC_ID);
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new TopicDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case TOPIC:
                cursor = database.query(TopicContract.TopicEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TOPIC_ID:
                selection = TopicContract.TopicEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(TopicContract.TopicEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TOPIC:
                return TopicContract.TopicEntry.CONTENT_LIST_TYPE;
            case TOPIC_ID:
                return TopicContract.TopicEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TOPIC:
                return insertTopic(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertTopic(Uri uri, ContentValues values) {
        String name = values.getAsString(TopicContract.TopicEntry.COLUMN_TOPIC_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(TopicContract.TopicEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e("In provider", "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the Fixture content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TOPIC:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(TopicContract.TopicEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TOPIC_ID:
                // Delete a single row given by the ID in the URI
                selection = TopicContract.TopicEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TopicContract.TopicEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TOPIC:
                return updateTopic(uri, values, selection, selectionArgs);
            case TOPIC_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = TopicContract.TopicEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateTopic(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateTopic(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(TopicContract.TopicEntry.COLUMN_TOPIC_NAME)) {
            String name = values.getAsString(TopicContract.TopicEntry.COLUMN_TOPIC_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Team requires a name");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(TopicContract.TopicEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }
}
