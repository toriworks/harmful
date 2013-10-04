package net.healthroad.harmful.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import net.healthroad.harmful.R;
import net.healthroad.harmful.util.ICommonCodes;
import net.healthroad.harmful.util.Toxin;
import net.healthroad.harmful.util.ToxinDBAdapter;

public class ToxinDetailActivity extends Activity {

    public static String TAG = "Harmful";
    private int dIdx = -1;

    /** 영문제목 */
    private static TextView textEngTitle;
    /** 한글제목 */
    private static TextView textKorTitle;
    /** 내용출력용 뷰 */
    private static TextView textToxinDetail;
    /** SQLite 어뎁터 */
    private ToxinDBAdapter toxinDBAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toxin_detail);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();

        toxinDBAdapter = new ToxinDBAdapter(getApplicationContext());
        toxinDBAdapter.open();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 번들에서 값 추출
        dIdx = this.getIntent().getIntExtra(ICommonCodes.TOXIN_DETAIL_BUNDLE_KEY, -1);
        Log.d(TAG, "번들에서 받은 값:" + dIdx);

        // 데이터 읽기
        loadData(dIdx);
    }

    @Override
    protected void onDestroy() {
        toxinDBAdapter.close();
        super.onDestroy();
    }

    /**
     * 화면 컴포넌트 초기화를 수행한다.
     */
    private void initScreenComponents() {
        textEngTitle = (TextView) findViewById(R.id.text_eng_title);
        textKorTitle = (TextView) findViewById(R.id.text_kor_title);
        textToxinDetail = (TextView) findViewById(R.id.textview_toxin_detail);
    }

    /**
     * 데이터를 읽어온다.
     * @param idx
     */
    private void loadData(int idx) {
        Cursor dataCursor = null;

        dataCursor = toxinDBAdapter.fetchToxins(idx);
        if (dataCursor != null) {
            if (dataCursor.moveToFirst()) {
                do {
                    int rowid = dataCursor.getInt(dataCursor.getColumnIndex("rowid"));
                    String engData = dataCursor.getString(dataCursor.getColumnIndex("eng"));
                    String korData = dataCursor.getString(dataCursor.getColumnIndex("kor"));
                    String keywordData = dataCursor.getString(dataCursor.getColumnIndex("keyword"));
                    String contentsData = dataCursor.getString(dataCursor.getColumnIndex("contents"));

                    Log.d(TAG, rowid + ">" + engData + ":" + korData + ":" + keywordData + ":" + contentsData);

                    textEngTitle.setText(engData);
                    textKorTitle.setText(korData);
                    textToxinDetail.setText(contentsData);
                    // textToxinDetail.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));

                } while (dataCursor.moveToNext());
            }
            dataCursor.close();
        }
    }
}