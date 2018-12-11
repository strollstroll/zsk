package com.farm.doc.dao;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.FarmDocrelation;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

public abstract interface FarmDocRelationDaoInter
{
  public abstract void deleteEntity(FarmDocrelation paramFarmDocrelation);
  
  public abstract FarmDocrelation getEntity(String paramString);
  
  public abstract FarmDocrelation insertEntity(FarmDocrelation paramFarmDocrelation);
  
  public abstract int getAllListNum();
  
  public abstract void editEntity(FarmDocrelation paramFarmDocrelation);
  
  public abstract Session getSession();
  
  public abstract DataResult runSqlQuery(DataQuery paramDataQuery);
  
  public abstract void deleteEntitys(List<DBRule> paramList);
  
  public abstract List<FarmDocrelation> selectEntitys(List<DBRule> paramList);
  
  public abstract void updataEntitys(Map<String, Object> paramMap, List<DBRule> paramList);
  
  public abstract List<FarmDocrelation> getDocRelationDoc(String paramString);
}


/* Location:           D:\wcp\wcp340\WEB-INF\lib\wcp-doc-3.2.3.jar
 * Qualified Name:     com.farm.doc.dao.FarmDocRelationDaoInter
 * JD-Core Version:    0.7.0.1
 */