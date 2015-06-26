/*
 * Created on Jan 8, 2015
 *
 * $HeadURL$
 * $Date: Jan 8, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.lang.reflect.Field;

/**
 * object to hold the field of a class
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jan 8, 2015 liang.guisheng Exp $
 */
public class ParsedField {

	private int id;

	private String simpleName;

	private String longName;

	private String type;

	private Field field;

	public ParsedField(Field field) {
		setField(field);
		setSimpleName(field.getName());
	}

	public ParsedField() {
	}

	public ParsedField(String simpleName, String longName) {
		setSimpleName(simpleName);
		setLongName(longName);
	}

	public ParsedField(String longName) {
		setLongName(longName);
		parseLongName(longName);
	}

	private void parseLongName(String longName) {

	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
