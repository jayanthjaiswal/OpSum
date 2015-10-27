<%-- 
    Document   : response
    Created on : 7 Apr, 2014, 12:57:23 PM
    Author     : zerone
--%>

<%@ page import="opsum.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
		
		Double alpha = Double.parseDouble(request.getParameter("alpha"));
		Double r = Double.parseDouble(request.getParameter("r"));
		Integer func = Integer.parseInt(request.getParameter("Submodular Function"));
		String text = request.getParameter("text");
		int budget = Integer.parseInt(request.getParameter("WordBudget"));
		System.out.println(text);
		
			out.println("<h1> Details </h1> <hr>");
			out.println("Function= A"+func);
			out.println("   &#945 = "+alpha);
			out.println("   r= "+r);
			
			out.println("<h1> Text </h1> <hr>");
			out.println(text);
			
		out.println("<h1> Summary </h1> <hr>");
		List<Sentence> ls = opsum.Main.getSummary(func, alpha, r, budget, text);
		for( Sentence s:ls){
			out.println("<br>"+s.getSentString());
		}
        %>
	<h1> <a href="index.jsp"> Home </a> </h1>
    </body>
</html>
