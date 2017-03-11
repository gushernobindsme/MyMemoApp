package com.example.android.sample.mymemoapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * メモを入力する画面のフラグメント。
 */
public class MemoFragment extends Fragment {

    /**
     * テキスト入力エリア。
     */
    private MemoEditText mMemoEditText;

    /**
     * メモ帳データのURI。
     */
    private Uri mMemoUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // レイアウトXMLからViewを生成
        View view = inflater.inflate(R.layout.fragment_memo,container,false);
        mMemoEditText = (MemoEditText) view.findViewById(R.id.Memo);

        return view;
    }

    /**
     * 設定を反映する。
     */
    public void reflectSettings(){
        Context context = getActivity();

        if(context != null){
            // SharedPreferencesから値を取得して、設定を反映する
            setFontSize(SettingPrefUtil.getFontSize(context));
            setTypeface(SettingPrefUtil.getTypeface(context));
            setMemoColor(SettingPrefUtil.isScreenReverse(context));
        }
    }

    /**
     * 文字サイズの設定を反映する。
     * @param fontSizePx
     */
    private void setFontSize(float fontSizePx){
        mMemoEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX,fontSizePx);
    }

    /**
     * 文字装飾の設定を反映する。
     * @param typeface
     */
    private void setTypeface(int typeface){
        mMemoEditText.setTypeface(Typeface.DEFAULT,typeface);
    }

    /**
     * 色の反転の設定を反映する。
     * @param reverse
     */
    private void setMemoColor(boolean reverse){
        int backgroundColor = reverse ? Color.BLACK : Color.WHITE;
        int textColor = reverse ? Color.WHITE : Color.BLACK;

        mMemoEditText.setBackgroundColor(backgroundColor);
        mMemoEditText.setTextColor(textColor);
    }

    /**
     * 保存する。
     */
    public void save(){
        if(mMemoUri != null){
            // Memono
            // URIがある(すでに一度保存したか、読み込んできたファイル)場合、更新する
            MemoRepository.update(getActivity(),
                    mMemoUri,
                    mMemoEditText.getText().toString());
        }else{
            // 新規作成
            MemoRepository.create(getActivity(),
                    mMemoEditText.getText().toString());
        }
        // 「保存しました」と表示する
        Toast.makeText(getActivity(),"保存しました",Toast.LENGTH_SHORT).show();

    }

    /**
     * 読み込む。
     * @param uri
     */
    public void load(Uri uri){
        mMemoUri = uri;

        if(uri != null){
            // メモを読み込む
            String memo = MemoRepository.findMemoByUri(getActivity(),uri);

            //EditTextに反映する
            mMemoEditText.setText(memo);
        }else{
            // URIがnullの場合には、メモをクリアするだけ
            mMemoEditText.setText(null);
        }
    }

}
