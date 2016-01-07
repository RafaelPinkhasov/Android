package org.evreyatlanta.models;

import java.util.ArrayList;
import java.util.List;

public class TeacherModel {
	public String id;
	public String name;
	public List<MediaTypeModel> mediaTypes;
	public List<ClassModel> classes;
	
	public TeacherModel() {
		mediaTypes = new ArrayList<MediaTypeModel>();
		classes = new ArrayList<ClassModel>();
	}
}
