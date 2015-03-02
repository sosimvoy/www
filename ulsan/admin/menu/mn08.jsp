<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : mn08.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 시스템운영
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.etax.entity.*" %>
<%
  String userName = (String)session.getAttribute("session.user_name");
  if( userName == null ) {
    userName = "";
  }

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>울산광역시 세입 및 자금배정관리 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/class.css" type="text/css">
<script language="javascript" src="../js/base.js"></script>
<script language="JavaScript">
<!--

	function MM_findObj(n, d) { //v3.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		 d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
			if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
	}

	function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		 if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}

	function MM_showHideLayers() { //v3.0
		var i,p,v,obj,args=MM_showHideLayers.arguments;
		for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
			if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
			obj.visibility=v; }
	}

//-->
</script>
</head>
 
<body  topmargin="0" >
<div id="Layer1" style="position:absolute; left:2px; top:40px; width:268px; height:50px; z-index:1; visibility: hidden"> 
  <table width="259" border="0" cellspacing="0" cellpadding="0" height="220" style="filter: Alpha(Opacity=90)" align="center">
    <tr> 
      <td width="246" height="197" valign="top">
        <table width="245" border="0" cellspacing="1" cellpadding="5" bgcolor="#D7D7D7">
          <tr bgcolor="#0099CC"> 
            <td height="50"> 
              <div align="center"><b><font color="#FFFFFF">사용자등록/변경신청 내역조회/승인 </font></b></div>
            </td>
          </tr>
					 <tr bgcolor="#FFFFFF"> 
            <td>
						<img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080110.etax">사용자등록/변경신청 내역조회/승인</a></td> 
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080210.etax">사용자 내역조회</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080310.etax">개인정보수정/a></td>
          </tr>
					 <tr bgcolor="#FFFFFF"> 
            <td>
						<img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080410.etax">부서변경 승인신청a></td> 
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080510.etax">업무변경 승인신청</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080610.etax">로그관리</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080710.etax">코드관리</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080810.etax">휴일관리</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR081410.etax">납부자코드관리</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR080910.etax">계좌등록</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn08/IR081010.etax">계좌조회</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td height="20">&nbsp;</td>
          </tr>
        </table>
      </td>
      <td width="13" height="197"> <a href="#" onclick="MM_showHideLayers('Layer1','','hide','Layer2','','show')"><img src="../img/close.gif" width="11" height="36" border="0"></a> 
      </td>
    </tr>
  </table>
</div>
<div id="Layer2" style="position:absolute; left:1px; top:250px; width:22px; height:52px; z-index:2; visibility: hidden"><a href="#" onclick="MM_showHideLayers('Layer1','','show','Layer2','','hide')"><img src="../img/open.gif" height="36" border="0"></a></div>
</div>  
</body>
</html>