<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "com.etax.helper.MessageHelper" %>
<%@ page import = "com.etax.framework.exception.AuthException" %>
<%@ page import = "com.etax.framework.exception.ProcessException" %>
<%
  System.out.println("error page start...");
	Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
	String message = "";
	String detailMessage = "";
	String errorCode = "";
	while(true) {
		try {
			throw throwable;
		} catch( AuthException e ) {
            System.out.println("AuthException");
			errorCode = e.getMessage();
			//message = MessageHelper.getMessage(errorCode);
			break;
		} catch( ProcessException e ) {
			if( throwable.getCause() != null ) {
				throwable = throwable.getCause();
			} else {
				errorCode = e.getMessage();
				//message = MessageHelper.getMessage(errorCode);
				break;
			}
		} catch( SQLException e ) {
			message = "DataBase �����Դϴ�";
			detailMessage = e.toString();
			break;
        } catch( NullPointerException e ) {	
            System.out.println("NullPointerException catched");
            break;
		} catch( Throwable e ) {
            System.out.println("Throwable="+e);
			if( throwable.getCause() != null ) {
				throwable = throwable.getCause();
			} else {
				message = e.getMessage();
				detailMessage = e.toString();
				e.printStackTrace();
				break;
			}
		}
	}
    if( throwable != null ) {
        message = throwable.getMessage();
        if( message != null ) {
            if( message.length() == 4 ) {
                message = MessageHelper.getMessage(message);
            } else {
                //message = "���� ���� ���� �Դϴ�";
            }
        } else {
            message = "���� ���� ���� �Դϴ�";
        }
    }
%>
<html>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<!----- ������ ����  ------> 
<table BORDER="0" CELLSPACING="0" CELLPADDING="0" width="100%" >
<tr><td class="c_title" align="center">
<Br><br><Br><br><!--<img src="image/bullet_alert.gif" align="absmiddle">--> ������ �߻��Ͽ����ϴ�. �ٽ� �õ����ּ���.</td></tr>
<tr><td height="10"></td></tr>
<tr><td align="center"><br><b><%=message%><b></td></tr>
<tr><td height="10"></td></tr>
<tr><td align="center"><br><b><%=detailMessage%><b></td></tr>
<br>
<tr><td align="center"><input type="button" value="Ȯ��" onclick="history.back()"></td></tr>
<%
	if( "L100".equals(errorCode) ) {
		/* �α��� �������� �̵� */
		// returnUrl ����(�ʿ��� ��� ���)
		String returnUrl = (String)request.getAttribute("etax.returnURL");
%>
<script>
try {
	top.location = "/admin/";
} catch(e) {
	document.location = "/admin/";
}
</script>
<%
	}
%>
</table>


</body>
</html>
