<%
/****************************************************************
* ������Ʈ��		  : ���������ý� ������¹�ȣ ���ΰ����� �ý���
* ���α׷���		  : super_two.jsp
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2009-05-04
* ���α׷�����	  : �ֹι�ȣ �ߺ�üũ �� ����üũ ��� ó��
*                 : iframe ���� ����
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
        alert("����ó���� ������ �߻��Ͽ����ϴ�.");        
      </script>    
<%
    } else { %>
<%    if ("CHECKFAIL".equals(message)) { %>
        <script>
          parent.memberEnabled = false;
          alert("��ȿ�������� �������Դϴ�.");
        </script>
<%    } else if ("FAIL".equals(user_state) ) {%>
        <script>
          alert("�α����� ����ڿ� �������� ��ġ���� �ʽ��ϴ�.");
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
          alert("���� �Ǵ� ��� ������ �����ϴ�.");
        </script>
<%    }
    } %>