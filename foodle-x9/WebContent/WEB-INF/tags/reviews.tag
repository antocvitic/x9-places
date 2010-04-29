<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@tag import="java.util.*"%>
<%@tag import="com.x9.foodle.review.*"%>
<%@ attribute name="reviews" type="java.util.List" required="true"%>
<%
@SuppressWarnings("unchecked")
	List<ReviewModel> reviews_typed = (List<ReviewModel>) reviews;
%>
<%
if (reviews.isEmpty()) {
    %>
    No reviews
    <%
} else {
    for (ReviewModel review : reviews_typed) {
    %>
        <h:review review="<%= review %>" />
        <br />
    <%
	} 
}
%>