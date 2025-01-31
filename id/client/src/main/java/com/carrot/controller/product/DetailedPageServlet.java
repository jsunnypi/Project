package com.carrot.controller.product;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.carrot.dao.ProductsDAO;
import com.carrot.vo.ProductVO;

/**
 * Servlet implementation class DetailedPageServlet
 */
@WebServlet("/detailedPage.do")
public class DetailedPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailedPageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		System.out.println("나오나요 : " + request.getParameter("id"));
		
		
		int id = Integer.parseInt(request.getParameter("id"));	
		
		

		ProductsDAO pdao = ProductsDAO.getInstance();
		ProductVO pvo = pdao.getProductById(id);

		request.setAttribute("product", pvo);

		RequestDispatcher rd = request.getRequestDispatcher("views/product/detailedPage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
