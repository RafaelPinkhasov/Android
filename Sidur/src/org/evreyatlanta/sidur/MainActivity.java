package org.evreyatlanta.sidur;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.RejectedExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ExpandableListView lstoptions;
	private ArrayList<MainModel> _list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayList<String> shaharit = new ArrayList<String>(); 
		shaharit.add(getString(R.string.birkat_hashar));
		shaharit.add(getString(R.string.psukey_dzimra));
		shaharit.add(getString(R.string.kriyat_shma_amida));
		
		_list = new ArrayList<MainModel>();
		_list.add(new MainModel(getString(R.string.shaharit), shaharit));
		_list.add(new MainModel(getString(R.string.mincha)));
		_list.add(new MainModel(getString(R.string.maariv)));
		_list.add(new MainModel(getString(R.string.birkat_hamazon)));
		_list.add(new MainModel(getString(R.string.bracha_achrona)));
		_list.add(new MainModel("Settings"));
		
		lstoptions = (ExpandableListView)findViewById(R.id.home_options);
		//lstoptions.setGroupIndicator(getResources().getDrawable(R.drawable.expandable_list_icon_selector));
		
		lstoptions.setOnGroupClickListener(new OnGroupClickListener() {			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				
				MainModel value = _list.get(groupPosition);
				
				if(value.name.equals(getString(R.string.mincha))) {
					redirect(AmidaActivity.class, "amida", "mincha");
				} else if(value.name.equals(getString(R.string.maariv))) {
					redirect(AmidaActivity.class, "amida", "aravit");
				} else if(value.name.equals(getString(R.string.birkat_hamazon))) {
					redirect(AmidaActivity.class, "amida", "birkat_hamazon");
				} else if(value.name.equals(getString(R.string.bracha_achrona))) {
					redirect(AmidaActivity.class, "amida", "bracha_achrona");
				} else if (value.name.equals("Settings")) {
					redirect(SettingsActivity.class);
				}
				return false;
			}	
        });
		
		lstoptions.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				MainModel main = _list.get(groupPosition);
				String value = main.list.get(childPosition);
				
				if(value.equals(getString(R.string.birkat_hashar))) {
					redirect(AmidaActivity.class, "amida", "birkat_hashahar");
				} else  if(value.equals(getString(R.string.psukey_dzimra))) {
					redirect(AmidaActivity.class, "amida", "psukey_dzimra");
				} else  if(value.equals(getString(R.string.kriyat_shma_amida))) {
					redirect(AmidaActivity.class, "amida", "kriyat_shma_amida");
				} 
				return false;
			}
		});
		MainAdapter adapter = new MainAdapter(_list);			
		lstoptions.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		JDate jd = new JDate(new Date());
		
		Toast.makeText(this, jd.getJewishDayOfMonth() + " - " + jd.getJewishMonthName() + " - " + jd.getJewishYear(), Toast.LENGTH_LONG).show();
	
	}
	
	private class MainModel {
		
		public MainModel(String name) {
			this.name = name;
			this.list = new ArrayList<String>();
		}
		
		public MainModel(String name, ArrayList<String> list) {
			this.name = name;
			this.list = list;
		}
		String name;
		ArrayList<String> list;
	}	

	public void redirect(Class<?> to) {
		try {
			Intent intent = new Intent();			
		    intent.setClass(this, to);	
		    this.startActivity(intent);
		} catch (RejectedExecutionException ex){ }
	}
	
	public void redirect(Class<?> to, String key, String value) {
		try {
			Intent intent = new Intent();			
		    intent.setClass(this, to);	
		    intent.putExtra(key, value);
		    this.startActivity(intent);
		} catch (RejectedExecutionException ex){ }
	}
	
	class MainAdapter extends BaseExpandableListAdapter {
	    
		public MainAdapter(ArrayList<MainModel> list) {
	        _list = list;	        
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	        return _list.get(groupPosition).list.get(childPosititon);
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	    	return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	 
	    	final String value = (String) getChild(groupPosition, childPosition);
	 
	        if (convertView == null) {
	        	LayoutInflater infalInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	convertView = infalInflater.inflate(R.layout.row_item, (ViewGroup)null);
	        }
	 
	        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblItem);	 
	        txtListChild.setText(value);
	        return convertView;
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return _list.get(groupPosition).list.size();
	    }
	 
	    @Override
	    public Object getGroup(int groupPosition) {
	        return _list.get(groupPosition);
	    }
	 
	    @Override
	    public int getGroupCount() {
	        return _list.size();
	    }
	 
	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }
	 
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	    	MainModel main = (MainModel) getGroup(groupPosition);
	    	if (convertView == null) {
	    		LayoutInflater infalInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.row_item, (ViewGroup)null);	           
	        } 
	    	TextView lblTitle = (TextView) convertView.findViewById(R.id.lblItem);
	    	lblTitle.setText(main.name);	    	
	        return convertView;
	    }
	 
	    @Override
	    public boolean hasStableIds() {
	        return false;
	    }
	 
	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	}

}
