package org.evreyatlanta.models;

public class NameValueModel {
	public String name;
	public String value;
	public Boolean selected;
	public Object info;
	
	public NameValueModel(){
		this.selected = false;
	}
	
	public NameValueModel(String name) {
		this(name, null);
	}
	
	public NameValueModel(String name, String value) {
		this(name, value, false);
	}
	
	public NameValueModel(String name, String value, Boolean selected) {
		this.name = name;
		this.value = value;
		this.selected = selected;
	}
}
