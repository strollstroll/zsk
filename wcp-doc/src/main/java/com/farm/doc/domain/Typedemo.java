package com.farm.doc.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity(name="Typedemo")
@Table(name="farm_doctype_demo")
public class Typedemo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GenericGenerator(name="systemUUID", strategy="uuid")
  @GeneratedValue(generator="systemUUID")
  @Column(name="ID", length=32, insertable=true, updatable=true, nullable=false)
  private String id;
  @Column(name="NAME", length=64, nullable=false)
  private String name;
  @Column(name="DEMOTEXT", length=65535, nullable=false)
  private String demotext;
  @Column(name="TYPEID", length=32)
  private String typeid;
  @Column(name="PCONTENT", length=128)
  private String pcontent;
  @Column(name="PSTATE", length=2, nullable=false)
  private String pstate;
  @Column(name="CUSERNAME", length=64, nullable=false)
  private String cusername;
  @Column(name="CTIME", length=16, nullable=false)
  private String ctime;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getDemotext()
  {
    return this.demotext;
  }
  
  public void setDemotext(String demotext)
  {
    this.demotext = demotext;
  }
  
  public String getTypeid()
  {
    return this.typeid;
  }
  
  public void setTypeid(String typeid)
  {
    this.typeid = typeid;
  }
  
  public String getPcontent()
  {
    return this.pcontent;
  }
  
  public void setPcontent(String pcontent)
  {
    this.pcontent = pcontent;
  }
  
  public String getPstate()
  {
    return this.pstate;
  }
  
  public void setPstate(String pstate)
  {
    this.pstate = pstate;
  }
  
  public String getCusername()
  {
    return this.cusername;
  }
  
  public void setCusername(String cusername)
  {
    this.cusername = cusername;
  }
  
  public String getCtime()
  {
    return this.ctime;
  }
  
  public void setCtime(String ctime)
  {
    this.ctime = ctime;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
}


/* Location:           D:\wcp\wcp340\WEB-INF\lib\wcp-doc-3.2.3.jar
 * Qualified Name:     com.farm.doc.domain.Typedemo
 * JD-Core Version:    0.7.0.1
 */