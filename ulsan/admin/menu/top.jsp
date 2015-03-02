<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : top.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 상단메뉴
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%
String user_name = (String)session.getAttribute("session.user_name");

%>

<html>
<head>
<title>울산광역시 세입 및 자금배정관리 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/class.css" type="text/css">
<script language="JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

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

/* 세입 */
function mn01_1() {
  parent.mainFrame.document.location = "/admin/mn01/IR010000.etax";
}

/* 세출 */
function mn02_1() {
	parent.mainFrame.document.location = "/admin/mn02/IR020000.etax";
}

/* 세입세출외현금 */
function mn03_1() {
  parent.mainFrame.document.location = "/admin/mn03/IR030000.etax";
}

/* 세외수입 */
function mn04_1() {
	parent.mainFrame.document.location = "/admin/mn04/IR040000.etax";
}

/* 자금배정 */
function mn05_1() {
	parent.mainFrame.document.location = "/admin/mn05/IR050000.etax?cmd=allotingStat";
}

/* 자금운용 */
function mn06_1() {
	parent.mainFrame.document.location = "/admin/mn06/IR060000.etax";
}

/* 일계/보고서 */
function mn07_1() {
	parent.mainFrame.document.location = "/admin/mn07/IR070000.etax";
}

/* 시스템운영 */
function mn09_1() {
	parent.mainFrame.document.location = "/admin/mn09/IR090000.etax";
}
//-->
function logout() {
    var form = document.sForm;
	form.cmd.value="managerLogout";
	form.submit();
}
</script>
</head>
 
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" oncontextmenu="return false" onLoad="MM_preloadImages('../img/sub_menu1_1.gif','../img/sub_menu2_1.gif','../img/sub_menu3_1.gif','../img/sub_menu4_1.gif','../img/sub_menu5_1.gif','../img/sub_menu6_1.gif','../img/sub_menu7_1.gif','../img/sub_menu8_1.gif')">
<table width="1004" border="0" cellspacing="0" cellpadding="0"  oncontextmenu="return false" >
  <tr> 
    <td width="210"><img src="../img/sub_top1.gif" width="210" height="39"></td>
    <td width="805"> 
      <table width="790" border="0" cellspacing="0" cellpadding="0" background="../img/sub_top2.gif"  oncontextmenu="return false" >
        <tr> 
          <td height="39" width="1%">&nbsp;</td>
          <td width="99%"><br>
            <b><%=user_name%></b>님 로그인중입니다.<img src="../img/btn_logout.gif" onClick="logout()" style="cursor:hand"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td width="210"><img src="../img/sub_top3.gif" width="210" height="39"></td>
    <td width="805"> 
      <table width="792" border="0" cellspacing="0" cellpadding="0" oncontextmenu="return false">
        <tr> 
          <td width="75"><a href="javascript:mn01_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','../img/sub_menu1_1.gif',1)"><img name="Image3" border="0" src="../img/sub_menu1.gif" width="75" height="39"></a></td>
          <td width="57"><a href="javascript:mn02_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','../img/sub_menu2_1.gif',1)"><img name="Image4" border="0" src="../img/sub_menu2.gif" width="57" height="39"></a></td>
          <td width="112"><a href="javascript:mn03_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','../img/sub_menu3_1.gif',1)"><img name="Image5" border="0" src="../img/sub_menu3.gif" width="112" height="39"></a></td>
          <td width="85"><a href="javascript:mn04_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','../img/sub_menu4_1.gif',1)"><img name="Image6" border="0" src="../img/sub_menu4.gif" width="85" height="39"></a></td>
          <td width="80"><a href="javascript:mn05_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','../img/sub_menu5_1.gif',1)"><img name="Image7" border="0" src="../img/sub_menu5.gif" width="80" height="39"></a></td>
          <td width="84"><a href="javascript:mn06_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','../img/sub_menu6_1.gif',1)"><img name="Image8" border="0" src="../img/sub_menu6.gif" width="84" height="39"></a></td>
          <td width="95"><a href="javascript:mn07_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','../img/sub_menu7_1.gif',1)"><img name="Image9" border="0" src="../img/sub_menu7.gif" width="95" height="39"></a></td>
          <td width="40"><a href="javascript:mn09_1()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','../img/sub_menu8_1.gif',1)"><img name="Image10" border="0" src="../img/sub_menu8.gif" width="85" height="39"></a></td>
          <td width="164"><img name="Image11" border="0" src="../img/sub_menu9.gif" width="117" height="39"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td colspan="2"><img src="../img/sub_top4.gif" width="1000" height="49"></td>
  </tr>
</table>
<form name="sForm" method="post" action="../common/blank.etax">
<input type="hidden" name="cmd" value="managerLogout">
</form>
</body>
</html>
