<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/farmdoc.tld" prefix="DOC"%>
<!--40 <span style="font-size: 14px; font-weight: bold;margin: 20px;"> 系统管理控制台2.0.0</span> -->
<div>
	<table width="100%">
		<tr>
<%-- 			<td style="width: 185px;"><a style="font-size:35px;color:#fff"
				href='<DOC:defaultIndexPage/>'> <img
					alt="<PF:ParameterValue key="config.sys.title" />"
					src="text/img/logo_bw.png"
					style="padding: 2px 0 0 0; max-height: 42px" />
					知识库
			</a></td> --%>
			<td style="width: 185px;"><a style="font-size:35px;color:#fff"
				href='<DOC:defaultIndexPage/>'> <img
					alt="<PF:ParameterValue key="config.sys.title" />"
					src="text/img/logo_bwhead.png"
					style="padding: 2px 0 0 0; max-height: 42px" />
					
			</a></td>
			<td class="head_logo1">&nbsp;</td>
			<td
				style="float: right; width: 450px; padding-top: 7px; color: #fff;">
				<em class="top_right3"> 
					<span class="spanIcon7">当前登录
					        <c:forEach var="node" varStatus="index" items="${sessionScope.LOGINROLES}">
							<c:if test="${index.count!=1}">,</c:if>
							${node.name}
						</c:forEach> ：${sessionScope.USEROBJ.name}
					</span>
					<span class="spanIcon9"><a id="logout_a">退出系统</a></span> 
					<span class="spanIcon10"><a id="editPW_a" target="main">修改密码</a></span>
				</em>
			</td>
		</tr>
	</table>
</div>
<div id="DIV_EDIT_PASSWORD_WINDOW"></div>
<script type="text/javascript">
	var lastNum;
	$('#logout_a').bind('click', function() {
		$.messager.confirm('退出系统', '确定要注销此用户并退出系统吗？', function(r) {
			if (r) {
				window.location = basePath + "login/out.do";
			}
		});
	});
	$('#editPW_a').bind('click', function() {
		$('#DIV_EDIT_PASSWORD_WINDOW').dialog({
			title : '修改用户密码',
			width : 300,
			height : 200,
			closed : false,
			cache : false,
			href : 'user/updatePassword.do',
			modal : true
		});
	});
</script>
