package com.driviz.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Driviz on 19-07-2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME="notepad.db";
    private String TABLE_NOTES="notes";
    private String COLUMN_ID="_id";
    private String COLUMN_FILE_NAME="file_name";
    private String COLUMN_NOTES_DATA="notes_data";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE "+TABLE_NOTES+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY, "+ COLUMN_NOTES_DATA+" TEXT DEFAULT '', "+COLUMN_FILE_NAME+" TEXT );";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+ TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }

    public void add_data(Notes_objects notes_objects)
    {
        ContentValues values=new ContentValues();

        values.put(COLUMN_NOTES_DATA,"");
        values.put(COLUMN_ID,notes_objects.get_id());
        values.put(COLUMN_FILE_NAME,notes_objects.get_file_name());
        SQLiteDatabase database=getWritableDatabase();
        database.insert(TABLE_NOTES,null,values);
        database.close();
    }

    public void delete_data(int file_number)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_NOTES+" WHERE "+COLUMN_ID+ "="+file_number+";");
        database.close();
    }

    public void update_data(int file_number,String data)
    {
        SQLiteDatabase database=getWritableDatabase();
        String query=("UPDATE "+TABLE_NOTES+" SET "+COLUMN_NOTES_DATA+"=\""+data+"\" WHERE "+COLUMN_ID+"="+file_number+";");
        database.execSQL(query);
        database.close();
    }

    public String databasetostring(int file_number)
    {
        String dbstring="";
        SQLiteDatabase database=getWritableDatabase();
        String query=("Select "+COLUMN_NOTES_DATA+ " FROM "+TABLE_NOTES+" WHERE "+COLUMN_ID+"="+file_number+";");
        Cursor c=database.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("notes_data"))!=null)
            {
                dbstring=c.getString(c.getColumnIndex("notes_data"));
            }
            c.moveToNext();
        }
        database.close();
        c.close();
        return dbstring;
    }

    public List<Notes_objects> getFileName()
    {
        List<Notes_objects> filename=new ArrayList<>();
        SQLiteDatabase database=getWritableDatabase();
        String query=("Select * FROM "+TABLE_NOTES+";");
        Cursor c=database.rawQuery(query,null);
        Notes_objects obj;
        if(c.getCount()>0) {
            c.moveToFirst();
            do {
                int id = (c.getInt(c.getColumnIndex("_id")));
                String file_name = (c.getString(c.getColumnIndex("file_name")));

                obj = new Notes_objects(id, file_name);
                filename.add(obj);
            } while (c.moveToNext());
            database.close();
            c.close();
        }
        return filename;
    }

}
