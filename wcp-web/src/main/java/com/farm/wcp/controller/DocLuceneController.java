package com.farm.wcp.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.service.UserServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.ex.DocBrief;
import com.farm.doc.domain.ex.TypeBrief;
import com.farm.doc.server.FarmDocIndexInter;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.FarmDocOperateRightInter;
import com.farm.doc.server.FarmDocRunInfoInter;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.FarmDocgroupManagerInter;
import com.farm.doc.server.FarmDocmessageManagerInter;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.parameter.FarmParameterService;
import com.farm.util.web.WebHotCase;
import com.farm.wcp.know.service.KnowServiceInter;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;

/**
 * 检索
 * 
 * @author wangdong
 *
 */
@RequestMapping("/websearch")
@Controller
public class DocLuceneController extends WebUtils {
	@Resource
	private FarmDocgroupManagerInter farmDocgroupManagerImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private FarmDocManagerInter farmDocManagerImpl;
	@Resource
	private FarmDocRunInfoInter farmDocRunInfoImpl;
	@Resource
	private KnowServiceInter KnowServiceImpl;
	@Resource
	private FarmDocTypeInter farmDocTypeManagerImpl;
	@Resource
	private FarmDocmessageManagerInter farmDocmessageManagerImpl;
	@Resource
	private FarmDocOperateRightInter farmDocOperateRightImpl;
	@Resource
	private FarmDocIndexInter farmDocIndexManagerImpl;
	@Resource
	private UserServiceInter userServiceImpl;

	/**
	 * 检索首页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/PubHome", method = RequestMethod.GET)
	public ModelAndView show(Integer pagenum, HttpSession session, HttpServletRequest request) throws Exception {
		ViewMode mode = ViewMode.getInstance();
		
		// remyxo, 2017-11-11
		int topnum, newnum, hotnum, hotcasenum;
		topnum = FarmParameterService.getInstance().getIntParameter("config.sys.topdocs.show.num", 4);
		newnum = FarmParameterService.getInstance().getIntParameter("config.sys.newdocs.show.num", 6);
		hotnum = FarmParameterService.getInstance().getIntParameter("config.sys.hotdocs.show.num", 10);
		hotcasenum = FarmParameterService.getInstance().getIntParameter("config.sys.webhotcase.show.num", 5);
		// end, 2017-12-01
		
		// 加载搜索热词
		List<String> hotCase = WebHotCase.getCases(hotcasenum);
		// 用户分类
		List<TypeBrief> typesons = farmDocTypeManagerImpl.getTypeInfos(getCurrentUser(session), "NONE");
		// 用户小组
		if (getCurrentUser(session) != null) {
			DataResult groups = farmDocgroupManagerImpl.getGroupsByUser(getCurrentUser(session).getId(), 100, 1);
			mode.putAttr("groups", groups.getResultList());
		}
		
		// 获取前五条置顶文档
		List<DocBrief> topdocs = farmDocRunInfoImpl.getPubTopDoc(topnum);
        // 加载最新知识， added by remyxo, 2017-07-19
        List<DocBrief> newdocs = farmDocRunInfoImpl.getNewKnowList(newnum);
		// 加载常用文档
		List<DocBrief> hotdocs = farmDocRunInfoImpl.getPubHotDoc(hotnum);
		
		return mode.putAttr("typesons", typesons).putAttr("topDocList", topdocs).putAttr("hotdocs", hotdocs).putAttr("newdocs", newdocs)
				.putAttr("hotCase", hotCase).returnModelAndView(ThemesUtil.getThemePath() + "/lucene/search");
	}

	/**
	 * 检索
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/PubDo")
	public ModelAndView search(String word, Integer pagenum, HttpSession session, HttpServletRequest request)
			throws Exception 
	{		
		String userid = null;
		if (getCurrentUser(session) != null) {
			userid = getCurrentUser(session).getId();
		}
		if (word == null) {
			word = "";
		}
		word = URLDecoder.decode(word, "utf-8");
		ViewMode mode = ViewMode.getInstance();
		int hotcasenum;
		hotcasenum = FarmParameterService.getInstance().getIntParameter("config.sys.webhotcase.show.num", 5);
		
		List<String> hotCase = WebHotCase.getCases(hotcasenum);
		
		if (word == null || word.isEmpty()) {
			int topnum, newnum, hotnum;
			topnum = FarmParameterService.getInstance().getIntParameter("config.sys.topdocs.show.num", 4);
			newnum = FarmParameterService.getInstance().getIntParameter("config.sys.newdocs.show.num", 6);
			hotnum = FarmParameterService.getInstance().getIntParameter("config.sys.hotdocs.show.num", 10);
			
			// 获取文档分类列表
			List<TypeBrief> typesons = farmDocTypeManagerImpl.getTypeInfos(getCurrentUser(session), "NONE");
			// 获取前五条置顶文档
			List<DocBrief> topdocs = farmDocRunInfoImpl.getPubTopDoc(topnum);
			// 加载最热知识
			List<DocBrief> hotdocs = farmDocRunInfoImpl.getPubHotDoc(hotnum);
			
			// 加载最新知识， added by remyxo, 2017-07-19
			List<DocBrief> newdocs = farmDocRunInfoImpl.getNewKnowList(newnum);
			
			//return mode.setError("请输入检索词").putAttr("topDocList", topdocs).putAttr("hotCase", hotCase).putAttr("newdocs", newdocs)
			return mode.putAttr("topDocList", topdocs).putAttr("hotCase", hotCase).putAttr("newdocs", newdocs)
					.putAttr("hotdocs", hotdocs).putAttr("typesons", typesons)
					.returnModelAndView(ThemesUtil.getThemePath() + "/lucene/search");
		}
		try {
			List<TypeBrief> types = farmDocTypeManagerImpl.getPopTypesForReadDoc(getCurrentUser(session));
			DataResult result = farmDocIndexManagerImpl.search(word, userid, pagenum);
			
			return mode.putAttr("result", result).putAttr("types", types).putAttr("hotCase", hotCase)
					.putAttr("word", word).returnModelAndView(ThemesUtil.getThemePath() + "/lucene/searchResult");
		} catch (Exception e) {
			return mode.setError(e.toString()).returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	/**
	 * 查看知识的关联知识
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/PubRelationDocs")
	@ResponseBody
	public Map<String, Object> relationDocs(String docid, HttpSession session,
			HttpServletRequest request) throws Exception {
		ViewMode page = ViewMode.getInstance();
		try {
			List<DocBrief> relationdocs = farmDocIndexManagerImpl.getRelationDocs(docid, 10);
			page.putAttr("RELATIONDOCS", relationdocs);
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage()).returnObjMode();
		}
		return page.returnObjMode();
	}
}
