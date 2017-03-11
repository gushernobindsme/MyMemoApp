package com.example.android.sample.mymemoapp;

import android.app.ListFragment;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * メモの一覧を表示するためのフラグメント。
 */
public class MemoLoadFragment extends ListFragment {
    /**
     * ユーザが一覧をタップしたイベントのコールバック
     */
    public interface MemoLoadFragmentListener{
        void onMemoSelected(@Nullable Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(!(context instanceof MemoLoadFragmentListener)){
            throw new RuntimeException(context.getClass().getSimpleName() + "does not implement MemoLoadFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ヘッダを追加する
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.memo_list_create,null);
        getListView().addHeaderView(header);

        // データベースを検索する
        Cursor cursor = MemoRepository.query(getActivity());

        // アダプタをセットする
        MemoAdapter adapter = new MemoAdapter(getActivity(),cursor,true);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(position == 0){
            // ヘッダの場合
            ((MemoLoadFragmentListener)getActivity()).onMemoSelected(null);
        } else{
            // リストの項目の場合
            Uri selectedItem = ContentUris.withAppendedId(MemoProvider.CONTENT_URI,id);
            ((MemoLoadFragmentListener)getActivity()).onMemoSelected(selectedItem);
        }
    }
}
