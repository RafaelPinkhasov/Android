package org.evreyatlanta;

import java.util.ArrayList;

import org.evreyatlanta.R;
import org.evreyatlanta.controls.*;
import org.evreyatlanta.models.*;
import org.evreyatlanta.util.RedirectManager;
import org.evreyatlanta.views.*;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends BaseActivity {

	private ListView lstoptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lstoptions = (ListView)findViewById(R.id.home_options);
		lstoptions.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				NameValueModel model = (NameValueModel)arg0.getItemAtPosition(position);
				
				if(model.value == "0") {
					RedirectManager.redirect(MainActivity.this, ParshaListActivity.class);
				} else if(model.value == "1") {
					RedirectManager.redirect(MainActivity.this, SubjectTypeListActivity.class);
				} else if(model.value == "2") {
					RedirectManager.redirect(MainActivity.this, TeacherListActivity.class);
				} else if (model.value == "3") {
					RedirectManager.redirect(MainActivity.this, HistoryListActivity.class);
				} else if (model.value == "4") {
					RedirectManager.redirect(MainActivity.this, InfoActivity.class);
				} else if (model.value == "5") {
					RedirectManager.redirect(MainActivity.this, DownloadedListActivity.class);
				}
			}	
        });
		lstoptions.setAdapter(getAdapter());
	}
	
	private MainAdapter<NameValueModel> getAdapter() {
		ArrayList<NameValueModel> list = new ArrayList<NameValueModel>(); 
    	list.add(new NameValueModel(getString(R.string.menu_wp), "0"));
    	list.add(new NameValueModel(getString(R.string.menu_sb), "1"));
    	list.add(new NameValueModel(getString(R.string.menu_rb), "2"));
    	list.add(new NameValueModel(getString(R.string.menu_hs), "3"));
    	list.add(new NameValueModel(getString(R.string.menu_dw), "5"));
    	list.add(new NameValueModel(getString(R.string.menu_in), "4"));
    	
    	MainAdapter<NameValueModel> adapter = new MainAdapter<NameValueModel>(
    			MainActivity.this, 
				R.layout.row_info, 
				list);
		
		adapter.setOnItemBind(new OnAdapterItemBindListener<NameValueModel>() {
			public Object bind(View view, NameValueModel item) {
				ListItemAdapterView sview = new ListItemAdapterView(view);
				sview.setColumn1(item.name);	
				return sview;
			}
		});
		adapter.notifyDataSetChanged();
		return adapter;
    }   

}
