<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR070410.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-12
* ���α׷�����	   : �ϰ�/���� > ���Ϻ����� 
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
  CommonEntity dateNote = (CommonEntity) request.getAttribute("page.mn07.dateNote");

  int dataNoteSize = 0;
  if (dateNote != null) {
    dataNoteSize = dataNoteSize;
  }

  CommonEntity agoNote = (CommonEntity) request.getAttribute("page.mn07.agoNote");

  int agoNoteSize = 0;
  if (agoNote != null) {
    agoNoteSize = agoNote.size();
  }

  String SucMsg = (String) request.getAttribute("page.mn07.SucMsg");

  if (SucMsg == null) {
    SucMsg = "";
  }

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

	String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date)) {
    acc_date = TextHandler.getCurrentDate();
	}


  String M470_CAR_AGODATE_GC01 = "";
  String M470_CAR_AGODATE_GC02 = "";
  String M470_NONG_AGODATE_GC01 = "";
  String M470_NONG_AGODATE_GC02 = "";
  String M470_BAL_AGODATE_GP01 = "";
  String M470_BAL_AGODATE_GC01 = "";
  String M470_BAL_AGODATE_GC02 = "";

  String M470_CD_GUM_CARD_DATE = "";
  String M470_NT_GUM_CARD_DATE = "";
  String M470_BS_GUM_CARD_DATE = "";
  String M470_BS_GUM_NOCARD_DATE = "";
  String M470_BS_GUM_OARS_DATE = "";
  String M470_BS_GUM_NARS_DATE = "";
  //���ܼ���
  String M470_SS_GUM_OCARD_DATE = "";
  String M470_SS_GUM_NCARD_DATE = "";
  String M470_SS_GUM_OARS_DATE = "";
  String M470_SS_GUM_NARS_DATE = "";

  if (agoNoteSize > 0 ) {
    M470_CAR_AGODATE_GC01 = agoNote.getString("M470_CAR_AGODATE_GC01");
    M470_CAR_AGODATE_GC02 = agoNote.getString("M470_CAR_AGODATE_GC02");
    M470_NONG_AGODATE_GC01 = agoNote.getString("M470_NONG_AGODATE_GC01");
    M470_NONG_AGODATE_GC02 = agoNote.getString("M470_NONG_AGODATE_GC02");
    M470_BAL_AGODATE_GP01 = agoNote.getString("M470_BAL_AGODATE_GP01");
    M470_BAL_AGODATE_GC01 = agoNote.getString("M470_BAL_AGODATE_GC01");
    M470_BAL_AGODATE_GC02 = agoNote.getString("M470_BAL_AGODATE_GC02");

    M470_CD_GUM_CARD_DATE = agoNote.getString("M470_CD_GUM_CARD_DATE");
    M470_NT_GUM_CARD_DATE = agoNote.getString("M470_NT_GUM_CARD_DATE");
    M470_BS_GUM_CARD_DATE = agoNote.getString("M470_BS_GUM_CARD_DATE");
    M470_BS_GUM_NOCARD_DATE = agoNote.getString("M470_BS_GUM_NOCARD_DATE");
    M470_BS_GUM_OARS_DATE = agoNote.getString("M470_BS_GUM_OARS_DATE");
    M470_BS_GUM_NARS_DATE = agoNote.getString("M470_BS_GUM_NARS_DATE");
    //���ܼ���
    M470_SS_GUM_OCARD_DATE = agoNote.getString("M470_SS_GUM_OCARD_DATE");
    M470_SS_GUM_NCARD_DATE = agoNote.getString("M470_SS_GUM_NCARD_DATE");
    M470_SS_GUM_OARS_DATE = agoNote.getString("M470_SS_GUM_OARS_DATE");
    M470_SS_GUM_NARS_DATE = agoNote.getString("M470_SS_GUM_NARS_DATE");
  }

  if (dataNoteSize > 0) {
    M470_CAR_AGODATE_GC01 = dateNote.getString("M470_CAR_AGODATE_GC01");
    M470_CAR_AGODATE_GC02 = dateNote.getString("M470_CAR_AGODATE_GC02");
    M470_NONG_AGODATE_GC01 = dateNote.getString("M470_NONG_AGODATE_GC01");
    M470_NONG_AGODATE_GC02 = dateNote.getString("M470_NONG_AGODATE_GC02");
    M470_BAL_AGODATE_GP01 = dateNote.getString("M470_BAL_AGODATE_GP01");
    M470_BAL_AGODATE_GC01 = dateNote.getString("M470_BAL_AGODATE_GC01");
    M470_BAL_AGODATE_GC02 = dateNote.getString("M470_BAL_AGODATE_GC02");

    M470_CD_GUM_CARD_DATE = dateNote.getString("M470_CD_GUM_CARD_DATE");
    M470_NT_GUM_CARD_DATE = dateNote.getString("M470_NT_GUM_CARD_DATE");
    M470_BS_GUM_CARD_DATE = dateNote.getString("M470_BS_GUM_CARD_DATE");
    M470_BS_GUM_NOCARD_DATE = dateNote.getString("M470_BS_GUM_NOCARD_DATE");
    M470_BS_GUM_OARS_DATE = dateNote.getString("M470_BS_GUM_OARS_DATE");
    M470_BS_GUM_NARS_DATE = dateNote.getString("M470_BS_GUM_NARS_DATE");
    
    //���ܼ���
    M470_SS_GUM_OCARD_DATE = dateNote.getString("M470_SS_GUM_OCARD_DATE");
    M470_SS_GUM_NCARD_DATE = dateNote.getString("M470_SS_GUM_NCARD_DATE");
    M470_SS_GUM_OARS_DATE = dateNote.getString("M470_SS_GUM_OARS_DATE");
    M470_SS_GUM_NARS_DATE = dateNote.getString("M470_SS_GUM_NARS_DATE");
  }
  
  


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ������� �ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
	  function init() {
	    typeInitialize();
    }

	  function goSearch() {
	    var form = document.sForm;
	    form.action = "IR070410.etax";
	    form.cmd.value = "dateNoteList"; 
	    form.submit();
	  }

    function saveData() {
	    var form = document.sForm;
      if (confirm(form.acc_date.value + "���� �ڷḦ ����մϱ�?")) {
	      form.action = "IR070410.etax";
	      form.cmd.value = "dateNoteInsert"; 
	      form.submit();
      }
	  }

    function goDelete() {
	    var form = document.sForm;
      if (confirm (form.acc_date.value + "���� �ڷḦ �����մϱ�?")) {
        form.action = "IR070410.etax";
	      form.cmd.value = "dateNoteDelete"; 
	      form.submit();
      }	    
	  }

	</script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
  <form name="sForm" method="post" action="IR070410.etax">
  <input type="hidden" name="cmd" value="dateNoteList">
  
<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr> 
	<td width="18">&nbsp;</td>
	<td width="975" height="40"><img src="../img/title7_11.gif"></td>
  </tr>
  <tr>
	<td width="18">&nbsp;</td>
	<td width="975" height="40"> 
	  <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
		<tr> 
		  <td><img src="../img/box_top.gif" width="964" height="11"></td>
		</tr>
		<tr> 
		  <td> 
			<table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td width="860">
				  <span style="width:50px"></span>
				  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
				  ȸ�迬��&nbsp;
				  <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
					<option value="<%=this_year%>"><%=this_year%></option>
					<option value="<%=last_year%>"><%=last_year%></option>
				  </select>
				  <span style="width:50px"></span>
				  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
				  ȸ������&nbsp;<input type="text" value="<%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%>" class="box3" name="acc_date" size="8" userType="date">
				   <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand">
				</td>
				<td><img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="absmiddle"></td>
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
<% if (agoNote != null && agoNote.size() > 0) { %>
<tr>
  <td width="18">&nbsp;</td>
  <td width="975">
<!-- ǥ���� -->
<table border="0" cellspacing="0" cellpadding="0" class="list">
<tr>
	<th colspan="3" width="24%">���������׸�</th>
	<th rowspan="2" width="16%">���ϱ�������</th>
	<th rowspan="2" width="16%">���ϼ�����</th>
	<th rowspan="2" width="16%">����<br>�ڱ���ü��</th>
	<th rowspan="2" width="16%">�� ��</th>
  <th rowspan="2" width="12%">�ڱ���ü������</th>
</tr>
<tr>
	<th>����</th>
	<th>�Ⱓ</th>
	<th>����</th>
</tr>
<tr>
	<td rowspan="3">������漼</td>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_CAR_AGO_GC01" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_CAR_SUM_GC01"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_CAR_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CAR_GC01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_CAR_ICHE_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CAR_ICHE_GC01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_CAR_SUM_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CAR_SUM_GC01"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_CAR_AGODATE_GC01" class="box3" value="<%=M470_CAR_AGODATE_GC01%>" size="10" userType="date"></td>
</tr>
<tr>
  <td>�ݰ��</td>
  <td>ī��</td>
	<td class="right"><input type="text" name="M470_CD_GUM_CARD_SUM" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_CD_GUM_CARD_SUM"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_CD_GUM_CARD_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CD_GUM_CARD_SUNAP"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_CD_GUM_CARD_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CD_GUM_CARD_ICHE"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_CD_GUM_CARD_TOT" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CD_GUM_CARD_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_CD_GUM_CARD_DATE" class="box3" value="<%=M470_CD_GUM_CARD_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_CAR_AGO_GC02" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_CAR_SUM_GC02"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_CAR_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CAR_GC02"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_CAR_ICHE_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CAR_ICHE_GC02"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_CAR_SUM_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_CAR_SUM_GC02"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_CAR_AGODATE_GC02" class="box3" value="<%=M470_CAR_AGODATE_GC02%>" size="10" userType="date"></td>
</tr>

<tr>
	<td rowspan="3">������Ư��</td>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_NONG_AGO_GC01" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_NONG_SUM_GC01"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_NONG_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NONG_GC01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_NONG_ICHE_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NONG_ICHE_GC01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_NONG_SUM_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NONG_SUM_GC01"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_NONG_AGODATE_GC01" class="box3" value="<%=M470_NONG_AGODATE_GC01%>" size="10" userType="date"></td>
</tr>
<tr>
  <td>�ݰ��</td>
  <td>ī��</td>
	<td class="right"><input type="text" name="M470_NT_GUM_CARD_SUM" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_NT_GUM_CARD_SUM"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_NT_GUM_CARD_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NT_GUM_CARD_SUNAP"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_NT_GUM_CARD_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NT_GUM_CARD_ICHE"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_NT_GUM_CARD_TOT" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NT_GUM_CARD_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_NT_GUM_CARD_DATE" class="box3" value="<%=M470_NT_GUM_CARD_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_NONG_AGO_GC02" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_NONG_SUM_GC02"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_NONG_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NONG_GC02"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_NONG_ICHE_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NONG_ICHE_GC02"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_NONG_SUM_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_NONG_SUM_GC02"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_NONG_AGODATE_GC02" class="box3" value="<%=M470_NONG_AGODATE_GC02%>" size="10" userType="date"></td>
</tr>

<tr>
	<td rowspan="7">����û <br>���� �ü�</td>
	<td>�ݰ��</td>
	<td>�Ϲ�</td>
	<td class="right"><input type="text" name="M470_BAL_AGO_GP01" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BAL_SUM_GP01"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BAL_GP01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_GP01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BAL_ICHE_GP01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_ICHE_GP01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BAL_SUM_GP01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_SUM_GP01"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BAL_AGODATE_GP01" class="box3" value="<%=M470_BAL_AGODATE_GP01%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_BAL_AGO_GC01" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BAL_SUM_GC01"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BAL_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_GC01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BAL_ICHE_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_ICHE_GC01"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BAL_SUM_GC01" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_SUM_GC01"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BAL_AGODATE_GC01" class="box3" value="<%=M470_BAL_AGODATE_GC01%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_BS_GUM_CARD_SUM" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BS_GUM_CARD_SUM"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BS_GUM_CARD_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_CARD_SUNAP"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_CARD_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_CARD_ICHE"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_CARD_TOT" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_CARD_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BS_GUM_CARD_DATE" class="box3" value="<%=M470_BS_GUM_CARD_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ��</td>
	<td>ARS</td>
	<td class="right"><input type="text" name="M470_BS_GUM_OARS_SUM" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BS_GUM_OARS_SUM"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BS_GUM_OARS_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_OARS_SUNAP"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_OARS_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_OARS_ICHE"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_OARS_TOT" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_OARS_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BS_GUM_OARS_DATE" class="box3" value="<%=M470_BS_GUM_OARS_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ��</td>
	<td>ARS</td>
	<td class="right"><input type="text" name="M470_BS_GUM_NARS_SUM" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BS_GUM_NARS_SUM"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BS_GUM_NARS_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_NARS_SUNAP"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_NARS_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_NARS_ICHE"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_NARS_TOT" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_NARS_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BS_GUM_NARS_DATE" class="box3" value="<%=M470_BS_GUM_NARS_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_BAL_AGO_GC02" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BAL_SUM_GC02"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BAL_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_GC02"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BAL_ICHE_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_ICHE_GC02"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BAL_SUM_GC02" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BAL_SUM_GC02"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BAL_AGODATE_GC02" class="box3" value="<%=M470_BAL_AGODATE_GC02%>" size="10" userType="date"></td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td class="right"><input type="text" name="M470_BS_GUM_NOCARD_SUM" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_BS_GUM_NOCARD_SUM"))%>" userType="money" readonly></td>
	<td class="right"><input type="text" name="M470_BS_GUM_NOCARD_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_NOCARD_SUNAP"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_NOCARD_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_NOCARD_ICHE"))%>" userType="money"></td>
	<td class="right"><input type="text" name="M470_BS_GUM_NOCARD_TOT" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_BS_GUM_NOCARD_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_BS_GUM_NOCARD_DATE" class="box3" value="<%=M470_BS_GUM_NOCARD_DATE%>" size="10" userType="date"></td>
</tr>
<!-- ���ܼ��� -->
<tr>
    <td rowspan="4">���� <br>����</td>
    <td>�ݰ��</td>
    <td>ī��</td>
    <td class="right"><input type="text" name="M470_SS_GUM_OCARD_SUM_AGO" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_SS_GUM_OCARD_SUM"))%>" userType="money" readonly></td>
    <td class="right"><input type="text" name="M470_SS_GUM_OCARD_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_OCARD_SUNAP"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_OCARD_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_OCARD_ICHE"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_OCARD_SUM" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_OCARD_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_SS_GUM_OCARD_DATE" class="box3" value="<%=M470_SS_GUM_OCARD_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
    <td>�ݰ��</td>
    <td>ī��</td>
    <td class="right"><input type="text" name="M470_SS_GUM_NCARD_SUM_AGO" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_SS_GUM_NCARD_SUM"))%>" userType="money" readonly></td>
    <td class="right"><input type="text" name="M470_SS_GUM_NCARD_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_NCARD_SUNAP"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_NCARD_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_NCARD_ICHE"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_NCARD_SUM" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_NCARD_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_SS_GUM_NCARD_DATE" class="box3" value="<%=M470_SS_GUM_NCARD_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
    <td>�ݰ��</td>
    <td>ARS</td>
    <td class="right"><input type="text" name="M470_SS_GUM_OARS_SUM_AGO" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_SS_GUM_OARS_SUM"))%>" userType="money" readonly></td>
    <td class="right"><input type="text" name="M470_SS_GUM_OARS_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_OARS_SUNAP"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_OARS_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_OARS_ICHE"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_OARS_SUM" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_OARS_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_SS_GUM_OARS_DATE" class="box3" value="<%=M470_SS_GUM_OARS_DATE%>" size="10" userType="date"></td>
</tr>
<tr>
    <td>�ݰ��</td>
    <td>ARS</td>
    <td class="right"><input type="text" name="M470_SS_GUM_NARS_SUM_AGO" class="box0" size="15"  value="<%=TextHandler.formatNumber(agoNote.getString("M470_SS_GUM_NARS_SUM"))%>" userType="money" readonly></td>
    <td class="right"><input type="text" name="M470_SS_GUM_NARS_SUNAP" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_NARS_SUNAP"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_NARS_ICHE" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_NARS_ICHE"))%>" userType="money"></td>
    <td class="right"><input type="text" name="M470_SS_GUM_NARS_SUM" class="box_r" size="15" value="<%=TextHandler.formatNumber(dateNote.getString("M470_SS_GUM_NARS_SUM"))%>" userType="money" readonly></td>
  <td><input type="text" name="M470_SS_GUM_NARS_DATE" class="box3" value="<%=M470_SS_GUM_NARS_DATE%>" size="10" userType="date"></td>
</tr>

</table>
</td>
</tr>
<% } %>
</table>
<!-------- ǥ �� ----->

<table class="btn">
  <tr>
	  <td align="right" width="975">
		  <img src="../img/btn_order.gif" alt="���" onClick="saveData()" style="cursor:hand" align="absmiddle">	
			<img src="../img/btn_delete.gif" alt="����" onClick="goDelete()" style="cursor:hand" align="absmiddle">
		</td>
	</tr>
</table>

<table>
  <tr>
	  <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
</form>
</body>
<% if (!"".equals(SucMsg)) { %>
<script>
  alert("<%=SucMsg%>");
</script>
<% } %>
</html>