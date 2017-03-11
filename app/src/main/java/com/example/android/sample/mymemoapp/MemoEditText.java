package com.example.android.sample.mymemoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * メモ帳風のEditTextを表示するためのUIコンポーネント。
 */
@SuppressLint("AppCompatCustomView")
public class MemoEditText extends EditText{

    private static final int SOLID = 1;
    private static final int DASH = 2;
    private static final int NORMAL = 4;
    private static final int BOLD = 8;

    private int mMeasuredWidth;
    private int mLineHeight;
    private int mDisplayLineCount;

    private Path mPath;
    private Paint mPaint;

    /**
     * コンストラクタ。
     * @param context
     */
    public MemoEditText(Context context) {
        this(context,null);
    }

    /**
     * コンストラクタ。
     * @param context
     * @param attrs
     */
    public MemoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    /**
     * コンストラクタ。
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MemoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 横幅
        mMeasuredWidth = getMeasuredWidth();
        // 高さ
        int measuredHeight = getMeasuredHeight();
        // 1行の高さ
        mLineHeight = getLineHeight();

        // 画面内に何行表示できるか
        mDisplayLineCount = measuredHeight / mLineHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // パディング
        int paddingTop = getExtendedPaddingTop();
        // Y軸方向にスクロールされている量
        int scrollY = getScrollY();
        // 画面上に表示されている最初の行
        int firstVisibleLine = getLayout().getLineForVertical(scrollY);
        // 画面上に表示される最後の行
        int lastVisibleLine = firstVisibleLine + mDisplayLineCount;

        mPath.reset();
        for(int i= firstVisibleLine;i<=lastVisibleLine;i++){
            // 行の左端に移動
            mPath.moveTo(0,i* mLineHeight+paddingTop);
            // 右端へ線を引く
            mPath.lineTo(mMeasuredWidth,i*mLineHeight+paddingTop);
        }
        // Pathの描画
        canvas.drawPath(mPath,mPaint);

        super.onDraw(canvas);
    }

    /**
     * 初期設定を行う。
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){
        mPath = new Path();
        mPaint = new Paint();

        // 塗りつぶしなしで、輪郭線を描画するスタイル
        mPaint.setStyle(Paint.Style.STROKE);

        if(attrs != null && !isInEditMode()){
            //属性情報を取得
            int lineEffectBit;
            int lineColor;

            // 属性に設定された値を取得
            Resources resources = context.getResources();
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MemoEditText);
            try{
                lineEffectBit = typedArray.getInteger(R.styleable.MemoEditText_lineEffect,SOLID);
                lineColor = typedArray.getColor(R.styleable.MemoEditText_lineColor, Color.GRAY);
            }finally{
                typedArray.recycle();
            }

            //罫線のエフェクトを設定
            if((lineEffectBit & DASH) == DASH){
                DashPathEffect effect = new DashPathEffect(new float[]{
                        resources.getDimension(R.dimen.text_rule_interval_on),
                        resources.getDimension(R.dimen.text_rule_interval_off)},
                        0f);
                mPaint.setPathEffect(effect);
            }

            float strokeWidth;
            if((lineEffectBit & BOLD) == BOLD){
                //太線が設定されている場合
                strokeWidth = resources.getDimension(R.dimen.text_rule_width_bold);
            }else{
                strokeWidth = resources.getDimension(R.dimen.text_rule_width_normal);
            }
            mPaint.setStrokeWidth(strokeWidth);

            //色を指定
            mPaint.setColor(lineColor);
        }
    }

}