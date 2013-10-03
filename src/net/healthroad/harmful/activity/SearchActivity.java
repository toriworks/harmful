package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import net.healthroad.harmful.R;
import net.healthroad.harmful.util.ICommonCodes;

public class SearchActivity extends Activity {

    public static String TAG = "Harmful";

    /**
     * 검색어 입력 창
     */
    private static EditText editSearch;
    /**
     * 검색 실행 버튼
     */
    private static Button buttonSearch;
    /**
     * 한글 버튼
     */
    private static Button[] buttonKors = new Button[14];
    private int[] rscKors = {
            R.id.button_kor_1, R.id.button_kor_2, R.id.button_kor_3, R.id.button_kor_4, R.id.button_kor_5,
            R.id.button_kor_6, R.id.button_kor_7, R.id.button_kor_8, R.id.button_kor_9, R.id.button_kor_10,
            R.id.button_kor_11, R.id.button_kor_12, R.id.button_kor_13, R.id.button_kor_14
    };

    /**
     * 영문 버튼
     */
    private static Button[] buttonsEngs = new Button[26];
    private int[] rscEngs = {
            R.id.button_eng_1, R.id.button_eng_2, R.id.button_eng_3, R.id.button_eng_4, R.id.button_eng_5,
            R.id.button_eng_6, R.id.button_eng_7, R.id.button_eng_8, R.id.button_eng_9, R.id.button_eng_10,
            R.id.button_eng_11, R.id.button_eng_12, R.id.button_eng_13, R.id.button_eng_14, R.id.button_eng_15,
            R.id.button_eng_16, R.id.button_eng_17, R.id.button_eng_18, R.id.button_eng_19, R.id.button_eng_20,
            R.id.button_eng_21, R.id.button_eng_22, R.id.button_eng_23, R.id.button_eng_24, R.id.button_eng_25,
            R.id.button_eng_26
    };

    /**
     * 숫자 버튼
     */
    private static Button[] buttonsNums = new Button[10];
    private int[] rscNums = {
            R.id.button_num_1, R.id.button_num_2, R.id.button_num_3, R.id.button_num_4, R.id.button_num_5,
            R.id.button_num_6, R.id.button_num_7, R.id.button_num_8, R.id.button_num_9, R.id.button_num_0
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();
    }

    /**
     * 화면 컴포넌트 초기화를 수행한다.
     */
    private void initScreenComponents() {
        editSearch = (EditText) findViewById(R.id.edit_search);
        buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new ButtonClickListener());

        for (int i = 0; i < 14; i++) {
            buttonKors[i] = (Button) findViewById(rscKors[i]);
            buttonKors[i].setOnClickListener(new ButtonClickListener());
        }

        for (int j = 0; j < 26; j++) {
            buttonsEngs[j] = (Button) findViewById(rscEngs[j]);
            buttonsEngs[j].setOnClickListener(new ButtonClickListener());
        }

        for (int k = 0; k < 10; k++) {
            buttonsNums[k] = (Button) findViewById(rscNums[k]);
            buttonsNums[k].setOnClickListener(new ButtonClickListener());
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

    /**
     * 클릭 이벤트 처리 클래스
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int vId = view.getId();
            String strSearch = "";
            String searchType = "";

            if (vId == R.id.button_search) {
                // 검색 버튼이 눌린 경우
                strSearch = editSearch.getText().toString();
                if (strSearch.length() > 0) {
                    // 검색어로 가공
                    strSearch = strSearch.trim();
                    searchType = ICommonCodes.SEARCH_TYPE_PHASE;
                } else {
                    Toast toast = new Toast(getApplicationContext());
                    toast.makeText(getApplicationContext(), "검색어 입력란에 검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                // 항목 검색 버튼이 눌린 경우
                Button dummyButton = (Button) view;
                strSearch = "" + dummyButton.getText();
                searchType = ICommonCodes.SEARCH_TYPE_BUTTON;
            }

            // 호출한 쪽 인텐트 얻어서 검색어 값 전달
            Intent cIntent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putString(ICommonCodes.SEARCH_BUNDLE_KEY, strSearch);
            bundle.putString(ICommonCodes.SEARCH_BUNDLE_TYPE_KEY, searchType);
            cIntent.putExtras(bundle);
            setResult(RESULT_OK, cIntent);
            finish();
        }
    }
}