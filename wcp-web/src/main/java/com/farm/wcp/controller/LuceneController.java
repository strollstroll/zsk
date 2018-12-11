package com.farm.wcp.controller;

//import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.ex.DocBrief;
import com.farm.doc.domain.ex.TypeBrief;
import com.farm.doc.server.FarmDocIndexInter;
import com.farm.doc.server.FarmDocRunInfoInter;
//import com.farm.doc.server.FarmDocRunInfoInter.TOP_TYPE;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.FarmDocgroupManagerInter;
import com.farm.parameter.FarmParameterService;
import com.farm.util.web.WebHotCase;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;
import java.net.URLDecoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping({"/websearchs"})
@Controller
public class LuceneController
  extends WebUtils
{
  @Resource
  private FarmDocRunInfoInter farmDocRunInfoImpl;
  @Resource
  private FarmDocTypeInter farmDocTypeManagerImpl;
  @Resource
  private FarmDocIndexInter farmDocIndexManagerImpl;
  @Resource
  private FarmDocgroupManagerInter farmDocgroupManagerImpl;
  
  @RequestMapping({"/PubPlus"})
  public ModelAndView searchPlus(String word, HttpSession session, HttpServletRequest request)
    throws Exception
  {
	  // remyxo,2017-10-19, 分类
	  List<TypeBrief> typesons = farmDocTypeManagerImpl.getPopTypesForReadDoc(getCurrentUser(session));
	  // end, 2017-10-19
	
	  if (word == null) {
		  word = "";
	  }
	  word = URLDecoder.decode(word, "utf-8");
	  ViewMode mode = ViewMode.getInstance();
	  if (getCurrentUser(session) != null)
	  {
		  DataResult groups = this.farmDocgroupManagerImpl.getGroupsByUser(getCurrentUser(session).getId(), 100, Integer.valueOf(1));
		  mode.putAttr("groups", groups.getResultList());
	  }
	  try
	  {
		  return mode.putAttr("word", word).putAttr("typesons", typesons).returnModelAndView(ThemesUtil.getThemePath() + "/lucene/searchPro");
	  }
	  catch (Exception e)
	  {
		  return mode.setError(e.toString()).returnModelAndView(ThemesUtil.getThemePath() + "/error");
	  }
  }
  
  @RequestMapping({"/PubDo"})
  public ModelAndView search(String word, String indexTypes, String indexGroups, Integer pagenum, HttpSession session, HttpServletRequest request)
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
    
	  int hotcasenum = FarmParameterService.getInstance().getIntParameter("config.sys.webhotcase.show.num", 5);
	  List<String> hotCase = WebHotCase.getCases(hotcasenum);
    
	  if (getCurrentUser(session) != null)
	  {
		  DataResult groups = this.farmDocgroupManagerImpl.getGroupsByUser(getCurrentUser(session).getId(), 100, Integer.valueOf(1));
		  mode.putAttr("groups", groups.getResultList());
	  }
	  if ((word == null) || (word.isEmpty()))
	  {
		  int topnum, hotnum;
		  topnum = FarmParameterService.getInstance().getIntParameter("config.sys.topdocs.show.num", 4);
		  hotnum = FarmParameterService.getInstance().getIntParameter("config.sys.hotdocs.show.num", 10);
		
		  // 置顶文档
		  List<DocBrief> topdocs = this.farmDocRunInfoImpl.getPubTopDoc(topnum);
		  // 常用文档
		  List<DocBrief> hotdocs = this.farmDocRunInfoImpl.getPubHotDoc(hotnum);
    	
		  return mode.setError("请输入检索词").putAttr("topDocList", topdocs).putAttr("hotCase", hotCase)
    			.putAttr("hotdocs", hotdocs).returnModelAndView(ThemesUtil.getThemePath() + "/lucene/search");
	  }
	  try
	  {
		  int newnum = FarmParameterService.getInstance().getIntParameter("config.sys.newdocs.show.num", 6);
		    	
		  // 分类
		  List<TypeBrief> types = farmDocTypeManagerImpl.getPopTypesForReadDoc(getCurrentUser(session));
		  // 获取最新文档
		  List<DocBrief> newdocs = this.farmDocRunInfoImpl.getNewKnowList(newnum);
    	
		  DataResult result = this.farmDocIndexManagerImpl.search(word, parseIds(indexTypes), parseIds(indexGroups), 
    			userid, pagenum);
		  return mode.putAttr("result", result).putAttr("hotCase", hotCase).putAttr("newdocs", newdocs).putAttr("types", types)
    			.putAttr("word", word).returnModelAndView(ThemesUtil.getThemePath() + "/lucene/searchResult");
	  }
	  catch (Exception e)
	  {
		  return mode.setError(e.toString()).returnModelAndView(ThemesUtil.getThemePath() + "/error");
	  }
  }
}
