package com.farm.doc.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.farm.doc.dao.FarmDocBrowseDaoInter;
import com.farm.doc.domain.FarmDocBrowse;
@Repository 
public class FarmDocBrowseDao extends HibernateSQLTools<FarmDocBrowse> implements FarmDocBrowseDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;
	@Override
	public void deleteEntity(FarmDocBrowse entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	@Override
	public FarmDocBrowse getEntity(String id) {
		Session session=sessionFatory.getCurrentSession();
		return (FarmDocBrowse)session.get(FarmDocBrowse.class, id);
	}

	@Override
	public FarmDocBrowse insertEntity(FarmDocBrowse entity) {
		Session session=sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	@Override
	public void editEntity(FarmDocBrowse entity) {
		Session session=sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@Override
	public Session getSession() {
		return sessionFatory.getCurrentSession();
	}

	@Override
	public DataResult runSqlQuery(DataQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<FarmDocBrowse> selectEntitys(List<DBRule> rules) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Class<FarmDocBrowse> getTypeClass() {
		// TODO Auto-generated method stub
		return FarmDocBrowse.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return sessionFatory;
	}

	@Override
	public int getAllListNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FarmDocBrowse getEntityByUserIdAndDocId(String userId, String docId) {
		Session session=sessionFatory.getCurrentSession();
		SQLQuery sqlQuery=session.createSQLQuery("select ID from farm_doc_browse where USERID=? and DOCID=?");
		sqlQuery.setString(0,userId);
		sqlQuery.setString(1,docId);
		if(sqlQuery.list().size()>0) {
			String d=(String) sqlQuery.list().get(0);
			return (FarmDocBrowse)session.get(FarmDocBrowse.class,d);
		}else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<FarmDocBrowse> getEntityByDocId(String docId) {
		Session session=this.sessionFatory.getCurrentSession();
		SQLQuery sqlQuery=session.createSQLQuery("select ID from farm_doc_browse where DOCID=? order by BROWSENUM desc");
		sqlQuery.setString(0,docId);
		List<FarmDocBrowse> list=new ArrayList<FarmDocBrowse>();
		if(sqlQuery.list().size()>0) {
			for(int i=0;i<sqlQuery.list().size();i++) {
				if(i==10) break;
				String d=(String) sqlQuery.list().get(i);
				list.add((FarmDocBrowse)session.get(FarmDocBrowse.class,d));
			}
			return list;
		}
		return null;
	}

}
