package com.example.android.sample.mymemoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * メモを入力する画面のアクティビティクラス。
 */
public class MemoActivity extends AppCompatActivity {

    /**
     * URIを取得する際のキー。
     */
    public static final String BUNDLE_KEY_URI = "uri";

    /**
     * リクエスト。
     */
    private static final int REQUEST_SETTING = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        // 指定されたメモのURIを取得する
        Uri uri = getIntent().getParcelableExtra(BUNDLE_KEY_URI);

        // 指定されたメモを読み込む
        MemoFragment memoFragment = (MemoFragment) getFragmentManager().findFragmentById(R.id.MemoFragment);
        memoFragment.load(uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューを生成する
        getMenuInflater().inflate(R.menu.menu_memo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // メニューアイテムのIDを取得
        int id = item.getItemId();

        switch (id) {
            // 「設定」アクション
            case R.id.action_settings:
                // 設定アクティビティへ遷移する
                Intent intent = new Intent(this,SettingActivity.class);
                startActivityForResult(intent,REQUEST_SETTING);
                return true;

            // 「保存」アクション
            case R.id.action_save:
                // 保存する
                MemoFragment memoFragment = (MemoFragment) getFragmentManager().findFragmentById(R.id.MemoFragment);
                memoFragment.save();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SETTING && resultCode == RESULT_OK){
            // 設定の変更を反映させる
            MemoFragment memoFragment = (MemoFragment) getFragmentManager().findFragmentById(R.id.MemoFragment);
            memoFragment.reflectSettings();
        }

    }
}
