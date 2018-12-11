package com.farm.wcp.webfile.server.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

//import com.farm.authority.service.impl.UserServiceImpl;
import com.farm.core.auth.domain.LoginUser;
import com.farm.doc.domain.Doc;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.domain.ex.DocEntire;
import com.farm.doc.exception.CanNoWriteException;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.FarmDocOperateRightInter;
import com.farm.doc.server.FarmDocOperateRightInter.POP_TYPE;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.FarmFileIndexManagerInter;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.wcp.webfile.server.WcpWebFileManagerInter;

@Service
public class WcpWebFileManagerImpl implements WcpWebFileManagerInter {
	@Resource
	private FarmDocManagerInter farmDocManagerImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private FarmDocOperateRightInter farmDocOperateRightImpl;
	@Resource
	private FarmDocTypeInter farmDocTypeManagerImpl;
	@Resource
	private FarmFileIndexManagerInter farmFileIndexManagerImpl;
	
	private static final Logger log = Logger.getLogger(WcpWebFileManagerImpl.class);

	@Override
	@Transactional
	public DocEntire creatWebFile(List<String> fileid, String typeId, String fileName, String tag, String groupId,
			String text, POP_TYPE editPop, POP_TYPE readPop, 
			String imgFileId, /* remyxo, 2017-12-07 */
			LoginUser currentUser) {
		Doc doc = new Doc();
		doc.setTitle(fileName);
		doc.setTagkey(tag);
		doc.setDomtype("5");
		doc.setDocgroupid(groupId);
		doc.setReadpop(readPop.getValue());
		doc.setWritepop(editPop.getValue());
		doc.setState("1");

		// remyxo, 2017-12-07
		if(imgFileId == null || imgFileId.equals("")) {
			doc.setImgid(null);
		}
		else {
			doc.setImgid(imgFileId);
			farmFileManagerImpl.submitFile(imgFileId);   // 标记为“使用”(pstate="1")
		}
		// end, 2017-12-07
		
		DocEntire doce = new DocEntire(doc);
		doce.setTexts(text, currentUser);
		doce.setType(farmDocTypeManagerImpl.getType(typeId));
		doce = farmDocManagerImpl.createDoc(doce, currentUser);
		String docid = doce.getDoc().getId();
		for (String fid : fileid) {
			farmFileManagerImpl.addFileForDoc(docid, fid, currentUser);
			farmFileManagerImpl.submitFile(fid);
		}
		return doce;
	}

	@Override
	@Transactional
	public DocEntire editWebFile(String docid, List<String> fileid, String typeId, String fileName, String tag,
			String groupId, String text, POP_TYPE editPop, POP_TYPE readPop, String editNote, 
			String imgFileId,  /* remyxo, 2017-12-07 */
			LoginUser currentUser) {
		DocEntire doce = farmDocManagerImpl.getDoc(docid);
		List<FarmDocfile> originalfiles=doce.getFiles();
		doce.getDoc().setTitle(fileName);
		doce.getDoc().setTagkey(tag);
		doce.getDoc().setDomtype("5");
		doce.getDoc().setDocgroupid(groupId);
		doce.getDoc().setReadpop(readPop.getValue());
		doce.getDoc().setWritepop(editPop.getValue());
		String oldimg = doce.getDoc().getImgid();
		// remyxo, 2017-12-07
		if(imgFileId == null || imgFileId.equals("")) {
			doce.getDoc().setImgid(null);
			if(oldimg != null) {
				farmFileManagerImpl.delFile(oldimg, null);
				log.debug("***** WcpWebFileManagerImpl.editWebFile: delFile(oldimg=" + oldimg + ") deleted.");
			}
		}
		else {
			if(oldimg == null || !imgFileId.equals(oldimg)) {
				doce.getDoc().setImgid(imgFileId);
				farmFileManagerImpl.submitFile(imgFileId);   // 标记为“使用”(pstate="1")
				log.debug("***** WcpWebFileManagerImpl.editWebFile: submitFile(imgFileId=" + imgFileId + ") submited.");
			}
			if(oldimg != null) {
				log.debug("***** WcpWebFileManagerImpl.editWebFile: delFile(oldimg=" + oldimg + ") deleted.");
				farmFileManagerImpl.delFile(oldimg, null);
			}
		}
		// end, 2017-12-07
		
		doce.setTexts(text, currentUser);
		doce.setFiles(new ArrayList<FarmDocfile>());// 重置附件_zhanghc_20150919
		for (String fid : fileid) {
			doce.addFile(farmFileManagerImpl.getFile(fid));
		}
		farmFileIndexManagerImpl.delFileLucenneIndexs(originalfiles,doce.getFiles(), doce);
		doce.setType(farmDocTypeManagerImpl.getType(typeId));
		try {
			farmFileManagerImpl.delFileForDoc(doce.getDoc().getId(), currentUser);
			doce = farmDocManagerImpl.editDocByUser(doce, editNote, currentUser);
		} catch (CanNoWriteException e) {
			throw new RuntimeException(e);
		}
		return doce;
	}

}
