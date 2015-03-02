<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050000.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity allotingInfo = (CommonEntity)request.getAttribute("page.mn05.allotingInfo");
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
<div id="Layer1" style="position:absolute; left:2px; top:0px; width:268px; height:50px; z-index:1; visibility: visible"> 
  <table width="259" border="0" cellspacing="0" cellpadding="0" height="220" style="filter: Alpha(Opacity=90)" align="center">
    <tr> 
      <td width="246" height="197" valign="top" bgcolor="#FFFFFF">
        <table width="245" border="0" cellspacing="1" cellpadding="5" bgcolor="#D7D7D7">
          <tr bgcolor="#0099CC"> 
            <td height="35"> 
              <div align="center"><b><font color="#FFFFFF">자금배정</font></b></div>
            </td>
          </tr>
          
					<tr bgcolor="#FFFFFF"> 
            <td>
						<img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050110.etax">자금배정미처리잔액조회</a></td> 
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050210.etax">자금배정승인내역조회/배정처리</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050310.etax">자금재배정승인내역조회/재배정처리</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050410.etax">자금(재)배정통지서조회</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050710.etax?cmd=bankSugiList">자금배정수기분등록</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050810.etax">자금배정수기분조회/취소</a></td>
          </tr>
          <!-- 
          <tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR051510.etax">통신장애재처리</a></td>
          </tr>
           -->
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050910.etax?cmd=surplusList">잉여금이입요구등록</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR051010.etax">잉여금이입요구조회/취소</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR051110.etax">잉여금이입승인조회/이입처리</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR051210.etax">잉여금이입통지서조회</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR051310.etax?cmd=srpSugiList">잉여금이입수기분등록</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR051410.etax">잉여금이입수기분조회/취소</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050510.etax?cmd=bankAcctInfoList">계좌등록</a></td>
          </tr>
					<tr bgcolor="#FFFFFF"> 
            <td><img src="../img/arr.gif" width="12" height="13" align="absmiddle"><a href="/admin/mn05/IR050610.etax">계좌별거래내역조회</a></td>
          </tr>
          <tr bgcolor="#FFFFFF"> 
            <td height="350">&nbsp;</td>
          </tr>
         
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="Layer2" style="position:absolute; left:1px; top:150px; width:22px; height:52px; z-index:2; visibility: visible"><a href="#" onclick="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','show')"><img src="../img/open.gif" height="36" border="0"></a></div>
</div>
<div id="Layer3" style="position:absolute; left:252px; top:150px; width:22px; height:52px; z-index:2; visibility: visible"><a href="#" onclick="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide')"><img src="../img/close.gif" height="36" border="0"></a></div>
<% if (allotingInfo.size() > 0 && allotingInfo != null) { %>
<table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40">&nbsp;</td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td align="center"> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금배정 및 재배정 총건수<span style="width:10"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("TOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr>
									  <td width="45%">&nbsp;</td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금배정총건수<span style="width:70"></span>
								      <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("ALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
									</tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금배정요구건수<span style="width:58"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("REQ_ALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금배정사업소승인건수<span style="width:22"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("WORK_ALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금배정세정과승인건수<span style="width:22"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("CITY_ALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    은행배정처리완료건수<span style="width:34"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("BANK_ALLOTED_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    은행배정처리미완료건수<span style="width:22"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("BANK_ALLOTING_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금재배정총건수<span style="width:58"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("NOALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금재배정요구건수<span style="width:46"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("REQ_NOALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금재배정사업소승인건수<span style="width:10"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("WORK_NOALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금재배정세정과승인건수<span style="width:10"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("CITY_NOALLOT_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    은행재배정처리완료건수<span style="width:22"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("BANK_NOALLOTED_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr> 
									  <td width="45%">&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    은행재배정처리미완료건수<span style="width:10"></span>
										  <input type="text" name="tot_cnt" value="<%=TextHandler.formatNumber(allotingInfo.getString("BANK_NOALLOTING_CNT"))%>" class="box_r" size="10" readonly>건
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
        </td>
			</tr>	
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
    </table>
		<% } %>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
</body>
</html>