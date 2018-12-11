<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div id="orgBoxId">
	<ul class="doctypeUl">
		<c:forEach var="node" items="${orgs}">
			<c:if test="${node.parentid=='NONE'}">
				<li>
					<h5 class="showLableType"><a id="${node.id}">${node.name} </a></h5>
					<ul>
						<c:forEach var="node1" items="${orgs}">
							<c:if test="${node1.parentid==node.id}">
								<li><h5 class="showLableType"><a id="${node1.id}">${node1.name} </a></h5></li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
			</c:if>
		</c:forEach>
	</ul>
</div>
