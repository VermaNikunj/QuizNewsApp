package com.nikunjpc.quiznewsapp.TipOfTheDay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TipBackupDatabaseHelperClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "tip_backup_database";

    private static final String TABLE_NAME = "TIP_BACKUP_TABLE";

    public static final String ID = "id";
    public static final String TIP_BACKUP_LINE= "tip_Backup_Line";

    private SQLiteDatabase sqLiteDatabase;


    public TipBackupDatabaseHelperClass(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIP_BACKUP_LINE+ " TEXT NOT NULL ) ";

        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( " DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public void addItem(TipBackupModelClass modelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( TipBackupDatabaseHelperClass.TIP_BACKUP_LINE, modelClass.getTipBackupLine() );

        SQLiteDatabase db = getWritableDatabase();
        db.insert( TABLE_NAME, null, contentValues );
    }

    public List<TipBackupModelClass> getList() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db= this.getWritableDatabase();
        List<TipBackupModelClass> store = new ArrayList<>();
        Cursor cursor = db.rawQuery( sql, null );
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt( cursor.getString( 0 ) );
                String line = cursor.getString( 1 );
                store.add( new TipBackupModelClass( id, line ) );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return store;
    }

    public void deleteItem ()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int id=getList().size()-1;
//        id-=1;

//        Log.e("check delete", getList().get( id ).getTipBackupLine());
        db.delete( TABLE_NAME, "ID = ? ", new String[] {String.valueOf( id )} );

//        Log.e("check delete", getList().get( id ).getTipBackupLine());
    }


//    public String lastLine()
//    {
//        String line;
//        String selectsql = "select * from " + TABLE_NAME;
//        SQLiteDatabase db= this.getReadableDatabase();
//        Cursor selectcursor = db.rawQuery( selectsql, null );
//        selectcursor.moveToFirst();
//        line= selectcursor.getString( 1 );
//        Log.e("Checking", "1111111");
//        return line;
//    }

    public String lastLine(int m)
    {
        String line;

//        Log.e("Checking", "100110");

        List<TipBackupModelClass> list=  getList();
//        Log.e("Checking", "100111----"+ list.size());
        int n=list.size();
        if(n==0)
            line="Every day is best";
        else {
            if(n>m)
            line = list.get(  n-m ).getTipBackupLine();
            else if(n<m)
                line = list.get(  m-n ).getTipBackupLine();
            else line = list.get(  m-1 ).getTipBackupLine();

        }
//        Log.e("Checking Line", "100112"+line);
        return line;
    }
}
