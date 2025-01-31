package com.carrot.controller.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.carrot.dao.MembersDAO;
import com.carrot.vo.MemberVO;

/**
 * Servlet implementation class MyPageModifyServlet
 */
@WebServlet("/myPageModify.do")
public class MyPageModifyServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();
      
      MemberVO mvo = new MemberVO();
      MembersDAO mdao = MembersDAO.getInstance();
      
      RequestDispatcher rd = request.getRequestDispatcher("myPageModify.jsp");
      rd.forward(request, response);
      
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      String id = request.getParameter("id");
      String userid = request.getParameter("userid");
      String password = request.getParameter("password");
      String name = request.getParameter("name");
      String nickname = request.getParameter("nickname");
      String phone = request.getParameter("phone");
      String email = request.getParameter("email");
      String address = request.getParameter("address");
      
      MembersDAO mdao = MembersDAO.getInstance();
      MemberVO member = new MemberVO();
      
      member.setId(Integer.parseInt(id));
      member.setUserid(userid);
      member.setPassword(password);
      member.setName(name);
      member.setNickname(nickname);
      member.setPhone(phone);
      member.setEmail(email);
      member.setAddress(address);
      
      int result = mdao.updateMember(member);
      
      HttpSession session = request.getSession();
      session.setAttribute("userLogin", member);
      
      if(result >= 1 ) {
         request.setAttribute("message", "회원정보 수정에 성공하였습니다.");
      } else {request.setAttribute("message", "회원정보 수정에 실패하였습니다.");}
      
      RequestDispatcher rd = request.getRequestDispatcher("views/member/myPage.jsp");
      rd.forward(request, response);
      
      
   }

}
