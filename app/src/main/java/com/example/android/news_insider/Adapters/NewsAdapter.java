package com.example.android.news_insider.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.news_insider.ModelClasses.Article;
import com.example.android.news_insider.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hp on 09-07-2018.
 */

public class NewsAdapter extends ArrayAdapter<Article> {
    private ImageView img;
    private TextView title,description,author;
    public NewsAdapter(Context context, ArrayList<Article> list)
    {
        super(context,0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }
        author = listItemView.findViewById(R.id.author);
        title = listItemView.findViewById(R.id.title);
        description = listItemView.findViewById(R.id.description);
        img = listItemView.findViewById(R.id.news_img);
        Article article = getItem(position);
        author.setText(article.getAuthor());
        title.setText(article.getTitle());
        description.setText(article.getDescription());
        Picasso.with(getContext()).load(article.getUrlToImage()).into(img);
        return listItemView;
    }
}
