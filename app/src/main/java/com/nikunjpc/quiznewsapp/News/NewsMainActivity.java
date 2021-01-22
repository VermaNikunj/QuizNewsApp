package com.nikunjpc.quiznewsapp.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nikunjpc.quiznewsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsMainActivity extends AppCompatActivity {

//    String NewsUrl="";
    String api="YOUR_API_KEY";
//    String author, title, url, urlToImage;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etQuery;
    Button btnSearch;
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

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

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
        if (!etQuery.getText().toString().equals("")){
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


    //        RetrieveJSON(NewsUrl);

//        adapter = new Adapter(NewsMainActivity.this,list);
//        Log.e( "news flow", "0000011" );
//
//        recyclerView.setAdapter(adapter);

//    }

//    private void RetrieveJSON(String NewsUrl)
//    {
////        swipeRefreshLayout.setRefreshing(true);
//        Log.e( "news flow", "000004" );
//
//        RequestQueue requestQueue2 = Volley.newRequestQueue( NewsMainActivity.this);
//
//        Log.e( "news flow", "000005"+NewsUrl );
//
//NewsUrl= "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com&apiKey="+api;
//        JsonObjectRequest stringRequest2 = new JsonObjectRequest( Request.Method.GET, NewsUrl,null
//                ,new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e( "news flow", "000006" );
//
////                        swipeRefreshLayout.setRefreshing(false);
//
//                        try {
//
////                            JSONObject obj2 = new JSONObject(response);
//
////                            String status= response.getString( "status" );
////
////                            Log.e( "Status", "+"+status );
//                            JSONArray resultArray2 = response.getJSONArray("articles");
//
//                            for (int i = 0; i < resultArray2.length(); i++) {
//                                JSONObject resultObject2 = resultArray2.getJSONObject( i );
//
//                                Log.e( "news flow", "000008" );
//
//                                author = resultObject2.getString( "author" );
//
//                                title = resultObject2.getString( "title" );
//
//                                url = resultObject2.getString( "url" );
//
//                                urlToImage = resultObject2.getString( "urlToImage" );
//
//                                NewsModelClass newsModelClass= new NewsModelClass(author, title, url, urlToImage);
//
//                                list.add(newsModelClass);
//
//                            }
////                                list.clear();
//
//                                Log.e( "news flow", "000009" );
//
//                                Log.e( "news flow", "000010" );
//
//                                Log.e("news flow size", ""+list.size());
//
//                                Log.e( "news flow", "0000012" );
//
//                            Log.e( "news flow", "000013" );
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        NetworkResponse response = error.networkResponse;
//                        if (error instanceof ServerError && response != null) {
//                            try {
//                                String res = new String(response.data,
//                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                                // Now you can use any deserializer to make sense of data
//                                JSONObject obj = new JSONObject(res);
//                            } catch (UnsupportedEncodingException e1) {
//                                // Couldn't properly decode data to string
//                                e1.printStackTrace();
//                            } catch (JSONException e2) {
//                                // returned data is not JSONObject?
//                                e2.printStackTrace();
//                            }
//                        }
//
//                    }
//                });
//
//
//
//        Log.e( "news flow", "000014" +NewsUrl );
//
//
//        requestQueue2.add(stringRequest2);
//        Log.e( "news flow", "000015" );
//
//    }


}