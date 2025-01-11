package edu.commonwealthu.mindeye;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * DBHandler is a database helper class for managing a SQLite database
 * that stores journal log entries. Each log entry includes fields such as:
 * - Feeling (float)
 * - Journal text (string)
 * - Time (hour and minute)
 * - Date (month, day, and year)
 *
 * This class provides methods for:
 * - Creating, reading, updating, and deleting (CRUD) log entries.
 * - Fetching all log entries sorted in descending order by date and time.
 * - Calculating metadata such as the total number of logs and the maximum log ID.
 *
 * The database schema includes:
 * - Table Name: journal
 * - Columns: id (primary key), feeling, text, hour, minute, month, day, year
 * @author myurkunas
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database configuration
    private static final String DB_NAME = "journaldb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "journal";

    // Table column names
    private static final String ID_COL = "id";
    private static final String FEELING_COL = "feeling";
    private static final String TEXT_COL = "text";
    private static final String HOUR_COL = "hour";
    private static final String MINUTE_COL = "minute";
    private static final String MONTH_COL = "month";
    private static final String DAY_COL = "day";
    private static final String YEAR_COL = "year";

    /**
     * Constructor for DBHandler.
     *
     * @param context The context in which the database is accessed.
     */
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FEELING_COL + " FLOAT, "
                + TEXT_COL + " TEXT, "
                + HOUR_COL + " INTEGER, "
                + MINUTE_COL + " INTEGER, "
                + MONTH_COL + " INTEGER, "
                + DAY_COL + " INTEGER, "
                + YEAR_COL + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void newLog(float feeling, String journalText, int hour, int minute, int month, int day, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (!db.isOpen()) {
            Log.e("SQLite", "Database not open for writing");
        }

        ContentValues values = new ContentValues();
        values.put(FEELING_COL, feeling);
        values.put(TEXT_COL, journalText);
        values.put(HOUR_COL, hour);
        values.put(MINUTE_COL, minute);
        values.put(MONTH_COL, month);
        values.put(DAY_COL, day);
        values.put(YEAR_COL, year);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.e("SQLite", "Failed to insert row");
        } else {
            Log.d("SQLite", "Row inserted with ID: " + result);
        }
        db.close();
    }

    public LogEntry getLog(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        LogEntry logEntry = null;
        if (cursor.moveToFirst()) {
            logEntry = new LogEntry(
                    id,
                    cursor.getFloat(cursor.getColumnIndexOrThrow(FEELING_COL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TEXT_COL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(HOUR_COL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MINUTE_COL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MONTH_COL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DAY_COL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(YEAR_COL))
            );
        }

        cursor.close();
        db.close();
        return logEntry;
    }

    public int maxLogID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + ID_COL + ") FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        int maxID = -1;
        if (cursor.moveToFirst()) {
            maxID = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return maxID;
    }

    public int logCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    public List<LogEntry> getAllLogs() {
        List<LogEntry> logEntries = new ArrayList<>();
        int maxEntry = maxLogID();

        for (int i = 1; i <= maxEntry; i++) {
            LogEntry log = getLog(i);
            if (log != null) {
                logEntries.add(log);
            }
        }

        logEntries.sort(Comparator.comparing(LogEntry::getYear)
                .thenComparing(LogEntry::getMonth)
                .thenComparing(LogEntry::getDay)
                .thenComparing(LogEntry::getHour)
                .thenComparing(LogEntry::getMinute).reversed());

        return logEntries;
    }

    public boolean deleteLog(int logId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(logId)});
        db.close();
        return result > 0;
    }

    public void deleteAllLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate the table
        onCreate(db);
        db.close();
    }


    public boolean updateLog(int logId, float feeling, String journalText, int hour, int minute, int month, int day, int year) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FEELING_COL, feeling);
        contentValues.put(TEXT_COL, journalText);
        contentValues.put(HOUR_COL, hour);
        contentValues.put(MINUTE_COL, minute);
        contentValues.put(MONTH_COL, month);
        contentValues.put(DAY_COL, day);
        contentValues.put(YEAR_COL, year);

        int rowsAffected = db.update(TABLE_NAME, contentValues, ID_COL + " = ?", new String[]{String.valueOf(logId)});
        db.close();
        return rowsAffected > 0;
    }

}
