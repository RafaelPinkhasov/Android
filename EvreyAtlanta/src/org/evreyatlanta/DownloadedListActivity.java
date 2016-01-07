package org.evreyatlanta;


import java.util.ArrayList;
import java.util.Arrays;

import org.evreyatlanta.R;
import org.evreyatlanta.controls.*;

import org.evreyatlanta.util.*;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DownloadedListActivity extends BaseActivity {

	private ListView lstoptions;
	private FileManager fileManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fileManager = FileManager.init();
		fileManager.reset();
		lstoptions = (ListView)findViewById(R.id.home_options);
		lstoptions.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String model = (String)parent.getItemAtPosition(position);
				if (model.indexOf(".mp3") > -1) {
					PlayDialog dialog = new PlayDialog(view.getContext(), fileManager.getDir() + "/" + model);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setOnDeleteListener(new PlayDialog.IDeleteListenerCallback() {
						@Override
						public void onDelete() {
							new DownloadedAction().execute();
						}
					});
					dialog.show();
				} else {
					fileManager.goTo(model);
					new DownloadedAction().execute();
				}
			}	
        });
		
		new DownloadedAction().execute();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    boolean flag = false;
		switch(keyCode) {
	    	case KeyEvent.KEYCODE_BACK:
	    		if (fileManager.isRoot()) { 
	    			flag = super.onKeyDown(keyCode, event);
	    	    } else {
	    	    	fileManager.getParent();
	    	    	new DownloadedAction().execute();
	    	    	flag = false;
	    	    }	 
	    		break;	
	    }
	    	
	    return flag;
	} 	
		
	
	class DownloadedAction {
		
		ArrayList<String> list;
		public DownloadedAction() {
			list = new ArrayList<String>(Arrays.asList(fileManager.getDir().list()));
		}
		
		public void execute() {
			
			MainAdapter<String> adapter = new MainAdapter<String>(
					DownloadedListActivity.this, 
					R.layout.row_download, 
					list);
			
			adapter.setOnItemBind(new OnAdapterItemBindListener<String>() {
				public Object bind(View view, String item) {
					TextView txt = (TextView)view.findViewById(R.id.lblHeader);
					ImageView img = (ImageView)view.findViewById(R.id.thumbImage);
					if (fileManager.isDirectory(item)) {
						img.setBackgroundResource(R.drawable.folder);
					} else {
						img.setBackgroundResource(R.drawable.button_play);
					}
						
					txt.setText(item.replace(".mp3", ""));
					return view;
				}
			});
			
			lstoptions.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
    }

}
