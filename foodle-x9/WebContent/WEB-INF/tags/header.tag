<%@ attribute name="title" required="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/general.js"></script>  
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/placeholders.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/venue.js"></script>
<link rel="stylesheet" type="text/css" charset="utf-8"
    href="${pageContext.request.contextPath}/style/general.css" />
<link rel="stylesheet" type="text/css" charset="utf-8"
    href="${pageContext.request.contextPath}/style/header.css" />
<link rel="stylesheet" type="text/css" charset="utf-8"
    href="${pageContext.request.contextPath}/style/venue.css" />

<!-- jQuery UI -->
<link type="text/css"
    href="${pageContext.request.contextPath}/style/jquery-ui/jquery-ui-1.8.1.custom.css"
    rel="stylesheet" />
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/jquery-ui-1.8.1.custom.min.js"></script>
<jsp:doBody />
</head>