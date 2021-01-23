package com.nikunjpc.quiznewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikunjpc.quiznewsapp.History.HistoryActivity;
import com.nikunjpc.quiznewsapp.News.NewsMainActivity;
import com.nikunjpc.quiznewsapp.TipOfTheDay.Tip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static  final int TIME_INTERVAL=2000;
    private long mBackPressed;

    String Url;

    ProgressBar progressBar;
//    public ArrayList<Result> resultList= new ArrayList<>( 10 );

    ArrayList<String> questionlist= new ArrayList<>( 10 );
    ArrayList<String> correctlist= new ArrayList<>( 10 );
    ArrayList<String> incorrectlist= new ArrayList<>( 30 );

    Button bt;

    ArrayList<String> List1;
    ArrayAdapter<String> ad1;

    ArrayList<String> List2;
    ArrayAdapter<String> ad2;

    Spinner sp1,sp2;
    Button btnews,bthistory,btTip;

    int cat, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        bt = (Button) findViewById( R.id.bt );
        btnews = (Button) findViewById( R.id.btnews );
        bthistory = (Button) findViewById( R.id.bthistory );
        sp1 = (Spinner) findViewById( R.id.sp1 );
        sp2 = (Spinner) findViewById( R.id.sp2 );


        btTip=findViewById( R.id.btTip );

        List1 = new ArrayList<String>();
        List1.add( "Select the Category" );
        List1.add( "General Knowledge" );
        List1.add( "Entertainment: Books" );
        List1.add( "Entertainment: Film" );
        List1.add( "Entertainment: Music" );
        List1.add( "Entertainment: Musicals & Theatres" );
        List1.add( "Entertainment: Television" );
        List1.add( "Entertainment: Video Games" );
        List1.add( "Entertainment: Board Games" );
        List1.add( "Science & Nature" );
        List1.add( "Science: Computers" );
        List1.add( "Science: Mathematics" );
        List1.add( "Mythology" );
        List1.add( "Sports" );
        List1.add( "Geography" );
        List1.add( "History" );
        List1.add( "Politics" );
        List1.add( "Art" );
        List1.add( "Celebrities" );
        List1.add( "Animals" );
        List1.add( "Vehicles" );
        List1.add( "Entertainment: Comics" );
        List1.add( "Science: Gadgets" );
        List1.add( "Entertainment: Japanese Anime & Manga" );
        List1.add( "Entertainment: Cartoon & Animations" );

        ad1 = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, List1 );
        ad1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        sp1.setAdapter( ad1 );

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        List2 = new ArrayList<String>();
        List2.add( "Select the Type" );
        List2.add( "Easy" );
        List2.add( "Medium" );
        List2.add( "Hard" );

        ad2 = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, List2 );
        ad2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        sp2.setAdapter( ad2 );

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnews.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsMainActivity.class);
                startActivity( intent );
            }
        } );

        bthistory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity( intent );
            }
        } );

        btTip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tip.class);
                startActivity( intent );
            }
        } );

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cat>0 && type>0)
                {
                    quizstart();
                }
                else Toast.makeText( MainActivity.this, "Select the option", Toast.LENGTH_SHORT ).show();

            }
        });
    }
        private void quizstart()
                {


//                    Log.e( "flow", "000000" );

                progressBar.setVisibility( View.VISIBLE);
//                Log.e( "flow", "000001" );


//            loadResultList();
//                Log.e( "flow", "000016" );




//            }
//        } );
//
//    }
//
//    private void loadResultList() {
//        Log.e( "flow", "000002" );

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

//        Log.e( "flow", "000003" );

        Url= "https://opentdb.com/api.php?amount=10&category="+(cat+8)+"&difficulty="+List2.get( type ).toLowerCase()+"&type=multiple";


                StringRequest stringRequest = new StringRequest( Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.INVISIBLE);
//                        Log.e( "flow", "000004" );


                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray resultArray = obj.getJSONArray("results");

                            for (int i = 0; i < resultArray.length(); i++) {
                                JSONObject resultObject = resultArray.getJSONObject(i);

//                                Log.e( "flow", "000005" );

//                                ArrayList<String> IncorrectAnswerslist = new ArrayList<String>();
                                JSONArray jArray = resultObject.getJSONArray("incorrect_answers");
                                for (int j=0;j<jArray.length();j++){
                                    incorrectlist.add(jArray.getString(j));
//                                    Log.e("Check","List "+j+" : "+jArray.getString( j ));
                                }

//                                Log.e( "Check 2", "question : "+resultObject.getString( "question" ) );


//                                Log.e( "Check 3", "answer : "+resultObject.getString( "correct_answer" ) );

//                                Log.e( "flow", "000006" );


//                                Result result= new Result(resultObject.getString("question"), resultObject.getString("correct_answer"), IncorrectAnswerslist);

//                                Log.e( "flow", "000007" );

//                                Log.e( "Q","Ques:" +result.getQuestion() );
//                                resultList.add( result );
//                                Log.e( "flow", "000008" );

                                questionlist.add(resultObject.getString("question"));

//                                Log.e( "list","size:" +questionlist.size() );

                                correctlist.add(resultObject.getString("correct_answer"));

//                                Log.e( "list","size:" +correctlist.size() );
//                                Log.e( "list","size:" +incorrectlist.size() );
//
//                                Log.e( "flow", "000009" );

                            }


//                            Log.e( "flow", "000013" );
//                            Log.e( "size", ""+resultList.isEmpty() );


//                            Log.e( "flow", "000015" );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
//                            Log.e( "size", ""+resultList.isEmpty() );
//
//                            setResultList( resultList );
                            intentstart();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



//        Log.e( "Size", "Size : "+resultList.size() );

//        Log.e( "flow", "000010" );

        requestQueue.add(stringRequest);

//        Log.e( "flow", "000011" );


//        Log.e( "flow", "000012" );





        }

//
//    });
//    }

    private void intentstart() {
//        if(resultList.size()==10)
//        {
            Intent intent= new Intent( MainActivity.this, QuizQuestion.class );
            intent.putExtra( "questionlist", questionlist);
        intent.putExtra( "correctlist", correctlist);
        intent.putExtra( "incorrectlist", incorrectlist);
        intent.putExtra( "type", List2.get(type) );
        intent.putExtra( "cat", List1.get( cat ) );

//            intent.put
//            Log.e( "flow type", List2.get( type ) );
//
//        Log.e( "flow cat", List1.get( cat ) );

//            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//Log.e( "sizzzze", "size::: "+questionlist.size() );
//        Log.e( "sizzzze", "size::: "+correctlist.size() );
//
//        Log.e( "sizzzze", "size::: "+incorrectlist.size() );

        Toast.makeText( MainActivity.this, "Start Quiz", Toast.LENGTH_SHORT ).show();


        startActivity( intent );
//        }

    }
//
//    public ArrayList<Result> getResultList()
//    {
//        Log.e( "size get", ""+resultList.isEmpty() );
//
//        return resultList;
//    }
//
//    public void setResultList(ArrayList<Result> resultList)
//    {
//        Log.e( "size set", ""+resultList.isEmpty() );
//
//        this.resultList=resultList;
//    }


    @Override
    public void onBackPressed() {
        if(mBackPressed+ TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        }
        else{
            Toast.makeText( this, "Click two times to exit", Toast.LENGTH_SHORT ).show();
        }
        mBackPressed= System.currentTimeMillis();
    }


}