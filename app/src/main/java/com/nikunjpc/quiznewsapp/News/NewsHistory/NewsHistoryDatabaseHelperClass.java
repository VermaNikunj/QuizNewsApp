package com.nikunjpc.quiznewsapp.News.NewsHistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsHistoryDatabaseHelperClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "news_database";

    private static final String TABLE_NAME = "NEWS_TABLE";

    public static final String ID = "id";
    public static final String SEARCHED = "searched";

    private SQLiteDatabase sqLiteDatabase;


    public NewsHistoryDatabaseHelperClass(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + SEARCHED + " TEXT NOT NULL)";

        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( " DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public void addItem(NewsHistoryModelClass modelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( NewsHistoryDatabaseHelperClass.SEARCHED, modelClass.getSearched() );

        SQLiteDatabase db = getWritableDatabase();
        db.insert( TABLE_NAME, null, contentValues );
    }

    public List<NewsHistoryModelClass> getList() {
        String sql = "select * from " + TABLE_NAME+" order by ID desc";
        SQLiteDatabase db= this.getWritableDatabase();
        List<NewsHistoryModelClass> store = new ArrayList<>();
        Cursor cursor = db.rawQuery( sql, null );
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt( cursor.getString( 0 ) );
                String searched = cursor.getString( 1 );
                store.add( new NewsHistoryModelClass( id, searched ) );
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
}
