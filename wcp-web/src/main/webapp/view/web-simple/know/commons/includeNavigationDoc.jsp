<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 文档中的目录导航 -->
<style>
<!--
.affix1 {
	top: 54px;
	width: 250px;
}
-->
</style>
<div class="panel panel-default " id="docContentMenuId"
	style="z-index: 1000;" width="95%">
	<div class="panel-heading">
		<span class="glyphicon glyphicon-list-alt"></span> 目录[ <a
			id="topbuttonId">返回顶端</a>][ <a id="buttombuttonId">返回底端</a>]
	</div>
	<div class="panel-body" id="docContentPanelbody"
		style="line-height: 20px; list-style: none; padding: 4px;margin-left: -6px;">
		<ul id="ContentMenuId" class="doc_vavigat"></ul>
	</div>
	<div class="panel-heading">
		<ul style="margin-left: -20px;">
			<li><a style="color: #D9534F;" id='markVersionListSuperLink'>文档版本（${fn:length(VERSIONS)}）</a>
			</li>
			<c:if test="${fn:length(DOCE.files)>0}">
				<li><a style="color: #D9534F;" id='markFileListSuperLink'>文档附件（${fn:length(DOCE.files)}）</a>
				</li>
			</c:if>
		</ul>
		<div style="text-align: center;">
			<jsp:include page="includeDocEvaluate.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
	var n1 = 1;
	var n2 = 1;
	var n3 = 1;
	function creatMenus(i, obj) {
		if ($(obj).get(0).tagName == 'H1') {
			$('#ContentMenuId').append(
					'<li class="doc_vavigat_h1"><a  id="linkmark'
							+ i + '">'
							+ $(obj).text() + '</a></li>');
			n1++;
			n2 = 1;
			n3 = 1;
		}
		if ($(obj).get(0).tagName == 'H2') {
			$('#ContentMenuId')
					.append(
							'<li class="doc_vavigat_h2"><a  id="linkmark'
									+ i + '">'
									+ $(obj).text()
									+ '</a></li>');
			n2++;
			n3 = 1;
		}
		if ($(obj).get(0).tagName == 'H3') {
			$('#ContentMenuId').append(
					'<li class="doc_vavigat_h2"><a  id="linkmark'
									+ i + '">'
							+ $(obj).text() + '</a></li>');
			n3++;
		}
	}
	function initLeftMenu() {
		if(${sessionScope.USEROBJ.type}!=3){
		 $('#docContentMenuId').affix({
			offset : {
				top : 50,
				bottom : 5
			}
		}) }
		$('h1,h2,h3', '#docContentsId').each(function(i, obj) {
			creatMenus(i, obj);
			$(obj).before("<a name='mark" + i + "'></a>");
			$('#linkmark' + i).bind('click', function() {
				$('html,body').animate({
					scrollTop : $("a[name='mark" + i + "']").offset().top - 100
				}, 500);
			});
		});
		if ($.trim($('#ContentMenuId').text()) != null
				&& $.trim($('#ContentMenuId').text()) != "") {
			$('#ContentMenuId').css('height', $(window).height() - 400);
		} else {
			$('#docContentPanelbody').remove();
		}
		$('#ContentMenuId').css('overflow-y', 'auto');
	}
	$(function() {
		$('#topbuttonId').bind('click', function() {
			//window.scrollTo(0, 0);
			$('html,body').animate({
				scrollTop : 0
			}, 500);
		});
		$('#markDocPriceSuperLink').bind('click', function() {
			//location.hash = "markFileList";
			$('html,body').animate({
				scrollTop : $("a[name='markDocPrice']").offset().top - 50
			}, 500);
		});
		$('#buttombuttonId').bind('click', function() {
			//location.hash = "markbuttombutton";
			$('html,body').animate({
				scrollTop : $("a[name='markdocbottom']").offset().top
			}, 500);
		});
		$('#markFileListSuperLink').bind('click', function() {
			//location.hash = "markbuttombutton";
			$('html,body').animate({
				scrollTop : $("a[name='markdocFile']").offset().top
			}, 500);
		});
		$('#markVersionListSuperLink').bind('click', function() {
			//location.hash = "markVersionList";
			$('html,body').animate({
				scrollTop : $("a[name='markdocVersions']").offset().top
			}, 500);
		});
		initLeftMenu();
	});
	var wd = $(".col-md-3").width();
	jQuery(window).scroll(
			function(){ $("#docContentMenuId").css("width", wd);}
		);
</script>
