package org.evreyatlanta.sidur;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class AmidaActivity extends Activity {

	WebView webInfo;
	JCalendar jc;
	String amida;
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		settings = getSharedPreferences(PREFS_NAME, 0);
		
		amida = getIntent().getExtras().getString("amida");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); 
		if (amida.equals("aravit") && c.get(Calendar.HOUR_OF_DAY) > 16) {
			c.add(Calendar.DATE, 1);
		}
		jc = new JCalendar(c.getTime());
		jc.setInIsrael(isInIsrael());
		
		webInfo = (WebView)findViewById(R.id.webview);		
		webInfo.setLayerType(View.LAYER_TYPE_SOFTWARE, null);		
		webInfo.setFocusable(false);
		webInfo.setFocusableInTouchMode(false);
		
		WebSettings settings = webInfo.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		//settings.setFixedFontFamily("LSHONTOV.TTF");
		webInfo.setBackgroundColor(0);
		webInfo.loadDataWithBaseURL("file:///android_asset/", getHtml(), "text/html", "utf-8", null);
		webInfo.reload();
	}	
	
	private String getHtml() {
		try {
			
			boolean tahanun = true;
			
			if (amida.equals("mincha") && jc.getDayOfWeek() == 6) {
				tahanun = false;
			}
			if (tahanun) {
				tahanun = jc.isTahanun();
			}
			
			boolean talumatar = jc.isTalumatar();
			boolean summer = jc.isSummer();
			boolean tshuva = jc.isTshuvaDays();
			boolean man = isMan();
			boolean mon_thu = (jc.getDayOfWeek() == 2 || jc.getDayOfWeek() == 5);
						
			int omer = jc.isOmer();
			StringBuilder style = new StringBuilder();
			style.append("<html><head><meta http-equiv=Content-Type content=\"text/html; charset=windows-1255\"><style>");
			style.append("@font-face { font-family: 'Lashon Tov'; src: url('LSHONTOV.TTF'); }");			
			style.append("body {font-family: 'Lashon Tov';font-size:26px;padding-left:5px; padding-right:10px; padding-top:20px;	padding-bottom:20px;}");
			style.append(".header {font-size:28px;color:#000099;font-weight:bold;}");
			style.append("div { padding-top:25px;}");
			style.append("b {font-size:28px;color:#000099;font-weight:bold;}");
			style.append(".color1 {color:#9900FF;}.color3 {background-color:lightgray;} .color4 {background-color: yellow;}");
	        style.append(".KADISH { background-color:lightgray;display:" + (!isKadish() ? "none" : "block") + ";}");
	        style.append(".CHANUKAH {background-color: yellow;display:" + (jc.isChanukah() ? "block" : "none") + ";}");
	        style.append(".TAANIT {background-color: yellow;display:" + (jc.isTaanis() ? "block" : "none") + ";}");
	        style.append(".ROSHKODESH {background-color: yellow;display:" + (jc.isRoshChodesh() ? "block" : "none") + ";}");
	        style.append(".SUCCOT {background-color: yellow;display:" + (jc.isCholHamoedSuccot() ? "block" : "none") + ";}");
	        style.append(".PESACH {background-color: yellow;display:" + (jc.isCholHamoedPesach() ? "block" : "none") + ";}");
	        style.append(".PURIM {background-color: yellow;display:" + (jc.isPurim() ? "block" : "none") + ";}");
	        style.append(".TSHUVA {background-color: yellow;display:" + (tshuva ? "block" : "none") + ";}");
	        style.append(".HOL {display:" + (!tshuva ? "block" : "none") + ";}");
	        style.append(".TAL {display:" + (talumatar ? "block" : "none") + ";}");   
	        style.append(".GESHEM {display:" + (!talumatar ? "block" : "none") + ";}");    
	        style.append(".SUMMER {display:" + (summer ? "block" : "none") + ";}"); 
	        style.append(".WINTER {display:" + (!summer ? "block" : "none") + ";}");
	        style.append(".TAHANUN {display:" + (tahanun ? "block" : "none") + ";}");
	        style.append(".NONTAHANUN {display:" + (!tahanun ? "block" : "none") + ";}");
	        style.append(".MON_THU {display:" + (mon_thu ? "block" : "none") + ";}");
	        style.append(".OMER {display:" + (omer > 0 ? "block" : "none") + ";}");
	        style.append("#OMER_" + omer + "{display:block;}");
	        style.append(".MAN {display:" + (man ? "block" : "none") + ";}");
	        style.append(".WOMAN {display:" + (!man ? "block" : "none") + ";}");
	        style.append(".ZIMUN {display:" + (isZimun() ? "block" : "none") + ";}");
	        style.append(".DAGAN {display:" + (isDagan() ? "block" : "none") + ";}");
	        style.append(".YAIN {display:" + (isYain() ? "block" : "none") + ";}");
	        style.append(".PEROT {display:" + (isPerot() ? "block" : "none") + ";}");
	        style.append(".MOADIM  {display:none;}");	
	        style.append("</style></head><body dir=RTL leftmargin=10 topmargin=10>"); 
	        
	        
	        
	        InputStream is = getAssets().open(amida + ".txt");
			int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        style.append(new String(buffer));
	        style.append("</body></html>");
	        return style.toString();
		} catch (Exception e) {
			return null;
		}
	}	
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private boolean isKadish() {		
		return settings.getBoolean("WithMinyan", false);
	}
	
	private boolean isInIsrael() {		
		return settings.getBoolean("InIsrael", false);
	}
	
	private boolean isMan() {		
		return settings.getBoolean("isIsh", true);
	}
	
	private boolean isZimun() {		
		return settings.getBoolean("isZimun", false);
	}
	
	private boolean isDagan() {		
		return settings.getBoolean("isDagan", true);
	}
	
	private boolean isPerot() {		
		return settings.getBoolean("isPerot", false);
	}
	
	private boolean isYain() {		
		return settings.getBoolean("isYain", false);
	}
}