<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- ID,TITLE,DOCDESCRIBE,AUTHOR,PUBTIME,TAGKEY ,IMGID,VISITNUM,PRAISEYES,PRAISENO,HOTNUM,TYPENAME -->
<!-- Carousel  ================================================== -->
<style>
<!--
.layer-btn {
	background-color: transparent;
	color: #ffffff;
	border: solid 1px #ffffff;
	padding: 8px;
	padding-left: 16px;
	padding-right: 16px;
}
.layer-btn:HOVER {
	background-color: #ffffff;
	color: #000000;
        border: solid 1px #ffffff;
        padding: 8px;
        padding-left: 16px;
        padding-right: 16px;
}
-->
</style>
<div id="myCarousel" class="carousel slide" data-ride="carousel" style="min-height:250px;">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<c:forEach var="topDoc" items="${topDocList}" varStatus="varstatus">
			<c:if test="${topDoc.imgurl!=null&&varstatus.index<2}">
				<c:if test="${varstatus.index==0}">
					<li data-target="#myCarousel" data-slide-to="${varstatus.index}" class="active"></li>
				</c:if>
				<c:if test="${varstatus.index!=0}">
					<li data-target="#myCarousel" data-slide-to="${varstatus.index}"></li>
				</c:if>
			</c:if>
		</c:forEach>
	</ol>
	<div class="carousel-inner" role="listbox">
		<c:forEach var="topDoc" items="${topDocList}" varStatus="varstatus">
			<c:if test="${topDoc.imgurl!=null&&varstatus.index<2}">
				<c:if test="${varstatus.index==0}">
					<div class="item active" style="background-color: #393d42;">
						<div class="row" style="text-align: right;">
							<img class="first-slide" style="width: 100%; height: 250px;" src="${topDoc.imgurl}" alt="${topDoc.title}">
						</div>
						<div class="row" style="max-width:650px;">
							<div class="carousel-caption">
								<h1>${topDoc.title}</h1>
								<p>${topDoc.text}</p>
								<div style="margin: 24px; font-size: 16px;">	
									<a class="layer-btn" href="webdoc/view/Pub${topDoc.docid}.html" role="button">查看详情</a>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${varstatus.index!=0}">
					<div class="item" style="background-color: #393d42;">
						<div class="row" style="text-align: right;">
							<img class="first-slide" style="width: 100%; height: 250px;" src="${topDoc.imgurl}" alt="${topDoc.title}">
						</div>
						<div class="row" style="max-width:650px">
							<div class="carousel-caption">
								<h1>${topDoc.title}</h1>
								<p>${topDoc.text}</p>
								<div style="margin: 24px; font-size: 16px;">
									<a class="layer-btn" href="webdoc/view/Pub${topDoc.docid}.html" role="button">查看详情</a>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</c:if>
		</c:forEach>
	</div>
	<a class="left carousel-control" href="#myCarousel" role="button"
		data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
		aria-hidden="true"></span> <span class="sr-only">Previous</span>
	</a> <a class="right carousel-control" href="#myCarousel" role="button"
		data-slide="next"> <span class="glyphicon glyphicon-chevron-right"
		aria-hidden="true"></span> <span class="sr-only">Next</span>
	</a>
</div>
