<%
/****************************************************************
* ������Ʈ��		  : ���������ý� ������¹�ȣ ���ΰ����� �ý���
* ���α׷���		  : auth_step_two.jsp
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
		String serial = auth.getString("serial");
		String subjectDN = auth.getString("subjectDN");
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
<%    } else if ("OK".equals(user_state) ) {%>
        <script>
          parent.memberEnabled = true;
          var form = parent.document.sForm;
          form.serial.value = "<%=serial%>";
          form.subjectDN.value = "<%=subjectDN%>";
          alert("���԰����մϴ�.");
          form.currentdepart.focus();
        </script>
<%    } else if ("RENEWAL".equals(user_state) ) {%>
        <script>
          if (confirm("��ϵ� ���̵��Դϴ�. ������������ �ʿ��մϴ�. �������� �����Ͻðڽ��ϱ�?") ) {
            parent.document.location = "../common/auth_confirm.etax";
          } else {
						parent.memberEnabled = false;
					}
        </script>
<%    } else if ("ORGAUTH".equals(user_state) ) {%>
        <script>
          alert("�̹� ��ϵ� �������Դϴ�. ���̵� üũ�ٶ��ϴ�. ���̵� : " + "<%=auth.getString("org_id")%>");
				  parent.memberEnabled = false;
        </script>
<%    } else if ("PMS_WAIT".equals(user_state) ) {%>
        <script>
					parent.memberEnabled = false;
          alert("���Ե� ������Դϴ�. ���� ������Դϴ�.");
          parent.document.location = "../";
        </script>
<%    } else if ("PMS_OK".equals(user_state) ) {%>
        <script>
          parent.memberEnabled = false;
          alert("���Ե� ������Դϴ�. ���οϷ�Ǿ� �α��� �����մϴ�.");
          parent.document.location = "../";
        </script>
<%    }
    } %>