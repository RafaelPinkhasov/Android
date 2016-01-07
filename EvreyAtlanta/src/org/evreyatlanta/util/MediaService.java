package org.evreyatlanta.util;

import org.evreyatlanta.AudioActivity;
import org.evreyatlanta.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MediaService extends Service {
	
	public static final String START_SERVICE = "org.evreyatlanta.AudioActivity.start";
	public static final String STOP_SERVICE = "org.evreyatlanta.AudioActivity.stop";
	public static final int FOREGROUND_SERVICE = 101;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getAction().equals(START_SERVICE)) {
			Intent notificationIntent = new Intent(this, AudioActivity.class);
			notificationIntent.setAction(START_SERVICE);
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

			Bitmap icon = BitmapFactory.decodeResource(getResources(),	R.drawable.tora);

			Notification notification = new NotificationCompat.Builder(this)
					.setContentTitle("Truiton Music Player")
					.setTicker("Truiton Music Player")
					.setContentText("My Music")
					.setSmallIcon(R.drawable.ic_launcher)
					.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
					.setContentIntent(pendingIntent)
					.setOngoing(true)
					.build();
			
			startForeground(FOREGROUND_SERVICE, notification);
			Log.w("start", "Started");
			
		} else {
			stopForeground(true);
			stopSelf();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
