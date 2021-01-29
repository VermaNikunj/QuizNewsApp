package com.nikunjpc.quiznewsapp.TipOfTheDay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TipDatabaseHelperClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "tip_database";

    private static final String TABLE_NAME = "TIP_DISPLAY_TABLE";

    public static final String ID = "id";
    public static final String TIP_DATE = "tipDate";
    public static final String  TIP_LINE= "tipLine";

    private SQLiteDatabase sqLiteDatabase;


    public TipDatabaseHelperClass(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIP_DATE + " TEXT NOT NULL," +
                TIP_LINE+ " TEXT NOT NULL ) ";


        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( " DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public void addItem(TipModelClass modelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( TipDatabaseHelperClass.TIP_DATE, modelClass.getTipDate() );
        contentValues.put( TipDatabaseHelperClass.TIP_LINE, modelClass.getTipLine() );

        SQLiteDatabase db = getWritableDatabase();
        db.insert( TABLE_NAME, null, contentValues );
    }

    public List<TipModelClass> getList() {
        String sql = "select * from " + TABLE_NAME +" order by ID desc";
        SQLiteDatabase db= this.getWritableDatabase();
        List<TipModelClass> store = new ArrayList<>();
        Cursor cursor = db.rawQuery( sql, null );
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt( cursor.getString( 0 ) );
                String date = cursor.getString( 1 );
                String line = cursor.getString( 2 );
                store.add( new TipModelClass( id, date, line ) );
            } while (cursor.moveToNext());

        }
        cursor.close();
        return store;
    }

    public void deleteItem (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete( TABLE_NAME, "ID = ? ", new String[] {String.valueOf( id )} );

    }

    public String lastDate()
    {
        String date2;

//        Log.e("Checking", "000110");

        List<TipModelClass> list=  getList();
//        Log.e("Checking", "000111----"+ list.size());
        date2= list.get( getList().size()-1 ).getTipDate();
//        Log.e("Checking", "000112");
        return date2;
    }
}
