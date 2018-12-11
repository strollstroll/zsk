package com.farm.lucene.server;

import com.farm.lucene.common.IRResult;
import com.farm.lucene.common.ScoreDocFilterInter;

public interface DocQueryInter {

	/**
	 * 查询结果集合从索引中WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC)
	 * 
	 * @param Iql
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	IRResult query(String Iql, int currentPage, int pagesize) throws Exception;

	/**
	 * 查询结果集合从索引中WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC)
	 * 
	 * @param Iql
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	IRResult queryByMultiIndex(String Iql, int currentPage, int pagesize)
			throws Exception;
	
	IRResult queryByMultiIndex(String paramString, int paramInt1, int paramInt2, ScoreDocFilterInter paramScoreDocFilterInter)
		    throws Exception;
}
