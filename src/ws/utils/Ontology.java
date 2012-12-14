package ws.utils;

import java.util.Date;

public class Ontology {

	int c_hlevel;
	String c_fullname;
	String c_name;
	char c_synonym_cd;
	String c_visualattributes;
	int c_totalnum;
	String c_basecode;
	String c_metadataxml;
	String c_facttablecolumn;
	String c_tablename;
	String c_columnname;
	String c_columndatatype;
	String c_operator;
	String c_dimcode;
	String c_comment;
	String c_tooltip;
	Date update_date;
	Date download_date;
	Date import_date;
	String sourcesystem_cd;
	String valuetype_cd;
	
	@Override
	public String toString() {
		return "Ontology [c_hlevel=" + c_hlevel + ", c_fullname=" + c_fullname
				+ ", c_name=" + c_name + ", c_synonym_cd=" + c_synonym_cd
				+ ", c_visualattributes=" + c_visualattributes
				+ ", c_totalnum=" + c_totalnum + ", c_basecode=" + c_basecode
				+ ", c_metadataxml=" + c_metadataxml + ", c_facttablecolumn="
				+ c_facttablecolumn + ", c_tablename=" + c_tablename
				+ ", c_columnname=" + c_columnname + ", c_columndatatype="
				+ c_columndatatype + ", c_operator=" + c_operator
				+ ", c_dimcode=" + c_dimcode + ", c_comment=" + c_comment
				+ ", c_tooltip=" + c_tooltip + ", update_date=" + update_date
				+ ", download_date=" + download_date + ", import_date="
				+ import_date + ", sourcesystem_cd=" + sourcesystem_cd
				+ ", valuetype_cd=" + valuetype_cd + "]";
	}
	public int getC_hlevel() {
		return c_hlevel;
	}
	public void setC_hlevel(int c_hlevel) {
		this.c_hlevel = c_hlevel;
	}
	public String getC_fullname() {
		return c_fullname;
	}
	public void setC_fullname(String c_fullname) {
		this.c_fullname = c_fullname;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public char getC_synonym_cd() {
		return c_synonym_cd;
	}
	public void setC_synonym_cd(char c_synonym_cd) {
		this.c_synonym_cd = c_synonym_cd;
	}
	public String getC_visualattributes() {
		return c_visualattributes;
	}
	public void setC_visualattributes(String c_visualattributes) {
		this.c_visualattributes = c_visualattributes;
	}
	public int getC_totalnum() {
		return c_totalnum;
	}
	public void setC_totalnum(int c_totalnum) {
		this.c_totalnum = c_totalnum;
	}
	public String getC_basecode() {
		return c_basecode;
	}
	public void setC_basecode(String c_basecode) {
		this.c_basecode = c_basecode;
	}
	public String getC_metadataxml() {
		return c_metadataxml;
	}
	public void setC_metadataxml(String c_metadataxml) {
		this.c_metadataxml = c_metadataxml;
	}
	public String getC_facttablecolumn() {
		return c_facttablecolumn;
	}
	public void setC_facttablecolumn(String c_facttablecolumn) {
		this.c_facttablecolumn = c_facttablecolumn;
	}
	public String getC_tablename() {
		return c_tablename;
	}
	public void setC_tablename(String c_tablename) {
		this.c_tablename = c_tablename;
	}
	public String getC_columnname() {
		return c_columnname;
	}
	public void setC_columnname(String c_columnname) {
		this.c_columnname = c_columnname;
	}
	public String getC_columndatatype() {
		return c_columndatatype;
	}
	public void setC_columndatatype(String c_columndatatype) {
		this.c_columndatatype = c_columndatatype;
	}
	public String getC_operator() {
		return c_operator;
	}
	public void setC_operator(String c_operator) {
		this.c_operator = c_operator;
	}
	public String getC_dimcode() {
		return c_dimcode;
	}
	public void setC_dimcode(String c_dimcode) {
		this.c_dimcode = c_dimcode;
	}
	public String getC_comment() {
		return c_comment;
	}
	public void setC_comment(String c_comment) {
		this.c_comment = c_comment;
	}
	public String getC_tooltip() {
		return c_tooltip;
	}
	public void setC_tooltip(String c_tooltip) {
		this.c_tooltip = c_tooltip;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public Date getDownload_date() {
		return download_date;
	}
	public void setDownload_date(Date download_date) {
		this.download_date = download_date;
	}
	public Date getImport_date() {
		return import_date;
	}
	public void setImport_date(Date import_date) {
		this.import_date = import_date;
	}
	public String getSourcesystem_cd() {
		return sourcesystem_cd;
	}
	public void setSourcesystem_cd(String sourcesystem_cd) {
		this.sourcesystem_cd = sourcesystem_cd;
	}
	public String getValuetype_cd() {
		return valuetype_cd;
	}
	public void setValuetype_cd(String valuetype_cd) {
		this.valuetype_cd = valuetype_cd;
	}
		
}
