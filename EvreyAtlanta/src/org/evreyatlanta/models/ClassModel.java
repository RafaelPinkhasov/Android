package org.evreyatlanta.models;

import java.io.Serializable;

public class ClassModel implements Serializable {
	private static final long serialVersionUID = -280991935122053764L;
	public String id;
    public String name;
    public String notes;
    public String url;
    public String publishedDate;
    public String mediaType;
    public String teacher;
    public int position;
}
