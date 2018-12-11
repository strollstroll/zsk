package com.farm.doc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//每个人访问了那个文档的次数 

@Entity(name = "FarmDocBrowse")
@Table(name = "farm_doc_browse")
public class FarmDocBrowse implements java.io.Serializable{
	private static final long serialVersionUID=1L;
    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
    private String id;
    @Column(name = "CUSERNAME", length = 32)
    private String cusername;
    @Column(name = "DOCNAME", length = 32)
    private String docname;
    @Column(name = "BROWSENUM", length = 11)
    private Integer browsenum;
    @Column(name="USERID", length=32)
    private String userid;
    @Column(name="DOCID", length=32)
    private String docid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCusername() {
		return cusername;
	}
	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public Integer getBrowsenum() {
		return browsenum;
	}
	public void setBrowsenum(Integer browsenum) {
		this.browsenum = browsenum;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
    
	@Override
	public String toString() {
		return "FarmDocBrowse [id=" + id + ", cusername=" + cusername + ", docname=" + docname + ", browsenum="
				+ browsenum + ", userid=" + userid + ", docid=" + docid + "]";
	}
    
}
