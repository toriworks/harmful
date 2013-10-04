package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import net.healthroad.harmful.R;
import net.healthroad.harmful.util.*;

import java.util.ArrayList;
import java.util.List;

public class ToxinMainActivity extends Activity implements IDataTransfer {

    public static String TAG = "Harmful";

    /**
     * 검색 결과가 없는 경우 레이아웃
     */
    private static LinearLayout relativeNoResult;
    /**
     * 데이터 전체보기 버튼
     */
    private static Button buttonAllData;
    /**
     * 리스트 뷰
     */
    private static ListView listView;
    /**
     * 뷰에 출력할 데이터
     */
    private List<Toxin> listToxinData = new ArrayList<Toxin>();
    /**
     * 데이터 어뎁터
     */
    private AdapterToxinData adapterToxinData;
    /**
     * SQLite 어뎁터
     */
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
        relativeNoResult = (LinearLayout) findViewById(R.id.relative_no_result);
        buttonAllData = (Button) findViewById(R.id.button_all_data);
        buttonAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData("", "");
            }
        });

        listView = (ListView) findViewById(R.id.list_search);
        adapterToxinData = new AdapterToxinData(getApplicationContext(), R.layout.list_search_data,
                listToxinData, this);

        listView.setAdapter(adapterToxinData);
    }

    /**
     * 데이터를 읽어온다.
     *
     * @param strSearch 검색어
     * @param strType   검색타입
     */
    private void loadData(String strSearch, String strType) {
        int length = strSearch.length();
        Cursor allDataCursor = null;
        if (length > 0) {
            Log.d(TAG, "검색어로 검색하기");
            if (strType.equals(ICommonCodes.SEARCH_TYPE_PHASE)) {
                allDataCursor = toxinDBAdapter.fetchToxins(strSearch, strType);
            } else {
                allDataCursor = toxinDBAdapter.fetchToxins(strSearch, strType);
            }
        } else if (length == 0 && strType.equals("")) {
            Log.d(TAG, "전체 데이터 출력하기");
            allDataCursor = toxinDBAdapter.fetchAllToxins();
        }

        if (null != allDataCursor) {
            Log.d(TAG, "전체 데이터 수:" + allDataCursor.getCount());
            if (allDataCursor.getCount() == 0) {
                relativeNoResult.setVisibility(View.VISIBLE);
            } else {
                relativeNoResult.setVisibility(View.GONE);
            }

            // 전에 검색목록을 삭제
            listToxinData.clear();

            // 검색된 데이터를 리스트뷰에 담기
            showSearchData(allDataCursor);

            adapterToxinData.notifyDataSetChanged();
        }
    }

    /**
     * 검색된 데이터를 리스트뷰에 담아둔다.
     *
     * @param allDataCursor
     */
    private void showSearchData(Cursor allDataCursor) {
        if (allDataCursor != null) {
            if (allDataCursor.moveToFirst()) {
                do {
                    int rowid = allDataCursor.getInt(allDataCursor.getColumnIndex("rowid"));
                    String engData = allDataCursor.getString(allDataCursor.getColumnIndex("eng"));
                    String korData = allDataCursor.getString(allDataCursor.getColumnIndex("kor"));
                    String keywordData = allDataCursor.getString(allDataCursor.getColumnIndex("keyword"));
                    String contentsData = allDataCursor.getString(allDataCursor.getColumnIndex("contents"));

                    Log.d(TAG, rowid + ">" + engData + ":" + korData + ":" + keywordData + ":" + contentsData);

                    Toxin toxin = new Toxin(rowid, korData, engData, keywordData, contentsData);
                    listToxinData.add(toxin);

                } while (allDataCursor.moveToNext());
            }
            allDataCursor.close();
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
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "requestCode:" + requestCode + ", resultCode:" + resultCode + ", OK:" + Activity.RESULT_OK);

        if (Activity.RESULT_OK == resultCode) {
            // 검색창에서 값이 도착했다면 처리
            if (ICommonCodes.SEARCH_REQ_CODE == requestCode) {
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

    @Override
    public void dataTransfer(int idx) {
        Log.d(TAG, "전달받은 index값:" + idx);

        Intent dIntent = new Intent();
        dIntent.setClass(getApplicationContext(), ToxinDetailActivity.class);
        dIntent.putExtra(ICommonCodes.TOXIN_DETAIL_BUNDLE_KEY, idx);

        startActivity(dIntent);
    }
}