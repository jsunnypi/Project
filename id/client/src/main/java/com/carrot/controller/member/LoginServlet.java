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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public LoginServlet() {
      super();
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      RequestDispatcher rd = request.getRequestDispatcher("views/login.jsp");
      rd.forward(request, response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      String userid = request.getParameter("userid");
      String password = request.getParameter("password");
      MembersDAO mdao = MembersDAO.getInstance();
      
      
      System.out.println("폼데이터 userid :" + userid);
      System.out.println("폼데이터 password :" + password);
      
      
      MemberVO memberVO = mdao.loginMember(userid, password);//
      
      if (memberVO != null) {
          System.out.println(memberVO);
          String url = "views/main.jsp";
          HttpSession session = request.getSession();
          session.setAttribute("userLogin", memberVO);
          // RequestDispatcher forward 사용 
          RequestDispatcher rd = request.getRequestDispatcher(url);
          rd.forward(request, response);
      } else {
          // 아이디 또는 비밀번호가 일치하지 않으면 리다이렉트
          String url = "views/login.jsp";
          request.setAttribute("message", "아이디와 비밀번호를 확인하세요.");
          RequestDispatcher rd = request.getRequestDispatcher(url);
          rd.forward(request, response); 
      }
      
   
      
   

      //HttpSession session = request.getSession();
      //session.setAttribute("userLogin", member);

   }
   }
