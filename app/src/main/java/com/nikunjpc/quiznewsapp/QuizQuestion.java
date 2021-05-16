package com.nikunjpc.quiznewsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikunjpc.quiznewsapp.History.HistoryDatabaseHelperClass;
import com.nikunjpc.quiznewsapp.History.HistoryModelClass;

import java.util.ArrayList;
import java.util.Random;

public class QuizQuestion extends AppCompatActivity {

    TextView clock,tvquestion, tvcorrect, tvincorrect;
    RadioButton option1, option2, option3, option4;
    Button btsubmit, btnext;
    RadioGroup radioGroup;

    int correct=0, i=0,next=1;
    String type,cat;

    private static  final int TIME_INTERVAL=2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quiz_question );

        clock = (TextView) findViewById( R.id.clock );
        tvquestion = (TextView) findViewById( R.id.tvquestion );
        tvcorrect = (TextView) findViewById( R.id.tvcorrect );
        tvincorrect = (TextView) findViewById( R.id.tvincorrect );
        option1 = (RadioButton) findViewById( R.id.option1 );
        option2 = (RadioButton) findViewById( R.id.option2 );
        option3 = (RadioButton) findViewById( R.id.option3 );
        option4 = (RadioButton) findViewById( R.id.option4 );
        btsubmit = (Button) findViewById( R.id.btsubmit );
        btnext = (Button) findViewById( R.id.btnext );
        radioGroup = (RadioGroup) findViewById( R.id.radiogrp );

//
//        Log.e("Print2", "List --- size:"+obj.incorrectlist.isEmpty());
//        Log.e( "sizzzze22", "List --- size::: "+obj.questionlist.size() );
//        Log.e( "sizzzze22", "list --- size::: "+obj.correctlist.size() );

        final ArrayList<String> fcl = getIntent().getStringArrayListExtra( "correctlist" );

        final ArrayList<String> fql = getIntent().getStringArrayListExtra( "questionlist" );

        final ArrayList<String> fil = getIntent().getStringArrayListExtra( "incorrectlist" );

        i= getIntent().getIntExtra( "number",0 );

        correct= getIntent().getIntExtra( "correct",0 );

        type=getIntent().getStringExtra( "type" );

        cat=getIntent().getStringExtra( "cat" );

//        Log.e( "flow type 2", type  );
//
//        Log.e( "flow cat 2", cat  );


//        Log.e( "Print2", "List --- size:" + fcl.isEmpty() );
//        Log.e( "sizzzze22", "List --- size::: " + fql.size() );
//        Log.e( "sizzzze22", "list --- size::: " + fil.size() );

//
//        for(Result r:obj.resultList)
//        {
//            Log.e( "flow", "111111111" );
//
//            Log.e( "Result", "List "+i+" : "+r.getQuestion() );
//            i++;
//        }
//        Log.e("Print", "Print1110");
//         while(i<10){
//             next=1;

//            Log.e("Print", "Print1111");
            new CountDownTimer( 11000, 1000 ) {
                public void onTick(long millisUntilFinished) {
                    clock.setText( "Seconds remaining: " + millisUntilFinished / 1000 );
                }
                    public void onFinish() {
                        clock.setText( "Time Up!!!" );
                        clock.setVisibility( View.INVISIBLE );
                        option1.setEnabled( false );
                        option2.setEnabled( false );
                        option3.setEnabled( false );
                        option4.setEnabled( false );
                        btsubmit.performClick();
                    }
                }.start();

            tvquestion.setText( "Q "+(i+1)+". "+fql.get( i ) );
            Random rand = new Random();
//            Log.e("Print", "Print1112");
            final int random = rand.nextInt(3);
//            Log.e("Print", "Print1113");
            switch(random)
            {
                case 0: {
                    option1.setText( fcl.get( i ) );
                    option2.setText( fil.get( 3*i ) );
                    option3.setText( fil.get( (3*i)+1 ) );
                    option4.setText( fil.get( (3*i)+2 ) );
                    break;
                }
                case 1: {
                    option1.setText( fil.get( 3*i ) );
                    option2.setText( fcl.get( i ) );
                    option3.setText( fil.get( (3*i)+1 ) );
                    option4.setText( fil.get( (3*i)+2 ) );
                    break;
                }
                case 2: {
                option1.setText( fil.get( 3*i ) );
                option2.setText( fil.get( (3*i)+1 ) );
                option3.setText( fcl.get( i ) );
                option4.setText( fil.get( (3*i)+2 ) );
                break;
                }
            case 3: {
                option1.setText( fil.get( 3*i ) );
                option2.setText( fil.get( (3*i)+1 ) );
                option3.setText( fil.get( (3*i)+2 ) );
                option4.setText( fcl.get( i ) );
                break;
                }
            }

//            Log.e("Print", "Print1114");
            final int finalI = i;
            btsubmit.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

//                    Log.e("Print", "Print1116");

                    int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if (selectedRadioButtonId != -1) {

                        clock.setVisibility( View.INVISIBLE );
                        option1.setEnabled( false );
                        option2.setEnabled( false );
                        option3.setEnabled( false );
                        option4.setEnabled( false );

                        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                        String selectedRbText = selectedRadioButton.getText().toString();
                        if(selectedRbText.equals( fcl.get( finalI ) ))
                        {


                            tvcorrect.setVisibility( View.VISIBLE );
                            correct=correct+1;
                            selectedRadioButton.setTextColor( getResources().getColor(R.color.green));
                        }
                        else
                        {

                            tvincorrect.setVisibility( View.VISIBLE );
                            selectedRadioButton.setTextColor( getResources().getColor(R.color.red));
                                    if(random==0) option1.setTextColor( getResources().getColor(R.color.green) );
                                    else if(random==1) option2.setTextColor( getResources().getColor(R.color.green) );
                                    else if(random==2) option3.setTextColor( getResources().getColor(R.color.green) );
                                    else option4.setTextColor( getResources().getColor(R.color.green) );


//                           RadioButton selectedRadioButton1 = findViewById(random);
//                            selectedRadioButton1.setTextColor( getResources().getColor(R.color.green));
                        }
                    } else {

                        clock.setVisibility( View.INVISIBLE );

                        if(random==0) option1.setTextColor( getResources().getColor(R.color.green) );
                        else if(random==1) option2.setTextColor( getResources().getColor(R.color.green) );
                        else if(random==2) option3.setTextColor( getResources().getColor(R.color.green) );
                        else option4.setTextColor( getResources().getColor(R.color.green) );


//                        Toast.makeText( QuizQuestion.this, "Nothing selected", Toast.LENGTH_SHORT ).show();
                    }
                }

            });

//            Log.e("Print", "Print1115");
            btnext.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("Print", "Print1117");
                    i++;
                    if(i==10)
                    {
                        HistoryDatabaseHelperClass databaseHelperClass = new HistoryDatabaseHelperClass( QuizQuestion.this );
                        HistoryModelClass historymodelClass = new HistoryModelClass( type , cat , correct   );
                        databaseHelperClass.addItem( historymodelClass );

                        AlertDialog.Builder dialog=new AlertDialog.Builder(QuizQuestion.this);
                        dialog.setMessage("Total Questions :: 10 \n  Correct Answered : "+correct+"\nIncorrect Answered : "+(10-correct));
                        dialog.setTitle("Result");
                        dialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent intent=new Intent( QuizQuestion.this,MainActivity.class );
                                        startActivity( intent );

                                    }
                                });
                        AlertDialog alertDialog=dialog.create();
                        alertDialog.show();

                    }
                    else {
                        Intent intent = new Intent( QuizQuestion.this, QuizResult.class );
                        intent.putExtra( "number", i );
                        intent.putExtra( "questionlist", fql );
                        intent.putExtra( "correctlist", fcl );
                        intent.putExtra( "incorrectlist", fil );
                        intent.putExtra( "correct", correct );
                        intent.putExtra( "type", type );
                        intent.putExtra( "cat", cat );


//                        Log.e( "flow type 3", type );
//
//                        Log.e( "flow cat 3", cat );


                        startActivity( intent );
                    }
                }
            } );

        }


    @Override
    public void onBackPressed() {
        if(mBackPressed+ TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent intent=new Intent( QuizQuestion.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText( this, "Click two times to quit", Toast.LENGTH_SHORT ).show();
        }
        mBackPressed= System.currentTimeMillis();
    }
    }


