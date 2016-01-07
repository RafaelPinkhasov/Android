package org.evreyatlanta.mishna;

import java.io.InputStream;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.evreyatlanta.mishna.MainActivity;
import org.evreyatlanta.mishna.R;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	int book = 0;
	int language = 0;
	int chapter = 0;
	int mishna = 0;
	boolean eng = false;
	boolean rus = true;
	String defaultLanguage;
	TextView txtTitle;
	Dialog dialogFind;
	Dialog dialogSetting;
	Spinner ddlBooks;
	EditText txtChapter;
	EditText txtMishna;
	TextView txtEnPasuk;
	TextView txtHePasuk;
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		try {
			loadPreferences();
			loadLanguage();			
			initActivity();
			initFindDialog();
			initSettingsDialog();			
			
			if (book == 0 || chapter == 0 || mishna == 0) {
				dialogFind.show();
			} else {
				showPasuk();
			}
		} catch (Exception e) {}
	}
	
	protected void initActivity() {
		setContentView(R.layout.activity_book);		
		
		txtTitle = (TextView)findViewById(R.id.txtTitle);
		
		ImageButton btnNext = (ImageButton)findViewById(R.id.btnNext);
		ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack);
		ImageButton btnDialog = (ImageButton)findViewById(R.id.btnDialog);	
		ImageButton btnSettings = (ImageButton)findViewById(R.id.btnSettings);	
		
		final ToggleButton btnEnglish = (ToggleButton)findViewById(R.id.btnEng);
		final ToggleButton btnRussian = (ToggleButton)findViewById(R.id.btnRus);
		btnEnglish.setChecked(eng);
		btnRussian.setChecked(rus);
		
		
		btnBack.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				onBack();
			}
		});
		btnNext.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				onNext();
			}
		});
		btnDialog.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				dialogFind.show();
			}
		});		
		btnSettings.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				dialogSetting.show();
			}
		});
		btnEnglish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	eng = isChecked;
		    	if (eng)  { 
		    		rus = false; 
		    		btnRussian.setChecked(false);
		    	} 
		    	showPasuk();
		    }
		});
		btnRussian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	rus = isChecked;
		    	if (rus) { 
		    		eng = false;
		    		btnEnglish.setChecked(false);
		    	} 
		    	showPasuk();
		    }
		});
	}
	
	protected void initFindDialog() {
		dialogFind = new Dialog(MainActivity.this);
		dialogFind.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogFind.setContentView(R.layout.dialog_find);
		ddlBooks = (Spinner)dialogFind.findViewById(R.id.ddlBooks);
		txtChapter = (EditText)dialogFind.findViewById(R.id.txtChapter);
		txtMishna = (EditText)dialogFind.findViewById(R.id.txtMishna);
		
		Button btnFind = (Button)dialogFind.findViewById(R.id.btnFind);
		btnFind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					book = ddlBooks.getSelectedItemPosition() + 1;				
					chapter = Integer.parseInt(txtChapter.getText().toString());
					mishna = Integer.parseInt(txtMishna.getText().toString());				
					dialogFind.dismiss();
					showPasuk();
				} catch (Exception e){}
			}
		});
		dialogFind.setCanceledOnTouchOutside(false);
	}
	
	protected void initSettingsDialog() {
		dialogSetting = new Dialog(MainActivity.this);
		dialogSetting.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogSetting.setContentView(R.layout.dialog_language);
		final RadioButton engDefault = (RadioButton)dialogSetting.findViewById(R.id.engDefault);
		final RadioButton rusDefault = (RadioButton)dialogSetting.findViewById(R.id.rusDefault);
		final RadioButton hebDefault = (RadioButton)dialogSetting.findViewById(R.id.hebDefault);		
		
		if (defaultLanguage.equals("en")) engDefault.setChecked(true);
		if (defaultLanguage.equals("ru")) rusDefault.setChecked(true);
		if (defaultLanguage.equals("iw")) hebDefault.setChecked(true);
		
		Button btnSet = (Button)dialogSetting.findViewById(R.id.btnSet);
		btnSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					
					if (engDefault.isChecked()) defaultLanguage = "en";
					if (rusDefault.isChecked()) defaultLanguage = "ru";
					if (hebDefault.isChecked()) defaultLanguage = "iw";
					 
					dialogSetting.dismiss();
					
					loadLanguage();
					initSettingsDialog();
					initFindDialog();
					showPasuk();
				} catch (Exception e) {}
			}
		});
		dialogSetting.setCanceledOnTouchOutside(false);
	}
	
	protected void loadLanguage() {
		Locale locale = new Locale(defaultLanguage); 
	    Locale.setDefault(locale);
	    Configuration config = new Configuration();
	    config.locale = locale;
	    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
	}
	
	protected void showPasuk() {
		txtEnPasuk = (TextView)findViewById(R.id.txtEnPasuk);
		txtHePasuk = (TextView)findViewById(R.id.txtHePasuk); 
	
		appendPasuk("mishna_" + book + "_heb.xml", txtHePasuk);
		if (rus) appendPasuk("mishna_" + book + "_rus.xml", txtEnPasuk);
		if (eng) appendPasuk("mishna_" + book + "_eng.xml", txtEnPasuk);
		if (rus == false && eng == false) 
			txtEnPasuk.setVisibility(View.GONE);
		else
			txtEnPasuk.setVisibility(View.VISIBLE);
		
		Resources res = getResources();
		String[] books = res.getStringArray(R.array.books_array);
		
		txtTitle.setText(books[book - 1] + " " + chapter + ":" + mishna);
		txtMishna.setText(mishna + "");
		txtChapter.setText(chapter + "");
		ddlBooks.setSelection(book -1);
		
		savePreferences();
	}

	protected void loadPreferences() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		book = settings.getInt("book", 0);
		chapter = settings.getInt("chapter", 0);
		mishna = settings.getInt("mishna", 0);
		eng = settings.getBoolean("eng", true);
		rus = settings.getBoolean("rus", true);
		defaultLanguage = settings.getString("defaultLanguage", "en");
	}
	
	protected void savePreferences() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("book", book);
		editor.putInt("chapter", chapter);
		editor.putInt("mishna", mishna);
		editor.putBoolean("eng", eng);
		editor.putBoolean("rus", rus);
		editor.putString("defaultLanguage", defaultLanguage);		
		editor.commit();
	}
	
	protected void appendPasuk(String xmlFile, TextView pasuk) {
		try {
			Document dom = getXml(xmlFile);
			XPathFactory xpathFactory = XPathFactory.newInstance();
		    XPath xPath = xpathFactory.newXPath();		    
		   
		    Node node = (Node) xPath.evaluate("/book/chapter[@id='" + chapter + "']/mishna[@id='" + mishna + "']/text()", dom, XPathConstants.NODE);
		    if (node != null) {		    	
		    	
		    	String[] htmlArray = node.getNodeValue().trim().split("\"");
		    	
		    	for (int i = 0; i < htmlArray.length; i++) {
		    		if (i % 2 == 0 && htmlArray.length -1 > i) {
		    			htmlArray[i] = htmlArray[i] + "<font color=\"#9A0C0C\">\"";
		    		} else if (i % 2 != 0 ) {
		    			htmlArray[i] = htmlArray[i] + "\"</font>";
		    		} 			    		
		    	}
		    	
		    	String html = TextUtils.join("", htmlArray);
		    	htmlArray = html.split("\\(");
		    	html = "";
		    	for (int i = 0; i < htmlArray.length - 1; i++) {
		    		html = html + htmlArray[i] + "<small><font color=\"#f39c12\">(";
		    	}
		    	html = html + htmlArray[htmlArray.length - 1];
		    	
		    	htmlArray = html.split("\\)");
		    	html = "";
		    	for (int i = 0; i < htmlArray.length - 1; i++) {
		    		html = html + htmlArray[i] + ")</font></small>";
		    	}
		    	html = html + htmlArray[htmlArray.length - 1];	
		    	if (xmlFile.indexOf("_heb") > -1) {
		    		html = html.replace(".", ".<br/>");
		    	}
		    	
		    	Log.i("html",html );
		    	Spanned sp = Html.fromHtml(html);
		    	pasuk.setText(sp);
		    }
		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}
	}
	
	private Document getXml(String xmlFile) {
		try {
			AssetManager assetManager = getAssets();
			InputStream is = assetManager.open(xmlFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			return db.parse(is); 
		} catch (Exception e) {
			return null;
		}
	}
	
	private void onBack() {
		if (mishna == 1) {
			if (chapter > 1) {
				chapter -= 1;
				mishna = getMishna();
			} else if (chapter == 1) {
				if (book > 1) {
					book -= 1;
					chapter = getChapter();
					mishna = getMishna();
				} 
			}	
		}
		else {
			mishna -= 1;
		}
		showPasuk();
	}
	
	private void onNext() {
		try {
			Integer max = getMishna();
			if (max == mishna) {
				if (chapter == getChapter()) {
					if (book < 63) {
						book += 1;
						chapter = 1;
						mishna = 1;
					}
				} else {
					chapter += 1;
					mishna = 1;
				}
			} else {
				mishna += 1;
			}
			showPasuk();
		} catch (Exception e) {}
	}
	
	private int getMishna() {
		try {
			Document dom = getXml("mishna_" + book + "_heb.xml");
			XPathFactory xpathFactory = XPathFactory.newInstance();
		    XPath xPath = xpathFactory.newXPath();		    
		   
		    double mishna = (double) xPath.evaluate("count(/book/chapter[@id='" + chapter + "']/mishna)", dom, XPathConstants.NUMBER);
		    return (int)Math.round(mishna);
		} catch (Exception e) { 
			return 0;
		} 	
	}
	
	private int getChapter() {
		try {
			Document dom = getXml("mishna_" + book + "_heb.xml");
			XPathFactory xpathFactory = XPathFactory.newInstance();
		    XPath xPath = xpathFactory.newXPath();		    
		   
		    double chapter = (double) xPath.evaluate("count(/book/chapter)", dom, XPathConstants.NUMBER);
		    
		    return (int)Math.round(chapter);
		} catch (Exception e) { 
			return 0;
		} 	
	}
}

