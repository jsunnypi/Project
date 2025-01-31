package com.carrot.controller.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.carrot.dao.MembersDAO;
import com.carrot.vo.MemberVO;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/join.do")
public class JoinServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher rd = request.getRequestDispatcher("views/member/join.jsp");
       rd.forward(request, response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      
      String userid = request.getParameter("userid");
      String password = request.getParameter("password");
      String name = request.getParameter("name");
      String nickname = request.getParameter("nickname");
      String phone = request.getParameter("phone");
      String email = request.getParameter("email");
      String address = request.getParameter("address");
      String grade = request.getParameter("grade");
      
      //Stirng registrationDate = request.getParameter("registrationDate");
      
      MemberVO member = new MemberVO();
      
      member.setUserid(userid);
      member.setPassword(password);
      member.setName(name);
      member.setNickname(nickname);
      member.setPhone(phone);
      member.setEmail(email);
      member.setAddress(address);
      
      MembersDAO mdao = MembersDAO.getInstance();
      mdao.insertMember(member); 
      
      HttpSession session = request.getSession();
      session.setAttribute("member", member);
      request.setAttribute("message", "회원가입에 성공하였습니다.");
      RequestDispatcher rd = request.getRequestDispatcher("views/member/successJoin.jsp");
      rd.forward(request, response);
      
   }

}