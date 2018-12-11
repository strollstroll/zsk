package com.farm.doc.server.impl;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
//import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.doc.dao.TypedemoDaoInter;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.domain.Typedemo;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.TypedemoServiceInter;
import com.farm.util.web.FarmFormatUnits;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TypedemoServiceImpl
  implements TypedemoServiceInter
{
  @Resource
  private TypedemoDaoInter typedemoDaoImpl;
  @Resource
  private FarmDocTypeInter farmDocTypeManagerImpl;
  
  @Transactional
  public Typedemo insertTypedemoEntity(Typedemo entity, LoginUser user)
  {
    entity.setCtime(TimeTool.getTimeDate14());
    entity.setCusername(user.getName());
    entity.setPstate("1");
    if ((entity.getTypeid() != null) && (entity.getTypeid().isEmpty())) {
      entity.setTypeid(null);
    }
    return this.typedemoDaoImpl.insertEntity(entity);
  }
  
  @Transactional
  public Typedemo editTypedemoEntity(Typedemo entity, LoginUser user)
  {
    Typedemo entity2 = this.typedemoDaoImpl.getEntity(entity.getId());
    entity2.setName(entity.getName());
    entity2.setDemotext(entity.getDemotext());
    if ((entity.getTypeid() != null) && (entity.getTypeid().isEmpty())) {
      entity2.setTypeid(null);
    } else {
      entity2.setTypeid(entity.getTypeid());
    }
    entity2.setPcontent(entity.getPcontent());
    entity2.setPstate(entity.getPstate());
    entity2.setId(entity.getId());
    this.typedemoDaoImpl.editEntity(entity2);
    return entity2;
  }
  
  @Transactional
  public void deleteTypedemoEntity(String id, LoginUser user)
  {
    this.typedemoDaoImpl.deleteEntity(this.typedemoDaoImpl.getEntity(id));
  }
  
  @Transactional
  public Typedemo getTypedemoEntity(String id)
  {
    if (id == null) {
      return null;
    }
    return this.typedemoDaoImpl.getEntity(id);
  }
  
  @Transactional
  public DataQuery createTypedemoSimpleQuery(DataQuery query)
  {
    DataQuery dbQuery = DataQuery.init(query, "FARM_DOCTYPE_DEMO A LEFT JOIN FARM_DOCTYPE B ON A.TYPEID=B.ID", 
      "A.ID as ID,A.NAME AS NAME,A.DEMOTEXT AS DEMOTEXT,B.NAME AS TYPENAME,A.TYPEID AS TYPEID,A.PCONTENT AS PCONTENT,A.PSTATE AS PSTATE,A.CUSERNAME AS CUSERNAME,A.CTIME AS CTIME");
    return dbQuery;
  }
  
  public TypedemoDaoInter getTypedemoDaoImpl()
  {
    return this.typedemoDaoImpl;
  }
  
  public void setTypedemoDaoImpl(TypedemoDaoInter dao)
  {
    this.typedemoDaoImpl = dao;
  }
  
  @Transactional
  public List<Typedemo> getDemoList(String typeid)
  {
    DataQuerys.wipeVirus(typeid);
    FarmDoctype doctype = this.farmDocTypeManagerImpl.getType(typeid);
    List<String> typeids = FarmFormatUnits.SplitStringByLen(doctype.getTreecode(), 32);
    DataQuery dbQuery = DataQuery.getInstance(1, 
      "A.ID AS ID,A.CTIME AS CTIME,A.CUSERNAME AS CUSERNAME,A.PSTATE AS PSTATE,A.PCONTENT AS PCONTENT,A.NAME AS NAME,A.DEMOTEXT AS DEMOTEXT,A.TYPEID AS TYPEID", 
      "FARM_DOCTYPE_DEMO A LEFT JOIN FARM_DOCTYPE B ON A.TYPEID = B.ID");
    dbQuery.setPagesize(20);
    String ids = null;
    for (String id : typeids) {
      if (ids == null) {
        ids = "'" + id + "'";
      } else {
        ids = ids + ",'" + id + "'";
      }
    }
    dbQuery.addSqlRule("AND A.TYPEID IS NULL OR B.ID IN (" + ids + ")");
    try
    {
      return dbQuery.search().getObjectList(Typedemo.class);
    }
    catch (SQLException e) {}
    return new ArrayList<Typedemo>();
  }
}
