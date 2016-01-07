package org.evreyatlanta.controls;

import java.io.File;

import org.evreyatlanta.R;
import org.evreyatlanta.models.NextPrevModel;
import org.evreyatlanta.util.FileManager;
import org.evreyatlanta.util.MediaManager;
import org.evreyatlanta.util.SqlManager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayDialog extends Dialog {
    
	private String mFile;
	private ImageButton btnPlay;
	private TextView mTotalTime;
	private TextView mCurrentTime;
	private SeekBar mSeekBar;
	private MediaManager mMedia;
	private NextPrevModel mModel;
	private SqlManager sql;
	private IDeleteListenerCallback mDeleteCallback;
	
	
	public PlayDialog(Context context, String file) {
		super(context);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mFile = file;
	}
	
	public void setOnDeleteListener(IDeleteListenerCallback callback) {
		mDeleteCallback = callback;
    }
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_audio);
        
        sql = new SqlManager(this.getContext().getApplicationContext());
        
        TextView txtDialogTitle = (TextView)findViewById(R.id.dialogTitle);
        
        ImageButton btnDelete = (ImageButton)findViewById(R.id.delete);
        btnDelete.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMedia.exit();
				File file = new File(mModel.url);
				boolean deleted = file.delete();
				if (deleted) {
					FileManager fm = FileManager.init();
					if (fm.isRoot() == false && fm.getDir().list().length == 0) {
						 fm.getDir().delete();
						 fm.getParent();
					}
					deleteDownload();
					if (mDeleteCallback != null)
						mDeleteCallback.onDelete();
				}
				cancel();
			}
        });
        
        btnPlay = (ImageButton)findViewById(R.id.play);        
        mTotalTime = (TextView)findViewById(R.id.songTotalDurationLabel);
		mCurrentTime = (TextView)findViewById(R.id.songCurrentDurationLabel);
		mSeekBar = (SeekBar)findViewById(R.id.songProgressBar);	
		
		
		mModel = new NextPrevModel();
		mModel.name = getFileName();
		mModel.url = mFile;	
		mModel.position = sql.getDownloadCurrentPosistion(mModel.url);
		mMedia = new MediaManager(this.getContext().getApplicationContext(), mModel, btnPlay, mSeekBar, mTotalTime, mCurrentTime);
		mMedia.setOnSaveListenerCallback(new MediaManager.OnSaveListenerCallback() {
			@Override
			public void save() {
				saveDownload();
			}
		});
		
		mMedia.load();
		
		txtDialogTitle.setText(mModel.name.replace(".mp3", ""));
    } 
    
    public String getFileName() {
		String [] portions = mFile.split("/");		
		return portions[portions.length - 1];
	}
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch(keyCode) {
	    	case KeyEvent.KEYCODE_BACK:
	    		mMedia.exit();
	    		saveDownload();
	    		break;	
	    }
	    	
	    return super.onKeyDown(keyCode, event);
	} 	
    
	private void deleteDownload() {
		try {
			if (mModel != null) {
				sql.deleteDownload(mModel);
				sql.close();
			}
		} catch (Exception e) {
			Log.e("deleteDownload", e.getMessage());
		}
	}
	
	private void saveDownload() {
		try {
			if (mModel != null && mModel.position > 0) {
				sql.saveDownload(mModel);
				sql.close();
			}
		} catch (Exception e) {
			Log.e("saveDownload", e.getMessage());
		}
	}	
	
	public interface IDeleteListenerCallback {
		void onDelete();
	}

}