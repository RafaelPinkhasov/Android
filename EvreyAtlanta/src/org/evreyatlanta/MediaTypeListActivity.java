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

public class MediaTypeListActivity extends BaseActivity {

	private ListView lstoptions;
	private RestManager mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lstoptions = (ListView)findViewById(R.id.home_options);
		lstoptions.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				NameValueModel model = (NameValueModel)arg0.getItemAtPosition(position);
				Map<String, Serializable> params = new HashMap<String, Serializable>();
				params.put("media_type_id", model.value);
				params.put("teacher_id", getIntent().getExtras().getString("teacher_id"));
				if (model.value.equals("8")) {
					RedirectManager.redirect(arg1.getContext(), SubjectListActivity.class, params);
				} else {
					params.put("link_id", "");
					RedirectManager.redirect(arg1.getContext(), ClassListActivity.class, params);
				}
			}	
        });
		
		mData = new RestManager(this);
        mData.GetMediaTypes(getIntent().getExtras().getString("teacher_id"), getIntent().getExtras().getString("subject_type_id"), new SubjectAction());
	}

	class SubjectAction implements OnResponseListener<ArrayList<NameValueModel>> {
		public void execute(ArrayList<NameValueModel> param) {
			
			MainAdapter<NameValueModel> adapter = new MainAdapter<NameValueModel>(
					MediaTypeListActivity.this, 
					R.layout.row_info, 
					param);
			
			adapter.setOnItemBind(new OnAdapterItemBindListener<NameValueModel>() {
				public Object bind(View view, NameValueModel item) {
					ListItemAdapterView sview = new ListItemAdapterView(view);
					sview.setColumn1(item.name);
					return sview;
				}
			});
			
			lstoptions.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
    }

}
