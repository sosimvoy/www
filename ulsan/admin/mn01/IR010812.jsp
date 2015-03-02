<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010812.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 영수필통지서 팝업
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
    String file_name = "temp.gif";  // 임시 생성파일
//    String file_dir = "/home1/tmax/jeus6/webhome/app_home/report/";  // 파일생성경로 
  String file_dir = "/home1/tmax/jeus6/webhome/app_home/report/";  // 파일생성경로 
	//String file_dir = request.getRealPath("/");
	String file_path = request.getParameter("file");    // TIF파일 경로
    String d = file_dir + file_path; 
    

    File file = new File(d); // 파일을 가져온다
	//System.out.println("file:::::"+file);
    
    BufferedImage bi = ImageIO.read(file);
	
	//System.out.println("bi ------------------->"+bi);
   
    File new_file = new File(file_dir+file_name);   // temp 파일을 해당경로에 만든다.
   // System.out.println("저장할 파일 :::: "+new_file);
    ImageIO.write(bi, "gif", new_file);             // tif 읽어서 gif 포멧변환
    
//System.out.println("gif 생성완료");
*/
 %>

<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
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
  <img src="../img/btn_print.gif" alt="인쇄" align="absmiddle" onclick="goPrint()" style="cursor:hand">
</div>

</body>
</html>