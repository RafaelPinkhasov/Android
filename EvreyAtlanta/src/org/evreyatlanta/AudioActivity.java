package org.evreyatlanta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.evreyatlanta.R;
import org.evreyatlanta.models.NextPrevModel;
import org.evreyatlanta.util.FileDownloader;
import org.evreyatlanta.util.MediaManager;
import org.evreyatlanta.util.OnResponseListener;
import org.evreyatlanta.util.RestManager;
import org.evreyatlanta.util.SqlManager;
import org.evreyatlanta.util.StringManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class AudioActivity extends Activity {
	MediaManager mMedia;
	NextPrevModel mModel;
	RestManager mData;
	WebView webNotes;
	TextView txtTitle;
	TextView txtTeacher;
	TextView txtTotalTime;
	TextView txtCurrentTime;
	SeekBar seekbar;
	ImageButton btnPlay;
	ImageButton btnNext;
	ImageButton btnPrev;
	ImageButton btnDownload;
	String mediaId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		
		mediaId = getIntent().getExtras().getString("media_id");
		mData = new RestManager(this);
        mData.GetClass(mediaId, new ClassAction());
		
		webNotes = (WebView)findViewById(R.id.webview);
		txtTitle = (TextView)findViewById(R.id.title);
		txtTeacher = (TextView)findViewById(R.id.teacher);
		txtTotalTime = (TextView)findViewById(R.id.songTotalDurationLabel);
		txtCurrentTime = (TextView)findViewById(R.id.songCurrentDurationLabel);
		seekbar = (SeekBar)findViewById(R.id.songProgressBar);
		btnPlay = (ImageButton)findViewById(R.id.play);
		btnNext = (ImageButton)findViewById(R.id.next);
		btnPrev = (ImageButton)findViewById(R.id.prev);
		btnDownload = (ImageButton)findViewById(R.id.download);
		
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveHistory();
				mData = new RestManager(AudioActivity.this);
		        mData.GetClass(mModel.nextId, new ClassAction());
		        mMedia.stop();
			}
		});
		
		btnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveHistory();
				mData = new RestManager(AudioActivity.this);
		        mData.GetClass(mModel.prevId, new ClassAction());
		        mMedia.stop();
			}
		});
		
		btnDownload.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				FileDownloader downloader = new FileDownloader(AudioActivity.this, mModel);
				downloader.execute();
			}
		});
	}
	
	class ClassAction implements OnResponseListener<NextPrevModel> {
		public void execute(NextPrevModel param) {			
			
			if (param == null) {
				param = new NextPrevModel();
				param.name = "ERROR";
				param.notes = "Please check your internet connection";
			}
			
			mModel = param;
			
			txtTeacher.setText(mModel.teacher);
			txtTitle.setText(mModel.name);
			if (mModel.notes == null || mModel.notes.equals("")) {
				webNotes.setVisibility(View.GONE);
			} else {
				String html = "";
				if (mModel.notes.startsWith("www")){
					String link = mModel.notes;
					Pattern r = Pattern.compile("^(.*?)\\s");
					Matcher m = r.matcher(mModel.notes);
					if (m.find( )) {
						link = m.group(0).trim();
					}
					html = "<style>div {font-size:18px;background-color:#f4f4f4;}</style><div>Лекция взята с сайта <a href=\"http://" + link + "\" target=\"new\">" + link + "</a>" + mModel.notes.replace(link, "") + "</div>";
				} else {
					html = "<style>i { color: #00688B; font-size:12px;} div {font-size:18px;text-align:right;background-color:#f4f4f4;}</style><div>" + mModel.notes + "</div>";
				}
				
				WebSettings settings = webNotes.getSettings();
				settings.setDefaultTextEncodingName("utf-8");
				webNotes.setVisibility(View.VISIBLE);
				webNotes.loadData(html, "text/html; charset=utf-8", "utf-8");
				webNotes.setBackgroundColor(0);
				webNotes.reload();
			}
			
			mMedia = new MediaManager(AudioActivity.this.getApplicationContext(), mModel, btnPlay, seekbar, txtTotalTime, txtCurrentTime);
			mMedia.setOnSaveListenerCallback(new MediaManager.OnSaveListenerCallback() {
				@Override
				public void save() {
					saveHistory();
				}
			});
			if (mModel.url != null) {
				mMedia.load();
			}
			
			if (!StringManager.isNullOrEmpty(param.nextId)) {
				btnNext.setVisibility(View.VISIBLE);
			} else {
				btnNext.setVisibility(View.INVISIBLE);
			}
			if (!StringManager.isNullOrEmpty(param.prevId)) {
				btnPrev.setVisibility(View.VISIBLE);
			} else {
				btnPrev.setVisibility(View.INVISIBLE);
			}
			
			if (mModel.url == null) {
				btnDownload.setVisibility(View.INVISIBLE);
				btnPlay.setVisibility(View.INVISIBLE);
				seekbar.setVisibility(View.INVISIBLE);
				txtTotalTime.setVisibility(View.INVISIBLE);
				txtCurrentTime.setVisibility(View.INVISIBLE);
			}			
		}
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch(keyCode) {
	    	case KeyEvent.KEYCODE_BACK:
	    		mMedia.exit();
	    		saveHistory();
	    		mMedia = null;
				break;	
	    }
	    	
	    return super.onKeyDown(keyCode, event);
	} 	
	
	private void saveHistory() {
		try {
			if (mModel != null && mModel.position > 0) {
				SqlManager sql = new SqlManager(getApplicationContext());
				sql.saveHistory(mModel);
				sql.close();				
			}
		} catch (Exception e) {
			Log.e("saveHistory", e.getMessage());
		}
	}	
}
