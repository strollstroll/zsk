<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>分类模板数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/themes/default/default.css">
<script type="text/javascript"
	src="text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="text/lib/kindeditor/zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 200px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="FarmdocTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新菜单</a> <a id="FarmdocTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">全部展开</a>
		</div>
		<ul id="FarmdocTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchTypedemoForm">
				<table class="editTable">
					<tr>
						<td class="title">分类:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="B.TREECODE:like" type="hidden"></td>
						<td class="title"></td>
						<td></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="4"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataTypedemoGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="40">模板名称</th>
						<th field="PCONTENT" data-options="sortable:true" width="80">备注</th>
						<th field="PSTATE" data-options="sortable:true" width="40">状态</th>
						<th field="TYPENAME" data-options="sortable:true" width="40">分类</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionTypedemo = "typedemo/del.do";//删除URL
	var url_formActionTypedemo = "typedemo/form.do";//增加、修改、查看URL
	var url_searchActionTypedemo = "typedemo/query.do";//查询URL
	var title_windowTypedemo = "分类模板管理";//功能名称
	var gridTypedemo;//数据表格对象
	var searchTypedemo;//条件查询组件对象
	var toolBarTypedemo = [ /** {
		               		id : 'view',
		               		text : '查看',
		               		iconCls : 'icon-tip',
		               		handler : viewDataTypedemo
		               	},**/
	{
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataTypedemo
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataTypedemo
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataTypedemo
	} ];
	$(function() {
		//初始化数据表格
		gridTypedemo = $('#dataTypedemoGrid').datagrid({
			url : url_searchActionTypedemo,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarTypedemo,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchTypedemo = $('#searchTypedemoForm').searchForm({
			gridObj : gridTypedemo
		});
		$('#FarmdocTree').tree({
			url : 'doctype/FarmDoctypeLoadTreeNode.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchTypedemo.dosearch({
					'ruleText' : searchTypedemo.arrayStr()
				});
			},
			loadFilter : function(data, parent) {
				return data.treeNodes;
			}
		});
		$('#FarmdocTreeReload').bind('click', function() {
			$('#FarmdocTree').tree('reload');
		});
		$('#FarmdocTreeOpenAll').bind('click', function() {
			$('#FarmdocTree').tree('expandAll');
		});
	});
	//查看
	function viewDataTypedemo() {
		var selectedArray = $(gridTypedemo).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionTypedemo + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTypedemo',
				width : 730,
				height : 400,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//新增
	function addDataTypedemo() {
		if (!$('#PARENTID_RULE').val()) {
			$.messager.confirm(MESSAGE_PLAT.PROMPT, "如果不选择任何分类，将创建通用模板，是否继续？",
					function(flag) {
						if (flag) {
							addDemo()
						}
					});
		} else {
			addDemo();
		}
	}
	function addDemo() {
		var url = url_formActionTypedemo + '?typeid='
				+ $('#PARENTID_RULE').val() + '&operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winTypedemo',
			width : 730,
			height : 400,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataTypedemo() {
		var selectedArray = $(gridTypedemo).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionTypedemo + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTypedemo',
				width : 730,
				height : 400,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//删除
	function delDataTypedemo() {
		var selectedArray = $(gridTypedemo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridTypedemo).datagrid('loading');
					$.post(url_delActionTypedemo + '?ids='
							+ $.farm.getCheckedIds(gridTypedemo, ''), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridTypedemo).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridTypedemo).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>