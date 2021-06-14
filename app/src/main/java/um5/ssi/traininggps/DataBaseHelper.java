package um5.ssi.traininggps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "way_points";
    public static final String COLUMN_NAME_ENTRY_ID = "location_id";
    public static final String COLUMN_NAME_TITLE = "location_title";
    public static final String COLUMN_NAME_LATITUDE = "lat";
    public static final String COLUMN_NAME_LONGTITUDE = "lon";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TIME = "time";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WayPoints.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String DATE_TYPE = " TEXT";
    private static final String TIME_TYPE = " INTEGER";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_TITLE + TEXT_TYPE + ", " +
                COLUMN_NAME_DATE + DATE_TYPE +", " +
                COLUMN_NAME_TIME + TIME_TYPE +", " +
                COLUMN_NAME_LATITUDE + DOUBLE_TYPE +", " +
                COLUMN_NAME_LONGTITUDE + DOUBLE_TYPE + " )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(WayPoint wayPoint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_DATE,wayPoint.getDate());
        cv.put(COLUMN_NAME_LATITUDE,wayPoint.getLat());
        cv.put(COLUMN_NAME_LONGTITUDE,wayPoint.getLon());
        cv.put(COLUMN_NAME_TITLE,wayPoint.getTitle());
        cv.put(COLUMN_NAME_TIME,wayPoint.getTime());

        long insert = db.insert(TABLE_NAME, null, cv);
        if(insert == -1){return false;}
        else {return true;}
    }

    public boolean deleteOne(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_NAME+" WHERE "+ COLUMN_NAME_ENTRY_ID +" = "+id;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){return true;}
        else {return false;}
    }

    public boolean editOne(String title, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_TITLE,title);
        int update = db.update(TABLE_NAME, cv, COLUMN_NAME_ENTRY_ID + " = " + id, null);
        if(update == -1){return false;}
        else {return true;}
    }

    public List<WayPoint> getAll(){
        List<WayPoint> returnList = new ArrayList<>();
        //get data from db
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            //loop through results
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ENTRY_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TIME));
                double lat = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_LATITUDE));
                double lon = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_LONGTITUDE));

                WayPoint wayPoint = new WayPoint(id,title,date,time,lat,lon);
                returnList.add(wayPoint);
            }while (cursor.moveToNext());

        }
        // close the cursor and the db
        cursor.close();
        db.close();
        return returnList;
    }
}
