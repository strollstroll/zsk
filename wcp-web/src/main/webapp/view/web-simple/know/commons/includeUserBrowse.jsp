<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="panel panel-default" style="background-color: #FCFCFA;">
	<div class="panel-body">
		<p>
			<span class="glyphicon glyphicon-user  ">用户浏览排名</span>
		</p>
		<table class="table" style="font-size: 12px;">
			<tr>
				<th>排名</th>
				<th>用户</th>
				<th>浏览次数</th>
			</tr>
			<c:forEach items="${goodBrowseUsers}" varStatus="status" var="node">
				<tr>
					<td>${status.index+1}</td>
					<td>${node.cusername}</td>
					<td>${node.browsenum}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>