package com.example.android.sample.mymemoapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * メモ帳アプリのメインアクティビティ。
 */
public class MainActivity extends AppCompatActivity implements MemoLoadFragment.MemoLoadFragmentListener,SettingFragment.SettingFragmentListener {

    /**
     * 2ペインかどうか。
     */
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2ペインかどうかは、マスタがあるかどうかで判定する
        isDualPane = (findViewById(R.id.FragmentContainer) != null);
        if(isDualPane){
            // 2ペインの場合には、メモの一覧フラグメントをマスタに追加する
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FragmentContainer,new MemoLoadFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューを生成する
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                // 設定アクション
                if (isDualPane){
                    // 2ペインの場合
                    FragmentManager manager = getFragmentManager();

                    if(manager.findFragmentById(R.id.FragmentContainer) instanceof SettingFragment){
                        // すでに設定フラグメントがある場合にはメモの一覧をマスタに追加する
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FragmentContainer,new MemoFragment())
                                .commit();
                    }else{
                        // 設定フラグメントがない場合には設定フラグメントをマスタに追加する
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FragmentContainer,new SettingFragment())
                                .commit();
                    }
                }else{
                    // 2ペイン出ない場合には、設定画面を起動する
                    Intent intent = new Intent(this,SettingActivity.class);
                    startActivity(intent);
                }
                return true;

            case R.id.action_save:
                // 保存アクション
                MemoFragment memoFragment = (MemoFragment) getFragmentManager().findFragmentById(R.id.MemoFragment);
                memoFragment.save();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMemoSelected(@Nullable Uri uri) {
        if(isDualPane){
            //　2ペインの場合には、メモを更新する
            MemoFragment memoFragment = (MemoFragment) getFragmentManager().findFragmentById(R.id.MemoFragment);
            memoFragment.load(uri);
        }else{
            // 2ペインでない場合には、メモ画面を起動する
            Intent intent = new Intent(this,MemoActivity.class);
            intent.putExtra(MemoActivity.BUNDLE_KEY_URI,uri);
            startActivity(intent);
        }
    }

    @Override
    public void onSettingChanged() {
        // 設定が変化した場合
        // この画面で設定が変化するのは、2ペインの場合のみ
        MemoFragment memoFragment = (MemoFragment) getFragmentManager().findFragmentById(R.id.MemoFragment);
        memoFragment.reflectSettings();
    }
}
