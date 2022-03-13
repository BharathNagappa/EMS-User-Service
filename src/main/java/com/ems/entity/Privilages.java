package com.ems.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name="ROLE_PRIVILAGES")
public class Privilages {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
Long id;
String moduleName;
String path;
boolean readable;
boolean writeable;

@ManyToMany(cascade = CascadeType.ALL)
List<UserRoles> roles;

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getModuleName() {
	return moduleName;
}
public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public boolean isReadable() {
	return readable;
}
public void setReadable(boolean readable) {
	this.readable = readable;
}
public boolean isWriteable() {
	return writeable;
}
public void setWriteable(boolean writeable) {
	this.writeable = writeable;
}



}
