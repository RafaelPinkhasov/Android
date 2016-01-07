package org.evreyatlanta.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class RedirectManager {
	public static void redirect(Context context, Class<?> to) {
		try {
			Intent intent = new Intent();			
		    intent.setClass(context, to);
		    intent.putExtra("from", context.getClass());
		    context.startActivity(intent);
		} catch (RejectedExecutionException ex){ }
	}
	
	public static void redirectToUrl(Context context, String url) {		
		Uri uri = Uri.parse(url);
    	Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
    	context.startActivity(launchBrowser);		
	}
	
	public static void redirect(Context context, Class<?> to, String key, String value) {
		try {
			Intent intent = new Intent();
			intent.putExtra(key, value);
			intent.putExtra("from", context.getClass());
		    intent.setClass(context, to);
		    context.startActivity(intent);
		} catch (RejectedExecutionException ex){ }
	}
	
	public static void redirect(Context context, Class<?> to, Map<String, Serializable> params ) {
		try {
			Intent intent = new Intent();
			
			for(String key: params.keySet()){
				Serializable value = params.get(key);
				intent.putExtra(key, value);
			}
			intent.putExtra("from", context.getClass());
		    intent.setClass(context, to);
		    context.startActivity(intent);
		} catch (RejectedExecutionException ex){ }
	}
	
}
