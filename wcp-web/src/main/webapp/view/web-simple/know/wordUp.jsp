<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmdoc.tld" prefix="DOC"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>由word创建知识-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/zh_CN.js"></script>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-search-kindeditor.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<link rel="stylesheet" type="text/css"
	href="text/lib/uploadify/uploadify.css">
<script type="text/javascript"
	src="text/lib/uploadify/jquery.uploadify-3.1.min.js"></script>
<style>
<!--
.ke-button-common {
	background-image: none;
	background-color: transparent;
	margin: 0px;
	height: 26px;
}

.ke-button {
	cursor: pointer;
	color: #fff;
	margin: 0px;
	font-size: 14px;
}
-->
</style>
</head>
<body style="background:#fff">
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row column_box">
				<div class="col-md-1  visible-lg visible-md"></div>
				<div class="col-md-10">
					<div class="row">
						<div class="col-md-12">
							<ol class="breadcrumb">
								<li>知识库</li>
								<li>创建知识</li>
								<li class="active">由word文件创建知识</li>
							</ol>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<c:if test="${pageset.commitType=='1'}">
								<div class="alert alert-danger">${pageset.message}</div>
							</c:if>
							<div class="panel panel-default" style="margin-top: 50px;">
								<div class="panel-body text-center">点击下方按钮，上传一个docx格式的word文档，由该文档内容来创建超文本知识。</div>
							</div>
							<div class="text-center">
								<form role="form"
									action="pro/wordDLoadDo.do?typeid=${typeid }&groupid=${groupid}"
									id="knowSubmitFormId" method="post">
									<input type="hidden" name="fileid" id="fileboxid">
									<div class="form-group" id="upbuttonId">
										<input type="button" class="btn btn-info btn-xs center-block"
											style="padding: 0px; padding-bottom: 8px; font-family: 18px;"
											id="uploadButton" value="选择word文档并创建超文本知识(请选择docx文件)" />
									</div>
									<div class="form-group" id="upmessageId">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade bs-example-modal-sm" tabindex="-1"
			role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">...</div>
			</div>
		</div>
	</div>
	<!-- /.modal -->
	<div class="footer navbar-fixed-bottom">
	<jsp:include page="../commons/foot.jsp"></jsp:include></div>
	<script>
		KindEditor.ready(function(K) {
			var uploadbutton = K.uploadbutton({
				button : K('#uploadButton')[0],
				fieldName : 'imgFile',
				url : basePath + 'actionImg/PubFPupload.do?limittypes=docx',
				afterUpload : function(data) {
					if (data.error === 0) {
						$('#fileboxid').val(data.id);
						$('#upbuttonId').hide();
						$('#upmessageId').html('<p class="text-warning"><h3>提交中......</h3></p>'); 
						$('#knowSubmitFormId').submit();
					} else {
						alert(data.message);
					}
				},
				afterError : function(str) {
					alert('自定义错误信息: ' + str);
				}
			});
			uploadbutton.fileBox.change(function(e) {
				uploadbutton.submit();
			});
		});
	</script>
</body>
</html>