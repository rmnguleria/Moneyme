package com.gb.raman.moneyme.helperclass;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by raman on 24/10/14.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "money_data.sqlite";
    public static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }




}
