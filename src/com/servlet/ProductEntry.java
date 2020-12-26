package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.Product;
@WebServlet("/ProductEntry")
public class ProductEntry extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		//doGet(request, response);
		String id = request.getParameter("prodId");
		
		String sql = "SELECT * from product WHERE id = '" + id + "'";;
		
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
			PreparedStatement pst = conn.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();
			boolean idFound = false;
			while (rs.next()) {
				String idStr = rs.getString(1);
				
				if (id.equalsIgnoreCase(request.getParameter("prodId"))) {
					response.getWriter().println("Search Successful");
					idFound = true;
					HttpSession session= request.getSession();
					Product e = new Product();
					session.setAttribute("sesname",request.getParameter("prodId"));
		
					break;
				}
			}
			if (!idFound || id == null) {
				response.getWriter().println("Product does not match our records.");
				response.sendRedirect("Invalid.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<a href='index.jsp'>Home</a>");
		out.println("<h1>Product:</h1>");
		
		String text=request.getParameter("prodId");
		
		
		Product theProd = ProdDao.getProductById(text);
		
		out.print("<table border='1' width='100%'");
		out.print("<tr><th>Id</th><th>Product Name</th>");
		
			out.print("<tr><td>"+theProd.getId()+"</td><td>"+theProd.getName()+"</td></tr>");
		
		out.print("</table>");
		
		out.close();
	}
}
