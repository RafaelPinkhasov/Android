package org.evreyatlanta.views;

import org.evreyatlanta.R;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ListItemAdapterView {
	private TextView txtcolumn1;
	private TextView txtcolumn2;
	private TextView txtcolumn3;
	
	public ListItemAdapterView(View view) {		
		txtcolumn1 = (TextView)view.findViewById(R.id.txtcolumn1);
		txtcolumn2 = (TextView)view.findViewById(R.id.txtcolumn2);
		txtcolumn3 = (TextView)view.findViewById(R.id.txtcolumn3);
	}
	
	public void setColumn1(String value) {
		txtcolumn1.setText(value);
	}
	
	public void setColumn2(String value) {
		txtcolumn2.setText(value);
	}
	
	public void setColumn3(String value) {
		txtcolumn3.setText(value);
	}
	
	
	public void higlite(boolean value) {
		if (value) {
			if (txtcolumn1 != null) {
				((ViewGroup)txtcolumn1.getParent()).setBackgroundResource(R.drawable.list_hilite);
				txtcolumn1.setTextColor(Color.parseColor("#FFFFFF"));
			}
			if (txtcolumn2 != null)
				txtcolumn2.setTextColor(Color.parseColor("#FFFFFF"));
			if (txtcolumn3 != null)
				txtcolumn3.setTextColor(Color.parseColor("#FFFFFF"));
		} else {
			if (txtcolumn1 != null) {
				((ViewGroup)txtcolumn1.getParent()).setBackgroundResource(R.drawable.list_unhilite);
				txtcolumn1.setTextColor(Color.parseColor("#000000"));
			}
			if (txtcolumn2 != null)
				txtcolumn2.setTextColor(Color.parseColor("#000000"));
			if (txtcolumn3 != null)
				txtcolumn3.setTextColor(Color.parseColor("#000000"));
		}
	}
}
