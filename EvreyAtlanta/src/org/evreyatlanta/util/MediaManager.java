package org.evreyatlanta.util;
import org.evreyatlanta.R;
import org.evreyatlanta.models.ClassModel;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MediaManager {

	private ClassModel mModel;
	private ImageButton mButton;
	private SeekBar mSeekBar;
	private TextView mCurrentTime;
	private TextView mTotalTime;
	private MediaPlayer mPlayer;
	private Context mContext;
	private Handler seekHandler = new Handler();
	private OnSaveListenerCallback mOnSaveCallback;
   
	
	public MediaManager(Context context, ClassModel model, ImageButton button, SeekBar seekbar, TextView totalTime, TextView currentTime) {
		mContext = context;
		mPlayer = new MediaPlayer();
		mModel = model;
		mButton = button;
		mSeekBar = seekbar;
		mTotalTime = totalTime;
		mCurrentTime = currentTime;
		init();
	}
	
	private void init() {
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.setOnCompletionListener(new OnCompletionListener() {			
			@Override
			public void onCompletion(MediaPlayer mp) {
				if(mPlayer.isPlaying()){
					pause();
				}
			}
		});
		mPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer arg0) {
				mPlayer.seekTo(mModel.position);	
				mSeekBar.setProgress(mModel.position);
			}
		});
		mPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				percent = percent * (mSeekBar.getMax() / 100);
				mSeekBar.setSecondaryProgress(percent);
			}
		});
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
	        public void onStopTrackingTouch(SeekBar seekbar) {
				updateSeekbar();
	        }

	        @Override
	        public void onStartTrackingTouch(SeekBar seekbar) {
	        	if(mPlayer.isPlaying()){
	        		pause();
	        	}
	        }

	        @Override
	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	            if (fromUser) {
	            	mPlayer.seekTo(progress);
	            	mModel.position = progress;
	            	updateSeekbar();
	            }
	        }
		});
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
		    public void onClick(View v) {   
				updateSeekbar();
				if(mPlayer.isPlaying()){
					pause();
				} else {
					play();
				}
		    }
		});
		TelephonyManager TelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		TelephonyMgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	public void setOnSaveListenerCallback(OnSaveListenerCallback callback) {
		mOnSaveCallback = callback;
	}
	
	public void load() {
		try {
			mPlayer.reset();
			mPlayer.setDataSource(mModel.url);
			mPlayer.prepare();
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            stop();
            error();
        } 
		
		if(mPlayer.isPlaying()){
			mButton.setImageResource(R.drawable.button_pause);
		} else {
			mButton.setImageResource(R.drawable.button_play);
		}
		mSeekBar.setMax(mPlayer.getDuration());
		mSeekBar.setProgress(mModel.position);
		mCurrentTime.setText(milliSecondsToTimer(mModel.position));
		mTotalTime.setText(milliSecondsToTimer(mPlayer.getDuration()));
	}
	
	private void error() {
		mButton.setEnabled(false);
		mSeekBar.setEnabled(false);
	}
	
	public void stop() {
		seekHandler.removeCallbacks(run);
		mCurrentTime.setText("");
		mTotalTime.setText("");
		mButton.setImageResource(R.drawable.button_play);
		if (mPlayer != null) {
			mPlayer.seekTo(0);		
			mPlayer.stop();	
			mPlayer.reset();
		}
		mSeekBar.setProgress(0);
		mSeekBar.setSecondaryProgress(0);
	}
	
	public void pause() {
		mButton.setImageResource(R.drawable.button_play);
		if (mPlayer != null) {
			mPlayer.pause();
		}
	}
	
	public void play() {
		mButton.setImageResource(R.drawable.button_pause);
		if (mPlayer != null) {
			mPlayer.start();
		}
	}
	
	public void exit() {
		mButton.setImageResource(R.drawable.button_play);
		if (mPlayer != null) {
			mModel.position = mPlayer.getCurrentPosition();
			if (mPlayer.isPlaying()) {
				mPlayer.pause();
			}
			mPlayer.release();	
			mPlayer = null;
		}
	}
	
	private Runnable run = new Runnable() { 
		@Override 
		public void run() { 
			updateSeekbar(); 
		} 
	};
	
	private void updateProgress() {
		if (mPlayer != null) {
			mModel.position = mPlayer.getCurrentPosition();
			mCurrentTime.setText(milliSecondsToTimer(mModel.position));
			mSeekBar.setProgress(mModel.position);
			if (mModel.position >= mSeekBar.getMax()) {
				mButton.setImageResource(R.drawable.button_play);
			}
		}
	}
	
	private void updateSeekbar() { 		
		if (mPlayer != null) {
			updateProgress();
			seekHandler.removeCallbacks(run);
			seekHandler.postDelayed(run, 1000);			
		}
	}
	
	PhoneStateListener phoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
		    if (state == TelephonyManager.CALL_STATE_RINGING) {
		    	//Incoming call: Pause music
		    	pause();
		    	if (mOnSaveCallback != null)
		    		mOnSaveCallback.save();
		    } else if(state == TelephonyManager.CALL_STATE_IDLE) {
		    	//Not in call: Play music
		    } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
		    	//A call is dialing, active or on hold
		    	pause();
		    	if (mOnSaveCallback != null)
		    		mOnSaveCallback.save();
		    }
		    super.onCallStateChanged(state, incomingNumber);
		}
	};
	
	
	/**
	 * Function to convert milliseconds time to
	 * Timer Format
	 * Hours:Minutes:Seconds
	 * */
	private String milliSecondsToTimer(long milliseconds){
		String finalTimerString = "";
		String secondsString = "";
		String minutesString = "";
		
		// Convert total duration into time
		   int hours = (int)( milliseconds / (1000*60*60));
		   int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
		   int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
		   // Add hours if there
		   if(hours > 0){
			   finalTimerString = hours + ":";
		   }
		   
		   // Prepending 0 to seconds if it is one digit
		   if(seconds < 10){ 
			   secondsString = "0" + seconds;
		   }else{
			   secondsString = "" + seconds;
		   }
		   
		   // Prepending 0 to seconds if it is one digit
		   if(minutes < 10){ 
			   minutesString = "0" + minutes;
		   }else{
			   minutesString = "" + minutes;
		   }
		   
		   finalTimerString = finalTimerString + minutesString + ":" + secondsString;
		
		// return timer string
		return finalTimerString;
	}
	
	public interface OnSaveListenerCallback {
		void save();
	}
}
