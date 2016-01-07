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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ParshaListActivity extends BaseActivity {

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
        mData.GetParsha(new ClassAction());
		
        expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				ClassModel model = _list.get(groupPosition).classes.get(childPosition);
				
				Map<String, Serializable> params = new HashMap<String, Serializable>();
				params.put("media_id", model.id);
				RedirectManager.redirect(ParshaListActivity.this, AudioActivity.class, params);
				return false;
			}	
        });
	}
	
	class ClassAction implements OnResponseListener<ArrayList<TeacherModel>> {
		public void execute(ArrayList<TeacherModel> list) {
			ClassAdapter adapter = new ClassAdapter(list);			
			expListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
    }
	
	class ClassAdapter extends BaseExpandableListAdapter {
	    
	    public ClassAdapter(List<TeacherModel> list) {
	        _list = list;
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	        return _list.get(groupPosition).classes.get(childPosititon);
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	    	return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	 
	    	final ClassModel classModel = (ClassModel) getChild(groupPosition, childPosition);
	 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) ParshaListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            ViewGroup parentView = null;
	            convertView = infalInflater.inflate(R.layout.row_class, parentView);
	        }
	 
	        TextView txtClassName = (TextView) convertView.findViewById(R.id.txtcolumn1);
	        TextView txtPublishDate = (TextView) convertView.findViewById(R.id.txtcolumn2);
	        TextView txtMediaName = (TextView) convertView.findViewById(R.id.txtcolumn3);
			txtClassName.setText(classModel.name);
			txtPublishDate.setText(classModel.publishedDate);
			txtMediaName.setText(classModel.mediaType);
	        return convertView;
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return _list.get(groupPosition).classes.size();
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
	            LayoutInflater infalInflater = (LayoutInflater) ParshaListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
