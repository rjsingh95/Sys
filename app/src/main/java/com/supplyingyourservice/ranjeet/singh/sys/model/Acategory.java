package com.supplyingyourservice.ranjeet.singh.sys.model;

public class Acategory{
	private String c_t;
	private String t_c;
	private String category;
	private String type;
	private  String types;
	private String categorys;
	Acategory(){}

	public Acategory(String c_t, String t_c, String category, String type, String types, String categorys) {
		this.c_t = c_t;
		this.t_c = t_c;
		this.category = category;
		this.type = type;
		this.types = types;
		this.categorys = categorys;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getC_t() {
		return c_t;
	}

	public void setC_t(String c_t) {
		this.c_t = c_t;
	}

	public String getT_c() {
		return t_c;
	}

	public void setT_c(String t_c) {
		this.t_c = t_c;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
