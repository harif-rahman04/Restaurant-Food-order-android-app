package com.opus.restaurant.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class QuickSandRegularEditText extends EditText {

	public QuickSandRegularEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public QuickSandRegularEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public QuickSandRegularEditText(Context context) {
		super(context);
		init();
	}

	public void init() {

	setTypeface (Typeface.createFromAsset(getContext().getAssets(),
                "fonts/"+ Fonts.QuickSandRegular));

	}

}