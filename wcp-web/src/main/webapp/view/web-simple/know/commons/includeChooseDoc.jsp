<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="modal fade" id="myDocs" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 700px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">选择知识</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="input-group" style="max-width: 250px;">
						<input name="word" id="searchDocCaseId" value=""
							class="form-control input-sm" placeholder="输入知识名称或分类名称查询"
							type="text"><span class="input-group-btn">
							<button type="button" id="docsSearchButtonId"
								class="btn btn-default btn-sm">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							</button>
						</span>
					</div>
				</div>
				<table class="table table-striped" id="docsloadboxid">
				</table>
				<div id="searchdocsloadinfoboxid">加载知识中...</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<script type="text/javascript">
	$(function() {
		$('#docsSearchButtonId').click(function() {
			loadRelaDocs();
		});
		loadRelaDocs();
	});
	//提交表单的时候，生成关联知识
	function parseRelasDocsId(){
		//合计关联知识id
		var relationDocIds;
		$('.relation_tagname').each(function(i,obj){
			if(!relationDocIds){
				relationDocIds=$(obj).attr('id').replace("tag", "");
			}else{
				relationDocIds=relationDocIds+','+$(obj).attr('id').replace("tag", "");
			}
		});
		var relationFileIds;
		$('.relation_file_tagname').each(function(i,obj){
			if(!relationFileIds){
				relationFileIds=$(obj).attr('id').replace("tag", "");
			}else{
				relationFileIds=relationFileIds+','+$(obj).attr('id').replace("tag", "");
			}
		});
		$('#relationfilesid').val(relationFileIds);
		$('#relationdocsid').val(relationDocIds);
	}
	function userRelation(docid, docname) {
		if($('#tag'+docid).length > 0){
			alert('该知识已经存在!');
			return;
		}
		$('#docrelation_tagbox').append(creatKnowtagHtml(docid,docname));
		$('#myDocs').modal('hide');
	}
	
	function creatKnowtagHtml(docid,docname){
		var html = new Array();
		html.push("<div class='doc_relation_tag'> <span class='glyphicon glyphicon-book'></span>&nbsp;");
		html.push("<span class='relation_tagname' id='tag"+docid+"'>" + docname
				+ "</span> ");
		html.push("<span onclick=\"removeRelationTag('"+docid+"',this)\" class='glyphicon glyphicon-remove tagbutton'></span>");
		html.push("</div>");
		return html.join("");
	}
	function creatFiletagHtml(docid,docname){
		var html = new Array();
		html.push("<div class='doc_relation_tag'> <span class='glyphicon glyphicon-file'></span>&nbsp;");
		html.push("<span class='relation_file_tagname' id='tag"+docid+"'>" + docname
				+ "</span> ");
		html.push("<span onclick=\"removeRelationTag('"+docid+"',this)\" class='glyphicon glyphicon-remove tagbutton'></span>");
		html.push("</div>");
		return html.join("");
	}
	function removeRelationTag(docid,obj){
		$(obj).parent('.doc_relation_tag').remove();
	}
	function loadRelaDocs() {
		$.post('home/FPsearchKnow.do',
						{
							'wordcase' : $('#searchDocCaseId').val()
						},
						function(flag) {
							$('#searchdocsloadinfoboxid').html("");
							$('#docsloadboxid').html("");
							$('#docsloadboxid')
									.append(
											"<tr><th>知识名称</th><th >知识类型</th><th >分类</th><th ></th></tr>");
							var demosobj = $.parseJSON(flag);
							demos = new Array();
							$(demosobj.list)
									.each(
											function(i, obj) {
												var html = new Array();
												demos.push(obj.demotext);
												html.push("<tr>");
												html.push("<td>" + obj.TITLE
														+ "</td>");
												html.push("<td >" + obj.DOMTYPE
														+ "</td>");
												html.push("<td >"
														+ obj.TYPENAME
														+ "</td>");
												var buttonhtml = "<button onclick='userRelation(\""
														+ obj.ID
														+ "\",\""
														+ obj.TITLE
														+ "\")' type='button' class='btn btn-primary btn-xs userdemocs'>关联</button>";
												html.push("<td >" + buttonhtml
														+ "</td>");
												html.push("</tr>");
												$('#docsloadboxid').append(
														html.join(""));
											});
						});
		return true;
	}
</script>