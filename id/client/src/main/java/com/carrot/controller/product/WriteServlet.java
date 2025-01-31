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
import com.carrot.vo.MemberVO;
import com.carrot.vo.ProductVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class ProductWriteServlet
 */
@WebServlet("/write.do")
public class WriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WriteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("views/product/write.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("userLogin");
			
		ServletContext context = getServletContext();
		String path = context.getRealPath("/photo/product");//
		System.out.println(path);
		String encType = "UTF-8";
		int sizeLimit = 50 * 1024 * 1024;

		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());

		String title = multi.getParameter("title");
		double price = Double.parseDouble(multi.getParameter("price"));
		int category = Integer.parseInt(multi.getParameter("category"));
		String photoUrl = multi.getParameter("photoUrl");
		String description = multi.getParameter("description");

		ProductVO pvo = new ProductVO();
		
		HttpSession session1 = request.getSession();
		session1.setAttribute("product", pvo);
		
		
		pvo.setAuthorId(memberVO.getId());
		pvo.setTitle(title);
		pvo.setPrice(price);
		pvo.setCategory(category);
		pvo.setPhotoUrl(photoUrl);
		pvo.setDescription(description);

		ProductsDAO pdao = ProductsDAO.getInstance();
		pdao.insertProduct(pvo);

		response.sendRedirect("main.do");
	}

}
