package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.healthroad.harmful.R;

public class HarmfulMainActivity extends Activity {

    public static String TAG = "Harmful";
    public static String BUNDLE_KEY = "Info";

    /** 담배 유해성 정보 버튼 */
    private static Button buttonHarmfulInfo;
    /** 담배 유해 성분 버튼 */
    private static Button buttonHarmfulComponent;
    /** 담배가 인체에 끼지는 영향 */
    private static Button buttonHarmfulEffect;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_harmful);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();

        // 액션바 초기화
        this.initActionBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
    }

    /**
     * 화면 컴포넌트 초기화를 수행한다.
     */
    private void initScreenComponents() {
        buttonHarmfulInfo = (Button) findViewById(R.id.button_harmful_info);
        buttonHarmfulComponent = (Button) findViewById(R.id.button_harmful_component);
        buttonHarmfulEffect = (Button) findViewById(R.id.button_harmful_effect);

        // 버튼에 이벤트 부여
        buttonHarmfulInfo.setOnClickListener(new ButtonClickListener());
        buttonHarmfulComponent.setOnClickListener(new ButtonClickListener());
        buttonHarmfulEffect.setOnClickListener(new ButtonClickListener());
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
            // 번들에 값을 채움
            Bundle bundle = new Bundle();

            switch (vId) {
                case R.id.button_harmful_info:
                    Log.d(TAG, "button_harmful_info clicked...");
                    Intent hIntent = new Intent();
                    hIntent.setClass(getApplicationContext(), HarmfulDetailActivity.class);

                    // 번들에 값을 채움
                    bundle.putChar(BUNDLE_KEY, 'A');
                    hIntent.putExtras(bundle);
                    startActivity(hIntent);
                    overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.button_harmful_component:
                    Log.d(TAG, "button_harmful_component clicked...");
                    Intent cIntent = new Intent();
                    cIntent.setClass(getApplicationContext(), HarmfulDetailActivity.class);

                    // 번들에 값을 채움
                    bundle.putChar(BUNDLE_KEY, 'B');
                    cIntent.putExtras(bundle);
                    startActivity(cIntent);
                    overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.button_harmful_effect:
                    Log.d(TAG, "button_harmful_effect clicked...");
                    Intent eIntent = new Intent();
                    eIntent.setClass(getApplicationContext(), HarmfulDetailActivity.class);

                    // 번들에 값을 채움
                    bundle.putChar(BUNDLE_KEY, 'C');
                    eIntent.putExtras(bundle);
                    startActivity(eIntent);
                    overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                default:
                    break;
            }
        }
    }
}