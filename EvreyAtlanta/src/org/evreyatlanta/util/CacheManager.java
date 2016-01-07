package org.evreyatlanta.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.evreyatlanta.models.ClassModel;

import android.content.Context;
import android.util.Log;

public final class CacheManager {
	public static void write(Context context, String key, ArrayList<ClassModel> classes) {
		try {
			FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(classes);
			oos.close();
			fos.close();
		} catch (Exception e) {
			Log.e("writeCache", e.getMessage());
		}
	}
 
	@SuppressWarnings("unchecked")
	public static ArrayList<ClassModel> read(Context context, String key) {
		try {
			FileInputStream fis = context.openFileInput(key);
			ObjectInputStream ois = new ObjectInputStream(fis);
			return (ArrayList<ClassModel>)ois.readObject();
	   	} catch (Exception e) {
	   		Log.e("readCache", e.getMessage());
	   		return new ArrayList<ClassModel>();
	   	}
	}
}
