package com.farm.doc.server.impl;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
//import com.farm.core.sql.result.DataResult;
import com.farm.doc.dao.FarmDocRelationDaoInter;
import com.farm.doc.domain.Doc;
import com.farm.doc.domain.FarmDocrelation;
import com.farm.doc.server.FarmDocRelationManagerInter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FarmDocrelationManagerImpl
  implements FarmDocRelationManagerInter
{
  @Resource
  private FarmDocRelationDaoInter farmDocRelationDao;
  
  @Transactional
  public FarmDocrelation insertFarmDocRelationEntity(FarmDocrelation entity, LoginUser user)
  {
    List<DBRule> list = new ArrayList<DBRule>();
    list.add(new DBRule("DOCID1", entity.getDocid1(), "="));
    list.add(new DBRule("DOCID2", entity.getDocid2(), "="));
    this.farmDocRelationDao.deleteEntitys(list);
    List<DBRule> list2 = new ArrayList<DBRule>();
    list2.add(new DBRule("DOCID2", entity.getDocid1(), "="));
    list2.add(new DBRule("DOCID1", entity.getDocid2(), "="));
    this.farmDocRelationDao.deleteEntitys(list2);
    


    entity.setPstate("1");
    return this.farmDocRelationDao.insertEntity(entity);
  }
  
  @Transactional
  public FarmDocrelation editFarmDocRelationEntity(FarmDocrelation entity, LoginUser user)
  {
    FarmDocrelation entity2 = this.farmDocRelationDao.getEntity(entity.getId());
    

    entity2.setPcontent(entity.getPcontent());
    this.farmDocRelationDao.editEntity(entity2);
    return entity2;
  }
  
  @Transactional
  public void deleteFarmDocRelationEntity(String id, LoginUser user)
  {
    this.farmDocRelationDao.deleteEntity(this.farmDocRelationDao.getEntity(id));
  }
  
  @Transactional
  public FarmDocrelation getFarmDocRelationEntity(String id)
  {
    if (id == null) {
      return null;
    }
    return this.farmDocRelationDao.getEntity(id);
  }
  
  @Transactional
  public DataQuery createFarmDocRelationSimpleQuery(DataQuery query)
  {
    DataQuery dbQuery = DataQuery.init(query, 
      "FARM_TOP TOP LEFT JOIN FARM_DOC A ON TOP.DOCID = A.ID and a.STATE='1'LEFT JOIN FARM_RF_DOCTYPE B ON B.DOCID =A.ID LEFT JOIN FARM_DOCTYPE C ON C.ID=B.TYPEID LEFT JOIN FARM_DOCGROUP D ON D.ID=A.DOCGROUPID LEFT JOIN FARM_DOCRUNINFO DOCRUNINFO ON A.RUNINFOID = DOCRUNINFO.ID", 
      


      "TOP.ID AS ID, TOP.SORT AS SORT, A.DOCDESCRIBE AS DOCDESCRIBE,A.WRITEPOP AS WRITEPOP,A.READPOP AS READPOP,A.TITLE AS TITLE,A.AUTHOR AS AUTHOR,A.PUBTIME AS PUBTIME,A.DOMTYPE AS DOMTYPE,A.SHORTTITLE AS SHORTTITLE,A.TAGKEY AS TAGKEY,A.STATE AS STATE,D.GROUPNAME AS GROUPNAME, DOCRUNINFO.VISITNUM AS VISITNUM, DOCRUNINFO.ANSWERINGNUM AS ANSWERINGNUM, A.IMGID AS IMGID, A.ID AS DOCID");
    



    return dbQuery;
  }
  
  @Transactional
  public List<Doc> getDocRelationDoc(String docid)
  {
    DataQuerys.wipeVirus(docid);
    List<FarmDocrelation> docrelations = this.farmDocRelationDao.getDocRelationDoc(docid);
    Set<String> ids = new HashSet<String>();
    for (FarmDocrelation node : docrelations)
    {
      ids.add(node.getDocid1());
      ids.add(node.getDocid2());
    }
    DataQuery dbQuery = DataQuery.init(null, "FARM_DOC", "ID,TITLE");
    String idsstr = null;
    for (String node : ids) {
      if (!node.equals(docid)) {
        if (idsstr == null) {
          idsstr = "'" + node + "'";
        } else {
          idsstr = idsstr + "," + "'" + node + "'";
        }
      }
    }
    dbQuery.addSqlRule("and id in (" + idsstr + ")");
    try
    {
      return dbQuery.search().getObjectList(Doc.class);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return new ArrayList<Doc>();
  }
  
  @Transactional
  public void deleteDocRelationsByDocid(String docid, LoginUser currentUser)
  {
    List<DBRule> list = new ArrayList<DBRule>();
    list.add(new DBRule("DOCID1", docid, "="));
    this.farmDocRelationDao.deleteEntitys(list);
    List<DBRule> list2 = new ArrayList<DBRule>();
    list2.add(new DBRule("DOCID2", docid, "="));
    this.farmDocRelationDao.deleteEntitys(list2);
  }
}
