package org.evreyatlanta.util;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.evreyatlanta.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private static Map<String, Bitmap> cache;
    private final String id;

    public ImageDownloaderTask(String id, ImageView imageView) {
        this.imageViewReference = new WeakReference<ImageView>(imageView);
        this.id =id;  
    }
    
    private static Map<String, Bitmap> getCache() {
		if (cache == null) {
			cache = new HashMap<String, Bitmap>();
		}
		return cache;
	}
    
    public static void clearCache() {
    	if (cache != null) {
    		cache.clear();
    	}
    }

    @Override
    protected Bitmap doInBackground(String... params) {
    	if (getCache().containsKey(this.id)) {
    		return getCache().get(this.id);
    	} else {
    		return downloadBitmap(params[0]);
    	}
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    getCache().put(this.id, bitmap);
                } else {
                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher);
                    imageView.setImageDrawable(placeholder);
                    getCache().put(this.id, null);
                }
            }
        }
    }
    
    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
