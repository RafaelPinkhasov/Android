package org.evreyatlanta;

import org.evreyatlanta.R;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class InfoActivity extends BaseActivity {

	WebView webInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		webInfo = (WebView)findViewById(R.id.webview);
		WebSettings settings = webInfo.getSettings();
		settings.setDefaultTextEncodingName("utf-8");   
		webInfo.loadData(getHtml(), "text/html; charset=utf-8", "utf-8");
	}

	private String getHtml() {
		return "<br/><br/>В версии 1.10 вы можете сохранить лекцию на своем телефоне, и позже прослушать когда у вас не будет доступа к интернету. Вы найдете сохраненные лекции на SD card в директории \"Music/Torah Audio\". <br/><br/>Наш сайт: <a href=\"http://www.evreyatlanta.org\">www.evreyatlanta.org</a><br/>";
	}
	
}
