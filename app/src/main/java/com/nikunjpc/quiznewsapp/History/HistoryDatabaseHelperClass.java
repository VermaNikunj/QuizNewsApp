package com.nikunjpc.quiznewsapp.History;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryDatabaseHelperClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    public static final String DATABASE_NAME = "quiz_database";

    private static final String TABLE_NAME = "QUIZ_TABLE";

    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static  final String CORRECT_ANSWER ="correct_answer";

    private SQLiteDatabase sqLiteDatabase;


    public HistoryDatabaseHelperClass(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE + " TEXT NOT NULL," +
                CATEGORY + " TEXT NOT NULL,"+ CORRECT_ANSWER+" INTEGER NOT NULL ) ";


        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( " DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public void addItem(HistoryModelClass modelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( HistoryDatabaseHelperClass.TYPE, modelClass.getType() );
        contentValues.put( HistoryDatabaseHelperClass.CATEGORY, modelClass.getCategory() );
        contentValues.put( HistoryDatabaseHelperClass.CORRECT_ANSWER, modelClass.getCorrect_answer() );

        SQLiteDatabase db = getWritableDatabase();
        db.insert( TABLE_NAME, null, contentValues );
    }

    public List<HistoryModelClass> getList() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db= this.getWritableDatabase();
        List<HistoryModelClass> store = new ArrayList<>();
        Cursor cursor = db.rawQuery( sql, null );
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt( cursor.getString( 0 ) );
                String type = cursor.getString( 1 );
                String category = cursor.getString( 2 );
                int ca = Integer.parseInt(cursor.getString( 3 ));
                store.add( new HistoryModelClass( id, type, category, ca ) );
            } while (cursor.moveToNext());

        }
        cursor.close();
        return store;
    }

//
//    public void updateItem(HistoryModelClass modelClass) {
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put( HistoryDatabaseHelperClass.TYPE, modelClass.getType() );
//        contentValues.put( HistoryDatabaseHelperClass.CATEGORY, modelClass.getCategory() );
//        contentValues.put( HistoryDatabaseHelperClass.CORRECT_ANSWER, modelClass.getCorrect_answer() );
//        SQLiteDatabase db= this.getWritableDatabase();
//        db.update( TABLE_NAME, contentValues, ID+" = ? ", new String[]
//                { String.valueOf( modelClass.getId())});
//
//    }

    public void deleteItem (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

//        id-=1;
//        Log.e("History delete cat1", getList().get(id).getCategory());
//
//        Log.e("History delete size 1", ""+getList().size());

        db.delete( TABLE_NAME, "ID = ? ", new String[] {String.valueOf( id )} );

//        Log.e("History delete size 2", ""+getList().size());

//        Log.e("History delete cat 2", getList().get(id).getCategory());
    }
}