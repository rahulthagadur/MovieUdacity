package com.example.thagadur.movieudacity.UtilsDatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.media.tv.TvContract.Programs.Genres.MOVIES;
import static com.example.thagadur.movieudacity.UtilsDatabase.ContentProviderUtils.MovieTuple.CONTENT_URI;
import static com.example.thagadur.movieudacity.UtilsDatabase.ContentProviderUtils.MovieTuple.TABLE_NAME;

/**
 * Created by Thagadur on 2/17/2018.
 */

public class DbContentProvider extends ContentProvider {

    Database databaseHelper;
    public static final int movies = 400;
    private static final UriMatcher uriMatcher = getUri();

    public static UriMatcher getUri() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContentProviderUtils.DOMAIN, ContentProviderUtils.MOVIE_PATH, movies);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        databaseHelper = new Database(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] proj, @Nullable String selection, @Nullable String[] selArguments, @Nullable String sortOrder) {
        final SQLiteDatabase db = databaseHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case movies:
                returnCursor = db.query(TABLE_NAME,
                        proj,
                        selection,
                        selArguments,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("URI Not Found" + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        Uri returnUri;

        switch(match){
            case movies:
                long id = db.insert(TABLE_NAME, null,contentValues);
                if(id > 0){
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                }else{
                    throw new android.database.SQLException("Insertion failed" + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("URI Not Found" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int id= uriMatcher.match(uri);
        int tasksDeleted;
        switch (id) {
            case movies:
                tasksDeleted = db.delete(TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("URI Not Found" + uri);
        }
        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int id = uriMatcher.match(uri);
        int tasksUpdated;
        switch (id) {
            case movies:

                tasksUpdated = db.update(TABLE_NAME, contentValues, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("URI Not Found" + uri);
        }
        if (tasksUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksUpdated;
    }
}

