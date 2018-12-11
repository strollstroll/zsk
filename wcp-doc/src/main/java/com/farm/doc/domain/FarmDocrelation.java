package com.farm.doc.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity(name="FarmDocrelation")
@Table(name="farm_doc_relation")
public class FarmDocrelation
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GenericGenerator(name="systemUUID", strategy="uuid")
  @GeneratedValue(generator="systemUUID")
  @Column(name="ID", length=32, insertable=true, updatable=true, nullable=false)
  private String id;
  @Column(name="PSTATE", length=2, nullable=false)
  private String pstate;
  @Column(name="PCONTENT", length=128)
  private String pcontent;
  @Column(name="NAME", length=128)
  private String name;
  @Column(name="DOCID1", length=32, nullable=false)
  private String docid1;
  @Column(name="DOCID2", length=32, nullable=false)
  private String docid2;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getPstate()
  {
    return this.pstate;
  }
  
  public void setPstate(String pstate)
  {
    this.pstate = pstate;
  }
  
  public String getPcontent()
  {
    return this.pcontent;
  }
  
  public void setPcontent(String pcontent)
  {
    this.pcontent = pcontent;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getDocid1()
  {
    return this.docid1;
  }
  
  public void setDocid1(String docid1)
  {
    this.docid1 = docid1;
  }
  
  public String getDocid2()
  {
    return this.docid2;
  }
  
  public void setDocid2(String docid2)
  {
    this.docid2 = docid2;
  }
}

