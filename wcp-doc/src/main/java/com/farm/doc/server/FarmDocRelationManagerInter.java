package com.farm.doc.server;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;
import com.farm.doc.domain.Doc;
import com.farm.doc.domain.FarmDocrelation;
import java.util.List;

public abstract interface FarmDocRelationManagerInter
{
  public abstract FarmDocrelation insertFarmDocRelationEntity(FarmDocrelation paramFarmDocrelation, LoginUser paramLoginUser);
  
  public abstract FarmDocrelation editFarmDocRelationEntity(FarmDocrelation paramFarmDocrelation, LoginUser paramLoginUser);
  
  public abstract void deleteFarmDocRelationEntity(String paramString, LoginUser paramLoginUser);
  
  public abstract FarmDocrelation getFarmDocRelationEntity(String paramString);
  
  public abstract DataQuery createFarmDocRelationSimpleQuery(DataQuery paramDataQuery);
  
  public abstract List<Doc> getDocRelationDoc(String paramString);
  
  public abstract void deleteDocRelationsByDocid(String paramString, LoginUser paramLoginUser);
}


/* Location:           D:\wcp\wcp340\WEB-INF\lib\wcp-doc-3.2.3.jar
 * Qualified Name:     com.farm.doc.server.FarmDocRelationManagerInter
 * JD-Core Version:    0.7.0.1
 */