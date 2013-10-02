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
import android.widget.ZoomButtonsController;
import net.healthroad.harmful.R;

public class SearchActivity extends Activity {

    public static String TAG = "Harmful";

    /** 검색어 입력 창 */
    private static EditText editSearch;
    /** 검색 실행 버튼 */
    private static Button buttonSearch;
    /** 한글 버튼 */
//    private static Button buttonKor1, buttonKor2, buttonKor3, buttonKor4, buttonKor5,
//            buttonKor6, buttonKor7, buttonKor8, buttonKor9, buttonKor10,
//            buttonKor11, buttonKor12, buttonKor13, buttonKor14;
    private static Button[] buttonKors = new Button[14];
    private int[] rscKors = {
            R.id.button_kor_1, R.id.button_kor_2, R.id.button_kor_3, R.id.button_kor_4, R.id.button_kor_5,
            R.id.button_kor_6, R.id.button_kor_7, R.id.button_kor_8, R.id.button_kor_9, R.id.button_kor_10,
            R.id.button_kor_11, R.id.button_kor_12, R.id.button_kor_13, R.id.button_kor_14
    };

    /** 영문 버튼 */
//    private static Button buttonEng1, buttonEng2, buttonEng3, buttonEng4, buttonEng5,
//            buttonEng6, buttonEng7, buttonEng8, buttonEng9, buttonEng10,
//            buttonEng11, buttonEng12, buttonEng13, buttonEng14, buttonEng15,
//            buttonEng16, buttonEng17, buttonEng18, buttonEng19, buttonEng20,
//            buttonEng21, buttonEng22, buttonEng23, buttonEng24, buttonEng25, buttonEng26;
    private static Button[] buttonsEngs = new Button[26];
    private int[] rscEngs = {
            R.id.button_eng_1, R.id.button_eng_2, R.id.button_eng_3, R.id.button_eng_4, R.id.button_eng_5,
            R.id.button_eng_6, R.id.button_eng_7, R.id.button_eng_8, R.id.button_eng_9, R.id.button_eng_10,
            R.id.button_eng_11, R.id.button_eng_12, R.id.button_eng_13, R.id.button_eng_14, R.id.button_eng_15,
            R.id.button_eng_16, R.id.button_eng_17, R.id.button_eng_18, R.id.button_eng_19, R.id.button_eng_20,
            R.id.button_eng_21, R.id.button_eng_22, R.id.button_eng_23, R.id.button_eng_24, R.id.button_eng_25,
            R.id.button_eng_26
    };

    /** 숫자 버튼 */
//    private static Button buttonNum1, buttonNum2, buttonNum3, buttonNum4, buttonNum5,
//            buttonNum6, buttonNum7, buttonNum8, buttonNum9, buttonNum0;
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

        // 액션바 초기화
        this.initActionBar();
    }

    /**
     * 화면 컴포넌트 초기화를 수행한다.
     */
    private void initScreenComponents() {
        editSearch = (EditText) findViewById(R.id.edit_search);
        buttonSearch = (Button) findViewById(R.id.button_search);

        for(int i=0; i<15; i++) {
            buttonKors[i] = (Button) findViewById(rscKors[i]);
            buttonKors[i].setOnClickListener(new ButtonClickListener());
        }

        for(int j=0; j<26; j++) {
            buttonsEngs[j] = (Button) findViewById(rscEngs[j]);
            buttonsEngs[j].setOnClickListener(new ButtonClickListener());
        }

        for(int k=0; k<10; k++) {
            buttonsNums[k] = (Button) findViewById(rscNums[k]);
            buttonsNums[k].setOnClickListener(new ButtonClickListener());
        }

    }

    /**
     * 액션바를 초기화 설정한다.
     */
    private void initActionBar() {
        // 액션바 생성 후, 텍스트 타이틀 제거
        ActionBar bar = getActionBar();

        // 커스터마이징 액션바 사용
        bar.setCustomView(R.layout.g_actionbar);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView tv = (TextView) bar.getCustomView().findViewById(R.id.text_g_title);
        tv.setText("담배 유해 정보");

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

        // 모드 설정
        bar.setDisplayHomeAsUpEnabled(true);
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

            Log.d(TAG, "vId is :" + vId);
        }
    }
}