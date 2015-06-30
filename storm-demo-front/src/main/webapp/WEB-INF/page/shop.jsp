<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>storm示例--商店首页</title>
</head>
<body>
	${obj.msg}<br/>
	<c:if test="${empty obj.customer}">
	<h2>登录</h2>
		id:<input type="text" id="customerId" value="1" placeholder="1"/>
		name:<input type="text" id="customerName" placeholder="新增需要输入用户名"/>
		<button onclick="login();">登录</button>
	</c:if>
	<c:if test="${not empty obj.customer}">
		用户ID:${obj.customer.id},用户名:${obj.customer.name}
	</c:if>
	<h2>全部商品</h2>
	<ul>
		<c:forEach items="${obj.goodsList}" var="goods">
			<li>
				<h2>${goods.name}</h2>
				<a href="javascript:operate('VIEW', ${goods.id})">浏览</a>
				<a href="javascript:operate('LIKE', ${goods.id})">收藏</a>
				<a href="javascript:operate('BUY', ${goods.id})">购买</a>
				<br/>
				标签:
				<c:forEach items="${goods.tags}" var="tag">
					${tag}&nbsp;&nbsp;
				</c:forEach>
			</li>
		</c:forEach>
	</ul>
	<a href="javascript:showHistory('1')">操作历史</a>
	<a href="javascript:showRecommend('1')">推荐商品</a>
</body>
<script>
function login() {
	var customerId = document.getElementById('customerId').value;
	var customerName = document.getElementById('customerName').value;
	window.location.href = "login?customerId=" + customerId + "&name=" + customerName;
}
function operate(type, goodsId) {
	window.location.href = "operate?opType=" + type + "&goodsId=" + goodsId;
}
function showHistory() {
	window.location.href = "history";
}
function showRecommend() {
	window.location.href = "recommend";
}
</script>
</html>