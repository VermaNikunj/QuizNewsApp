package com.nikunjpc.quiznewsapp.News.NewsHistory;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikunjpc.quiznewsapp.R;

import java.util.List;

public class NewsHistory extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_history );

        recyclerView = findViewById( R.id.recyclerViewhistory );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        NewsHistoryDatabaseHelperClass databaseHelperClass = new NewsHistoryDatabaseHelperClass( this );

        List<NewsHistoryModelClass> modelClasses = databaseHelperClass.getList();

        if(modelClasses.size()>0)
        {
            NewsHistoryAdapterClass adapterClass = new NewsHistoryAdapterClass(modelClasses, getApplicationContext());

            recyclerView.setAdapter( adapterClass );

        }
        else Toast.makeText( this, "There is no history", Toast.LENGTH_SHORT ).show();

    }
}