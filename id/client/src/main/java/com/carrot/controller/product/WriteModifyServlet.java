package com.carrot.controller.product;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.carrot.dao.ProductsDAO;
import com.carrot.vo.ProductVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class WriteModifyServlet
 */
@WebServlet("/writeModify.do")
public class WriteModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * //int id = Integer.parseInt(request.getParameter("id")); 
		 * ProductsDAO pdao =
		 * ProductsDAO.getInstance(); 
		 * ProductVO pvo = pdao.getProductById(id);
		 * 
		 * request.setAttribute("product", pvo);
		 */
	
		ProductVO pvo = new ProductVO();
		ProductsDAO pdao = ProductsDAO.getInstance();
		request.setAttribute("product", pvo);
		RequestDispatcher rd = request.getRequestDispatcher("views/product/writeModify.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ServletContext context = getServletContext();
		String path = context.getRealPath("photo/product");
		String encType = "UTF-8";
		int sizeLimit = 50 * 1024 * 1024;
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		
		int id = Integer.parseInt(multi.getParameter("id"));
		String title = multi.getParameter("title");
		int category = Integer.parseInt(multi.getParameter("category"));
		double price = Double.parseDouble(multi.getParameter("price"));
		String description = multi.getParameter("description");
		String photoUrl = multi.getParameter("photoUrl");
		
		if(photoUrl == null) {
			photoUrl = multi.getParameter("noimg.gif");
		}
		
		ProductVO pvo = new ProductVO();
		pvo.setId(id);
		pvo.setTitle(title);
		pvo.setCategory(category);
		pvo.setPrice(price);
		pvo.setDescription(description);
		pvo.setPhotoUrl(photoUrl);
		
		ProductsDAO pdao = ProductsDAO.getInstance();
		pdao.updateProduct(pvo);
		response.sendRedirect("salesList.do");
	}

}


