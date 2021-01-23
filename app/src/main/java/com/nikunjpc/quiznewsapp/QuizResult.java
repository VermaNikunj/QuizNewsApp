package com.nikunjpc.quiznewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizResult extends AppCompatActivity {


    int numb;
    String type, cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quiz_result );

        final ArrayList<String> fcl = getIntent().getStringArrayListExtra( "correctlist" );

        final ArrayList<String> fql = getIntent().getStringArrayListExtra( "questionlist" );

        final ArrayList<String> fil = getIntent().getStringArrayListExtra( "incorrectlist" );

        numb= getIntent().getIntExtra( "number",0 );

        int correct= getIntent().getIntExtra( "correct",0 );

        type=getIntent().getStringExtra( "type" );

        cat=getIntent().getStringExtra( "cat" );

//        Log.e( "flow type 4", type  );
//
//        Log.e( "flow cat 4", cat  );


        if(numb<10)
        {
            Intent intent = new Intent( QuizResult.this, QuizQuestion.class );
            intent.putExtra( "number", numb );
            intent.putExtra( "questionlist", fql);
            intent.putExtra( "correctlist", fcl);
            intent.putExtra( "incorrectlist", fil);
            intent.putExtra( "correct", correct );
            intent.putExtra( "type", type );
            intent.putExtra( "cat", cat );



//            Log.e( "flow type 6", type  );
//
//            Log.e( "flow cat 6", cat  );


            startActivity( intent );
        }


//        Log.e("Print", "Print1119");
        }

    public void onBackPressed() {
            Intent intent=new Intent( QuizResult.this, MainActivity.class);
            Toast.makeText( this, "Back to Main", Toast.LENGTH_SHORT ).show();
            startActivity(intent);

    }
    }
