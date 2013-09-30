package net.healthroad.harmful.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToxinDBAdapter {

    private static final String TAG = "Harmful";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_KOR = "kor";
    public static final String KEY_ENG = "eng";
    public static final String KEY_KEYWORD = "keyword";
    public static final String KEY_CONTENTS = "contents";

    private static final String DATABASE_NAME = "harmful";
    private static final String DATABASE_TABLE = "toxin";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context ctx;

    /**
     * 생성자
     * @param context
     */
    public ToxinDBAdapter(Context context) {
        this.ctx = context;
    }

    public ToxinDBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(ctx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /**
     * 전체 데이터를 얻어온다.
     * @return
     */
    public Cursor fetchAllToxins() {
        return mDb.query(DATABASE_TABLE,
                new String[] { KEY_ROWID, KEY_KOR, KEY_ENG, KEY_KEYWORD, KEY_CONTENTS },
                null, null, null, null, null);
    }

    /**
     * 특정 rowId의 데이터를 얻어온다.
     * @param rowId
     * @return
     * @throws SQLException
     */
    public Cursor fetchToxins(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_KOR, KEY_ENG, KEY_KEYWORD, KEY_CONTENTS }, KEY_ROWID
                + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }
}
