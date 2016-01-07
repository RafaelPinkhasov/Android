package org.evreyatlanta.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class AnimationControl extends ImageView {
	private int source;
	private AnimationDrawable animation;
	
	public AnimationControl(Context context) {
		super(context);		
	}

	public AnimationControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public AnimationControl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	private void init(AttributeSet attrs) {
		source = attrs.getAttributeResourceValue(null, "source", 0);
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (RuntimeException ex) { }
	}
		
	public void startAnimation() {
		this.setBackgroundResource(source);
		animation = (AnimationDrawable) this.getBackground();
		this.post(new Runnable(){
	        @Override
	        public void run() {
	            animation.start();
	        }
        });
	}
	
	public void stopAnimation() {
		if (animation.isRunning())
			animation.stop();
	}
	
	@Override
	public void setVisibility(int visibility) {
		switch (visibility) {
			case View.VISIBLE:
				startAnimation();
				break;
			case View.GONE:
			case View.INVISIBLE:
				stopAnimation();
				break;
		}
		super.setVisibility(visibility);
	}
}
