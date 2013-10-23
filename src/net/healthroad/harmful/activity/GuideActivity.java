package net.healthroad.harmful.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import net.healthroad.harmful.R;

/**
 * Created with IntelliJ IDEA.
 * User: kimhyoseok
 * Date: 13. 10. 23.
 * Time: 오전 11:05
 * To change this template use File | Settings | File Templates.
 */
public class GuideActivity extends Activity {

    public static String TAG = "Harmful";

    /** 내용을 표현할 웹 뷰 */
    private static WebView webviewDetail;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_harmful_detail);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();
    }

    /**
     * 화면 컴포넌트 초기화를 수행한다.
     */
    private void initScreenComponents() {
        webviewDetail = (WebView) findViewById(R.id.webview_detail);
        webviewDetail.getSettings().setJavaScriptEnabled(true);

        String url = "file:///android_asset/ghs.html";
        webviewDetail.loadUrl(url);
    }

}