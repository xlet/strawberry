<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>即时通信 - 达步溜</title>
    <script type="text/javascript" src="${ctx}/client/im.js"></script>
</head>
<body>
<a class="im_link" href="${ctx}/${to}.1.1"><img src="${ctx}/1.${to}.1" /></a>
<span id="im_uid">${from}</span>
<div style="height: 2000px;"></div>
</body>
</html>