package org.evreyatlanta.humash;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.evreyatlanta.humash.R;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;

public class MainActivity extends Activity {

	int book = 0;
	int language = 0;
	int chapter = 0;
	int pasuk = 0;
	boolean eng = false;
	boolean rus = true;
	boolean heb = true;
	String defaultLanguage;
	LinearLayout llContainer;
	TextView txtTitle;
	Dialog dialogFind;
	Dialog dialogSetting;
	Spinner ddlBooks;
	EditText txtChapter;
	EditText txtPasuk;
	public static final String PREFS_NAME = "MyPrefsFile";
		
	HashMap<String, Integer> hpsukim = new HashMap<String, Integer>();
	SparseIntArray hprakim = new SparseIntArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			loadPreferences();
			loadLanguage();
			initMap();
			initActivity();
			initFindDialog();
			initSettingsDialog();
			
			
			if (book == 0 || chapter == 0 || pasuk == 0) {
				dialogFind.show();
			} else {
				showPasuk();
			}
		} catch (Exception e) {}
	}
	
	protected void initMap() {
		hprakim.put(10, 22);hprakim.put(11, 25);hprakim.put(12, 66);hprakim.put(13, 52);hprakim.put(14, 48);hprakim.put(15, 14);hprakim.put(16, 4);hprakim.put(17, 9);hprakim.put(18, 1);hprakim.put(19, 4);hprakim.put(1, 50);hprakim.put(20, 7);hprakim.put(21, 3);hprakim.put(22, 3);hprakim.put(23, 3);hprakim.put(24, 2);hprakim.put(25, 14);hprakim.put(26, 3);hprakim.put(27, 150);hprakim.put(28, 31);hprakim.put(29, 42);hprakim.put(2, 40);hprakim.put(30, 8);hprakim.put(31, 4);hprakim.put(32, 5);hprakim.put(33, 12);hprakim.put(34, 10);hprakim.put(35, 12);hprakim.put(36, 10);hprakim.put(37, 13);hprakim.put(38, 29);hprakim.put(39, 36);hprakim.put(3, 27);hprakim.put(4, 36);hprakim.put(5, 34);hprakim.put(6, 24);hprakim.put(7, 21);hprakim.put(8, 31);hprakim.put(9, 24);
		hpsukim.put("10:1", 53);hpsukim.put("10:2", 46);hpsukim.put("10:3", 28);hpsukim.put("10:4", 20);hpsukim.put("10:5", 32);hpsukim.put("10:6", 38);hpsukim.put("10:7", 51);hpsukim.put("10:8", 66);hpsukim.put("10:9", 28);hpsukim.put("10:10", 29);hpsukim.put("10:11", 43);hpsukim.put("10:12", 33);hpsukim.put("10:13", 34);hpsukim.put("10:14", 31);hpsukim.put("10:15", 34);hpsukim.put("10:16", 34);hpsukim.put("10:17", 24);hpsukim.put("10:18", 46);hpsukim.put("10:19", 21);hpsukim.put("10:20", 43);hpsukim.put("10:21", 29);hpsukim.put("10:22", 54);hpsukim.put("11:1", 18);hpsukim.put("11:2", 25);hpsukim.put("11:3", 27);hpsukim.put("11:4", 44);hpsukim.put("11:5", 27);hpsukim.put("11:6", 33);hpsukim.put("11:7", 20);hpsukim.put("11:8", 29);hpsukim.put("11:9", 37);hpsukim.put("11:10", 36);hpsukim.put("11:11", 20);hpsukim.put("11:12", 22);hpsukim.put("11:13", 25);hpsukim.put("11:14", 29);hpsukim.put("11:15", 38);hpsukim.put("11:16", 20);hpsukim.put("11:17", 41);hpsukim.put("11:18", 37);hpsukim.put("11:19", 37);hpsukim.put("11:20", 21);hpsukim.put("11:21", 26);hpsukim.put("11:22", 20);hpsukim.put("11:23", 37);hpsukim.put("11:24", 20);hpsukim.put("11:25", 30);hpsukim.put("12:1", 31);hpsukim.put("12:2", 22);hpsukim.put("12:3", 26);hpsukim.put("12:4", 6);hpsukim.put("12:5", 30);hpsukim.put("12:6", 13);hpsukim.put("12:7", 25);hpsukim.put("12:8", 23);hpsukim.put("12:9", 20);hpsukim.put("12:10", 34);hpsukim.put("12:11", 16);hpsukim.put("12:12", 6);hpsukim.put("12:13", 22);hpsukim.put("12:14", 32);hpsukim.put("12:15", 9);hpsukim.put("12:16", 14);hpsukim.put("12:17", 14);hpsukim.put("12:18", 7);hpsukim.put("12:19", 25);hpsukim.put("12:20", 6);hpsukim.put("12:21", 17);hpsukim.put("12:22", 25);hpsukim.put("12:23", 18);hpsukim.put("12:24", 23);hpsukim.put("12:25", 12);hpsukim.put("12:26", 21);hpsukim.put("12:27", 13);hpsukim.put("12:28", 29);hpsukim.put("12:29", 24);hpsukim.put("12:30", 33);hpsukim.put("12:31", 9);hpsukim.put("12:32", 20);hpsukim.put("12:33", 24);hpsukim.put("12:34", 17);hpsukim.put("12:35", 10);hpsukim.put("12:36", 22);hpsukim.put("12:37", 38);hpsukim.put("12:38", 22);hpsukim.put("12:39", 8);hpsukim.put("12:40", 31);hpsukim.put("12:41", 29);hpsukim.put("12:42", 25);hpsukim.put("12:43", 28);hpsukim.put("12:44", 28);hpsukim.put("12:45", 25);hpsukim.put("12:46", 13);hpsukim.put("12:47", 15);hpsukim.put("12:48", 22);hpsukim.put("12:49", 26);hpsukim.put("12:50", 11);hpsukim.put("12:51", 23);hpsukim.put("12:52", 15);hpsukim.put("12:53", 12);hpsukim.put("12:54", 17);hpsukim.put("12:55", 13);hpsukim.put("12:56", 12);hpsukim.put("12:57", 21);hpsukim.put("12:58", 14);hpsukim.put("12:59", 21);hpsukim.put("12:60", 22);hpsukim.put("12:61", 11);hpsukim.put("12:62", 12);hpsukim.put("12:63", 19);hpsukim.put("12:64", 11);hpsukim.put("12:65", 25);hpsukim.put("12:66", 24);hpsukim.put("13:1", 19);hpsukim.put("13:2", 37);hpsukim.put("13:3", 25);hpsukim.put("13:4", 31);hpsukim.put("13:5", 31);hpsukim.put("13:6", 30);hpsukim.put("13:7", 34);hpsukim.put("13:8", 23);hpsukim.put("13:9", 25);hpsukim.put("13:10", 25);hpsukim.put("13:11", 23);hpsukim.put("13:12", 17);hpsukim.put("13:13", 27);hpsukim.put("13:14", 22);hpsukim.put("13:15", 21);hpsukim.put("13:16", 21);hpsukim.put("13:17", 27);hpsukim.put("13:18", 23);hpsukim.put("13:19", 15);hpsukim.put("13:20", 18);hpsukim.put("13:21", 14);hpsukim.put("13:22", 30);hpsukim.put("13:23", 40);hpsukim.put("13:24", 10);hpsukim.put("13:25", 38);hpsukim.put("13:26", 24);hpsukim.put("13:27", 22);hpsukim.put("13:28", 17);hpsukim.put("13:29", 32);hpsukim.put("13:30", 25);hpsukim.put("13:31", 39);hpsukim.put("13:32", 44);hpsukim.put("13:33", 26);hpsukim.put("13:34", 22);hpsukim.put("13:35", 19);hpsukim.put("13:36", 32);hpsukim.put("13:37", 21);hpsukim.put("13:38", 28);hpsukim.put("13:39", 18);hpsukim.put("13:40", 16);hpsukim.put("13:41", 18);hpsukim.put("13:42", 22);hpsukim.put("13:43", 13);hpsukim.put("13:44", 30);hpsukim.put("13:45", 5);hpsukim.put("13:46", 28);hpsukim.put("13:47", 7);hpsukim.put("13:48", 47);hpsukim.put("13:49", 39);hpsukim.put("13:50", 46);hpsukim.put("13:51", 64);hpsukim.put("13:52", 34);hpsukim.put("14:1", 28);hpsukim.put("14:2", 10);hpsukim.put("14:3", 27);hpsukim.put("14:4", 17);hpsukim.put("14:5", 17);hpsukim.put("14:6", 14);hpsukim.put("14:7", 27);hpsukim.put("14:8", 18);hpsukim.put("14:9", 11);hpsukim.put("14:10", 22);hpsukim.put("14:11", 25);hpsukim.put("14:12", 28);hpsukim.put("14:13", 23);hpsukim.put("14:14", 23);hpsukim.put("14:15", 8);hpsukim.put("14:16", 63);hpsukim.put("14:17", 24);hpsukim.put("14:18", 32);hpsukim.put("14:19", 14);hpsukim.put("14:20", 44);hpsukim.put("14:21", 37);hpsukim.put("14:22", 31);hpsukim.put("14:23", 49);hpsukim.put("14:24", 27);hpsukim.put("14:25", 17);hpsukim.put("14:26", 21);hpsukim.put("14:27", 36);hpsukim.put("14:28", 26);hpsukim.put("14:29", 21);hpsukim.put("14:30", 26);hpsukim.put("14:31", 18);hpsukim.put("14:32", 32);hpsukim.put("14:33", 33);hpsukim.put("14:34", 31);hpsukim.put("14:35", 15);hpsukim.put("14:36", 38);hpsukim.put("14:37", 28);hpsukim.put("14:38", 23);hpsukim.put("14:39", 29);hpsukim.put("14:40", 49);hpsukim.put("14:41", 26);hpsukim.put("14:42", 20);hpsukim.put("14:43", 27);hpsukim.put("14:44", 31);hpsukim.put("14:45", 25);hpsukim.put("14:46", 24);hpsukim.put("14:47", 23);hpsukim.put("14:48", 35);hpsukim.put("15:1", 9);hpsukim.put("15:2", 25);hpsukim.put("15:3", 5);hpsukim.put("15:4", 19);hpsukim.put("15:5", 15);hpsukim.put("15:6", 11);hpsukim.put("15:7", 16);hpsukim.put("15:8", 14);hpsukim.put("15:9", 17);hpsukim.put("15:10", 15);hpsukim.put("15:11", 11);hpsukim.put("15:12", 15);hpsukim.put("15:13", 15);hpsukim.put("15:14", 10);hpsukim.put("16:1", 20);hpsukim.put("16:2", 27);hpsukim.put("16:3", 5);hpsukim.put("16:4", 21);hpsukim.put("17:1", 15);hpsukim.put("17:2", 16);hpsukim.put("17:3", 15);hpsukim.put("17:4", 13);hpsukim.put("17:5", 27);hpsukim.put("17:6", 14);hpsukim.put("17:7", 17);hpsukim.put("17:8", 14);hpsukim.put("17:9", 15);hpsukim.put("18:1", 21);hpsukim.put("19:1", 16);hpsukim.put("19:2", 11);hpsukim.put("19:3", 10);hpsukim.put("19:4", 11);hpsukim.put("1:1", 31);hpsukim.put("1:2", 25);hpsukim.put("1:3", 24);hpsukim.put("1:4", 26);hpsukim.put("1:5", 32);hpsukim.put("1:6", 22);hpsukim.put("1:7", 24);hpsukim.put("1:8", 22);hpsukim.put("1:9", 29);hpsukim.put("1:10", 32);hpsukim.put("1:11", 32);hpsukim.put("1:12", 20);hpsukim.put("1:13", 18);hpsukim.put("1:14", 24);hpsukim.put("1:15", 21);hpsukim.put("1:16", 16);hpsukim.put("1:17", 27);hpsukim.put("1:18", 33);hpsukim.put("1:19", 38);hpsukim.put("1:20", 18);hpsukim.put("1:21", 34);hpsukim.put("1:22", 24);hpsukim.put("1:23", 20);hpsukim.put("1:24", 67);hpsukim.put("1:25", 34);hpsukim.put("1:26", 35);hpsukim.put("1:27", 46);hpsukim.put("1:28", 22);hpsukim.put("1:29", 35);hpsukim.put("1:30", 43);hpsukim.put("1:31", 54);hpsukim.put("1:32", 33);hpsukim.put("1:33", 20);hpsukim.put("1:34", 31);hpsukim.put("1:35", 29);hpsukim.put("1:36", 43);hpsukim.put("1:37", 36);hpsukim.put("1:38", 30);hpsukim.put("1:39", 23);hpsukim.put("1:40", 23);hpsukim.put("1:41", 57);hpsukim.put("1:42", 38);hpsukim.put("1:43", 34);hpsukim.put("1:44", 34);hpsukim.put("1:45", 28);hpsukim.put("1:46", 34);hpsukim.put("1:47", 31);hpsukim.put("1:48", 22);hpsukim.put("1:49", 33);hpsukim.put("1:50", 26);hpsukim.put("20:1", 16);hpsukim.put("20:2", 13);hpsukim.put("20:3", 12);hpsukim.put("20:4", 14);hpsukim.put("20:5", 14);hpsukim.put("20:6", 16);hpsukim.put("20:7", 20);hpsukim.put("21:1", 14);hpsukim.put("21:2", 14);hpsukim.put("21:3", 19);hpsukim.put("22:1", 17);hpsukim.put("22:2", 20);hpsukim.put("22:3", 19);hpsukim.put("23:1", 18);hpsukim.put("23:2", 15);hpsukim.put("23:3", 20);hpsukim.put("24:1", 15);hpsukim.put("24:2", 23);hpsukim.put("25:1", 17);hpsukim.put("25:2", 17);hpsukim.put("25:3", 10);hpsukim.put("25:4", 14);hpsukim.put("25:5", 11);hpsukim.put("25:6", 15);hpsukim.put("25:7", 14);hpsukim.put("25:8", 23);hpsukim.put("25:9", 17);hpsukim.put("25:10", 12);hpsukim.put("25:11", 17);hpsukim.put("25:12", 14);hpsukim.put("25:13", 9);hpsukim.put("25:14", 21);hpsukim.put("26:1", 14);hpsukim.put("26:2", 17);hpsukim.put("26:3", 24);hpsukim.put("27:1", 6);hpsukim.put("27:2", 12);hpsukim.put("27:3", 9);hpsukim.put("27:4", 9);hpsukim.put("27:5", 13);hpsukim.put("27:6", 11);hpsukim.put("27:7", 18);hpsukim.put("27:8", 10);hpsukim.put("27:9", 21);hpsukim.put("27:10", 18);hpsukim.put("27:11", 7);hpsukim.put("27:12", 9);hpsukim.put("27:13", 6);hpsukim.put("27:14", 7);hpsukim.put("27:15", 5);hpsukim.put("27:16", 11);hpsukim.put("27:17", 15);hpsukim.put("27:18", 51);hpsukim.put("27:19", 15);hpsukim.put("27:20", 10);hpsukim.put("27:21", 14);hpsukim.put("27:22", 32);hpsukim.put("27:23", 6);hpsukim.put("27:24", 10);hpsukim.put("27:25", 22);hpsukim.put("27:26", 12);hpsukim.put("27:27", 14);hpsukim.put("27:28", 9);hpsukim.put("27:29", 11);hpsukim.put("27:30", 13);hpsukim.put("27:31", 25);hpsukim.put("27:32", 11);hpsukim.put("27:33", 22);hpsukim.put("27:34", 23);hpsukim.put("27:35", 28);hpsukim.put("27:36", 13);hpsukim.put("27:37", 40);hpsukim.put("27:38", 23);hpsukim.put("27:39", 14);hpsukim.put("27:40", 18);hpsukim.put("27:41", 14);hpsukim.put("27:42", 12);hpsukim.put("27:43", 5);hpsukim.put("27:44", 27);hpsukim.put("27:45", 18);hpsukim.put("27:46", 12);hpsukim.put("27:47", 10);hpsukim.put("27:48", 15);hpsukim.put("27:49", 21);hpsukim.put("27:50", 23);hpsukim.put("27:51", 21);hpsukim.put("27:52", 11);hpsukim.put("27:53", 7);hpsukim.put("27:54", 9);hpsukim.put("27:55", 24);hpsukim.put("27:56", 14);hpsukim.put("27:57", 12);hpsukim.put("27:58", 12);hpsukim.put("27:59", 18);hpsukim.put("27:60", 14);hpsukim.put("27:61", 9);hpsukim.put("27:62", 13);hpsukim.put("27:63", 12);hpsukim.put("27:64", 11);hpsukim.put("27:65", 14);hpsukim.put("27:66", 20);hpsukim.put("27:67", 8);hpsukim.put("27:68", 36);hpsukim.put("27:69", 37);hpsukim.put("27:70", 6);hpsukim.put("27:71", 24);hpsukim.put("27:72", 20);hpsukim.put("27:73", 28);hpsukim.put("27:74", 23);hpsukim.put("27:75", 11);hpsukim.put("27:76", 13);hpsukim.put("27:77", 21);hpsukim.put("27:78", 72);hpsukim.put("27:79", 13);hpsukim.put("27:80", 20);hpsukim.put("27:81", 17);hpsukim.put("27:82", 8);hpsukim.put("27:83", 19);hpsukim.put("27:84", 13);hpsukim.put("27:85", 14);hpsukim.put("27:86", 17);hpsukim.put("27:87", 7);hpsukim.put("27:88", 19);hpsukim.put("27:89", 53);hpsukim.put("27:90", 17);hpsukim.put("27:91", 16);hpsukim.put("27:92", 16);hpsukim.put("27:93", 5);hpsukim.put("27:94", 23);hpsukim.put("27:95", 11);hpsukim.put("27:96", 13);hpsukim.put("27:97", 12);hpsukim.put("27:98", 9);hpsukim.put("27:99", 9);hpsukim.put("27:100", 5);hpsukim.put("27:101", 8);hpsukim.put("27:102", 29);hpsukim.put("27:103", 22);hpsukim.put("27:104", 35);hpsukim.put("27:105", 45);hpsukim.put("27:106", 48);hpsukim.put("27:107", 43);hpsukim.put("27:108", 14);hpsukim.put("27:109", 31);hpsukim.put("27:110", 7);hpsukim.put("27:111", 10);hpsukim.put("27:112", 10);hpsukim.put("27:113", 9);hpsukim.put("27:114", 8);hpsukim.put("27:115", 18);hpsukim.put("27:116", 19);hpsukim.put("27:117", 2);hpsukim.put("27:118", 29);hpsukim.put("27:119", 176);hpsukim.put("27:120", 7);hpsukim.put("27:121", 8);hpsukim.put("27:122", 9);hpsukim.put("27:123", 4);hpsukim.put("27:124", 8);hpsukim.put("27:125", 5);hpsukim.put("27:126", 6);hpsukim.put("27:127", 5);hpsukim.put("27:128", 6);hpsukim.put("27:129", 8);hpsukim.put("27:130", 8);hpsukim.put("27:131", 3);hpsukim.put("27:132", 18);hpsukim.put("27:133", 3);hpsukim.put("27:134", 3);hpsukim.put("27:135", 21);hpsukim.put("27:136", 26);hpsukim.put("27:137", 9);hpsukim.put("27:138", 8);hpsukim.put("27:139", 24);hpsukim.put("27:140", 14);hpsukim.put("27:141", 10);hpsukim.put("27:142", 8);hpsukim.put("27:143", 12);hpsukim.put("27:144", 15);hpsukim.put("27:145", 21);hpsukim.put("27:146", 10);hpsukim.put("27:147", 20);hpsukim.put("27:148", 14);hpsukim.put("27:149", 9);hpsukim.put("27:150", 6);hpsukim.put("28:1", 33);hpsukim.put("28:2", 22);hpsukim.put("28:3", 35);hpsukim.put("28:4", 27);hpsukim.put("28:5", 23);hpsukim.put("28:6", 35);hpsukim.put("28:7", 27);hpsukim.put("28:8", 36);hpsukim.put("28:9", 18);hpsukim.put("28:10", 32);hpsukim.put("28:11", 31);hpsukim.put("28:12", 28);hpsukim.put("28:13", 25);hpsukim.put("28:14", 35);hpsukim.put("28:15", 33);hpsukim.put("28:16", 33);hpsukim.put("28:17", 28);hpsukim.put("28:18", 24);hpsukim.put("28:19", 29);hpsukim.put("28:20", 30);hpsukim.put("28:21", 31);hpsukim.put("28:22", 29);hpsukim.put("28:23", 35);hpsukim.put("28:24", 34);hpsukim.put("28:25", 28);hpsukim.put("28:26", 28);hpsukim.put("28:27", 27);hpsukim.put("28:28", 28);hpsukim.put("28:29", 27);hpsukim.put("28:30", 33);hpsukim.put("28:31", 31);hpsukim.put("29:1", 22);hpsukim.put("29:2", 14);hpsukim.put("29:3", 25);hpsukim.put("29:4", 21);hpsukim.put("29:5", 27);hpsukim.put("29:6", 30);hpsukim.put("29:7", 21);hpsukim.put("29:8", 22);hpsukim.put("29:9", 35);hpsukim.put("29:10", 22);hpsukim.put("29:11", 20);hpsukim.put("29:12", 25);hpsukim.put("29:13", 28);hpsukim.put("29:14", 22);hpsukim.put("29:15", 35);hpsukim.put("29:16", 22);hpsukim.put("29:17", 16);hpsukim.put("29:18", 21);hpsukim.put("29:19", 29);hpsukim.put("29:20", 29);hpsukim.put("29:21", 34);hpsukim.put("29:22", 30);hpsukim.put("29:23", 17);hpsukim.put("29:24", 25);hpsukim.put("29:25", 6);hpsukim.put("29:26", 14);hpsukim.put("29:27", 23);hpsukim.put("29:28", 28);hpsukim.put("29:29", 25);hpsukim.put("29:30", 31);hpsukim.put("29:31", 40);hpsukim.put("29:32", 22);hpsukim.put("29:33", 33);hpsukim.put("29:34", 37);hpsukim.put("29:35", 16);hpsukim.put("29:36", 33);hpsukim.put("29:37", 24);hpsukim.put("29:38", 41);hpsukim.put("29:39", 30);hpsukim.put("29:40", 32);hpsukim.put("29:41", 26);hpsukim.put("29:42", 17);hpsukim.put("2:1", 22);hpsukim.put("2:2", 25);hpsukim.put("2:3", 22);hpsukim.put("2:4", 31);hpsukim.put("2:5", 23);hpsukim.put("2:6", 30);hpsukim.put("2:7", 29);hpsukim.put("2:8", 28);hpsukim.put("2:9", 35);hpsukim.put("2:10", 29);hpsukim.put("2:11", 10);hpsukim.put("2:12", 51);hpsukim.put("2:13", 22);hpsukim.put("2:14", 31);hpsukim.put("2:15", 27);hpsukim.put("2:16", 36);hpsukim.put("2:17", 16);hpsukim.put("2:18", 27);hpsukim.put("2:19", 25);hpsukim.put("2:20", 23);hpsukim.put("2:21", 37);hpsukim.put("2:22", 30);hpsukim.put("2:23", 33);hpsukim.put("2:24", 18);hpsukim.put("2:25", 40);hpsukim.put("2:26", 37);hpsukim.put("2:27", 21);hpsukim.put("2:28", 43);hpsukim.put("2:29", 46);hpsukim.put("2:30", 38);hpsukim.put("2:31", 18);hpsukim.put("2:32", 35);hpsukim.put("2:33", 23);hpsukim.put("2:34", 35);hpsukim.put("2:35", 35);hpsukim.put("2:36", 38);hpsukim.put("2:37", 29);hpsukim.put("2:38", 31);hpsukim.put("2:39", 43);hpsukim.put("2:40", 38);hpsukim.put("30:1", 17);hpsukim.put("30:2", 17);hpsukim.put("30:3", 11);hpsukim.put("30:4", 16);hpsukim.put("30:5", 16);hpsukim.put("30:6", 12);hpsukim.put("30:7", 14);hpsukim.put("30:8", 14);hpsukim.put("31:1", 22);hpsukim.put("31:2", 23);hpsukim.put("31:3", 18);hpsukim.put("31:4", 22);hpsukim.put("32:1", 22);hpsukim.put("32:2", 22);hpsukim.put("32:3", 66);hpsukim.put("32:4", 22);hpsukim.put("32:5", 22);hpsukim.put("33:1", 18);hpsukim.put("33:2", 26);hpsukim.put("33:3", 22);hpsukim.put("33:4", 17);hpsukim.put("33:5", 19);hpsukim.put("33:6", 12);hpsukim.put("33:7", 29);hpsukim.put("33:8", 17);hpsukim.put("33:9", 18);hpsukim.put("33:10", 20);hpsukim.put("33:11", 10);hpsukim.put("33:12", 14);hpsukim.put("34:1", 22);hpsukim.put("34:2", 23);hpsukim.put("34:3", 15);hpsukim.put("34:4", 17);hpsukim.put("34:5", 14);hpsukim.put("34:6", 14);hpsukim.put("34:7", 10);hpsukim.put("34:8", 17);hpsukim.put("34:9", 32);hpsukim.put("34:10", 3);hpsukim.put("35:1", 21);hpsukim.put("35:2", 49);hpsukim.put("35:3", 33);hpsukim.put("35:4", 34);hpsukim.put("35:5", 30);hpsukim.put("35:6", 29);hpsukim.put("35:7", 28);hpsukim.put("35:8", 27);hpsukim.put("35:9", 27);hpsukim.put("35:10", 21);hpsukim.put("35:11", 45);hpsukim.put("35:12", 13);hpsukim.put("36:1", 11);hpsukim.put("36:2", 70);hpsukim.put("36:3", 13);hpsukim.put("36:4", 24);hpsukim.put("36:5", 17);hpsukim.put("36:6", 22);hpsukim.put("36:7", 28);hpsukim.put("36:8", 36);hpsukim.put("36:9", 15);hpsukim.put("36:10", 44);hpsukim.put("37:1", 11);hpsukim.put("37:2", 20);hpsukim.put("37:3", 38);hpsukim.put("37:4", 17);hpsukim.put("37:5", 19);hpsukim.put("37:6", 19);hpsukim.put("37:7", 73);hpsukim.put("37:8", 18);hpsukim.put("37:9", 37);hpsukim.put("37:10", 40);hpsukim.put("37:11", 36);hpsukim.put("37:12", 47);hpsukim.put("37:13", 31);hpsukim.put("38:1", 54);hpsukim.put("38:2", 55);hpsukim.put("38:3", 24);hpsukim.put("38:4", 43);hpsukim.put("38:5", 41);hpsukim.put("38:6", 66);hpsukim.put("38:7", 40);hpsukim.put("38:8", 40);hpsukim.put("38:9", 44);hpsukim.put("38:10", 14);hpsukim.put("38:11", 47);hpsukim.put("38:12", 40);hpsukim.put("38:13", 14);hpsukim.put("38:14", 17);hpsukim.put("38:15", 29);hpsukim.put("38:16", 43);hpsukim.put("38:17", 27);hpsukim.put("38:18", 17);hpsukim.put("38:19", 19);hpsukim.put("38:20", 8);hpsukim.put("38:21", 30);hpsukim.put("38:22", 19);hpsukim.put("38:23", 32);hpsukim.put("38:24", 31);hpsukim.put("38:25", 31);hpsukim.put("38:26", 32);hpsukim.put("38:27", 34);hpsukim.put("38:28", 21);hpsukim.put("38:29", 30);hpsukim.put("39:1", 18);hpsukim.put("39:2", 17);hpsukim.put("39:3", 17);hpsukim.put("39:4", 22);hpsukim.put("39:5", 14);hpsukim.put("39:6", 42);hpsukim.put("39:7", 22);hpsukim.put("39:8", 18);hpsukim.put("39:9", 31);hpsukim.put("39:10", 19);hpsukim.put("39:11", 23);hpsukim.put("39:12", 16);hpsukim.put("39:13", 23);hpsukim.put("39:14", 14);hpsukim.put("39:15", 19);hpsukim.put("39:16", 14);hpsukim.put("39:17", 19);hpsukim.put("39:18", 34);hpsukim.put("39:19", 11);hpsukim.put("39:20", 37);hpsukim.put("39:21", 20);hpsukim.put("39:22", 12);hpsukim.put("39:23", 21);hpsukim.put("39:24", 27);hpsukim.put("39:25", 28);hpsukim.put("39:26", 23);hpsukim.put("39:27", 9);hpsukim.put("39:28", 27);hpsukim.put("39:29", 36);hpsukim.put("39:30", 27);hpsukim.put("39:31", 21);hpsukim.put("39:32", 33);hpsukim.put("39:33", 25);hpsukim.put("39:34", 33);hpsukim.put("39:35", 27);hpsukim.put("39:36", 23);hpsukim.put("3:1", 17);hpsukim.put("3:2", 16);hpsukim.put("3:3", 17);hpsukim.put("3:4", 35);hpsukim.put("3:5", 26);hpsukim.put("3:6", 23);hpsukim.put("3:7", 38);hpsukim.put("3:8", 36);hpsukim.put("3:9", 24);hpsukim.put("3:10", 20);hpsukim.put("3:11", 47);hpsukim.put("3:12", 8);hpsukim.put("3:13", 59);hpsukim.put("3:14", 57);hpsukim.put("3:15", 33);hpsukim.put("3:16", 34);hpsukim.put("3:17", 16);hpsukim.put("3:18", 30);hpsukim.put("3:19", 37);hpsukim.put("3:20", 27);hpsukim.put("3:21", 24);hpsukim.put("3:22", 33);hpsukim.put("3:23", 44);hpsukim.put("3:24", 23);hpsukim.put("3:25", 55);hpsukim.put("3:26", 46);hpsukim.put("3:27", 34);hpsukim.put("4:1", 54);hpsukim.put("4:2", 34);hpsukim.put("4:3", 51);hpsukim.put("4:4", 49);hpsukim.put("4:5", 31);hpsukim.put("4:6", 27);hpsukim.put("4:7", 89);hpsukim.put("4:8", 26);hpsukim.put("4:9", 23);hpsukim.put("4:10", 36);hpsukim.put("4:11", 35);hpsukim.put("4:12", 16);hpsukim.put("4:13", 33);hpsukim.put("4:14", 45);hpsukim.put("4:15", 41);hpsukim.put("4:16", 35);hpsukim.put("4:17", 28);hpsukim.put("4:18", 32);hpsukim.put("4:19", 22);hpsukim.put("4:20", 29);hpsukim.put("4:21", 35);hpsukim.put("4:22", 41);hpsukim.put("4:23", 30);hpsukim.put("4:24", 25);hpsukim.put("4:25", 18);hpsukim.put("4:26", 65);hpsukim.put("4:27", 23);hpsukim.put("4:28", 31);hpsukim.put("4:29", 39);hpsukim.put("4:30", 17);hpsukim.put("4:31", 54);hpsukim.put("4:32", 42);hpsukim.put("4:33", 56);hpsukim.put("4:34", 29);hpsukim.put("4:35", 34);hpsukim.put("4:36", 13);hpsukim.put("5:1", 46);hpsukim.put("5:2", 37);hpsukim.put("5:3", 29);hpsukim.put("5:4", 49);hpsukim.put("5:5", 30);hpsukim.put("5:6", 25);hpsukim.put("5:7", 26);hpsukim.put("5:8", 20);hpsukim.put("5:9", 29);hpsukim.put("5:10", 22);hpsukim.put("5:11", 32);hpsukim.put("5:12", 31);hpsukim.put("5:13", 19);hpsukim.put("5:14", 29);hpsukim.put("5:15", 23);hpsukim.put("5:16", 22);hpsukim.put("5:17", 20);hpsukim.put("5:18", 22);hpsukim.put("5:19", 21);hpsukim.put("5:20", 20);hpsukim.put("5:21", 23);hpsukim.put("5:22", 29);hpsukim.put("5:23", 26);hpsukim.put("5:24", 22);hpsukim.put("5:25", 19);hpsukim.put("5:26", 19);hpsukim.put("5:27", 26);hpsukim.put("5:28", 69);hpsukim.put("5:29", 28);hpsukim.put("5:30", 20);hpsukim.put("5:31", 30);hpsukim.put("5:32", 52);hpsukim.put("5:33", 29);hpsukim.put("5:34", 12);hpsukim.put("6:1", 18);hpsukim.put("6:2", 24);hpsukim.put("6:3", 17);hpsukim.put("6:4", 24);hpsukim.put("6:5", 15);hpsukim.put("6:6", 27);hpsukim.put("6:7", 26);hpsukim.put("6:8", 35);hpsukim.put("6:9", 27);hpsukim.put("6:10", 43);hpsukim.put("6:11", 23);hpsukim.put("6:12", 24);hpsukim.put("6:13", 33);hpsukim.put("6:14", 15);hpsukim.put("6:15", 63);hpsukim.put("6:16", 10);hpsukim.put("6:17", 18);hpsukim.put("6:18", 28);hpsukim.put("6:19", 51);hpsukim.put("6:20", 9);hpsukim.put("6:21", 43);hpsukim.put("6:22", 34);hpsukim.put("6:23", 16);hpsukim.put("6:24", 33);hpsukim.put("7:1", 36);hpsukim.put("7:2", 23);hpsukim.put("7:3", 31);hpsukim.put("7:4", 24);hpsukim.put("7:5", 31);hpsukim.put("7:6", 40);hpsukim.put("7:7", 25);hpsukim.put("7:8", 35);hpsukim.put("7:9", 57);hpsukim.put("7:10", 18);hpsukim.put("7:11", 40);hpsukim.put("7:12", 15);hpsukim.put("7:13", 25);hpsukim.put("7:14", 20);hpsukim.put("7:15", 20);hpsukim.put("7:16", 31);hpsukim.put("7:17", 13);hpsukim.put("7:18", 31);hpsukim.put("7:19", 30);hpsukim.put("7:20", 48);hpsukim.put("7:21", 25);hpsukim.put("8:1", 28);hpsukim.put("8:2", 36);hpsukim.put("8:3", 21);hpsukim.put("8:4", 22);hpsukim.put("8:5", 12);hpsukim.put("8:6", 21);hpsukim.put("8:7", 17);hpsukim.put("8:8", 22);hpsukim.put("8:9", 27);hpsukim.put("8:10", 27);hpsukim.put("8:11", 15);hpsukim.put("8:12", 25);hpsukim.put("8:13", 23);hpsukim.put("8:14", 52);hpsukim.put("8:15", 35);hpsukim.put("8:16", 23);hpsukim.put("8:17", 58);hpsukim.put("8:18", 30);hpsukim.put("8:19", 24);hpsukim.put("8:20", 42);hpsukim.put("8:21", 16);hpsukim.put("8:22", 23);hpsukim.put("8:23", 28);hpsukim.put("8:24", 23);hpsukim.put("8:25", 44);hpsukim.put("8:26", 25);hpsukim.put("8:27", 12);hpsukim.put("8:28", 25);hpsukim.put("8:29", 11);hpsukim.put("8:30", 31);hpsukim.put("8:31", 13);hpsukim.put("9:1", 27);hpsukim.put("9:2", 32);hpsukim.put("9:3", 39);hpsukim.put("9:4", 12);hpsukim.put("9:5", 25);hpsukim.put("9:6", 23);hpsukim.put("9:7", 29);hpsukim.put("9:8", 18);hpsukim.put("9:9", 13);hpsukim.put("9:10", 19);hpsukim.put("9:11", 27);hpsukim.put("9:12", 31);hpsukim.put("9:13", 39);hpsukim.put("9:14", 33);hpsukim.put("9:15", 37);hpsukim.put("9:16", 23);hpsukim.put("9:17", 29);hpsukim.put("9:18", 32);hpsukim.put("9:19", 44);hpsukim.put("9:20", 26);hpsukim.put("9:21", 22);hpsukim.put("9:22", 51);hpsukim.put("9:23", 39);hpsukim.put("9:24", 25);
	}
	
	
	protected void initActivity() {
		setContentView(R.layout.activity_book);		
		
		txtTitle = (TextView)findViewById(R.id.txtTitle);
		llContainer = (LinearLayout)findViewById(R.id.llContainer);
		
		ImageButton btnNext = (ImageButton)findViewById(R.id.btnNext);
		ImageButton btnBack = (ImageButton)findViewById(R.id.btnBack);
		ImageButton btnDialog = (ImageButton)findViewById(R.id.btnDialog);	
		ImageButton btnSettings = (ImageButton)findViewById(R.id.btnSettings);	
		
		final ToggleButton btnEnglish = (ToggleButton)findViewById(R.id.btnEng);
		final ToggleButton btnRussian = (ToggleButton)findViewById(R.id.btnRus);
		final ToggleButton btnHebrew = (ToggleButton)findViewById(R.id.btnHeb);
		btnEnglish.setChecked(eng);
		btnRussian.setChecked(rus);
		btnHebrew.setChecked(heb);
		
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
		    	showPasuk();
		    }
		});
		btnRussian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	rus = isChecked;
		    	showPasuk();
		    }
		});
		btnHebrew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	heb = isChecked;
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
		txtPasuk = (EditText)dialogFind.findViewById(R.id.txtPasuk);
		
		Button btnFind = (Button)dialogFind.findViewById(R.id.btnFind);
		btnFind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					book = ddlBooks.getSelectedItemPosition() + 1;				
					chapter = Integer.parseInt(txtChapter.getText().toString());
					pasuk = Integer.parseInt(txtPasuk.getText().toString());				
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
		rashiText = false;
		needSeparator = 0;
		llContainer.removeAllViews();
		if (heb) appendPasuk("book_" + book + "_heb.xml");
		if (rus) appendPasuk("book_" + book + "_rus.xml");
		if (eng) appendPasuk("book_" + book + "_eng.xml");		
		needSeparator = 0;
		if (heb) appendRashi("rashi_" + book + "_heb.xml");
		if (rus) appendRashi("rashi_" + book + "_rus.xml");
		if (eng) appendRashi("rashi_" + book + "_eng.xml");			
		
		
		Resources res = getResources();
		String[] books = res.getStringArray(R.array.books_array);
		
		txtTitle.setText(books[book - 1] + " " + chapter + ":" + pasuk);
		txtPasuk.setText(pasuk + "");
		txtChapter.setText(chapter + "");
		ddlBooks.setSelection(book -1);
		
		savePreferences();
	}

	protected void loadPreferences() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		book = settings.getInt("book", 0);
		chapter = settings.getInt("chapter", 0);
		pasuk = settings.getInt("pasuk", 0);
		eng = settings.getBoolean("eng", true);
		rus = settings.getBoolean("rus", true);
		heb = settings.getBoolean("heb", true);
		defaultLanguage = settings.getString("defaultLanguage", "en");
	}
	
	protected void savePreferences() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("book", book);
		editor.putInt("chapter", chapter);
		editor.putInt("pasuk", pasuk);
		editor.putBoolean("eng", eng);
		editor.putBoolean("heb", heb);
		editor.putBoolean("rus", rus);
		editor.putString("defaultLanguage", defaultLanguage);
		editor.commit();
	}
	
	private int needSeparator = 0;
	protected void appendPasuk(String xmlFile) {
		try {
			Document dom = getXml(xmlFile);
			XPathFactory xpathFactory = XPathFactory.newInstance();
		    XPath xPath = xpathFactory.newXPath();		    
		   
		    Node node = (Node) xPath.evaluate("/book/chapter[@id='" + chapter + "']/pasuk[@id='" + pasuk + "']/text()", dom, XPathConstants.NODE);
		    if (node != null) {
		    	
		    	appendSeparator();
		    	TextView pasuk = new TextView(this);		    	
		    	pasuk.setTextSize(28);
		    	pasuk.setPadding(10, 5, 10, 15);
		    	pasuk.setText(node.getNodeValue());
				llContainer.addView(pasuk);	
		    }
		} catch (Exception e) {}
	}
	
	protected void appendSeparator() {
		if (needSeparator > 0) {
			TextView separator = new TextView(this);		    	
			separator.setTextSize(1);
			separator.setPadding(10, 1, 10, 1);
			separator.setBackgroundColor(getResources().getColor(R.color.red));
			llContainer.addView(separator);	
    	} else {
    		needSeparator = 1;
    	}
		
	}
	
	boolean rashiText = false;
	@SuppressWarnings("deprecation")
	protected void appendRashi(String xmlFile) {
		try {
			Document dom = getXml(xmlFile);
			XPathFactory xpathFactory = XPathFactory.newInstance();
		    XPath xPath = xpathFactory.newXPath();
		   
		    NodeList nodes = (NodeList) xPath.evaluate("/book/chapter[@id='" + chapter + "']/pasuk[@id='" + pasuk + "']/text", dom, XPathConstants.NODESET);
		    int length = nodes.getLength();
		    if (rashiText == false && length > 0) {
		    	TextView rashiTitle = new TextView(this);
		    	rashiTitle.setTextSize(26);
		    	rashiTitle.setBackgroundColor(getResources().getColor(R.color.orange));
		    	rashiTitle.setTextColor(Color.BLACK);
		    	rashiTitle.setTypeface(Typeface.DEFAULT_BOLD);
		    	rashiTitle.setGravity(Gravity.CENTER);
		    	rashiTitle.setPadding(5, 5, 5, 5);
		    	rashiTitle.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		    	rashiTitle.setText(getString(R.string.rashi));
		    	llContainer.addView(rashiTitle);	
		    	rashiText = true;
		    }
		    
		    if (length > 0) {
		    	appendSeparator();
		    }
		    for (int i = 0; i < length; i++) {
		    	Node node = nodes.item(i);
		    	TextView pasukTitle = new TextView(this);
		    	pasukTitle.setTextSize(22);
		    	pasukTitle.setTextColor(getResources().getColor(R.color.red));
		    	pasukTitle.setTypeface(Typeface.DEFAULT_BOLD);
		    	pasukTitle.setPadding(10, 2, 10, 2);
		    	pasukTitle.setText(node.getAttributes().getNamedItem("title").getNodeValue());
		    	llContainer.addView(pasukTitle);	
		    	TextView pasukText = new TextView(this);
		    	pasukText.setTextSize(22);
		    	pasukText.setPadding(10, 2, 10, 2);
		    	pasukText.setText(node.getTextContent());
		    	llContainer.addView(pasukText);	
		    }
		    
		   
		   
		} catch (Exception e) {
			
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
		if (pasuk == 1) {
			if (chapter > 1) {
				chapter -= 1;
				pasuk = hpsukim.get(book + ":" + chapter);
			} else if (chapter == 1) {
				if (book > 1) {
					book -= 1;
					chapter = hprakim.get(book);
					pasuk = hpsukim.get(book + ":" + chapter);
				} 
			}	
		}
		else {
			pasuk -= 1;
		}
		showPasuk();
	}
	
	private void onNext() {
		try {
			Integer max = hpsukim.get(book + ":" + chapter);
			if (max == pasuk) {
				if (chapter == hprakim.get(book)) {
					if (book < 39) {
						book += 1;
						chapter = 1;
						pasuk = 1;
					}
				} else {
					chapter += 1;
					pasuk = 1;
				}
			} else {
				pasuk += 1;
			}
			showPasuk();
		} catch (Exception e) {}
	}
}
