package com.farm.doc.server;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;
import com.farm.doc.domain.Typedemo;
import java.util.List;

public abstract interface TypedemoServiceInter
{
  public abstract Typedemo insertTypedemoEntity(Typedemo paramTypedemo, LoginUser paramLoginUser);
  
  public abstract Typedemo editTypedemoEntity(Typedemo paramTypedemo, LoginUser paramLoginUser);
  
  public abstract void deleteTypedemoEntity(String paramString, LoginUser paramLoginUser);
  
  public abstract Typedemo getTypedemoEntity(String paramString);
  
  public abstract DataQuery createTypedemoSimpleQuery(DataQuery paramDataQuery);
  
  public abstract List<Typedemo> getDemoList(String paramString);
}


/* Location:           D:\wcp\wcp340\WEB-INF\lib\wcp-doc-3.2.3.jar
 * Qualified Name:     com.farm.doc.server.TypedemoServiceInter
 * JD-Core Version:    0.7.0.1
 */