package net.healthroad.harmful.activity;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.*;

public class UIApplication extends Application {

    private static final String TAG = "Harmful";
    private static final String DATABASE_NAME = "harmful.db";

    @Override
    public void onCreate() {
        super.onCreate();

        // assets의 sqlite 파일을 패키지 안으로 복사
        Log.d(TAG, "harmful.db 복사시작>>>>>");
        copyDatabase2Local();
        Log.d(TAG, "harmful.db 복사완료<<<<<");
    }

    /**
     * assets의 sql 파일을 패키지 안으로 복사한다.
     *
     * @throws IOException
     */
    private void copyDatabase2Local() {
        String outFileFullPath = "/data/data/" + getPackageName() + "/databases/";
        String copyCheckFullPath = outFileFullPath + DATABASE_NAME;

        try {
            InputStream myInput = getApplicationContext().getAssets().open(DATABASE_NAME);

            String outFileName = outFileFullPath + DATABASE_NAME;
            File file = new File(outFileFullPath);
            if(!file.exists()) {
                file.mkdirs();
            } else {
                File copyFile = new File(copyCheckFullPath);
                if(copyFile.exists()) {
                    Log.d(TAG, "이미 DB파일이 설치되어 있음");
                    return;
                }
            }

            Log.d(TAG, "DB파일 복사 수행");
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }
}