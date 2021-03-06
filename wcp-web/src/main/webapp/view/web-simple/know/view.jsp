<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${DOCE.doc.title}-<PF:ParameterValue
		key="config.sys.title" /></title>
<meta name="description" content="${DOCE.doc.docdescribe}" />
<meta name="author" content="${DOCE.doc.author}">
<meta name="keywords" content="${DOCE.doc.tagkey}">
<meta name="robots" content="index,fllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script src="<PF:basePath/>text/lib/syntax-highlighter/brush.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<link href="<PF:basePath/>text/lib/syntax-highlighter/shCore.css" rel="stylesheet" />
<link href="<PF:basePath/>text/lib/syntax-highlighter/shThemeDefault.css" rel="stylesheet" />
</head>
<c:set var="typeid" value="${DOCE.type.id}" scope="request"></c:set>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row" style="margin-top: 70px;">
				<div class="col-md-3  visible-lg visible-md">
					<jsp:include page="../know/commons/includeNavigationDoc.jsp"></jsp:include>
					<c:if test="${USEROBJ.type==3}">
					<jsp:include page="../know/commons/includeUserBrowse.jsp"></jsp:include>
					</c:if>
				</div>
				<div class="col-md-9">
					<div class="panel panel-default">
						<div class="panel-body ke-content"><jsp:include
								page="../know/commons/doc.jsp"></jsp:include></div>
					</div>
					<div style="margin-top: 20px;"></div>
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-12">
									<!-- 标签 -->
									<jsp:include page="../know/commons/includeTagInfo.jsp"></jsp:include> 
									<!-- 附件 -->
									<jsp:include page="../know/commons/includeDocFiles.jsp"></jsp:include>
									<!-- remyxo, 相关知识 -->
									<jsp:include page="../know/commons/includeRelationDoc.jsp"></jsp:include>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<!-- 同类知识 -->
									<jsp:include page="../know/commons/includeTypeDoc.jsp"></jsp:include>
								</div>
								<div class="col-md-6">
									<!-- 相关知识 -->
									<jsp:include page="../lucene/commons/includeLuceneResultMin.jsp"></jsp:include>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<!-- 发表评论 -->
									<jsp:include page="commons/includeCommentsForm.jsp"></jsp:include>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-body">
							<jsp:include page="../know/commons/includeDocVersions.jsp"></jsp:include>
							<jsp:include page="../know/commons/includeAuthInfo.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>
			<div class="row column_box">
				<div class="col-md-12"></div>
			</div>
		</div>
	</div>
	<a name="markdocbottom"></a>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			$('img', '#docContentsId').addClass("img-thumbnail");
			SyntaxHighlighter.config.clipboardSwf = basePath
					+ 'text/lib/syntax-highlighter/clipboard.swf';
			SyntaxHighlighter.config.strings = {
				expandSource : '展开代码',
				viewSource : '查看代码',
				copyToClipboard : '复制代码',
				copyToClipboardConfirmation : '代码复制成功',
				print : '打印',
				help : '?',
				alert : '知识改变命运\n',
				noBrush : '不能找到刷子: ',
				brushNotHtmlScript : '刷子没有配置html-script选项',
				aboutDialog : '<div></div>'
			};
			SyntaxHighlighter.all();
		});
	</script>
</body>
</html>