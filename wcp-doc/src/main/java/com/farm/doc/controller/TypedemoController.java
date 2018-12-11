package com.farm.doc.controller;

import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.domain.Typedemo;
import com.farm.doc.server.FarmDocTypeInter;
import com.farm.doc.server.TypedemoServiceInter;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping({"/typedemo"})
@Controller
public class TypedemoController
  extends WebUtils
{
  private static final Logger log = Logger.getLogger(TypedemoController.class);
  @Resource
  private TypedemoServiceInter typedemoServiceImpl;
  @Resource
  private FarmDocTypeInter farmDocTypeManagerImpl;
  
  public TypedemoServiceInter getTypedemoServiceImpl()
  {
    return this.typedemoServiceImpl;
  }
  
  public void setTypedemoServiceImpl(TypedemoServiceInter typedemoServiceImpl)
  {
    this.typedemoServiceImpl = typedemoServiceImpl;
  }
  
  @RequestMapping({"/query"})
  @ResponseBody
  public Map<String, Object> queryall(DataQuery query, HttpServletRequest request)
  {
    try
    {
      query = EasyUiUtils.formatGridQuery(request, query);
      DataResult result = this.typedemoServiceImpl.createTypedemoSimpleQuery(query).search();
      result.runDictionary("1:可用,0:禁用", "PSTATE");
      return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage()).returnObjMode();
    }
  }
  
  @RequestMapping({"/demolist"})
  @ResponseBody
  public Map<String, Object> demolist(String typeid, HttpServletRequest request)
  {
    try
    {
      List<Typedemo> list = this.typedemoServiceImpl.getDemoList(typeid);
      return ViewMode.getInstance().putAttr("DEMOS", list).returnObjMode();
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage()).returnObjMode();
    }
  }
  
  @RequestMapping({"/edit"})
  @ResponseBody
  public Map<String, Object> editSubmit(Typedemo entity, HttpSession session)
  {
    try
    {
      entity = this.typedemoServiceImpl.editTypedemoEntity(entity, getCurrentUser(session));
      return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage()).returnObjMode();
    }
  }
  
  @RequestMapping({"/add"})
  @ResponseBody
  public Map<String, Object> addSubmit(Typedemo entity, HttpSession session)
  {
    try
    {
      entity = this.typedemoServiceImpl.insertTypedemoEntity(entity, getCurrentUser(session));
      return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage()).returnObjMode();
    }
  }
  
  @RequestMapping({"/del"})
  @ResponseBody
  public Map<String, Object> delSubmit(String ids, HttpSession session)
  {
    try
    {
      for (String id : parseIds(ids)) {
        this.typedemoServiceImpl.deleteTypedemoEntity(id, getCurrentUser(session));
      }
      return ViewMode.getInstance().returnObjMode();
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage()).returnObjMode();
    }
  }
  
  @RequestMapping({"/list"})
  public ModelAndView index(HttpSession session)
  {
    return ViewMode.getInstance().returnModelAndView("doc/TypedemoResult");
  }
  
  @RequestMapping({"/form"})
  public ModelAndView view(RequestMode pageset, String ids, String typeid)
  {
    try
    {
    	FarmDoctype type;
    	Typedemo demo;
      switch (pageset.getOperateType())
      {
      case 0: 
        demo = this.typedemoServiceImpl.getTypedemoEntity(ids);
        type = this.farmDocTypeManagerImpl.getType(demo.getTypeid());
        return ViewMode.getInstance().putAttr("pageset", pageset)
          .putAttr("entity", this.typedemoServiceImpl.getTypedemoEntity(ids)).putAttr("type", type)
          .returnModelAndView("doc/TypedemoForm");
      case 1: 
        type = this.farmDocTypeManagerImpl.getType(typeid);
        return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("type", type)
          .returnModelAndView("doc/TypedemoForm");
      case 2: 
        demo = this.typedemoServiceImpl.getTypedemoEntity(ids);
        type = this.farmDocTypeManagerImpl.getType(demo.getTypeid());
        return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", demo).putAttr("type", type)
          .returnModelAndView("doc/TypedemoForm");
      }
      return ViewMode.getInstance().returnModelAndView("doc/TypedemoForm");
    }
    catch (Exception e)
    {
      return ViewMode.getInstance().setError(e + e.getMessage()).returnModelAndView("doc/TypedemoForm");
    }
  }
}
