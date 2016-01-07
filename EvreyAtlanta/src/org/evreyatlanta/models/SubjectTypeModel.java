package org.evreyatlanta.models;

import java.util.ArrayList;
import java.util.List;

public class SubjectTypeModel {
	public String id;
	public String name;
	public List<MediaTypeModel> mediaTypes;
	
	public SubjectTypeModel() {
		mediaTypes = new ArrayList<MediaTypeModel>();
	}
}
