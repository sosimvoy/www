<%
/****************************************************************
* 프로젝트명		  : 대전광역시시 가상계좌번호 납부관리자 시스템
* 프로그램명		  : super_two.jsp
* 프로그램작성자	: 
* 프로그램작성일	: 2009-05-04
* 프로그램내용	  : 주민번호 중복체크 및 인증체크 결과 처리
*                 : iframe 에서 실행
*****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.etax.entity.*" %>
<%

    CommonEntity auth = (CommonEntity)request.getAttribute("auth.info");
		String message = auth.getString("message");
		String user_state = auth.getString("user_state");
%>

<%
    if( auth == null ) { 
%>
      <script>
        parent.memberEnabled = false;
        alert("인증처리중 오류가 발생하였습니다.");        
      </script>    
<%
    } else { %>
<%    if ("CHECKFAIL".equals(message)) { %>
        <script>
          parent.memberEnabled = false;
          alert("유효하지않은 인증서입니다.");
        </script>
<%    } else if ("FAIL".equals(user_state) ) {%>
        <script>
          alert("로그인한 사용자와 인증서가 일치하지 않습니다.");
        </script>
<%    } else if ("GOOD".equals(user_state) ) {%>
        <script>
          var v_flag = "<%=request.getParameter("v_flag")%>";
          if (v_flag == "P") {
            parent.goPermission2();
          } else if (v_flag == "C") {
            parent.goCancel();
          }          
        </script>
<%    } else if ("NOPOW".equals(user_state) ) {%>
        <script>
          alert("승인 또는 취소 권한이 없습니다.");
        </script>
<%    }
    } %>