<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:if test="${USEROBJ==null}">
	<div class="row">
		<div class="col-xs-12 text-center">
			<h1 style="color: #666;">登录</h1>
			<hr />
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<div class="third-part tracking-ad">
				<div class="text-center">
					<img src="text/img/logo.png" alt="wcp" style="margin: 20px; max-width: 200px;" />
				</div>
				<hr />
			</div>
			<c:if test="${STATE=='1'}">
				<div class="text-center" id="romovealertMessageErrorId"
					style="margin: 4px; color: red;">
					<span class="glyphicon glyphicon-exclamation-sign"></span>
					${MESSAGE}
				</div>
			</c:if>
			<div class="text-center" id="alertMessageErrorId"
				style="margin: 4px; color: red;"></div>
		</div>
		<div class="col-sm-6" style="padding: 26px; padding-top: 4px;">
			<form class="form-signin" role="form" id="loginFormId"
				action="login/websubmit.do" method="post">
				<div class="form-group">
					<label for="exampleInputEmail1"> 登录名 </label> <input type="text"
						class="form-control" placeholder="用户登录名" value="${loginname}"
						style="margin-top: 4px;" id="loginNameId" name="name" required
						autofocus>
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1"> 登录密码 </label> <input
						type="password" class="form-control" placeholder="请录入密码"
						style="margin-top: 4px;" id="loginPassWId" name="password"
						required>
				</div>
				<div>
					<button class="btn  btn-primary text-left" id="loginButtonId"
						style="margin-top: 4px;" type="button">登录</button>
					<%-- <PF:IfParameterEquals key="config.sys.registable" val="true">
						<a
							style="float: right; text-decoration: underline; margin-top: 12px; margin-right: 8px;"
							type="button" href="webuser/PubRegist.do"> 注册 </a>
					</PF:IfParameterEquals> --%>
				</div>
			</form>
		</div>
	</div>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#alertMessageErrorId').hide();
		$('#loginButtonId')
			.bind('click', function() {
				if ($('#loginNameId').val()
					&& $('#loginPassWId').val()) {
					//$('#loginUrlId').val(document.referrer);
					$('#loginFormId').submit();
				} else {
					$('#alertMessageErrorId').show();
					$('#romovealertMessageErrorId').hide();
					$('#alertMessageErrorId').html(
						'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入用户登录名/密码');
				}
		});
		$('#loginNameId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
		$('#loginPassWId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
	});
//-->
</script>
