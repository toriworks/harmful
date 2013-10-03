package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.healthroad.harmful.R;
import net.healthroad.harmful.util.AdapterToxinData;
import net.healthroad.harmful.util.ICommonCodes;
import net.healthroad.harmful.util.Toxin;
import net.healthroad.harmful.util.ToxinDBAdapter;

import java.util.ArrayList;
import java.util.List;

public class ToxinMainActivity extends Activity {

    public static String TAG = "Harmful";

    /** 검색 결과가 없는 경우 레이아웃 */
    private static RelativeLayout relativeNoResult;
    /** 리스트 뷰 */
    private static ListView listView;
    /** 뷰에 출력할 데이터 */
    private List<Toxin> listToxinData = new ArrayList<Toxin>();
    /** 데이터 어뎁터 */
    private AdapterToxinData adapterToxinData;
    /** SQLite 어뎁터 */
    private ToxinDBAdapter toxinDBAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toxin);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();

        // 액션바 초기화
        this.initActionBar();

        toxinDBAdapter = new ToxinDBAdapter(getApplicationContext());
        toxinDBAdapter.open();

        // 기본 데이터 출력
        this.loadData("", "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
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
        // 검색 결과 없음 표시용
        relativeNoResult = (RelativeLayout) findViewById(R.id.relative_no_result);

        listView = (ListView) findViewById(R.id.list_search);
        adapterToxinData = new AdapterToxinData(getApplicationContext(), R.layout.list_search_data,
                listToxinData);

        listView.setAdapter(adapterToxinData);
    }

    /**
     * 데이터를 읽어온다.
     * @param strSearch 검색어
     * @param strType 검색타입
     */
    private void loadData(String strSearch, String strType) {
        int length = strSearch.length();
        if(length > 0) {
            Log.d(TAG, "검색어로 검색하기");
            Cursor allDataCursor = toxinDBAdapter.fetchToxins(strSearch);
            Log.d(TAG, "전체 데이터 수:" + allDataCursor.getCount());

            listToxinData.clear();
            if (allDataCursor != null ) {
                if  (allDataCursor.moveToFirst()) {
                    do {
                        int rowid = allDataCursor.getInt(allDataCursor.getColumnIndex("rowid"));
                        String engData = allDataCursor.getString(allDataCursor.getColumnIndex("eng"));
                        String korData = allDataCursor.getString(allDataCursor.getColumnIndex("kor"));
                        String keywordData = allDataCursor.getString(allDataCursor.getColumnIndex("keyword"));
                        String contentsData = allDataCursor.getString(allDataCursor.getColumnIndex("contents"));

                        Log.d(TAG, rowid + ">" + engData + ":" + korData + ":" + keywordData + ":" + contentsData);

                        Toxin toxin = new Toxin(rowid, korData, engData, keywordData, contentsData);
                        listToxinData.add(toxin);

                    }while (allDataCursor.moveToNext());
                }
                allDataCursor.close();
            }

            adapterToxinData.notifyDataSetChanged();
        } else {
            Log.d(TAG, "전체 데이터 출력하기");
            Cursor allDataCursor = toxinDBAdapter.fetchAllToxins();
            Log.d(TAG, "전체 데이터 수:" + allDataCursor.getCount());

            listToxinData.clear();
            if (allDataCursor != null ) {
                if  (allDataCursor.moveToFirst()) {
                    do {
                        int rowid = allDataCursor.getInt(allDataCursor.getColumnIndex("rowid"));
                        String engData = allDataCursor.getString(allDataCursor.getColumnIndex("eng"));
                        String korData = allDataCursor.getString(allDataCursor.getColumnIndex("kor"));
                        String keywordData = allDataCursor.getString(allDataCursor.getColumnIndex("keyword"));
                        String contentsData = allDataCursor.getString(allDataCursor.getColumnIndex("contents"));

                        Log.d(TAG, rowid + ">" + engData + ":" + korData + ":" + keywordData + ":" + contentsData);

                        Toxin toxin = new Toxin(rowid, korData, engData, keywordData, contentsData);
                        listToxinData.add(toxin);

                    }while (allDataCursor.moveToNext());
                }
                allDataCursor.close();
            }

            adapterToxinData.notifyDataSetChanged();
        }
    }

    /**
     * 액션바를 초기화 설정한다.
     */
    private void initActionBar() {
        // 액션바 생성 후, 텍스트 타이틀 제거
        ActionBar bar = getActionBar();

        // 커스터마이징 액션바 사용
        bar.setCustomView(R.layout.g_actionbar_s);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView tv = (TextView) bar.getCustomView().findViewById(R.id.text_g_title);
        tv.setText("담배 독성 물질");

        // 메인으로 이동 버튼
        Button buttonHome = (Button) bar.getCustomView().findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int vId = view.getId();
                switch (vId) {
                    case R.id.button_home:
                        Intent hIntent = new Intent();
                        hIntent.setClass(getApplicationContext(), SplashActivity.class);
                        startActivity(hIntent);

                        finish();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
                        break;
                    default:
                        break;
                }
            }
        });

        // 검색 버튼
        Button buttonSearch = (Button) bar.getCustomView().findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int vId = view.getId();
                switch (vId) {
                    case R.id.button_search:
                        Intent hIntent = new Intent();
                        hIntent.setClass(getApplicationContext(), SearchActivity.class);
                        startActivityForResult(hIntent, ICommonCodes.SEARCH_REQ_CODE);
                        break;
                    default:
                        break;
                }
            }
        });

        // 모드 설정
        bar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 검색어 창에서 검색어 값을 얻는다.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "requestCode:" + requestCode + ", resultCode:" + resultCode + ", OK:" + Activity.RESULT_OK);

        if(Activity.RESULT_OK == resultCode) {
            // 검색창에서 값이 도착했다면 처리
            if(ICommonCodes.SEARCH_REQ_CODE == requestCode) {
                String strSearch = data.getStringExtra(ICommonCodes.SEARCH_BUNDLE_KEY);
                String strType = data.getStringExtra(ICommonCodes.SEARCH_BUNDLE_TYPE_KEY);
                Log.d(TAG, "검색어:" + strSearch + ", 유형:" + strType);

                // 데이터를 검색 수행
                loadData(strSearch, strType);
            }
        }
    }

    /**
     * 옵션 메뉴가 선택되면 이벤트가 호출된다.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mId = item.getItemId();
        Log.d(TAG, "선택된 메뉴 아이디:" + mId + "<END");
        switch (mId) {
            case android.R.id.home:
                Log.d("nine", "홈 버튼이 눌림");
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}