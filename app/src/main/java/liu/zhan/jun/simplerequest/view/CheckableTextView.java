package liu.zhan.jun.simplerequest.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CheckableTextView extends AppCompatTextView implements Checkable {


	private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };
    
	public CheckableTextView(Context context) {
        super(context);
    }


    public CheckableTextView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }


	@Override
	public boolean isChecked() {
		return mChecked;
	}


	@Override
	public void setChecked(boolean arg0) {
		mChecked = arg0;
		refreshDrawableState();
	}


	@Override
	public void toggle() {
		mChecked = !mChecked;
	}
}