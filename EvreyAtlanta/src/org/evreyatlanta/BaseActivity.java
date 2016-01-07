package org.evreyatlanta;

import org.evreyatlanta.util.RedirectManager;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_wp:
	        	RedirectManager.redirect(this, ParshaListActivity.class);
				return true;
	        case R.id.menu_rb:
	        	RedirectManager.redirect(this, TeacherListActivity.class);
	            return true;
        	case R.id.menu_hs:
        		RedirectManager.redirect(this, HistoryListActivity.class);
	            return true;
        	case R.id.menu_in:
        		RedirectManager.redirect(this, InfoActivity.class);
	            return true;    
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
