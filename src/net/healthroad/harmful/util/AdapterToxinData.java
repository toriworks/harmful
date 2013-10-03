package net.healthroad.harmful.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.healthroad.harmful.R;

import java.util.List;

public class AdapterToxinData extends ArrayAdapter<Toxin> {

    private static final String TAG = "Harmful";

    /** 컨텍스트 */
    private Context ctx;
    /** 데이터 목록 객체 */
    private List<Toxin> listData;
    /** 레이아웃 객체 */
    private int layoutId;
    /** 뷰 홀더 객체 */
    private static ToxinHolder holder;
    /** 뷰 인플레이터 */
    private LayoutInflater li = null;

    /**
     * 생성자
     * @param context 컨텍스트
     * @param textViewResourceId 뷰 레이어 아이디
     * @param listData 출력 데이터
     */
    public AdapterToxinData(Context context, int textViewResourceId, List<Toxin> listData) {
        super(context, textViewResourceId, listData);
        this.ctx = ctx;
        this.listData = listData;
        this.layoutId = textViewResourceId;

        li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 전체 데이터의 갯수를 얻는다.
     * @return 전체 데이터 수
     */
    @Override
    public int getCount() {
        int rowCount = 0;
        rowCount = (null != listData) ? listData.size() : 0;

        Log.d(TAG, "Adapter에서 얻은 getCount:" + rowCount);
        return rowCount;
    }

    /**
     * 뷰에 데이터를 매핑한다.
     * @param position 위치값
     * @param convertView 뷰
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        Log.d(TAG, "Adapter에서 getView 호출" + pos);
        if(null == convertView) {
            // 뷰가 존재하지 않으면 새로 생성
            convertView = li.inflate(this.layoutId, null);

            // 홀더 패턴 사용
            holder = new ToxinHolder();
            holder.textEng = (TextView) convertView.findViewById(R.id.text_eng_title);
            holder.textKor = (TextView) convertView.findViewById(R.id.text_kor_title);

            convertView.setTag(holder);
        } else {
            holder = (ToxinHolder) convertView.getTag();
        }

        holder.textEng.setText(listData.get(pos).getEng());
        holder.textKor.setText(listData.get(pos).getKor());

        return convertView;
    }
}
