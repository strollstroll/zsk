package com.farm.wcp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.service.UserServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.doc.domain.Doc;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.domain.ex.DocEntire;
import com.farm.doc.domain.ex.TypeBrief;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.FarmDocOperateRightInter;
import com.farm.doc.server.FarmDocOperateRightInter.POP_TYPE;
import com.farm.parameter.FarmParameterService;
import com.farm.parameter.service.AloneApplogServiceInter;
import com.farm.doc.server.FarmDocRunInfoInter;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.FarmDocgroupManagerInter;
import com.farm.doc.server.FarmDocmessageManagerInter;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.wcp.util.ThemesUtil;
import com.farm.wcp.webfile.server.WcpWebFileManagerInter;
import com.farm.web.WebUtils;

/**
 * 资源文件
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/webfile")
@Controller
public class WebFileController extends WebUtils {
	@Resource
	private FarmDocgroupManagerInter farmDocgroupManagerImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private FarmDocManagerInter farmDocManagerImpl;
	@Resource
	private FarmDocRunInfoInter farmDocRunInfoImpl;
	@Resource
	private FarmDocmessageManagerInter farmDocmessageManagerImpl;
	@Resource
	private FarmDocOperateRightInter farmDocOperateRightImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private WcpWebFileManagerInter wcpWebFileManagerImpl;
	@Resource
	private FarmDocTypeInter farmDocTypeManagerImpl;
	@Resource
	AloneApplogServiceInter aloneApplogServiceImpl;

	private final static Logger log = Logger.getLogger(WebFileController.class);
	
	@RequestMapping("/add")
	public ModelAndView creatWebFile(String typeid, String groupid, HttpSession session) {
		DocEntire doc = new DocEntire(new Doc());
		if (typeid != null && !typeid.toUpperCase().trim().equals("NONE") && !typeid.toUpperCase().trim().equals("")) {
			FarmDoctype doctype = farmDocTypeManagerImpl.getType(typeid);
			doc.setType(doctype);
		}
		if (groupid != null && !groupid.toUpperCase().trim().equals("NONE")
				&& !groupid.toUpperCase().trim().equals("")) {
			doc.getDoc().setDocgroupid(groupid);
		}
		List<TypeBrief> types = farmDocTypeManagerImpl.getTypesForWriteDoc(getCurrentUser(session));

		String filetypeString = FarmParameterService.getInstance().getParameter("config.doc.upload.types").toLowerCase()
				.replaceAll("，", ",");
		StringBuffer filetypestrplus = new StringBuffer();
		for (String node : parseIds(filetypeString)) {
			if (filetypestrplus.length() > 0) {
				filetypestrplus.append(";");
			}
			filetypestrplus.append("*." + node);
		}
		return ViewMode.getInstance().putAttr("types", types).putAttr("doc", doc).putAttr("filetypestr", filetypeString)
				.putAttr("filetypestrplus", filetypestrplus.toString())
				.returnModelAndView(ThemesUtil.getThemePath() + "/webfile/creat");
	}

	@RequestMapping("/edit")
	public ModelAndView editWebfile(String docId, HttpSession session, HttpServletRequest request) {
		DocEntire doc = null;
		try {
			doc = farmDocManagerImpl.getDoc(docId, getCurrentUser(session));
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.toString())
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		List<TypeBrief> types = farmDocTypeManagerImpl.getTypesForWriteDoc(getCurrentUser(session));

		String filetypeString = FarmParameterService.getInstance().getParameter("config.doc.upload.types").toLowerCase()
				.replaceAll("，", ",");
		StringBuffer filetypestrplus = new StringBuffer();
		for (String node : parseIds(filetypeString)) {
			if (filetypestrplus.length() > 0) {
				filetypestrplus.append(";");
			}
			filetypestrplus.append("*." + node);
		}
		return ViewMode.getInstance().putAttr("doce", doc).putAttr("types", types)
				.putAttr("filetypestr", filetypeString).putAttr("filetypestrplus", filetypestrplus.toString())
				.returnModelAndView(ThemesUtil.getThemePath() + "/webfile/edit");
	}

	@RequestMapping("/editCommit")
	public ModelAndView editCommit(String docid, String fileId, String knowtype, String knowtitle, String knowtag,
			String docgroup, String writetype, String readtype, String text, String editnote, 
			String imgFileId,  // remyxo, 2017-12-07
			HttpServletRequest request,  /* remyxo ,2017-12-13 */
			HttpSession session) {
		DocEntire doc = null;
		try {
			if (docgroup.equals("0")) {
				docgroup = null;
			}
			doc = wcpWebFileManagerImpl.editWebFile(docid, Arrays.asList(fileId.trim().split(",")), knowtype, knowtitle,
					knowtag, docgroup, text, POP_TYPE.getEnum(writetype), POP_TYPE.getEnum(readtype), editnote,
					imgFileId, /* remyxo, 2017-12-07 */
					getCurrentUser(session));
			// remyxo, 2017-12-13
			aloneApplogServiceImpl.wcplog("修改文件资源", knowtitle,
					getCurrentUser(session).getLoginname(), "INFO", 
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					this.getClass().getName(), 
					getCurrentIp(request));
			// end, 2017-12-13
			if (doc.getAudit() != null) {
				return ViewMode.getInstance().returnRedirectUrl("/audit/tempdoc.do?auditid=" + doc.getAudit().getId());
			}
			return ViewMode.getInstance().returnRedirectUrl("/webdoc/view/Pub" + doc.getDoc().getId() + ".html");
		} catch (Exception e) {
			e.printStackTrace();
			return ViewMode.getInstance().setError(e.toString())
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	@RequestMapping("/addsubmit")
	public ModelAndView creatWebFileSubmit(String fileId, String creattype, String knowtype, String knowtitle,
			String knowtag, String text, String docgroup, String writetype, String readtype, 
			String imgFileId, 			/* remyxo, 2017-12-07 */
			HttpServletRequest request,	/* remyxo ,2017-12-13 */
			HttpSession session) {
		
		try {
			String nameStr="";
			List<String> fileids = Arrays.asList(fileId.trim().split(","));
			if (creattype != null && creattype.equals("on")) {
				/** 创建为独立知识 **/
				Map<String, String> doclinks = new HashMap<String, String>();
				DocEntire doc = null;
				int filecnt = 0;
				String imgid;
				for (String fileid : fileids) {
					List<String> fileidlist = new ArrayList<>();
					fileidlist.add(fileid);
					if (docgroup != null && docgroup.equals("0")) {
						docgroup = null;
					}
					String fileName = farmFileManagerImpl.getFile(fileid).getName();
					// 由于多个资源文件共享imgid，如果删除其中一个资源的内容图，则其它资源的内容图不可用，需要克隆。
					if(imgFileId!=null && !imgFileId.isEmpty()) {
						if(filecnt==0) { 
							imgid = imgFileId;   // first, use original img
						}
						else {   // rest, clone from original img
							imgid = farmFileManagerImpl.cloneFile(imgFileId, null);
							log.debug("***** WebFileController.creatWebFileSubmit： clone img=" + imgid + " from " + imgFileId);
						}
					}
					else {  // null
						imgid = null;
					}
					doc = wcpWebFileManagerImpl.creatWebFile(fileidlist, knowtype, fileName, knowtag, docgroup, text,
							POP_TYPE.getEnum(writetype), POP_TYPE.getEnum(readtype), 
							imgid, /* remyxo, 2017-12-07, only first fileid can have imgid */
							getCurrentUser(session));
					if (doc.getAudit() != null) {
						doclinks.put(fileName, "/audit/tempdoc.do?auditid=" + doc.getAudit().getId());
					} else {
						doclinks.put(fileName, "/webdoc/view/Pub" + doc.getDoc().getId() + ".html");
					}
					filecnt++;
					nameStr+=fileName+";";
				}
				// remyxo, 2017-12-13
				aloneApplogServiceImpl.wcplog("创建文件资源", filecnt+"个独立文件资源。分别为："+nameStr,
						getCurrentUser(session).getLoginname(), "INFO", 
						Thread.currentThread().getStackTrace()[1].getMethodName(),
						this.getClass().getName(), 
						getCurrentIp(request));
				
				// end, 2017-12-13
				if (doc.getAudit() != null) {
					return ViewMode.getInstance().putAttr("MESSAGE", fileids.size() + "个资源文件创建成功，但是需要审核后才能够被他人访问！")
							.returnModelAndView(ThemesUtil.getThemePath() + "/message");
				}
				return ViewMode.getInstance().putAttr("MESSAGE", fileids.size() + "个资源文件创建成功！")
						.putAttr("LINKS", doclinks).returnModelAndView(ThemesUtil.getThemePath() + "/message");
			} else {
				/** 创建为一个知识 **/
				DocEntire doc = null;
				if (docgroup.equals("0")) {
					docgroup = null;
				}
				doc = wcpWebFileManagerImpl.creatWebFile(fileids, knowtype, knowtitle, knowtag, docgroup, text,
						POP_TYPE.getEnum(writetype), POP_TYPE.getEnum(readtype), 
						imgFileId,  /* remyxo, 2017-12-07 */
						getCurrentUser(session));
				
				// remyxo, 2017-12-13
				aloneApplogServiceImpl.wcplog("创建文件资源", knowtitle,
						getCurrentUser(session).getLoginname(), "INFO", 
						Thread.currentThread().getStackTrace()[1].getMethodName(),
						this.getClass().getName(), 
						getCurrentIp(request));
				// end, 2017-12-13
				
				if (doc.getAudit() != null) {
					return ViewMode.getInstance()
							.returnRedirectUrl("/audit/tempdoc.do?auditid=" + doc.getAudit().getId());
				}
				return ViewMode.getInstance().returnRedirectUrl("/webdoc/view/Pub" + doc.getDoc().getId() + ".html");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ViewMode.getInstance().setError(e.toString())
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}
}
