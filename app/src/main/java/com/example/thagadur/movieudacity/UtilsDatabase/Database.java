package com.example.thagadur.movieudacity.UtilsDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thagadur on 2/17/2018.
 */

public class Database extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movie Database";

    private static final String CREATE_TABLE = "CREATE TABLE ";

    private static final String DATATYPE_NUMERIC = " INTEGER";
    private static final String DATATYPE_VARCHAR = " TEXT";
    private static final String PRIMARY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String OPEN_BRACE = "(";
    private static final String CLOSE_BRACE = ")";
    private static final String COMMA = ",";

    public Database(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE_FAV = new StringBuilder()
                .append(CREATE_TABLE).append(ContentProviderUtils.MovieTuple.TABLE_NAME).append(OPEN_BRACE)
                .append(ContentProviderUtils.MovieTuple.COLUMN_MOVIE_ID).append(PRIMARY_KEY + COMMA)
                .append(ContentProviderUtils.MovieTuple.COLUMN_TITLE).append(DATATYPE_VARCHAR + COMMA)
                .append(ContentProviderUtils.MovieTuple.COLUMN_RELEASE_DATE).append(DATATYPE_VARCHAR + COMMA)
                .append(ContentProviderUtils.MovieTuple.COLUMN_POSTER_PATH).append(DATATYPE_VARCHAR + COMMA)
                .append(ContentProviderUtils.MovieTuple.COLUMN_DETAILS).append(DATATYPE_VARCHAR + COMMA)
                .append(ContentProviderUtils.MovieTuple.COLUMN_RATING).append(DATATYPE_VARCHAR + CLOSE_BRACE).toString();

        sqLiteDatabase.execSQL(CREATE_TABLE_FAV);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentProviderUtils.MovieTuple.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
