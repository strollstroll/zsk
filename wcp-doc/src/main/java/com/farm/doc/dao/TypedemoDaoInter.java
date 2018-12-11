package com.farm.doc.dao;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.Typedemo;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

public abstract interface TypedemoDaoInter
{
  public abstract void deleteEntity(Typedemo paramTypedemo);
  
  public abstract Typedemo getEntity(String paramString);
  
  public abstract Typedemo insertEntity(Typedemo paramTypedemo);
  
  public abstract int getAllListNum();
  
  public abstract void editEntity(Typedemo paramTypedemo);
  
  public abstract Session getSession();
  
  public abstract DataResult runSqlQuery(DataQuery paramDataQuery);
  
  public abstract void deleteEntitys(List<DBRule> paramList);
  
  public abstract List<Typedemo> selectEntitys(List<DBRule> paramList);
  
  public abstract void updataEntitys(Map<String, Object> paramMap, List<DBRule> paramList);
  
  public abstract int countEntitys(List<DBRule> paramList);
}


/* Location:           D:\wcp\wcp340\WEB-INF\lib\wcp-doc-3.2.3.jar
 * Qualified Name:     com.farm.doc.dao.TypedemoDaoInter
 * JD-Core Version:    0.7.0.1
 */