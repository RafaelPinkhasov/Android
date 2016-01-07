package org.evreyatlanta.util;

import org.evreyatlanta.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class MediaNotificationManager {

	private Context mContext;
	private Class<?> mActivity;
	
	public MediaNotificationManager(Context context, Class<?> activity) {
		mContext = context;
		mActivity = activity;
	}
	
	@SuppressWarnings("deprecation")
	public void setNotification(String mediaId, String notificationTitle, String notificationMessage) {
	    NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	    android.app.Notification notification = 
	    		new android.app.Notification(R.drawable.ic_launcher, notificationMessage, System.currentTimeMillis()); 

	    Intent notificationIntent = new Intent(mContext, mActivity);
	    notificationIntent.putExtra("media_id", mediaId);
	    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
	    notification.setLatestEventInfo(mContext, notificationTitle, notificationMessage, pendingIntent);
	    notificationManager.notify(10001, notification);
	}
	
	/*
	NotificationCompat.Builder mBuilder =
	        new NotificationCompat.Builder(this)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setContentTitle("My notification")
	        .setContentText("Hello World!");
	// Creates an explicit intent for an Activity in your app
	Intent resultIntent = new Intent(this, AudioActivity.class);

	// The stack builder object will contain an artificial back stack for the
	// started Activity.
	// This ensures that navigating backward from the Activity leads out of
	// your application to the Home screen.
	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	// Adds the back stack for the Intent (but not the Intent itself)
	stackBuilder.addParentStack(AudioActivity.class);
	// Adds the Intent that starts the Activity to the top of the stack
	stackBuilder.addNextIntent(resultIntent);
	PendingIntent resultPendingIntent =
	        stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	        );
	mBuilder.setContentIntent(resultPendingIntent);
	NotificationManager mNotificationManager =
	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	// mId allows you to update the notification later on.
	mNotificationManager.notify(100, mBuilder.build());
	*/
	
}
