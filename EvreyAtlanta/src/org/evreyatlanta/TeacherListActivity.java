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
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherListActivity extends BaseActivity {
	
    private ExpandableListView expListView;
	private RestManager mData;
	private List<TeacherModel> _list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent);
		
		ImageDownloaderTask.clearCache();
		
		expListView = (ExpandableListView)findViewById(R.id.home_options);
		mData = new RestManager(this);
        mData.GetTeachers(new TeacherAction());
		
        expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				Map<String, Serializable> params = new HashMap<String, Serializable>();
				TeacherModel teacher =  _list.get(groupPosition);
				params.put("teacher_id", teacher.id);
				params.put("media_type_id", teacher.mediaTypes.get(childPosition).id);				
				if (_list.get(groupPosition).mediaTypes.get(childPosition).id.equals("8")) {
					RedirectManager.redirect(TeacherListActivity.this, SubjectListActivity.class, params);
				} else {
					params.put("link_id", "");
					RedirectManager.redirect(TeacherListActivity.this, ClassListActivity.class, params);
				}	
				
				return false;
			}	
        });
       
	}
	
	class TeacherAction implements OnResponseListener<ArrayList<TeacherModel>> {
		public void execute(ArrayList<TeacherModel> list) {
			
			TeacherAdapter adapter = new TeacherAdapter(list);			
			expListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
    }
	
	class TeacherAdapter extends BaseExpandableListAdapter {
	    
		public TeacherAdapter(List<TeacherModel> list) {
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
	        	LayoutInflater infalInflater = (LayoutInflater) TeacherListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	convertView = infalInflater.inflate(R.layout.row_item, (ViewGroup)null);
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
	    	TeacherModel teacher = (TeacherModel) getGroup(groupPosition);
	    	ImageView imageView;
	    	if (convertView == null) {
	    		LayoutInflater infalInflater = (LayoutInflater) TeacherListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.row_image_group, (ViewGroup)null);	           
	            imageView = (ImageView) convertView.findViewById(R.id.thumbImage);				
				convertView.setTag(imageView);
	        } else {
	        	imageView = (ImageView) convertView.getTag();
	        }
	    	TextView lblTitle = (TextView) convertView.findViewById(R.id.lblHeader);
	    	lblTitle.setText(teacher.name);
	    	new ImageDownloaderTask(teacher.id, imageView).execute("http://www.evreyatlanta.org/rest/" + teacher.id + ".jpg");
	    	
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
