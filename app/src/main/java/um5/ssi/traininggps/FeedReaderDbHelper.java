package um5.ssi.traininggps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FeedReaderDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WayPoints.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String DATE_TYPE = " TEXT";
    private static final String TIME_TYPE = " INTEGER";


    private static FeedReaderDbHelper instance;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + ", " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DATE + DATE_TYPE +", " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TIME + TIME_TYPE +", " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE + DOUBLE_TYPE +", " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_LONGTITUDE + DOUBLE_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public synchronized FeedReaderDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FeedReaderDbHelper(context);
        }
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean addOne(WayPoint wayPoint){
        SQLiteDatabase db = this.getWritableDatabase("Password");
        ContentValues cv = new ContentValues();
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,wayPoint.getDate());
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE,wayPoint.getLat());
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGTITUDE,wayPoint.getLon());
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,wayPoint.getTitle());
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME,wayPoint.getTime());

        long insert = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, cv);
        if(insert == -1){return false;}
        else {return true;}
    }

    public boolean deleteOne(int id){
        SQLiteDatabase db = this.getWritableDatabase("Password");
        String query = "DELETE FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME+" WHERE "+ FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID +" = "+id;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){return true;}
        else {return false;}
    }

    public boolean editOne(String title, int id){
        SQLiteDatabase db = this.getWritableDatabase("Password");
        ContentValues cv = new ContentValues();
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,title);
        int update = db.update(FeedReaderContract.FeedEntry.TABLE_NAME, cv, FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " = " + id, null);
        if(update == -1){return false;}
        else {return true;}
    }

    public List<WayPoint> getAll(){
        List<WayPoint> returnList = new ArrayList<>();
        //get data from db
        String query = "SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase("Password");
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            //loop through results
            do {
                int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID));
                String title = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE));
                int time = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME));
                double lat = cursor.getDouble(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE));
                double lon = cursor.getDouble(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGTITUDE));

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
