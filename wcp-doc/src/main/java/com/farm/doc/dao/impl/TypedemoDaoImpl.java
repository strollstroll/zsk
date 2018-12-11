package com.farm.doc.dao.impl;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.farm.doc.dao.TypedemoDaoInter;
import com.farm.doc.domain.Typedemo;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TypedemoDaoImpl
  extends HibernateSQLTools<Typedemo>
  implements TypedemoDaoInter
{
  @Resource(name="sessionFactory")
  private SessionFactory sessionFatory;
  
  public void deleteEntity(Typedemo typedemo)
  {
    Session session = this.sessionFatory.getCurrentSession();
    session.delete(typedemo);
  }
  
  public int getAllListNum()
  {
    Session session = this.sessionFatory.getCurrentSession();
    SQLQuery sqlquery = session.createSQLQuery("select count(*) from farm_code_field");
    BigInteger num = (BigInteger)sqlquery.list().get(0);
    return num.intValue();
  }
  
  public Typedemo getEntity(String typedemoid)
  {
    Session session = this.sessionFatory.getCurrentSession();
    return (Typedemo)session.get(Typedemo.class, typedemoid);
  }
  
  public Typedemo insertEntity(Typedemo typedemo)
  {
    Session session = this.sessionFatory.getCurrentSession();
    session.save(typedemo);
    return typedemo;
  }
  
  public void editEntity(Typedemo typedemo)
  {
    Session session = this.sessionFatory.getCurrentSession();
    session.update(typedemo);
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
  
  public void deleteEntitys(List<DBRule> rules)
  {
    deleteSqlFromFunction(this.sessionFatory.getCurrentSession(), rules);
  }
  
  public List<Typedemo> selectEntitys(List<DBRule> rules)
  {
    return selectSqlFromFunction(this.sessionFatory.getCurrentSession(), rules);
  }
  
  public void updataEntitys(Map<String, Object> values, List<DBRule> rules)
  {
    updataSqlFromFunction(this.sessionFatory.getCurrentSession(), values, rules);
  }
  
  public int countEntitys(List<DBRule> rules)
  {
    return countSqlFromFunction(this.sessionFatory.getCurrentSession(), rules);
  }
  
  public SessionFactory getSessionFatory()
  {
    return this.sessionFatory;
  }
  
  public void setSessionFatory(SessionFactory sessionFatory)
  {
    this.sessionFatory = sessionFatory;
  }
  
  protected Class<?> getTypeClass()
  {
    return Typedemo.class;
  }
  
  protected SessionFactory getSessionFactory()
  {
    return this.sessionFatory;
  }
}
