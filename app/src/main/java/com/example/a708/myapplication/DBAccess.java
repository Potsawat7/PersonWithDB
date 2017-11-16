package com.example.a708.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/14/2017.
 */

public class DBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DBAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DBAccess(Context context) {
        this.openHelper = new DBhelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DBAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DBAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }
    public void insertPerson(Person person) {
        ContentValues values = new ContentValues();
        values.put("nickName", person.getNickName());
        values.put("firstName", person.getFirstName());
        values.put("surName", person.getSurName());

        database.insert("PersonTable", null, values);
    }
    public List<Person> getAllData() {

        List<Person> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM PersonTable", null);
        Log.i("check", "getAllData: cursor  " + cursor.getCount());
//        System.out.println("first " +cursor.moveToFirst());
//        System.out.println("sec " +cursor.moveToNext());

        while (cursor.moveToNext()) {

                Log.i("in loop", "getAllData: ");
                String nickname = cursor.getString(0);
                String firstname = cursor.getString(1);
                String surname = cursor.getString(2);

                list.add(new Person(nickname,firstname,surname));


        }
        Log.i("listSize", "getAllData: " + list.size());
        cursor.close();
        return list;
    }
    public void delete(String nn,String fn , String sn){
        database.execSQL("delete from PersonTable where nickName="+nn+","+"firstName="+fn+","+"surName="+sn);
        database.close();
    }
}
