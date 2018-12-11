<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmdoc.tld" prefix="DOC"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>编辑知识-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link rel="stylesheet" href="<PF:basePath/>text/lib/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/zh_CN.js"></script>
<link rel="stylesheet"  href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.css" />
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<style>
<!--
.form-group .ke-button-common {
	background-image: none;
	background-color: transparent;
	margin: 0px;
	height: 26px;
}

.form-group .ke-button {
	cursor: pointer;
	color: #fff;
	margin: 0px;
}
-->
</style>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container">
			<div class="row column_box ">
				<div class="col-md-3  visible-lg visible-md">
					<jsp:include page="commons/includeNavigationDocEdit.jsp"></jsp:include>
				</div>
				<div class="col-md-9">
					<div class="row">
						<div class="col-md-12">
							<ol class="breadcrumb">
								<li class="active">WCP</li>
								<li class="active">编辑知识</li>
							</ol>
						</div>
					</div>
					<form role="form" action="know/editsubmit.do" id="knowSubmitFormId"
						method="post">
						<input type="hidden" name="docid" value="${doce.doc.id}" />
						<DOC:canNoDelIsShow docId="${doce.doc.id}">
							<div class="row">
								<div class="col-md-12">
									<span
										style="color: #008000; font-size: 12px; font-weight: lighter;">作者:<span
										style="color: #D9534F; font-size: 14px; font-weight: bold;">${doce.doc.author}</span>于<PF:FormatTime
											date="${doce.doc.pubtime}" yyyyMMddHHmmss="yyyy年MM月dd日" />发布
									</span>
									<h1>
										<a href="webdoc/view/Pub${doce.doc.id}.html">
											${doce.doc.title}</a> <small>[编辑:<c:if
												test="${doce.doc.writepop==1}">
									公开
									</c:if> <c:if test="${doce.doc.writepop==0}">
									私有
									</c:if> <c:if test="${doce.doc.writepop==2}">
									小组
									</c:if> ][读:<c:if test="${doce.doc.readpop==1}">
									公开
									</c:if> <c:if test="${doce.doc.readpop==0}">
									私有
									</c:if> <c:if test="${doce.doc.readpop==2}">
									小组
									</c:if> ]
										</small>
									</h1>
									<h1>
										<span class="typetagsearch" style="cursor: pointer;"
											title="${doce.type.name}"> <small>${doce.type.name}</small>
										</span>
									</h1>
								</div>
							</div>
						</DOC:canNoDelIsShow>
						<DOC:canDelIsShow docId="${doce.doc.id}">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="exampleInputEmail1"> 标题 <span
											class="alertMsgClass">*</span>
										</label> <input type="text" class="form-control" name="knowtitle"
											value="${doce.doc.title}" id="knowtitleId"
											placeholder="输入知识标题" />
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="exampleInputEmail1"> 文档分类 <span
											class="alertMsgClass">*</span>
										</label>
										<div class="row">
											<div class="col-md-9">
												<input type="text" class="form-control" id="knowtypeTitleId"
													readonly="readonly" placeholder="选择栏目分类"
													value="${doce.type.name}" /> <input type="hidden"
													name="knowtype" id="knowtypeId" value="${doce.type.id}" />
											</div>
											<div class="col-md-3">
												<button class="btn btn-info btn-sm" data-toggle="modal"
													id="openChooseTypeButtonId" data-target="#myModal">
													选择分类</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</DOC:canDelIsShow>
						<!-- remyxo, -->
						<div class="row">
							<div class="col-md-12">
								<textarea name="text" id="contentId"
									style="height: 500px; width: 100%;">${doce.texts.text1}</textarea>
							</div>
						</div>
						<!-- remyxo, to leave a line after text1 -->
						<br />
						<!-- end of add -->
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="exampleInputEmail1"> 标签 </label> <input
										type="text" class="form-control" id="knowtagId"
										value="${doce.doc.tagkey}" name="knowtag"
										placeholder="输入类别标签(如果没有系统将自动创建),多个标签间用逗号或空格分隔" />
								</div>
							</div>
						</div>
						<!-- added by remyxo, 2017-11-01 -->
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="exampleInputEmail1"> 关联知识 </label>
									<div class="row">
										<input type="hidden" name="relationdocs" value=""
											id="relationdocsid" />
										<input type="hidden" name="relationfiles" value=""
											id="relationfilesid" />
										<div id="docrelation_tagbox" class="col-md-12">
											<c:forEach items="${reladocs}" var="node">
												<div class="doc_relation_tag">
													<span class='glyphicon glyphicon-book'></span>&nbsp;
													<span class="relation_tagname" id="tag${node.id}">${node.title}</span>
													<span onclick="removeRelationTag('${node.id}',this)"
														class="glyphicon glyphicon-remove tagbutton"></span>
												</div>
											</c:forEach>
										</div>
										<div class="col-md-12">
											<button class="btn btn-info btn-sm" data-toggle="modal"
												style="margin-left: 4px;" id="openaAddRelationButtonId">添加关联知识</button>
											
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- end -->
						<!-- remyxo,2017-11-20 -->
						<div class="row">
							<DOC:canDelIsShow docId="${doce.doc.id}">
								<div class="col-md-12">
									<div class="form-group">
										<label for="exampleInputEmail1">内容图 </label>
										<div class="row">
											<div class="col-md-12">
												&nbsp;<input type="button" id="uploadButton"
													class="btn btn-info btn-xs center-block"
													style="padding: 0px; padding-bottom: 8px;" value="上传内容图" /> <input
													type="hidden" id="fileId" name="fileId"
													value="${doce.doc.imgid}">
												<button type="button" id="showImg"
													class="btn btn-info btn-sm" title="预览"
													data-container="body" data-toggle="popover"
													data-placement="bottom"
													<c:if test="${empty doce.doc.imgid }">
														style="display: none;"
														</c:if>
													data-trigger="focus"
													data-content="<img class='img-thumbnail center-block' style='width:270px;' />">预览</button>
												<button type="button" id="delImg"
													class="btn btn-info btn-sm" title="删除"
													<c:if test="${empty doce.doc.imgid }">
														style="display: none;"
														</c:if>
													onclick="_delImg();">删除</button>
											</div>
										</div>
									</div>
								</div>
								<script type="text/javascript">
									//上传内容图
									KindEditor.ready(function(K) {
												var uploadbutton = K.uploadbutton({
															button : K('#uploadButton')[0],
															fieldName : 'imgFile',
															url : basePath+ 'actionImg/PubFPuploadImg.do',
															afterUpload : function(data) {
																if (data.error === 0) {
																	$("#showImg").attr("data-content",
																					'<img class="img-thumbnail center-block" style="width:270px;" alt="内容图" src="actionImg/Publoadfile.do?id='
																							+ data.id+ '">');
																	$("#fileId").val(data.id);
																	$("#showImg").attr("style","");
																	$("#delImg").attr("style","");
																} else {
																	alert(data.message);
																}
															},
															afterError : function(str) {
																alert('自定义错误信息: '	+ str);
															}
														});
												uploadbutton.fileBox.change(function(e) {
															uploadbutton.submit();
														});
											});
								</script>
							</DOC:canDelIsShow>
						</div>
						<!-- end,2017-11-20 -->
						<div class="row">
							<DOC:canDelIsShow docId="${doce.doc.id}">
								<c:if test="${doce.doc.docgroupid==null}">
									<div class="col-md-4">
										<div class="form-group">
											<label for="exampleInputEmail1"> 是否发布到小组 </label> <select
												class="form-control" name="docgroup" id="docgroupId"
												val="${doce.doc.docgroupid}">
												<option value="0">否</option>
												<DOC:docGroupOption aroundS="[工作小组]:" />
											</select>
										</div>
									</div>
								</c:if>
							</DOC:canDelIsShow>
							<c:if test="${doce.doc.docgroupid!=null}">
								<input type="hidden" name="docgroupId"
									value="${doce.doc.docgroupid}" />
							</c:if>
							<DOC:canDelIsShow docId="${doce.doc.id}">
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputEmail1"> 编辑权限 <span
											class="alertMsgClass">*</span>
										</label> <select class="form-control" name="writetype"
											id="writetypeId" val="${doce.doc.writepop}">
											<option value="">~请选择~</option>
											<option value="0">创建人</option>
											<option value="1">分类</option>
											<option value="2">小组</option>
										</select>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputEmail1"> 阅读权限 <span
											class="alertMsgClass">*</span>
										</label> <select class="form-control" name="readtype" id="readtypeId"
											val="${doce.doc.readpop}">
											<option value="">~请选择~</option>
											<option value="0">创建人</option>
											<option value="1">分类</option>
											<option value="2">小组</option>
										</select>
									</div>
								</div>
							</DOC:canDelIsShow>
						</div>
						<!-- 修改原因 -->
						<c:if test="${doce.doc!=null&&doce.doc.id!=null}">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label for="exampleInputEmail1"> 修改原因 <span
											class="alertMsgClass">*</span>
										</label> <input type="text" class="form-control" id="knowEditNoteId"
											name="editNote" placeholder="修改原因将被记录在版本备注中" />
									</div>
								</div>
							</div>
							<script type="text/javascript">
								$(function() {
									validateInput('knowEditNoteId', function(
											id, val, obj) {
										// 分类
										if (valid_isNull(val)) {
											return {
												valid : false,
												msg : '不能为空'
											};
										}
										return {
											valid : true,
											msg : '正确'
										};
									});
								});
							</script>
						</c:if>
						<div class="row column_box">
							<div class="col-md-2">
								<button type="button" id="knowSubmitButtonId"
									class="btn btn-info">提交</button>
							</div>
							<div class="col-md-10">
								<span class="alertMsgClass" id="errormessageShowboxId"></span>
							</div>
						</div>
						<div class="row column_box">
							<div class="col-md-12"></div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/view/web-simple/type/commons/includeChooseTypes.jsp"></jsp:include>
	<jsp:include page="/view/web-simple/know/commons/includeChooseDoc.jsp"></jsp:include> <!-- remyxo,2017-11-01 -->
	<!-- /.modal -->
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	var editor = null;
	$(function() {
		$('a', '.showLableType').bind('click', function() {
			$('#knowtypeId').val($(this).attr('id'));
			$('#knowtypeTitleId').val($(this).text());
			$('#myModal').modal('hide');
		});
		editor = KindEditor.create('textarea[id="contentId"]', {
			resizeType : 1,
			afterChange : function() {
				//生成导航目录
				initLeftMenuFromHtml(this.html());
			},
			cssPath : '<PF:basePath/>text/lib/kindeditor/editInner.css',
			uploadJson : basePath + 'actionImg/PubFPuploadImg.do',
			formatUploadUrl : false,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			items : [ 'source', 'fullscreen', '|', 'fontsize', 'forecolor',
					'bold', 'italic', 'underline','strikethrough','removeformat', '|',
					'justifyleft', 'justifycenter', 'justifyright',
					'insertorderedlist', 'insertunorderedlist', 'lineheight',
					'|', 'formatblock', 'quickformat', 'table', 'hr',
					'pagebreak', '|', 'link', 'image', 'code', 'insertfile',
					'wcpknow' ]
		});
		$('#openChooseTypeButtonId').bind('click', function() {
			$('#myModal').modal({
				keyboard : false
			})
		});
		$('#openaAddRelationButtonId').bind('click', function() {
			$('#myDocs').modal({
				keyboard : false
			})
		});
		$('select', '#knowSubmitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		$('#knowSubmitButtonId').bind(
			'click',
			function() {
				editor.sync();
				if (!validate('knowSubmitFormId')) {
					$('#errormessageShowboxId').text('信息录入有误，请检查！');
				} else {
					if ($('#contentId').val() == null || $('#contentId').val() == '') {
						$('#errormessageShowboxId').text('请录入文档内容！');
						return false;
					}
					if ($('#contentId').val().length > 524288) {
						$('#errormessageShowboxId').text(
							'文档内容超长（' + $('#contentId').val().length + '>512KB）');
						return false;
					}
					parseRelasDocsId();
					if (confirm("是否提交数据?")) {
						$('#knowSubmitFormId').submit();
						$('#knowSubmitButtonId').hide();
						$('#knowSubmitButtonId').before(
							"<h2><span class='label label-info glyphicon glyphicon-link'>提交中...</span></h2>");
					}
				}
			});
		validateInput('knowtypeTitleId', function(id, val, obj) {
			// 分类
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//绑定一个表单的验证事件
		validateInput('knowtitleId', function(id, val, obj) {
			// 标题
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 128)) {
				return {
					valid : false,
					msg : '长度不能大于' + 128
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//绑定一个表单的验证事件
		validateInput('knowtagId', function(id, val, obj) {
			// 学生姓名
			if (valid_maxLength(val, 128)) {
				return {
					valid : false,
					msg : '长度不能大于' + 128
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//编辑权限
		validateInput('writetypeId', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (val == '2' && $('#docgroupId').val() == '0') {
				return {
					valid : false,
					msg : '请选择工作小组'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//阅读权限
		validateInput('readtypeId', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (val == '2' && $('#docgroupId').val() == '0') {
				return {
					valid : false,
					msg : '请选择工作小组'
				};
			}
			if ($('#docgroupId').val() != '0') {
				if (val == '0') {
					return {
						valid : false,
						msg : '阅读权限至少是小组'
					};
				}
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//工作小组
		validateInput('docgroupId', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
	});
	
	// remyxo,2017-11-20
	//激活弹窗插件
	$("[data-toggle='popover']").popover({
		html : true
	});
	$("#showImg").attr("data-content",
					'<img class="img-thumbnail center-block" style="width:270px;" alt="内容图" src="actionImg/Publoadfile.do?id=${doce.doc.imgid }">')
	function _delImg() {
		$("#fileId").val("");
		$("#showImg").attr("style", "visibility: hidden;");
		$("#delImg").attr("style", "visibility: hidden;");
		$("[data-toggle='popover']").popover('hide');
	}
	// end, 2017-11-20
</script>
</html>
