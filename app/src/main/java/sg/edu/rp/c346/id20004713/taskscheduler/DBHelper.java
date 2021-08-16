package sg.edu.rp.c346.id20004713.taskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "list.db";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_NAME = "toDo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_Title = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_Time= "time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_Title + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DATE + " DATE, " + COLUMN_Time + " TIME)";
        db.execSQL(createNoteTableSql);

        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_Title, "TITLE: " + i);
            values.put(COLUMN_DESCRIPTION, "Desc " + i);
            values.put(COLUMN_DATE, "202" + i +"-01-01");
            values.put(COLUMN_Time, "00:00:00");
            db.insert(TABLE_NAME, null, values);
        }
        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        //db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN  module_name TEXT ");  //Used to update a table without dropping all the items in database.
    }

    public ArrayList<item> getAllItem() {
        ArrayList<item> notes = new ArrayList<item>();

        String selectQuery = "SELECT " + COLUMN_Title + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_DATE + ", " +
                COLUMN_Time + ", " + COLUMN_ID +" FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String desc = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                int id = cursor.getInt(4);
                item item = new item(title, desc, date, time);
                item.setId(id);
                notes.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }

    public long insert(item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_Title, item.getTitle());
        values.put(COLUMN_DESCRIPTION, item.getDesc());
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_Time, item.getTime() + ":00");

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        return result;
    }

    public int update(item data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_Title, data.getTitle());
        values.put(COLUMN_DESCRIPTION, data.getDesc());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_Time, data.getTime() + ":00");

        String condition = COLUMN_ID + " = ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_NAME, values, condition, args);
        db.close();

        if (result < 1){
            Log.d("DBHelper", "Update failed");
        }

        return result;
    }

    public int delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + " = ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, condition, args);
        db.close();

        if (result < 1){
            Log.d("DBHelper", "Update failed");
        }

        return result;
    }
}
