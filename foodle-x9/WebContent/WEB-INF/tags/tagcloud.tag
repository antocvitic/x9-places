<%@ attribute name="tag_freq_list" type="java.util.List" required="true"%>
<%@ attribute name="tagmap" type="java.util.TreeMap" required="true"%>
<%@ tag import="java.util.*"%>

<div id="tagcloud" class="msg_msg">
	<h3>Tag cloud - most common tags</h3>
	<%
	// Print tag cloud with tagsize weighted according to tagfrequency (beta)
	if(tag_freq_list.size() > 0){	
			int most_freq_tag = (Integer) ((Map.Entry) tag_freq_list.get(tag_freq_list.size()-1)).getValue();
			
			// Skip to 10 most common tags
			Iterator<Map.Entry<String, Integer>> it = tag_freq_list.iterator();
			for(int tsize = tag_freq_list.size(); tsize > 10; tsize--){
				it.next();
			}
			
			while (it.hasNext() ) { 
				Map.Entry<String, Integer> entry = it.next(); %>
				<a href="${pageContext.request.contextPath}/adv_search.jsp?search_term=<%=entry.getKey()%>&adv_opt=tags" style="font-size: <%=(((Integer)(8*((Integer) entry.getValue())))/most_freq_tag)+8%>pt"><%=entry.getKey()%></a>&nbsp;
			<% 
			}
	%></div>
	<%
	}
%>