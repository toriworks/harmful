package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.healthroad.harmful.R;
import net.healthroad.harmful.util.ToxinDBAdapter;

public class SplashActivity extends Activity {

    public static String TAG = "Harmful";

    /** 담배 유해 정보 버튼 */
    private static Button buttonHarmful;
    /** 담배 독성 물질 버튼 */
    private static Button buttonToxin;

    /** 백버튼 누름 감지용 체크 값 */
    private static boolean bWillClosed = false;
    /** 백버튼 누름 감지용 핸들러 */
    private static Handler willCloseHandler = new Handler() {
        /*
         * (non-Javadoc)
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            bWillClosed = false;
        }
    };
    /** 메세지 처리 핸들러 */
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();

        // 뒤로 버튼에 대한 처리 이벤트 핸들러를 등록
        this.handleBackButtonPressed();
    }

    /**
     * 화면 컴포넌트 초기화를 수행한다.
     */
    private void initScreenComponents() {
        // 전체 화면으로 보이기 위해서 액션바를 숨김
        final ActionBar bar = getActionBar();
        bar.hide();

        buttonHarmful = (Button) findViewById(R.id.button_harmful);
        buttonToxin = (Button) findViewById(R.id.button_toxin);

        // 클릭 이벤트 처리
        buttonHarmful.setOnClickListener(new ButtonClickListener());
        buttonToxin.setOnClickListener(new ButtonClickListener());
    }

    /*
 * (non-Javadoc)
 *
 * @see android.support.v4.app.FragmentActivity#onBackPressed()
 */
    @Override
    public void onBackPressed() {
        if (!bWillClosed) {
            // Toast message 출력
            mHandler.sendEmptyMessage(0);

            bWillClosed = true;
            willCloseHandler.sendEmptyMessageDelayed(
                    1000, // 핸들러 아이디
                    3000); // 메세지 전달 시간적 여유
        } else {
            super.onBackPressed();
            //
            // 일정시간 내에 두번 뒤로 누르기를 했기 때문에 종료 시도
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 뒤로 버튼을 연속으로 누를 경우에 이벤트를 처리한다.
     */
    private void handleBackButtonPressed() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int what = msg.what;
                switch (what) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 클릭 이벤트 처리 클래스
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int vId = view.getId();
            switch (vId) {
                case R.id.button_harmful:
                    Log.d(TAG, "button_harmful clicked...");
                    Intent hIntent = new Intent();
                    hIntent.setClass(getApplicationContext(), HarmfulMainActivity.class);
                    startActivity(hIntent);
                    overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.button_toxin:
                    Log.d(TAG, "button_toxin clicked...");

                    ToxinDBAdapter dbAdapter = new ToxinDBAdapter(getApplicationContext());
                    dbAdapter = dbAdapter.open();
                    Cursor allCursor = dbAdapter.fetchAllToxins();

                    break;
                default:
                    break;
            }
        }
    }
}
