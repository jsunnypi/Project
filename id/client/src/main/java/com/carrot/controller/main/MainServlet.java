package com.carrot.controller.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.carrot.dao.FavoritesDAO;
import com.carrot.dao.ProductsDAO;
import com.carrot.dao.PurchasesDAO;
import com.carrot.dao.SearchDAO;
import com.carrot.vo.ProductVO;
import com.carrot.vo.PurchaseHistoryVO;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/main.do")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		SearchDAO searchDAO = SearchDAO.getInstance();
				
		List<ProductVO> searchList = searchDAO.searchProductsByPriceRange("under20000");
		
		
		for(int i = 0; i < searchList.size(); i++) {
			System.out.println(searchList.get(i).toString());
		}
		*/
		
		ProductsDAO pdao = ProductsDAO.getInstance();
		List<ProductVO> salesList = pdao.getAllProducts();
		
		HttpSession session = request.getSession();
		session.setAttribute("salesList", salesList);
		
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("views/main.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
