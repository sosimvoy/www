<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR010812.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ������������ �˾�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>		
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
							
<%@ page import = "java.awt.image.*" %>													
<%@ page import = "javax.imageio.ImageIO" %>								

<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

 <%
 /*
    String file_name = "temp.gif";  // �ӽ� ��������
//    String file_dir = "/home1/tmax/jeus6/webhome/app_home/report/";  // ���ϻ������ 
  String file_dir = "/home1/tmax/jeus6/webhome/app_home/report/";  // ���ϻ������ 
	//String file_dir = request.getRealPath("/");
	String file_path = request.getParameter("file");    // TIF���� ���
    String d = file_dir + file_path; 
    

    File file = new File(d); // ������ �����´�
	//System.out.println("file:::::"+file);
    
    BufferedImage bi = ImageIO.read(file);
	
	//System.out.println("bi ------------------->"+bi);
   
    File new_file = new File(file_dir+file_name);   // temp ������ �ش��ο� �����.
   // System.out.println("������ ���� :::: "+new_file);
    ImageIO.write(bi, "gif", new_file);             // tif �о gif ���亯ȯ
    
//System.out.println("gif �����Ϸ�");
*/
 %>

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">

  function init() {
    typeInitialize();
  }
    
  function goPrint() {
    window.print();
  }
</script>
<style type="text/css">
 
 @media print {
  .noprint {
	  display:none;
	}
}
 </style>
</head>
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0">

<br>
<img src="/report/temp.png" height="328" width="544">
<div class="noprint">
<div align="center">
  <img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
</div>

</body>
</html>