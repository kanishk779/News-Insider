package com.example.android.news_insider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.news_insider.Adapters.TopicsAdapter;
import com.example.android.news_insider.Databases.TopicContract;
import com.example.android.news_insider.ModelClasses.Topics;

import java.util.ArrayList;

/**
 * Created by hp on 20-07-2018.
 */

public class TopicsSaveInListActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TopicsAdapter mAdapter;
    public static ArrayList<String> listTopic;
    public static ArrayList<Topics> CompleteDataInfo;
    private Uri QUERY_URI;
    private EditText savingTopic;
    private Button saveInDatabase;
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_save_in_list);
        CompleteDataInfo = new ArrayList<>();
        rv = findViewById(R.id.topicRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        mEmptyStateTextView = findViewById(R.id.empty_view_for_topics);

        listTopic = listOfTopic();
        if(listTopic.size()>0)
        {
            rv.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setVisibility(View.GONE);
        }
        else{
            rv.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Headlines Topic For Notification Set");
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
        mAdapter = new TopicsAdapter(this,listTopic,CompleteDataInfo);
        rv.setAdapter(mAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        savingTopic = findViewById(R.id.topicEditText);
        saveInDatabase = findViewById(R.id.addBtn);
        saveInDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {
                    Uri saveUri = TopicContract.TopicEntry.CONTENT_URI_TOPIC;
                    String topicname = savingTopic.getText().toString().trim();
                    ContentValues cv = new ContentValues();
                    cv.put(TopicContract.TopicEntry.COLUMN_TOPIC_NAME,topicname);
                    getContentResolver().insert(saveUri,cv);
                    mAdapter.notifyDataSetChanged();
                    listTopic = listOfTopic();
                    mAdapter = new TopicsAdapter(TopicsSaveInListActivity.this,listTopic,CompleteDataInfo);
                    rv.setAdapter(mAdapter);
                }
            }
        });
    }

    private boolean isValid() {
        String name = savingTopic.getText().toString().trim();
        if(TextUtils.isEmpty(name))
        {
            return false;
        }
        return true;
    }

    private ArrayList<String> listOfTopic() {
        QUERY_URI = TopicContract.TopicEntry.CONTENT_URI_TOPIC;
        Cursor cursor = getContentResolver().query(QUERY_URI,null,null,null,null);
        ArrayList<String> list = new ArrayList<>();
        while(cursor.moveToNext())
        {
            int nameIndex = cursor.getColumnIndex(TopicContract.TopicEntry.COLUMN_TOPIC_NAME);
            int idIndex = cursor.getColumnIndex(TopicContract.TopicEntry._ID);
            String name = cursor.getString(nameIndex);
            int _id = cursor.getInt(idIndex);
            list.add(name);
            Topics topic = new Topics();
            topic.setName(name);
            topic.setId(_id);
            CompleteDataInfo.add(topic);
        }
        return list;
    }
}
