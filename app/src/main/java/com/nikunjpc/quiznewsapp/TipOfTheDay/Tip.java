package com.nikunjpc.quiznewsapp.TipOfTheDay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikunjpc.quiznewsapp.MainActivity;
import com.nikunjpc.quiznewsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Tip extends AppCompatActivity {

        RecyclerView recyclerViewTip;

        public String line=".";
        int clicked=0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_tip );


            clicked = getIntent().getIntExtra( "clicked",0 );

            Load();
//            Log.e("Checking", "000000");
            loadCheck();
//            Log.e("Checking", "000017");

            if(clicked ==0)
            {
                Intent intent= new Intent( Tip.this, MainActivity.class );
                intent.putExtra( "showDialog", 1 );
                intent.putExtra( "lineCheck", line);
                startActivity( intent );
            }
            else TipStart();

        }

        public void TipStart()
        {
//
//            Load();
////            Log.e("Checking", "000000");
//            loadCheck();
////            Log.e("Checking", "000017");

            recyclerViewTip = findViewById( R.id.recyclerViewTip );
            recyclerViewTip.setLayoutManager( new LinearLayoutManager( this ) );

            TipDatabaseHelperClass databaseHelperClass = new TipDatabaseHelperClass( this );

            List<TipModelClass> modelClasses = databaseHelperClass.getList();

            if(modelClasses.size()>0)
            {
                TipAdapterClass adapterClass = new TipAdapterClass(modelClasses, getApplicationContext());

                recyclerViewTip.setAdapter( adapterClass );

            }
            else Toast.makeText( this, "There is no history", Toast.LENGTH_SHORT ).show();

        }

        public void loadCheck()
        {

//            Log.e("Checking", "000001");
            SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.ENGLISH );
            Date date = new Date();
            String currentdatetime= formatter.format(date);

//            Log.e("SYSTEM TIME Check", currentdatetime);
//            Log.e("Checking", "000002");

                TipDatabaseHelperClass databaseHelperClass = new TipDatabaseHelperClass( Tip.this );

            if((databaseHelperClass.getList().size())>0) {
                String lastDate = databaseHelperClass.lastDate();
//                Log.e( "Last Date", lastDate );
                        lastDate+= " 00:00:00";
//                Log.e( "Last Date time", lastDate );
                Date date1, date2;
//                Log.e( "Checking", "000003" );
                try {
//                    Log.e( "Checking", "000004" );
                    date1 = formatter.parse( lastDate );
                    date2 = formatter.parse( currentdatetime );
//                    Log.e( "last date1", ""+date1 );
//                    Log.e("current date 2", ""+date2);
//                    Log.e( "Checking", "000005" );

                    if (TimeUnit.MILLISECONDS.toDays( date2.getTime() - date1.getTime() ) > 0)
                    {
                        if ((TimeUnit.MILLISECONDS.toHours( date2.getTime() - date1.getTime() ) > 0) ||
                                (TimeUnit.MILLISECONDS.toMinutes( date2.getTime() - date1.getTime() ) > 0) ||
                                (TimeUnit.MILLISECONDS.toSeconds( date2.getTime() - date1.getTime() ) > 0))
                        {
                            updateDisplay( currentdatetime, 1 );
                            if(clicked==0)
                            Toast.makeText( this, "New Tip of the day", Toast.LENGTH_SHORT ).show();
                        }
                    }
                    else {
                        if(clicked==0)
                        Toast.makeText( this, "Same Visit", Toast.LENGTH_SHORT ).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else updateDisplay(currentdatetime,0);

        }

        public void Load()
        {
            int left=checkBackup();
//            Log.e( "Size", ""+left );
            if(left<3) {
//                Log.e( "Checking", "200000" );
                RequestQueue requestQueue = Volley.newRequestQueue( Tip.this );
//                Log.e("Checking", "3000010");

                String Url = "https://goquotes-api.herokuapp.com/api/v1/random/30?type=tag&val=motivational";

//                Log.e("Checking", "3000011");
                StringRequest stringRequest2 = new StringRequest( Request.Method.GET, Url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
//                                    Log.e("Checking", "3000014");

                                    JSONObject obj = new JSONObject( response );

                                    JSONArray resultArray = obj.getJSONArray( "quotes" );
//                                    Log.e("Checking", "3000015");

                                    for (int i = 0; i < resultArray.length(); i++) {
                                        JSONObject resultObject = resultArray.getJSONObject(i);

//                                        Log.e("Checking", "3000016");

                                            TipBackupModelClass t= new TipBackupModelClass (resultObject.getString("text"));
//                                        Log.e("Checking", "3000017");
                                            TipBackupDatabaseHelperClass b= new TipBackupDatabaseHelperClass( Tip.this );
                                            b.addItem( t );
//                                            Log.e("Checking", "300000");
                                        }
//                                    }

                                } catch (JSONException e) {
//                                    Log.e("Checking", "3000018");
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText( getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT ).show();
//                                Log.e( "Checking", "300001" );
                            }
                        } );


//                Log.e("Checking", "3000012");
                requestQueue.add( stringRequest2 );
//                Log.e("Checking", "3000013");
            }

        }

        public String GetLine()
        {
//            Log.e("Checking", "000007");
            String tipLine;

            TipBackupDatabaseHelperClass obj= new TipBackupDatabaseHelperClass( Tip.this );
//            Log.e("Checking", "000008");
            TipDatabaseHelperClass databaseHelperClass = new TipDatabaseHelperClass( Tip.this );
            tipLine=obj.lastLine(databaseHelperClass.getList().size());
//            Log.e("Checking", "000009");

            return tipLine;
        }

        public int checkBackup()
        {
            int i;

            TipBackupDatabaseHelperClass bobj= new TipBackupDatabaseHelperClass( Tip.this );
            List<TipBackupModelClass> list=bobj.getList();
            i=list.size();

            return (i+1);
        }

        public void updateDisplay(String currentdatetime, int i)
        {
             if(i==1){
                TipBackupDatabaseHelperClass backupDatabasehelperClass = new TipBackupDatabaseHelperClass( Tip.this );
//                Log.e( "Checking", "000014" );
                backupDatabasehelperClass.deleteItem();
            }


            TipDatabaseHelperClass databaseHelperClass = new TipDatabaseHelperClass( Tip.this );
//            Log.e("Checking", "000006");
            line= GetLine();
//            Log.e("Checking", "000010");
            String currentdate=currentdatetime.substring( 0,10 );
//            Log.e("Checking", "000011");
            TipModelClass tipmodelClass = new TipModelClass( currentdate, line );
//            Log.e("Checking", "000012");
            databaseHelperClass.addItem( tipmodelClass );

//            line=null;


//            Log.e("Checking", "000013");
//            TipBackupDatabaseHelperClass backupDatabasehelperClass= new TipBackupDatabaseHelperClass( this );
//            Log.e("Checking", "000014");
//            backupDatabasehelperClass.deleteItem( 0 );

//            Log.e("Checking", "000015");
            if((databaseHelperClass.getList().size())>29)
                databaseHelperClass.deleteItem( 0 );
//            Log.e("Checking", "000016");

        }



}