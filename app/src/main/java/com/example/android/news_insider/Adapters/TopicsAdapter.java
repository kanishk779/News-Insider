package com.example.android.news_insider.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.news_insider.Databases.TopicContract;
import com.example.android.news_insider.Databases.TopicDbHelper;
import com.example.android.news_insider.ModelClasses.Topics;
import com.example.android.news_insider.R;

import java.util.ArrayList;

/**
 * Created by hp on 20-07-2018.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> {
    static Context context;
    private ArrayList<String> list;
    private static ArrayList<Topics> DatabaseInfo;
    private static TopicDbHelper mDbHelper;
    public TopicsAdapter(Context context,ArrayList<String> list,ArrayList<Topics> datalist)
    {
        this.context =context;
        this.list = list;
        DatabaseInfo = datalist;
        mDbHelper = new TopicDbHelper(context);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView topicName;
        Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            delete = itemView.findViewById(R.id.deleteTopic);
            delete.setOnClickListener(this);
            delete.setTag(R.integer.btn_tag,itemView);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==delete.getId())
            {
                View tempView = (View) delete.getTag(R.integer.btn_tag);
                TextView topicNameToSearch = tempView.findViewById(R.id.topicName);
                String name = topicNameToSearch.getText().toString().trim();
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                String id = getIdOfRowInDatabaseFromName(name);
                Uri uri = TopicContract.TopicEntry.CONTENT_URI_TOPIC;
                String selection = TopicContract.TopicEntry._ID + "=?";
                if(id!=null)
                {
                    String[] selectionArgs = {id};
                    //db.rawQuery("DELETE FROM "+TopicContract.TopicEntry.TABLE_NAME+" WHERE "+TopicContract.TopicEntry.COLUMN_TOPIC_NAME+"=\'"+name+"\';",null);
                    Log.e("Deleting","DELETE FROM "+TopicContract.TopicEntry.TABLE_NAME+" WHERE "+TopicContract.TopicEntry.COLUMN_TOPIC_NAME+"=\'"+name+"\';");
                    int noOfRowsDeleted = context.getContentResolver().delete(uri,selection,selectionArgs);
                    if(noOfRowsDeleted>0)
                    {
                        Toast.makeText(context, "Deletion Successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Deletion Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        private String getIdOfRowInDatabaseFromName(String name) {
            for(int i=0;i<DatabaseInfo.size();i++)
            {
                Topics topic = DatabaseInfo.get(i);
                if(topic.getName().equals(name))
                {
                    return String.valueOf(topic.getId());
                }
            }
            return null;
        }
    }
    @NonNull
    @Override
    public TopicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicsAdapter.ViewHolder holder, int position) {
        holder.topicName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list!=null)
        {
            return list.size();
        }
        return 0;
    }
}
