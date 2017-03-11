package com.example.android.sample.mymemoapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by eratakumi on 2017/03/11.
 */
public class SettingActivity extends AppCompatActivity implements SettingFragment.SettingFragmentListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // アクションバーに戻るボタンを設定する
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        // Resultはデフォルトでキャンセルを設定
        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            // 戻るボタンを押されたら、画面を閉じる
            this.finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSettingChanged() {
        // 設定が変更されたら、ResultにOKを設定
        setResult(RESULT_OK);
    }

}
