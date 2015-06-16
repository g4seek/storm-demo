<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>storm示例--推荐商品</title>
</head>
<body>
	<h2>标签权重</h2>
	<ul>
		<c:forEach items="${obj.tagWeightList}" var="tagWeight">
			<b>${tagWeight.tagName}</b>(${tagWeight.weight})&nbsp;&nbsp;&nbsp;&nbsp;
		</c:forEach>
	</ul>
	<h2>推荐商品</h2>
	<ul>
		<c:forEach items="${obj.sortableList}" var="sortGoods">
			<b>${sortGoods.goods.name}</b>(${sortGoods.weight})&nbsp;&nbsp;&nbsp;&nbsp;
		</c:forEach>
	</ul>	
	
</body>
</html>