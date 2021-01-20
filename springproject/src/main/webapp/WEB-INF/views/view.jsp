<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Sensor Page</title>
</head>
<body>
	<h1>view</h1>
	
	<%-- <c:forEach items="${sensorList}" var="sensorList" varStatus="vs">
		<c:forEach items="${sensorList}" var="sensor">
			<p><span>${sensor}</span></p>
		</c:forEach>
	</c:forEach> --%>
	<c:forEach items="${sensorList}" var="sensorList" varStatus="vs">
			<p><span>${sensorList.sensorName}</span></p>
			<p><span>${sensorList.sensorId}</span></p>
	</c:forEach>
</body>
</html>