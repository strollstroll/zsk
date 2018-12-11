<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${fn:length(RELATIONDOCS)>0}">
	<!-- 分类下的知识列表，小的列表页面(缩写) -->
	<div class="row doc_column_box">
		<div class="col-sm-12">
			<span class=" glyphicon glyphicon-certificate column_title">&nbsp;关联知识</span>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<hr class="hr_split" />
			<div id="newTypeBoxId">
				<ol>
					<c:forEach items="${RELATIONDOCS}" varStatus="status" var="node">
						<c:if test="${status.index%2==0}">
							<li><a href="webdoc/view/Pub${node.id}.html">${node.title}</a>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</div>
		<div class="col-md-6">
			<hr class="hr_split" />
			<div id="newTypeBoxId">
				<ol>
					<c:forEach items="${RELATIONDOCS}" varStatus="status" var="node">
						<c:if test="${status.index%2==1}">
							<li><a href="webdoc/view/Pub${node.id}.html">${node.title}</a>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</div>
	</div>
</c:if>