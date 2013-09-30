package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import net.healthroad.harmful.R;

import java.io.IOException;
import java.io.InputStream;

public class HarmfulDetailActivity extends Activity {

    public static String TAG = "Harmful";
    public static String BUNDLE_KEY = "Info";

    /** 내용을 표현할 웹 뷰 */
    private static WebView webviewDetail;

    // 번들에서 추출한 값을 저장
    private String types = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_harmful_detail);
        // 번들에서 값을 추출
        Bundle extras = this.getIntent().getExtras();
        types = "" + extras.getChar(BUNDLE_KEY);
        Log.d(TAG, "TYPES received : " + types);

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
        webviewDetail = (WebView) findViewById(R.id.webview_detail);
        webviewDetail.getSettings().setJavaScriptEnabled(true);

        // TODO : 유형에 따라서 데이터를 불러와서 출력
        String summaryUrl = "";
        summaryUrl = getSummaryUrl();
        webviewDetail.loadUrl(summaryUrl);
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

        // 타이틀 생성
        String title = getTitleString();
        TextView tv = (TextView) bar.getCustomView().findViewById(R.id.text_g_title);
        tv.setText(title);

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
     * 화면 제목 문자열을 얻는다.
     * @return
     */
    private String getTitleString() {
        String title = "";
        if(types.equals("A")) {
            title = getResources().getString(R.string.btn_harmful_info);
        } else if(types.equals("B")) {
            title = getResources().getString(R.string.btn_harmful_component);
        } else if(types.equals("C")) {
            title = getResources().getString(R.string.btn_harmful_effect);
        }
        return title;
    }

    /**
     * 웹뷰에 출력할 문자열을 얻는다.
     * @return
     */
    private String getSummaryUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("file:///android_asset/");

        if(types.equals("A")) {
            sb.append("a.html");
        } else if(types.equals("B")) {
            sb.append("b.html");
        } else if(types.equals("C")) {
            sb.append("c.html");
        }
        return sb.toString();
    }

    /**
     * Asset 폴더에서 내용을 읽어들인다.
     * @param inFile
     * @return
     */
    public String loadDataFromAsset(String inFile) {
        String tContents = "";

        try {
            AssetManager am = getResources().getAssets();
            InputStream stream = am.open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            tContents = "";
        }
        return tContents;
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