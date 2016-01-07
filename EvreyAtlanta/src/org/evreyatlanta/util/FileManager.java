package org.evreyatlanta.util;

import java.io.File;

import android.os.Environment;

public class FileManager {
	
	private static File _dir;	
	
	private FileManager() {
		
	}
	
	public static FileManager init() {		
		FileManager fm = new FileManager();
		if (_dir == null) {
			fm.reset();
		}
		return fm;
	}
		
	public File getDir() {
		return _dir;		
	}
	
	public File goTo(String path) {
		_dir = new File(_dir, path);
		_dir.mkdirs();
		return _dir;
	}
	
	public String[] getFiles() {
		return _dir.list();
	}
	
	public boolean isRoot() {
		return  _dir.getPath().endsWith("Torah Audio");
	}
	
	public boolean isDirectory(String file) {
		return  new File(_dir.getPath() + "/" + file).isDirectory();
	}
	
	public File getParent() {
		_dir = new File(_dir.getParent());
		return _dir;
	}
	
	public void reset() {
		_dir  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Torah Audio");
		_dir.mkdirs();
	}
	
}
