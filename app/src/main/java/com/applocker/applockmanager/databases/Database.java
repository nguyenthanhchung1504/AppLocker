package com.applocker.applockmanager.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.applocker.applockmanager.models.Been;
import com.applocker.applockmanager.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16-Oct-17.
 */

public class Database extends SQLiteOpenHelper
{


    public Database(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        String Command = "CREATE TABLE " + Constant.TABLE_NAME + "(" +
                Constant.SR_NO + " INTEGER," +
                Constant.APP_NAME + " STRING PRIMARY KEY NOT NULL" +");";
//        String Command = "create table "+Constant.TABLE_NAME+"("+Constant.STUDENT_ROLL_NO+" integer PRIMARY KEY, "+Constant.STUDENT_NAME+" text, "+
//                Constant.STUDENT_EMAIL+" text, "+Constant.STUDENT_ADDRESS+" text "+");";
        sqLiteDatabase.execSQL(Command);

        String Command1 = "CREATE TABLE " + Constant.TABLE_NAME1 + "(" +
                Constant.PIN + " INTEGER" +");";
        sqLiteDatabase.execSQL(Command1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean AppInsert(Been been)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constant.APP_NAME,been.getAppName());

        long i = sqLiteDatabase.insert(Constant.TABLE_NAME,null,contentValues);
        if(i>0) return true;

        return  false;
    }
    public boolean PinInsert(Been been)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constant.PIN,been.getPin());

        long i = sqLiteDatabase.insert(Constant.TABLE_NAME1,null,contentValues);
        if(i>0) return true;

        return  false;
    }

    public boolean AppRemove(Been been)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //String table = "beaconTable";
        //String whereClause = "_id=?";
        //String[] whereArgs = new String[] { String.valueOf(row) };

        sqLiteDatabase.execSQL("DELETE FROM " + Constant.TABLE_NAME + " WHERE " + Constant.APP_NAME + "= '" + been.getAppName() + "'");
       // long i = sqLiteDatabase.delete(Constant.TABLE_NAME, Constant.APP_NAME, new String[]{been.getAppName()});

        //if(i>0) return true;

        return  true;
    }


    public List<Been> AppCheck() {
        {
            String Query = "SELECT  * FROM " + Constant.TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(Query, null);
            List<Been> list = new ArrayList<Been>();

            while(cursor.moveToNext())
            {
                list.add(new Been(cursor.getString(1)));
            }
            if(cursor != null)
                cursor.close();
            db.close();
            return list;
        }
    }

    public boolean PinUpdate(Been been)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //String table = "beaconTable";
        //String whereClause = "_id=?";
        //String[] whereArgs = new String[] { String.valueOf(row) };
        long i =  sqLiteDatabase.update(Constant.TABLE_NAME1, contentValues, Constant.PIN + "=" + been.getPin(), null);
        // long i = sqLiteDatabase.delete(Constant.TABLE_NAME, Constant.APP_NAME, new String[]{been.getAppName()});

        if(i>0) return true;

        return  true;
    }

    public List<Been> PinCheck() {
        {
            String Query = "SELECT  * FROM " + Constant.TABLE_NAME1;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(Query, null);
            List<Been> list = new ArrayList<Been>();

            while(cursor.moveToNext())
            {
                list.add(new Been(cursor.getString(0)));
            }
            return list;
        }
    }


}
