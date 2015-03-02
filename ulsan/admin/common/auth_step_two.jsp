<%
/****************************************************************
* 프로젝트명		  : 대전광역시시 가상계좌번호 납부관리자 시스템
* 프로그램명		  : auth_step_two.jsp
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
		String serial = auth.getString("serial");
		String subjectDN = auth.getString("subjectDN");
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
<%    } else if ("OK".equals(user_state) ) {%>
        <script>
          parent.memberEnabled = true;
          var form = parent.document.sForm;
          form.serial.value = "<%=serial%>";
          form.subjectDN.value = "<%=subjectDN%>";
          alert("가입가능합니다.");
          form.currentdepart.focus();
        </script>
<%    } else if ("RENEWAL".equals(user_state) ) {%>
        <script>
          if (confirm("등록된 아이디입니다. 인증서갱신이 필요합니다. 인증서를 갱신하시겠습니까?") ) {
            parent.document.location = "../common/auth_confirm.etax";
          } else {
						parent.memberEnabled = false;
					}
        </script>
<%    } else if ("ORGAUTH".equals(user_state) ) {%>
        <script>
          alert("이미 등록된 인증서입니다. 아이디를 체크바랍니다. 아이디 : " + "<%=auth.getString("org_id")%>");
				  parent.memberEnabled = false;
        </script>
<%    } else if ("PMS_WAIT".equals(user_state) ) {%>
        <script>
					parent.memberEnabled = false;
          alert("가입된 사용자입니다. 승인 대기중입니다.");
          parent.document.location = "../";
        </script>
<%    } else if ("PMS_OK".equals(user_state) ) {%>
        <script>
          parent.memberEnabled = false;
          alert("가입된 사용자입니다. 승인완료되어 로그인 가능합니다.");
          parent.document.location = "../";
        </script>
<%    }
    } %>