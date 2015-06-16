<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>storm示例--操作历史</title>
</head>
<body>
	<ul>
		<c:forEach items="${obj.historyList}" var="history">
			<li>
				<b>${history.type}</b>&nbsp;&nbsp;goods [${history.goodsId}]
			</li>
		</c:forEach>
	</ul>
</body>
</html>