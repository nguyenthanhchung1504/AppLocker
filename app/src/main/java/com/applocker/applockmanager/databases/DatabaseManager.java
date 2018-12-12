package com.applocker.applockmanager.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.applocker.applockmanager.models.App;

import java.util.ArrayList;
import java.util.List;



public class DatabaseManager extends SQLiteOpenHelper {

    public static final int VERSION = 2;
    public static final String DATABASE_NAME = "applock";
    public static final String TABLE_NAME = "lock";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PACKAGE_NAME = "packagename";
    public static final String TYPE = "type";
    public static final String STATE = "state";

    Context mContext;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " integer primary key, " +
                NAME + " TEXT, " +
                PACKAGE_NAME + " TEXT, " +
                TYPE + " TEXT, " +
                STATE + " integer)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add new a student
    public void addApp(App app) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, app.getName());
        values.put(PACKAGE_NAME, app.getPackageName());
        values.put(STATE, app.getState());

        //Neu de null thi khi value bang null thi loi
        db.insert(TABLE_NAME, null, values);
//        db.close();
    }

    public int update(App app) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATE, app.getState());

        return db.update(TABLE_NAME, values, PACKAGE_NAME + "=?", new String[]{app.getPackageName()});
    }

    public App getAppByPackageName(String packageName) {
        Log.d("AAA", "packageName: " + packageName);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID,
                        NAME, PACKAGE_NAME, STATE}, PACKAGE_NAME + "=?",
                new String[]{packageName}, null, null, null, null);
        if (cursor.getCount()!=0) {
            cursor.moveToFirst();

            App student = new App(null, cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(3));
            cursor.close();
            db.close();

            return student;
        }

        return null;
    }

    public int getAppsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void deleteApp(App app) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, PACKAGE_NAME + " = ?",
                new String[]{app.getPackageName()});
        db.close();
    }

    public List<App> getAllApp() {
        List<App> listApp = new ArrayList<App>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                App app = new App();
                app.setName(cursor.getString(1));
                app.setPackageName(cursor.getString(2));
                app.setState(cursor.getInt(3));
                listApp.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listApp;
    }

    public List<App> getAppsAreLocking() {
        List<App> listApp = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + STATE + " = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                App app = new App();
                app.setName(cursor.getString(1));
                app.setPackageName(cursor.getString(2));
                app.setState(cursor.getInt(3));
                listApp.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listApp;
    }


}
