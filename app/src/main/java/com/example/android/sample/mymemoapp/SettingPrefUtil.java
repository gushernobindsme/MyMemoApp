package com.example.android.sample.mymemoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 設定情報を保存するためのユーティリティ。
 */
public class SettingPrefUtil {

    /**
     * 保存先ファイル名。
     */
    public static final String PREF_FILE_NAME = "settings";

    /**
     * ファイルメイプレフィックスのキー。
     */
    private static final String KEY_FILE_NAME_PREFIX = "file.name.prefix";

    /**
     * 未設定時のデフォルト値。
     */
    private static final String KEY_FILE_NAME_PREFIX_DEFAULT = "memo";

    /**
     * 文字サイズのキー。
     */
    private static final String KEY_TEXT_SIZE = "text.size";

    /**
     * Lサイズ。
     */
    public static final String TEXT_SIZE_LARGE = "large";

    /**
     * Mサイズ。
     */
    public static final String TEXT_SIZE_MEDIUM = "medium";

    /**
     * Sサイズ。
     */
    public static final String TEXT_SIZE_SAMLL = "small";

    /**
     * 文字スタイルのキー。
     */
    private static final String KEY_TEXT_STYLE = "text.style";

    /**
     * 太字。
     */
    public static final String TEXT_STYLE_BOLD = "blod";

    /**
     * 斜め。
     */
    public static final String TEXT_STYLE_ITALIC = "italic";

    /**
     * 画面の明暗を反転するか否かを保存するためのキー。
     */
    private static final String KEY_SCREEN_REVERSE = "screen.reverse";

    /**
     * ファイル名プレフィックスの値を取得する。
     * @param context
     * @return
     */
    public static String getFileNamePrefix(Context context) {
        // SharedPreferencesから設定値を取得
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sp.getString(KEY_FILE_NAME_PREFIX,KEY_FILE_NAME_PREFIX_DEFAULT);
    }

    /**
     * フォントサイズを取得する。
     * @param context
     * @return
     */
    public static float getFontSize(Context context){
        // SharedPreferencesから設定値を取得
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        String storedSize = sp.getString(KEY_TEXT_SIZE,TEXT_SIZE_MEDIUM);

        // 設定値に応じて、実際のテキストサイズを返す
        switch (storedSize){
            case TEXT_SIZE_LARGE:
                return context.getResources().getDimension(R.dimen.settings_text_size_large);
            case TEXT_SIZE_MEDIUM:
                return context.getResources().getDimension(R.dimen.settings_text_size_medium);
            case TEXT_SIZE_SAMLL:
                return context.getResources().getDimension(R.dimen.settings_text_size_small);
            default:
                return context.getResources().getDimension(R.dimen.settings_text_size_default);
        }
    }

    /**
     * 文字装飾の設定を取得する
     * @param context
     * @return
     */
    public static int getTypeface(Context context){
        // SharedPreferencesから設定値を取得
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        Set<String> storedTypeface = sp.getStringSet(KEY_TEXT_STYLE, Collections.<String>emptySet());

        // EditTextに設定するビットフラグに変換する
        int typefaceBit = Typeface.NORMAL;
        for(String value : storedTypeface){
            switch (value){
                case TEXT_STYLE_ITALIC:
                    typefaceBit |= Typeface.ITALIC;
                    break;
                case TEXT_STYLE_BOLD:
                    typefaceBit |= Typeface.BOLD;
                    break;
            }
        }

        return typefaceBit;
    }

    /**
     * 画面の明暗の有無を取得する。
     * @param context
     * @return
     */
    public static boolean isScreenReverse(Context context){
        // SharedPreferencesから設定値を取得
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_SCREEN_REVERSE,false);
    }

}
