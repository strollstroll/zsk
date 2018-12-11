<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="widget-box shadow-box">
	<div class="title">
		<h3>
			<i class="glyphicon glyphicon-star"></i> 最新知识<a
				style="float: right; font-size: 12px;color: #999;"
				href="webtype/view/Pub1.html">查看更多~</a>
		</h3>
	</div>
	<div class="stream-list p-stream">
		<div class="row">
			<div class="col-md-6">
				<ul class="box-list" id="hots">
					<c:forEach items="${newdocs}" varStatus="status" var="node">
						<c:if test="${status.index%2==0}">
							<li><div class="li-out">
								<span class="last"> </span><span class="name"
									style="width: 100%;"> <span style="font-size: 10px;">
									<PF:FormatTime date="${node.pubtime}" yyyyMMddHHmmss="yyyy-MM-dd" />
									</span> 
									<c:if test="${node.domtype==1}">
										<i class="glyphicon glyphicon-file"></i>
									</c:if> <c:if test="${node.domtype==5}">
										<i class="glyphicon glyphicon-download-alt"></i>
									</c:if> <a href="webdoc/view/Pub${node.docid}.html">${node.title}</a></span>
							</div></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="col-md-6">
				<ul class="box-list" id="hots">
					<c:forEach items="${newdocs}" varStatus="status" var="node">
						<c:if test="${status.index%2==1}">
							<li><div class="li-out">
								<span class="last"> </span><span class="name"
									style="width: 100%;"> <span style="font-size: 10px;">
									<PF:FormatTime date="${node.pubtime}" yyyyMMddHHmmss="yyyy-MM-dd" />
									</span> 
									<c:if test="${node.domtype==1}">
										<i class="glyphicon glyphicon-file"></i>
									</c:if> <c:if test="${node.domtype==5}">
										<i class="glyphicon glyphicon-download-alt"></i>
									</c:if> <a href="webdoc/view/Pub${node.docid}.html">${node.title}</a></span>
							</div></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div style="margin-top: 20px;"></div>
	</div>
</div>
