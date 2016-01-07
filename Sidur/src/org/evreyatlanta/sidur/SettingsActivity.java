package org.evreyatlanta.sidur;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class SettingsActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		
		final ImageView imgInIsrael = (ImageView)findViewById(R.id.imgInIsrael);
		imgInIsrael.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isInIsrael = settings.getBoolean("InIsrael", false);
				if (isInIsrael == false) {
					isInIsrael = true;
					imgInIsrael.setImageResource(R.drawable.on);
				} else {
					isInIsrael = false;
					imgInIsrael.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("InIsrael", isInIsrael);
				editor.commit();
				
			}
		});
		final ImageView imgWithMinyan = (ImageView)findViewById(R.id.imgWithMinyan);
		imgWithMinyan.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isWithMinyan = settings.getBoolean("WithMinyan", false);
				if (isWithMinyan == false) {
					isWithMinyan = true;
					imgWithMinyan.setImageResource(R.drawable.on);
				} else {
					isWithMinyan = false;
					imgWithMinyan.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("WithMinyan", isWithMinyan);
				editor.commit();
			}
		});
		final ImageView imgIsh = (ImageView)findViewById(R.id.imgIsh);
		imgIsh.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isIsh = settings.getBoolean("isIsh", true);
				if (isIsh == false) {
					isIsh = true;
					imgIsh.setImageResource(R.drawable.on);
				} else {
					isIsh = false;
					imgIsh.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isIsh", isIsh);
				editor.commit();				
			}
		});
		final ImageView imgZimun = (ImageView)findViewById(R.id.imgZimun);
		imgZimun.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isZimun = settings.getBoolean("isZimun", false);
				if (isZimun == false) {
					isZimun = true;
					imgZimun.setImageResource(R.drawable.on);
				} else {
					isZimun = false;
					imgZimun.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isZimun", isZimun);
				editor.commit();				
			}
		});
		
		final ImageView imgDagan = (ImageView)findViewById(R.id.imgDagan);
		imgDagan.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isDagan = settings.getBoolean("isDagan", false);
				if (isDagan == false) {
					isDagan = true;
					imgDagan.setImageResource(R.drawable.on);
				} else {
					isDagan = false;
					imgDagan.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isDagan", isDagan);
				editor.commit();				
			}
		});

		final ImageView imgPerot = (ImageView)findViewById(R.id.imgPerot);
		imgPerot.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isPerot = settings.getBoolean("isPerot", false);
				if (isPerot == false) {
					isPerot = true;
					imgPerot.setImageResource(R.drawable.on);
				} else {
					isPerot = false;
					imgPerot.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isPerot", isPerot);
				editor.commit();				
			}
		});
		
		final ImageView imgYain = (ImageView)findViewById(R.id.imgYain);
		imgYain.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean isYain = settings.getBoolean("isYain", false);
				if (isYain == false) {
					isYain = true;
					imgYain.setImageResource(R.drawable.on);
				} else {
					isYain = false;
					imgYain.setImageResource(R.drawable.off);
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isYain", isYain);
				editor.commit();				
			}
		});

		
		boolean isWithMinyan = settings.getBoolean("WithMinyan", false);
		if (isWithMinyan == false) {
			imgWithMinyan.setImageResource(R.drawable.off);
		} else {
			imgWithMinyan.setImageResource(R.drawable.on);
		}
		
		boolean isInIsrael = settings.getBoolean("InIsrael", false);
		if (isInIsrael == false) {
			imgInIsrael.setImageResource(R.drawable.off);
		} else {
			imgInIsrael.setImageResource(R.drawable.on);
		}
		
		boolean isIsh = settings.getBoolean("isIsh", true);
		if (isIsh == false) {
			imgIsh.setImageResource(R.drawable.off);
		} else {
			imgIsh.setImageResource(R.drawable.on);
		}
		
		boolean isZimun = settings.getBoolean("isZimun", false);
		if (isZimun == false) {
			imgZimun.setImageResource(R.drawable.off);
		} else {
			imgZimun.setImageResource(R.drawable.on);
		}
		
		boolean isYain = settings.getBoolean("isYain", false);
		if (isYain == false) {
			imgYain.setImageResource(R.drawable.off);
		} else {
			imgYain.setImageResource(R.drawable.on);
		}
		
		boolean isDagan = settings.getBoolean("isDagan", true);
		if (isDagan == false) {
			imgDagan.setImageResource(R.drawable.off);
		} else {
			imgDagan.setImageResource(R.drawable.on);
		}
		
		boolean isPerot = settings.getBoolean("isPerot", false);
		if (isPerot == false) {
			imgPerot.setImageResource(R.drawable.off);
		} else {
			imgPerot.setImageResource(R.drawable.on);
		}
	}	
}
