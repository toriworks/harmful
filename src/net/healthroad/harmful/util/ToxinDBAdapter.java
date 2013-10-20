package net.healthroad.harmful.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToxinDBAdapter {

    private static final String TAG = "Harmful";

    public static final String KEY_ROWID = "rowid";
    public static final String KEY_KOR = "kor";
    public static final String KEY_ENG = "eng";
    public static final String KEY_KEYWORD = "keyword";
    public static final String KEY_CONTENTS = "contents";

    private static final String DATABASE_NAME = "harmful.db";
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
     * 전체 데이터를 얻어온다.(최대 100개 데이터를 얻어온다.)
     * @return
     */
    public Cursor fetchAllToxins() {
        return mDb.query(DATABASE_TABLE,
                new String[] { KEY_ROWID, KEY_KOR, KEY_ENG, KEY_KEYWORD, KEY_CONTENTS },
                null, null, null, null, "eng ASC", "500");
    }

    public Cursor fetchToxins(String strSearch, String strType) throws SQLException {
//        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_KOR, KEY_ENG, KEY_KEYWORD, KEY_CONTENTS }, KEY_KEYWORD
//                + "LIKE %" + strSearch, null, null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;

        String sql = "";
        if(strType.equals(ICommonCodes.SEARCH_TYPE_PHASE)) {
            sql = "SELECT rowid, kor, eng, keyword, contents FROM toxin WHERE keyword LIKE '%" + strSearch + "%'";
        } else if(strType.equals(ICommonCodes.SEARCH_TYPE_BUTTON)) {
            // 코드 포인트
            int codePoint = strSearch.codePointAt(0);
            if(codePoint >= "ㄱ".codePointAt(0) && codePoint <= "ㅎ".codePointAt(0)) {
                sql = "SELECT rowid, kor, eng, keyword, contents FROM toxin WHERE " + getKorQuery(codePoint);
            } else if(codePoint >= "A".codePointAt(0) && codePoint <= "Z".codePointAt(0)) {
                sql = "SELECT rowid, kor, eng, keyword, contents FROM toxin WHERE eng LIKE '" + strSearch + "%'";
            } else {
                sql = "SELECT rowid, kor, eng, keyword, contents FROM toxin WHERE kor LIKE '" + strSearch + "%'";
                sql = sql + " OR eng LIKE '" + strSearch + "%'";
            }
        }
        Log.d(TAG, "쿼리:" + sql);

        Cursor mCursor = mDb.rawQuery(sql, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
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

    private String getKorQuery(int codePoint) {
        String sql = " kor BETWEEN ";
        if(codePoint == "ㄱ".codePointAt(0)) {
            sql = sql + "'가' AND '깋' ";
        } else if(codePoint == "ㄴ".codePointAt(0)) {
            sql = sql + "'나' AND '닣' ";
        } else if(codePoint == "ㄷ".codePointAt(0)) {
            sql = sql + "'다' AND '딯' ";
        } else if(codePoint == "ㄹ".codePointAt(0)) {
            sql = sql + "'라' AND '맇' ";
        } else if(codePoint == "ㅁ".codePointAt(0)) {
            sql = sql + "'마' AND '밓' ";
        } else if(codePoint == "ㅂ".codePointAt(0)) {
            sql = sql + "'바' AND '빟' ";
        } else if(codePoint == "ㅅ".codePointAt(0)) {
            sql = sql + "'사' AND '싷' ";
        } else if(codePoint == "ㅇ".codePointAt(0)) {
            sql = sql + "'아' AND '잏' ";
        } else if(codePoint == "ㅈ".codePointAt(0)) {
            sql = sql + "'자' AND '짛' ";
        } else if(codePoint == "ㅊ".codePointAt(0)) {
            sql = sql + "'차' AND '칳' ";
        } else if(codePoint == "ㅋ".codePointAt(0)) {
            sql = sql + "'카' AND '킿' ";
        } else if(codePoint == "ㅌ".codePointAt(0)) {
            sql = sql + "'타' AND '팋' ";
        } else if(codePoint == "ㅍ".codePointAt(0)) {
            sql = sql + "'파' AND '핗' ";
        } else if(codePoint == "ㅎ".codePointAt(0)) {
            sql = sql + "'하' AND '힣' ";
        }

        return sql;
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
