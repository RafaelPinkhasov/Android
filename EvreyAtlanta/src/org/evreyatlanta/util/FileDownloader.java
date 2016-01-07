package org.evreyatlanta.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.evreyatlanta.R;
import org.evreyatlanta.models.NextPrevModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
 

public class FileDownloader extends AsyncTask<Void, Integer, Void> {

	private ProgressDialog mDialog;
	private FileManager mFileManager;
	private Context mContext;
	private NextPrevModel mFile;
	
	public FileDownloader(Context context, NextPrevModel model) {
		mContext = context;
		mFile = model;
		mFileManager = FileManager.init();
		mFileManager.reset();
	}
	
	/**
	 * Downloading file in background thread
	 * */
	protected Void doInBackground(Void... params) {
		int count;
		try {
			URL url = new URL(mFile.url);
			URLConnection conection = url.openConnection();
			conection.connect();
			// getting file length
			int lenghtOfFile = conection.getContentLength();

			// input stream to read file - with 8k buffer
			InputStream input = new BufferedInputStream(url.openStream(), 8192);
			
			String filePath = mFileManager.goTo(mFile.teacher).getPath()  + "/" + mFile.name + ".mp3";
			
			Log.i("file_path", filePath);
			
			// Output stream to write file
			OutputStream output = new FileOutputStream(filePath);

			byte data[] = new byte[1024];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				// After this onProgressUpdate will be called
				publishProgress((int) ((total * 100) / lenghtOfFile));

				// writing data to file
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();

		} catch (Exception e) {
			Log.e("Error: ", e.getMessage());
		}

		return null;
	}
	
	public String getFileName(URL url) {
		String [] portions = url.getPath().split("/");		
		return "/" + portions[portions.length - 1];
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Before starting background thread Show Progress Bar Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	/**
	 * Updating progress bar
	 * */
	protected void onProgressUpdate(Integer... progress) {
		// setting progress percentage
		mDialog.setProgress(progress[0]);
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(Void prms) {
		dismissDialog();
	}
	
	private void showDialog() {
		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage("Saving file. Please wait...");
		mDialog.setIndeterminate(false);
		mDialog.setMax(100);
		mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mDialog.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.scrubber_progress));
		mDialog.setCancelable(false);
		mDialog.show();
	}
	
	private void dismissDialog() {
		mDialog.dismiss();
	}
}
