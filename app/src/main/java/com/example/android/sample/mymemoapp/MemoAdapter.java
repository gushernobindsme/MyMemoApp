package com.example.android.sample.mymemoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * メモ帳のアダプタクラス。
 */
public class MemoAdapter extends CursorAdapter {

    /**
     * レイアウトインフレータ。
     */
    private LayoutInflater mInflater;

    /**
     * このアダプタで使用するViewHolder。
     */
    static class ViewHolder{
        TextView title;
        TextView lastModified;

        ViewHolder(View view){
            title = (TextView) view.findViewById(R.id.ContentText);
            lastModified = (TextView) view.findViewById(R.id.UpdateTimestamp);
        }
    }

    /**
     * コンストラクタ。
     *
     * @param context
     * @param c
     * @param autoRequery
     */
    public MemoAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // リストの行のViewを生成する
        View view = mInflater.inflate(R.layout.memo_list_row,null);

        // Viewうちの各項目への参照を保持するためのViewHolderを作り、設定する
        ViewHolder holder = new ViewHolder(view);

        // タグとして入れておく
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // データを得る
        String title = cursor.getString(cursor.getColumnIndex(MemoDBHelper.TITLE));
        String lastModified = cursor.getString(cursor.getColumnIndex(MemoDBHelper.DATE_MODIFIED));

        // データを設定する
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(title);
        holder.lastModified.setText(lastModified);
    }
}
