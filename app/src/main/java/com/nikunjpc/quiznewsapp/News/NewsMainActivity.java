package com.nikunjpc.quiznewsapp.News;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nikunjpc.quiznewsapp.News.NewsHistory.NewsHistory;
import com.nikunjpc.quiznewsapp.News.NewsHistory.NewsHistoryDatabaseHelperClass;
import com.nikunjpc.quiznewsapp.News.NewsHistory.NewsHistoryModelClass;
import com.nikunjpc.quiznewsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsMainActivity extends AppCompatActivity {

//    String NewsUrl="";
    String api= "7e8b827c7ebe44acb452a7c682210861";
//        "YOUR_API_KEY";
//    String author, title, url, urlToImage;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etQuery;
    Button btnSearch;
    ImageButton btNewsHistory;
    Adapter adapter;
    List<NewsModelClass> list= new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_main );

        swipeRefreshLayout = findViewById( R.id.swipeRefreshLayout );
        recyclerView = findViewById( R.id.newsRecyclerView );

        etQuery = findViewById( R.id.etQuery );
        btnSearch = findViewById( R.id.btnSearch );
        btNewsHistory=findViewById( R.id.btnewsHistory );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        btNewsHistory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(NewsMainActivity.this, NewsHistory.class);
                startActivity(i);
            }
        } );

//        Log.e( "news flow", "000001" );
//
//        Log.e( "news flow", "000002" );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.e( "news flow", "000003" );

                RetrieveJSON("",api);
            }
        } );


//        "7e8b827c7ebe44acb452a7c682210861"

        RetrieveJSON("",api);

        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuery.getText().toString().equals("")){
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            RetrieveJSON(etQuery.getText().toString(),api);
                        }
                    });
                    RetrieveJSON(etQuery.getText().toString(),api);
                }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            RetrieveJSON("",api);
                        }
                    });
                    RetrieveJSON("",api);
                }
            }
        } );

    }

    public void RetrieveJSON(String query, String apiKey){


        swipeRefreshLayout.setRefreshing(true);
        Call<News> call;
//        "7e8b827c7ebe44acb452a7c682210861";
        if (!etQuery.getText().toString().equals("")){
            NewsHistoryDatabaseHelperClass databaseHelperClass = new NewsHistoryDatabaseHelperClass( NewsMainActivity.this );
            NewsHistoryModelClass historymodelClass = new NewsHistoryModelClass( query   );
            databaseHelperClass.addItem( historymodelClass );

            call= ApiClient.getInstance().getApi().getSpecificData(query,apiKey);
        }else{
            call= ApiClient.getInstance().getApi().getNews("in",apiKey);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, retrofit2.Response<News> response) {
                if (response.isSuccessful() && response.body().getNewsModelClasses() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    list.clear();
                    list = response.body().getNewsModelClasses();
                    adapter = new Adapter(NewsMainActivity.this,list);
                    recyclerView.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NewsMainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}