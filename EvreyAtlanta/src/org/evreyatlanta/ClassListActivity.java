package org.evreyatlanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.evreyatlanta.R;
import org.evreyatlanta.controls.*;
import org.evreyatlanta.models.*;
import org.evreyatlanta.util.*;
import org.evreyatlanta.views.*;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ClassListActivity extends BaseActivity {

	private ListView lstoptions;
	private RestManager mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lstoptions = (ListView)findViewById(R.id.home_options);
		lstoptions.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				ClassModel model = (ClassModel)arg0.getItemAtPosition(position);
				
				Map<String, Serializable> params = new HashMap<String, Serializable>();
				params.put("media_id", model.id);
				RedirectManager.redirect(arg1.getContext(), AudioActivity.class, params);
			}	
        });
		
		mData = new RestManager(this);
        mData.GetClasses(
        		getIntent().getExtras().getString("teacher_id"), 
        		getIntent().getExtras().getString("media_type_id"), 
        		getIntent().getExtras().getString("link_id"), new ClassAction());
	}
	
	class ClassAction implements OnResponseListener<ArrayList<ClassModel>> {
		public void execute(ArrayList<ClassModel> param) {
			
			MainAdapter<ClassModel> adapter = new MainAdapter<ClassModel>(
					ClassListActivity.this, 
					R.layout.row_class, 
					param);
			
			adapter.setOnItemBind(new OnAdapterItemBindListener<ClassModel>() {
				public Object bind(View view, ClassModel item) {
					ListItemAdapterView sview = new ListItemAdapterView(view);
					sview.setColumn1(item.name);
					sview.setColumn3(item.teacher);
					sview.setColumn2(item.publishedDate);
					return sview;
				}
			});			
			
			lstoptions.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
    }

}
