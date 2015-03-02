<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040000.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-01-13
* 프로그램내용	   : 세외수입
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

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
<div id="Layer1" style="position:absolute; left:2px; top:0px; width:268px; height:50px; z-index:1; visibility: visible"> 
  <table width="259" border="0" cellspacing="0" cellpadding="0" height="220" style="filter: Alpha(Opacity=90)" align="center">
    <tr> 
      <td width="246" height="197" valign="top">
        <table width="245" border="0" cellspacing="1" cellpadding="5" bgcolor="#D7D7D7">
          <tr bgcolor="#0099CC"> 
            <td height="35"> 
              <div align="center"><b><font color="#FFFFFF">세외수입</font></b></div>
            </td>
          </tr>
					 <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn04/IR040110.etax">계좌입금내역조회</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn04/IR040210.etax?cmd=budgetList">예산서조회</a></td>
          </tr>
					 <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn04/IR040310.etax?cmd=jingsuManList">징수결의 등록</a></td>
          </tr>
					 <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn04/IR040410.etax?cmd=jingsuList">등록내역조회/삭제</a></td>
          </tr>
					 <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn04/IR040510.etax?cmd=gwaonapList">과오납금송금반환통지서조회/결과통지</a></td>
          </tr>
          </tr>
           <tr bgcolor="#FFFFFF"> 
            <td height="350">&nbsp;</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="Layer2" style="position:absolute; left:1px; top:150px; width:22px; height:52px; z-index:2; visibility: hidden"><a href="#" onclick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','show')"><img src="../img/open.gif" height="36" border="0"></a></div>
</div>
<div id="Layer3" style="position:absolute; left:252px; top:150px; width:22px; height:52px; z-index:2; visibility: visible"><a href="#" onclick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide')"><img src="../img/close.gif" height="36" border="0"></a></div>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
</body>
</html>