package org.evreyatlanta.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;


public class BackgroundWorker<T> extends AsyncTask<Void, Object, T> implements OnCancelListener {
	
	private ProgressDialog  mDialog;
	private OnRequestListener<T> mRequest;
	private OnResponseListener<T> mResponse;
	private OnErrorListener mError;
	private Context mContext;
	
	private BackgroundWorker(Context context, OnRequestListener<T> request, OnResponseListener<T> response, OnErrorListener error) {
		mContext = context;
		mRequest = request;
		mResponse = response;
		mError = error;
	}
	
	public static <T> void Execute(Context context, OnRequestListener<T> request, OnResponseListener<T> response, OnErrorListener error) {
		BackgroundWorker<T> worker = new BackgroundWorker<T>(context, request, response, error);
		worker.execute();
	}
	
	@Override
	public T doInBackground(Void... params) {
		try {
			return mRequest.execute();
		} catch (Exception ex) {
			return null;
		}
	}
	
	@Override
	protected void onPreExecute() {
		try
		{
			mDialog = new ProgressDialog(mContext);
			mDialog.setMessage("Please wait...");
			mDialog.setIndeterminate(true);
			mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mDialog.setCancelable(true);
			mDialog.setOnCancelListener(this);
			mDialog.show();
		} catch(Exception ex){
			Log.e("onPreExecute", ex.getMessage());
		}
    }

	@Override
	public void onPostExecute(T result) {
		try {
			if (mResponse != null)
				mResponse.execute(result);
		} catch (Exception ex) {
			if (mError != null)
				mError.execute(ex.getMessage());
		} finally {
			try {
				mDialog.dismiss();
				mDialog = null;
			} catch (Exception ex2) {
				mDialog = null;
			}
		}
	}
	
	public void cancel() {		
		onCancel(mDialog);
		mDialog = null;
	}
	
	public void onCancel(DialogInterface arg0) {
		try {
			arg0.dismiss();			
			super.cancel(true);			
		} catch (Exception ex) { }
	}
}
