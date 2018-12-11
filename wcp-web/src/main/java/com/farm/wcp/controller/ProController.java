package com.farm.wcp.controller;

//import com.farm.core.ParameterService;
import com.farm.core.page.ViewMode;
import com.farm.doc.domain.Doc;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.domain.ex.DocEntire;
import com.farm.doc.domain.ex.TypeBrief;
import com.farm.doc.server.FarmDocRunInfoInter;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.FarmDocgroupManagerInter;
import com.farm.doc.server.FarmDocmessageManagerInter;
import com.farm.doc.server.FarmFileManagerInter;
//import com.farm.doc.server.FarmFileManagerInter.FILE_TYPE;
import com.farm.doc.server.FarmtopServiceInter;
import com.farm.doc.server.WeburlServiceInter;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.doc.utils.Docx2Html;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.know.service.KnowServiceInter;
import com.farm.web.WebUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.POIXMLException;
import org.apache.poi.xwpf.converter.core.IImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping({"/pro"})
@Controller
public class ProController
  extends WebUtils
{
  @Resource
  private FarmDocRunInfoInter farmDocRunInfoImpl;
  @Resource
  private KnowServiceInter KnowServiceImpl;
  @Resource
  private FarmtopServiceInter farmTopServiceImpl;
  @Resource
  private FarmFileManagerInter farmFileManagerImpl;
  @Resource
  private FarmDocTypeInter farmDocTypeManagerImpl;
  @Resource
  private FarmDocgroupManagerInter farmDocgroupManagerImpl;
  @Resource
  private WeburlServiceInter weburlServiceImpl;
  @Resource
  private FarmDocmessageManagerInter farmDocmessageManagerImpl;
  
  public static String getThemePath()
  {
    return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
  }
  
  @RequestMapping({"/wordup"})
  public ModelAndView wordup(String typeid, String groupid, HttpSession session, HttpServletRequest request)
  {
    return 
      ViewMode.getInstance().putAttr("typeid", typeid).putAttr("groupid", groupid).returnModelAndView(getThemePath() + "/know/wordUp");
  }
  
  @RequestMapping({"/wordDLoadDo"})
  public ModelAndView wordDLoadDo(String fileid, String typeid, String groupid, final HttpSession session, HttpServletRequest request)
  {
    try
    {
      FarmDocfile file = farmFileManagerImpl.getFile(fileid);
      if (file == null) {
        return 
          ViewMode.getInstance().putAttr("typeid", typeid).putAttr("groupid", groupid).returnModelAndView(getThemePath() + "/know/wordUp");
      }
      final Map<String, String> urlmap = new HashMap<String,String>();
      String docstr = new Docx2Html().doGenerateHTMLFile(file.getFile(), 
			new IImageExtractor() 
			{
				public void extract(String imagePath, byte[] imageData) throws IOException
				{
					//String fileName = (new StringBuilder(String.valueOf(UUID.randomUUID().toString().replaceAll("-", "")))).append(FarmDocFiles.getExName(imagePath.substring(imagePath.lastIndexOf("/")))).toString();
					String fileName = UUID.randomUUID().toString().replaceAll("-", "") 
							+ FarmDocFiles.getExName(imagePath.substring(imagePath.lastIndexOf("/")));
					String id = farmFileManagerImpl.saveFile(new ByteArrayInputStream(imageData), fileName, fileName, 
							FarmFileManagerInter.FILE_TYPE.HTML_INNER_IMG, getCurrentUser(session));
					urlmap.put(imagePath,  id);
				}
			}, 
			new IURIResolver()
			{
				public String resolve(String uri)
				{
					// log.debug("***** ProController.wordDLoadDo: URL="+(String)urlmap.get(uri));
					return farmFileManagerImpl.getFileURL((String)urlmap.get(uri));
				}
			});

      DocEntire doc = new DocEntire(new Doc());
      doc.getDoc().setTitle(file.getName());
      doc.setTexts(Docx2Html.formatHtmlForWCP(docstr), getCurrentUser(session));
      if ((typeid != null) && (!typeid.toUpperCase().trim().equals("NONE")) && (!typeid.toUpperCase().trim().equals("")))
      {
        FarmDoctype doctype = farmDocTypeManagerImpl.getType(typeid);
        doc.setType(doctype);
      }
      if ((groupid != null) && (!groupid.toUpperCase().trim().equals("NONE")) && (!groupid.toUpperCase().trim().equals("")))
      {
        doc.getDoc().setDocgroupid(groupid);
      }
      farmFileManagerImpl.delFile(fileid, getCurrentUser(session));
      List<TypeBrief> types = farmDocTypeManagerImpl.getTypesForWriteDoc(getCurrentUser(session));
      return ViewMode.getInstance().putAttr("doce", doc).putAttr("types", types).returnModelAndView(getThemePath() + "/know/creat");
    }
    catch (POIXMLException e)
    {
      e.printStackTrace();
      return ViewMode.getInstance().setError("本文档中的部分格式不被支持！（Strict OOXML isn't currently supported）")
        .returnModelAndView(getThemePath() + "/error");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return ViewMode.getInstance().setError(e.toString()).returnModelAndView(getThemePath() + "/error");
    }
  }
}
