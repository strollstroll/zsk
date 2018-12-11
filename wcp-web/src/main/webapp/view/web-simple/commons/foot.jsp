<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="foot" style="height:26px;font-size:10px">
	<PF:ParameterValue key="config.sys.foot" />-V<PF:ParameterValue key="config.sys.version"/>
</div>
<script type="text/javascript">
	$(function() {
		$(window).resize(function() {
			$('.containerbox').css('min-height', $(window).height() - 280);
		});
		$('.containerbox').css('min-height', $(window).height() - 280);
	});
</script>