package com.farm.doc.dao.impl;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.farm.doc.dao.FarmDocRelationDaoInter;
import com.farm.doc.domain.FarmDocrelation;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FarmDocRelationDao
  extends HibernateSQLTools<FarmDocrelation>
  implements FarmDocRelationDaoInter
{
  @Resource(name="sessionFactory")
  private SessionFactory sessionFatory;
  
  @Transactional
  public void deleteEntity(FarmDocrelation entity)
  {
    Session session = this.sessionFatory.getCurrentSession();
    session.delete(entity);
  }
  
  @Transactional
  public int getAllListNum()
  {
    Session session = this.sessionFatory.getCurrentSession();
    SQLQuery sqlquery = session.createSQLQuery("select count(*) from farm_rf_doctype");
    BigInteger num = (BigInteger)sqlquery.list().get(0);
    return num.intValue();
  }
  
  @Transactional
  public FarmDocrelation getEntity(String id)
  {
    Session session = this.sessionFatory.getCurrentSession();
    return (FarmDocrelation)session.get(FarmDocrelation.class, id);
  }
  
  @Transactional
  public FarmDocrelation insertEntity(FarmDocrelation entity)
  {
    Session session = this.sessionFatory.getCurrentSession();
    session.save(entity);
    return entity;
  }
  
  @Transactional
  public void editEntity(FarmDocrelation entity)
  {
    Session session = this.sessionFatory.getCurrentSession();
    session.update(entity);
  }
  
  public Session getSession()
  {
    return this.sessionFatory.getCurrentSession();
  }
  
  public DataResult runSqlQuery(DataQuery query)
  {
    try
    {
      return query.search(this.sessionFatory.getCurrentSession());
    }
    catch (Exception e) {}
    return null;
  }
  
  @Transactional
  public void deleteEntitys(List<DBRule> rules)
  {
    deleteSqlFromFunction(this.sessionFatory.getCurrentSession(), rules);
  }
  
  @Transactional
  public List<FarmDocrelation> selectEntitys(List<DBRule> rules)
  {
    return selectSqlFromFunction(this.sessionFatory.getCurrentSession(), rules);
  }
  
  @Transactional
  public void updataEntitys(Map<String, Object> values, List<DBRule> rules)
  {
    updataSqlFromFunction(this.sessionFatory.getCurrentSession(), values, rules);
  }
  
  public SessionFactory getSessionFatory()
  {
    return this.sessionFatory;
  }
  
  public void setSessionFatory(SessionFactory sessionFatory)
  {
    this.sessionFatory = sessionFatory;
  }
  
  protected SessionFactory getSessionFactory()
  {
    return this.sessionFatory;
  }
  
  protected Class<FarmDocrelation> getTypeClass()
  {
    return FarmDocrelation.class;
  }
  
  public List<FarmDocrelation> getDocRelationDoc(String docid)
  {
    Session session = this.sessionFatory.getCurrentSession();
    Query query = session.createQuery("from FarmDocrelation where docid1=? or docid2=?").setString(0, docid)
      .setString(1, docid);
    return query.list();
  }
}
