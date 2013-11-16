package com.rampgreen.caretakermobile.model;

import java.io.Serializable;

@SuppressWarnings("serial") 
public class Recived implements Serializable{
	public	String userid;
	public	String name;
	public	String ispending;
	public	String isignore;

	public String getUserid(){ return this.userid; }
	 public String getName(){ return this.name; }	

	 public String getIspending(){ return this.ispending; }	

	 public String getIsignore() { return this.isignore; }	

	 public Recived(String name, String ispending, String isignore,String userid) {
	  super();
	  this.name = name;
	  this.ispending = ispending;
	  this.isignore = isignore;
	  this.userid = userid;
	 }

	 public Recived() {
		  super();		  
		 }	
	 
}