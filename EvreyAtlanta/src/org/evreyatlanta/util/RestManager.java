package org.evreyatlanta.util;

import java.util.ArrayList;

import org.evreyatlanta.models.*;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;



public class RestManager {
	
	private Context mContext;
	
	public RestManager(Context context) {
		mContext = context;
	}
	
	public void GetTeachers(OnResponseListener<ArrayList<TeacherModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<TeacherModel>>() {
			public ArrayList<TeacherModel> execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/teachers_4.aspx");
				return toTeachers(json);
			}
		}, response, null);		
	} 
	
	public void GetSubjectTypes(OnResponseListener<ArrayList<SubjectTypeModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<SubjectTypeModel>>() {
			public ArrayList<SubjectTypeModel> execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/subjecttypes_4.aspx");
				return toSubjectTypes(json);
			}
		}, response, null);		
	} 
	
	public void GetMediaTypes(final String teacherId, final String subjectTypeId, OnResponseListener<ArrayList<NameValueModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<NameValueModel>>() {
			public ArrayList<NameValueModel> execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/mediatypes_3.aspx?teacher_id=" + teacherId + "&subject_type_id=" + subjectTypeId);
				return toNameValue(json);
			}
		}, response, null);
	} 
	
	public void GetSubjects(final String teacherId, OnResponseListener<ArrayList<NameValueModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<NameValueModel>>() {
			public ArrayList<NameValueModel> execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/subjects_3.aspx?teacher_id=" + teacherId);
				return toNameValue(json);
			}
		}, response, null);
	} 
	
	public void GetClasses(final String teacherId, final String mediaTypeId, final String linkId, OnResponseListener<ArrayList<ClassModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<ClassModel>>() {
			public ArrayList<ClassModel> execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/classes_3.aspx?teacher_id=" + teacherId + "&media_type_id=" + mediaTypeId + "&link_id=" + linkId);
				return toClasses(json);
			}
		}, response, null);
	} 
	
	public void GetClass(final String mediaId, OnResponseListener<NextPrevModel> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<NextPrevModel>() {
			public NextPrevModel execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/class_3.aspx?media_id=" + mediaId);
				NextPrevModel model = toClass(json);
				SqlManager sql= new SqlManager(mContext.getApplicationContext());
				model.position = sql.getHistoryCurrentPosistion(model.id);
				return model;
			}
		}, response, null);
	} 
	
	public void GetParsha(OnResponseListener<ArrayList<TeacherModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<TeacherModel>>() {
			public ArrayList<TeacherModel> execute() {
				String json = NetworkManager.getString("http://www.evreyatlanta.org/rest/parsha_4.aspx");
				return toTeachers2(json);
			}
		}, response, null);
	} 
	
	public void GetHistory(OnResponseListener<ArrayList<ClassModel>> response) {
		BackgroundWorker.Execute(mContext, new OnRequestListener<ArrayList<ClassModel>>() {
			public ArrayList<ClassModel> execute() {
				SqlManager sql = new SqlManager(mContext.getApplicationContext());
				ArrayList<ClassModel> list = sql.getHistoryList();
				sql.close();
				return list;
			}
		}, response, null);
	} 
	
	private static ArrayList<ClassModel> toClasses(String json) {
		ArrayList<ClassModel> list = new ArrayList<ClassModel>();
		if (json == null) return list;
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();
			for(int i = 0; i < length; i++) {
				JSONObject jobject = array.getJSONObject(i);
				ClassModel model = new ClassModel();
				toClass(model, jobject);
	     		list.add(model);
			}			
		} catch (Exception e) {
			Log.e("toClasses", e.getMessage());
		}
		
		return list;
	}
	
	private static NextPrevModel toClass(String json) {
		NextPrevModel model = new NextPrevModel();
		if (json == null) return model;
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();
			if (length > 0) {
				JSONObject jobject = array.getJSONObject(0);
				toClass(model, jobject);
				int nextId = jobject.getInt("NextId");
				if (nextId > 0)
					model.nextId = jobject.getString("NextId");
				
				int prevId = jobject.getInt("PrevId");
				if (prevId > 0)
					model.prevId = jobject.getString("PrevId");
			}
	    } catch (Exception e) {
			Log.e("toClass", e.getMessage());
		}
		
		return model;
	}
	
	private static ArrayList<NameValueModel> toNameValue(String json) {
		ArrayList<NameValueModel> list = new ArrayList<NameValueModel>();
		if (json == null) return list;
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();
			for(int i = 0; i < length; i++) {
				JSONObject jobject = array.getJSONObject(i);
				NameValueModel model = new NameValueModel();
				model.name = jobject.getString("name");
				model.value = jobject.getString("id");
				list.add(model);
			}			
		} catch (Exception e) {
			Log.e("toNameValue", e.getMessage());
		}
		
		return list;
	}	
	
	private static void toClass(ClassModel model, JSONObject jobject) {
		try {
			model.id = jobject.getString("Id");
	        model.name = jobject.getString("Name").replace("<br/>", " ");
	        model.notes = jobject.getString("Notes");
	        model.url = jobject.getString("Url");
	        model.publishedDate = jobject.getString("PublishedDate");
	        model.mediaType = jobject.getString("MediaType");
	        model.teacher = jobject.getString("Teacher");
	        if (model.url.startsWith("__media"))
	        	model.url = "http://www.evreyatlanta.org/" + model.url;
	        if (model.url.endsWith(".flv"))
	        	model.url = model.url.replace(".flv", ".mp3");
		} catch (Exception e) {
			Log.e("toClass", e.getMessage());
		}
		
	}
	
	private static ArrayList<TeacherModel> toTeachers(String json) {
		ArrayList<TeacherModel> list = new ArrayList<TeacherModel>();
		if (json == null) return list;
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();
			for(int i = 0; i < length; i++) {
				JSONObject jobject = array.getJSONObject(i);
				TeacherModel model = new TeacherModel();
				model.id = jobject.getString("id");
				model.name = jobject.getString("name");
				JSONArray jarray = jobject.getJSONArray("medeaTypes");
				for(int j = 0; j < jarray.length(); j ++) {
					JSONObject subobject = jarray.getJSONObject(j);
					
					MediaTypeModel mediaType = new MediaTypeModel();
					mediaType.id = subobject.getString("media_type_id");
					mediaType.name = subobject.getString("media_type_name");
					model.mediaTypes.add(mediaType);
				}
				list.add(model);
			}			
		} catch (Exception e) {
			Log.e("toNameValue", e.getMessage());
		}
		
		return list;
	}	
	
	private static ArrayList<TeacherModel> toTeachers2(String json) {
		ArrayList<TeacherModel> list = new ArrayList<TeacherModel>();
		if (json == null) return list;
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();
			for(int i = 0; i < length; i++) {
				JSONObject jobject = array.getJSONObject(i);
				TeacherModel model = new TeacherModel();
				model.id = jobject.getString("id");
				model.name = jobject.getString("name");
				JSONArray jarray = jobject.getJSONArray("classes");
				for(int j = 0; j < jarray.length(); j ++) {
					JSONObject subobject = jarray.getJSONObject(j);
					ClassModel classModel = new ClassModel();
					classModel.id = subobject.getString("Id");
					classModel.name = subobject.getString("Name").replace("<br/>", " ");
					classModel.notes = subobject.getString("Notes");
					classModel.url = subobject.getString("Url");
					classModel.publishedDate = subobject.getString("PublishedDate");
					classModel.mediaType = subobject.getString("MediaType");
			        if (classModel.url.startsWith("__media"))
			        	classModel.url = "http://www.evreyatlanta.org/" + classModel.url;
			        if (classModel.url.endsWith(".flv"))
			        	classModel.url = classModel.url.replace(".flv", ".mp3");
			        model.classes.add(classModel);
				}
				list.add(model);
			}			
		} catch (Exception e) {
			Log.e("toNameValue", e.getMessage());
		}
		
		return list;
	}	
	
	private static ArrayList<SubjectTypeModel> toSubjectTypes(String json) {
		ArrayList<SubjectTypeModel> list = new ArrayList<SubjectTypeModel>();
		if (json == null) return list;
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();
			for(int i = 0; i < length; i++) {
				JSONObject jobject = array.getJSONObject(i);
				SubjectTypeModel model = new SubjectTypeModel();
				model.id = jobject.getString("id");
				model.name = jobject.getString("name");
				JSONArray jarray = jobject.getJSONArray("mediaTypes");
				for(int j = 0; j < jarray.length(); j ++) {
					JSONObject subobject = jarray.getJSONObject(j);
					
					MediaTypeModel mediaType = new MediaTypeModel();
					mediaType.id = subobject.getString("media_type_id");
					mediaType.name = subobject.getString("media_type_name");
					model.mediaTypes.add(mediaType);
				}
				list.add(model);
			}			
		} catch (Exception e) {
			Log.e("toNameValue", e.getMessage());
		}
		
		return list;
	}	
}
