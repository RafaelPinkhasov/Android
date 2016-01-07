package org.evreyatlanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.evreyatlanta.R;
import org.evreyatlanta.models.*;
import org.evreyatlanta.util.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SubjectTypeListActivity extends BaseActivity {

	private ExpandableListView expListView;
	private RestManager mData;
	private List<SubjectTypeModel> _list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent);
		
		expListView = (ExpandableListView)findViewById(R.id.home_options);
		mData = new RestManager(this);
		mData.GetSubjectTypes(new SubjectTypeAction());
		
        expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				
				Map<String, Serializable> params = new HashMap<String, Serializable>();
				SubjectTypeModel subjecType =  _list.get(groupPosition);
				params.put("subject_type_id", subjecType.id);
				params.put("media_type_id", subjecType.mediaTypes.get(childPosition).id);				
				if (_list.get(groupPosition).mediaTypes.get(childPosition).id.equals("8")) {
					RedirectManager.redirect(SubjectTypeListActivity.this, SubjectListActivity.class, params);
				} else {
					params.put("link_id", "");
					RedirectManager.redirect(SubjectTypeListActivity.this, ClassListActivity.class, params);
				}	
				
				return false;
			}	
        });
	}
	
	class SubjectTypeAction implements OnResponseListener<ArrayList<SubjectTypeModel>> {
		public void execute(ArrayList<SubjectTypeModel> list) {
			
			SubjectTypeAdapter adapter = new SubjectTypeAdapter(list);			
			expListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
    }
	
	class SubjectTypeAdapter extends BaseExpandableListAdapter {
	    
	    public SubjectTypeAdapter(List<SubjectTypeModel> list) {
	        _list = list;
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	        return _list.get(groupPosition).mediaTypes.get(childPosititon);
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	    	return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	 
	    	final MediaTypeModel mediaType = (MediaTypeModel) getChild(groupPosition, childPosition);
	 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) SubjectTypeListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.row_item, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblItem);
	 
	        txtListChild.setText(mediaType.name);
	        return convertView;
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return _list.get(groupPosition).mediaTypes.size();
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
	    	SubjectTypeModel teacher = (SubjectTypeModel) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) SubjectTypeListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.row_group, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblHeader);
	        lblListHeader.setText(teacher.name);
	 
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
