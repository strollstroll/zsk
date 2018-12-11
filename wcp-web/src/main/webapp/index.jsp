<%@page import="com.farm.parameter.service.impl.ConstantVarService"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.farm.parameter.FarmParameterService"%>
<%@page import="com.farm.core.Context"%>
<%@page import="com.farm.doc.domain.ex.TypeBrief"%>
<%@page import="com.farm.util.spring.BeanFactory"%>
<%@page import="com.farm.doc.server.FarmDocTypeInter"%>
<%@page import="com.farm.doc.domain.FarmDoctype"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/farmdoc.tld" prefix="DOC"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title><PF:ParameterValue key="config.sys.title" /></title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link href="text/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="text/lib/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<script src="text/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="text/lib/bootstrap/respond.min.js"></script>
</head>
<body>
	<div class="container" style="padding-top: 100px;">
        	<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4 text-center">
				<img src="text/img/homelogo.png" width="237px" height="85px" alt="知识库">
			</div>
			<div class="col-md-4"></div>
		</div>

        	<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4 text-center">
				<h1>
	                                <a href="<DOC:defaultIndexPage/>">进入主站</a>
					</a>
				</h1>
				Loading...
			</div>
			<div class="col-md-4"></div>
		</div>
		<hr />
		<div class="row">
			<%
				FarmDocTypeInter aloneIMP = (FarmDocTypeInter) BeanFactory.getBean("farmDocTypeManagerImpl");
				List<TypeBrief> types = aloneIMP.getPubTypes();
				for (TypeBrief type : types) {
			%>
			<div class="col-md-3">
				<a href="webtype/view<%=type.getId()%>/Pub1.html"><%=type.getName()%></a>
			</div>
			<%
				}
			%>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 text-center" style="color: #b7b8b8;">
				<hr />
				<PF:ParameterValue key="config.sys.foot" />
				-V
				<PF:ParameterValue key="config.sys.version" />
			</div>
		</div>
    	</div>
</body>
<script type="text/javascript">
	//window.location = '<DOC:defaultIndexPage/>';
	setTimeout("window.location = '<DOC:defaultIndexPage/>'", 1000);
</script>
</html>
