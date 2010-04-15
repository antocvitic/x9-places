<%@ tag body-content="tagdependent" %>
<%@ attribute name="title" required="true"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<link rel="stylesheet" type="text/css" charset="utf-8"
    href="${pageContext.request.contextPath}/style/main.css" />
<title>${title}</title>

<script type="text/javascript">
</script>

<jsp:doBody />
</head>