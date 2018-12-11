<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="modal fade" id="myDemos" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">选择模板</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped" id="demoloadboxid">
				</table>
				<div id="demoloadinfoboxid">加载模板中...</div>
			</div>
			<div class="modal-footer">
				<button type="button"  class="btn btn-default" data-dismiss="modal">
					关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<script type="text/javascript">
	var demos;
	//加载模板
	function loadTypeDemo(typeid) {
		if (!typeid) {
			return false
		}
		$.post('typedemo/demolist.do', {
			'typeid' : typeid
		}, function(flag) {
			$('#demoloadinfoboxid').html("");
			$('#demoloadboxid').html("");
			$('#demoloadboxid').append(
					"<tr><th>模板名称</th><th >描述</th><th ></th></tr>");
			var demosobj = $.parseJSON(flag);
			demos=new Array();
			$(demosobj.DEMOS).each(function(i, obj) {
				var html = new Array();
				demos.push(obj.demotext);
				html.push("<tr>");
				html.push("<td>" + obj.name + "</td>");
				html.push("<td >" + obj.pcontent + "</td>");
				var buttonhtml="<button onclick='userDemo("+i+")' type='button' class='btn btn-primary btn-xs userdemocs'>使用</button>";
				html.push("<td >"+buttonhtml+"</td>");
				html.push("</tr>");
				$('#demoloadboxid').append(html.join(""));
			});
		});
		return true;
	}
	function ishaveDemoRun(typeid,func,func2){
		if (!typeid) {
			return false
		}
		$.post('typedemo/demolist.do', {
			'typeid' : typeid
		}, function(flag) {
			var ishave=false;
			var demosobj = $.parseJSON(flag);
			$(demosobj.DEMOS).each(function(i, obj) {
				ishave= true;
			});
			if(ishave){
				if(func){
					func(typeid);
				}
			}else{
				if(func2){
					func2(typeid);
				}
			}
		});
	}
	function userDemo(i){
		if(editor.html()){
			if(!confirm("已有知识内容存在，是否用模板覆盖知识?")){
				return;
			}
		}
		editor.html(demos[i]);
		$('#myDemos').modal('hide');
	}
</script>