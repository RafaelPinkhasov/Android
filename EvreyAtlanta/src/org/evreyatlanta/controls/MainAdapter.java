package org.evreyatlanta.controls;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainAdapter<T> extends BaseAdapter {
    
	protected Context mContext;
	protected LayoutInflater mInflater;
    protected ArrayList<T> mItems;
    protected OnAdapterItemBindListener<T> mListener;
    protected int mView;
      
    public MainAdapter(Context context, int view, ArrayList<T> items) {    	
    	mContext = context;
    	mInflater = LayoutInflater.from(context);
        mItems = items; 
        mView = view;
    }
   
    public int getCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }
    
    public long getItemId(int position) {
        return position;
    }
    
    public ArrayList<T> getItems() {
        return mItems;
    }    
    
    public void setOnItemBind(OnAdapterItemBindListener<T> listener) {
    	mListener = listener;
    }

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) 
			convertView = mInflater.inflate(mView, null);
		
		if (mListener != null) {
			mListener.bind(convertView, mItems.get(position));
		}
		
		return convertView;		
	}
}