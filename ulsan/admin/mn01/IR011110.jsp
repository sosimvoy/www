<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR011110.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > �����ϰ���ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
String saveFile = request.getParameter("saveFile");
%>
<%if(saveFile.equals("")){%>
<%@include file="../menu/mn01.jsp" %>
<%}%>
<%
  List<CommonEntity> semokTotalList = (List<CommonEntity>)request.getAttribute("page.mn01.semokTotalList");
  String eight_business_from_date  = (String)request.getAttribute("page.mn01.eight_business_from_date");    // 8������ �޾ƿ���
  String eight_business_to_date  = (String)request.getAttribute("page.mn01.eight_business_to_date");    // 8������ �޾ƿ���

  int listSize = 0;
  if (semokTotalList != null && semokTotalList.size() != 0) {
	  listSize = semokTotalList.size();
  }

  String gubun = "";
  String semok_name = "";
	String part_name = "";
	
	if("00000".equals(request.getParameter("part_code"))){	  
    part_name = "��û"; 
  }else if ("02130".equals(request.getParameter("part_code"))){
     part_name = "�ߺμҹ漭";           
  }else if ("02140".equals(request.getParameter("part_code"))){
     part_name = "���μҹ漭"; 
	}else if ("02190".equals(request.getParameter("part_code"))){
     part_name = "��ȭ����ȸ��";  
	}else if ("02220".equals(request.getParameter("part_code"))){
     part_name = "����깰���Ž�������繫��"; 
  }else if ("02240".equals(request.getParameter("part_code"))){
     part_name = "������ϻ����"; 
  }else if ("00110".equals(request.getParameter("part_code"))){
     part_name = "�߱�û";           
  }else if ("00140".equals(request.getParameter("part_code"))){
     part_name = "����û"; 
	}else if ("00170".equals(request.getParameter("part_code"))){
     part_name = "����û";  
	}else if ("00200".equals(request.getParameter("part_code"))){
     part_name = "�ϱ�û"; 
	}else if ("00710".equals(request.getParameter("part_code"))){
     part_name = "���ֱ�û";
	}

	

	if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���� ����ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }

  String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
  int last_int = Integer.parseInt(this_year) - 1;
  last_year = String.valueOf(last_int);
  
  String from_date = request.getParameter("from_date");
  if ("".equals(from_date)) {
    from_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  }
  String to_date = request.getParameter("to_date");
  if ("".equals(to_date)) {
    to_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<%}%>
<html>
	<head>
		<title>��걤���� ���� �� �ڱݹ��������ý���</title>
		<%if(saveFile.equals("")){%>
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
    <style>
    @media print {
      .noprint {
	     display:none;
	    }
    }
    </style>
    <%}%>
		<script language="javascript" src="../js/base.js"></script>
		<script language="javascript" src="../js/calendar.js"></script>
		<script language="javascript" src="../js/rowspan.js"></script>	 
		<script language="javascript" src="../js/tax_common.js"></script>
		<script language="javascript">
			function init() {
        typeInitialize();

			  <% if (listSize > 0 ) {%>
          cellMergeChk(document.all.dataList, 1, 0);
        <%}%>
        }     
		
		  function goSearch() {
				var form = document.sForm;		 
        form.saveFile.value = "";
				form.action = "IR011110.etax";
				form.cmd.value = "semokTotal";
				form.target = "_self";
				eSubmit(form);
			}

			function goSave(val){
        var form = document.sForm;
        form.saveFile.value = val;
				form.action = "IR011110.etax";
				form.cmd.value = "semokTotal";
				form.target = "_self";
        eSubmit(form);
      }

      function goPrint() {
        factory.printing.header = ""; // Header�� �� ����  
		    factory.printing.footer = ""; // Footer�� �� ����  
		    factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		    factory.printing.leftMargin = 1.0; // ���� ���� ������  
		    factory.printing.topMargin = 1.0; // �� ���� ������  
		    factory.printing.rightMargin = 1.0; // ������ ���� ������  
		    factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		    factory.printing.Print(true); // ����ϱ� 
      }

		</script>
	</head>
	<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
	<form name="sForm" method="post" action="IR011110.etax">
  <%if(saveFile.equals("")){%>
	<input type="hidden" name="cmd" value="semokTotal">
	<input type="hidden" name="saveFile" value="<%=saveFile%>">
		<table width="1001" border="0" cellspacing="0" cellpadding="0">
			<tr>
        <td width="15">&nbsp;</td>
        <td width="978" height="40"><div class="noprint"><img src="../img/title1_22.gif"></div></td>
      </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="978" height="40">
          <div class="noprint">
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
						<tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
						<tr> 
              <td> 
								<table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
								    <td>
											<span style="width:30px"></span>
								      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											ȸ�迬��&nbsp;
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
									    </select>

											<span style="width:30px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                      ȸ������&nbsp; 
                      <input type="text" name="from_date" class="box3" value="<%=from_date%>" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','from_date');" style="cursor:hand"> -
        			      	<input type="text" name="to_date" class="box3" value="<%=to_date%>" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','to_date');" style="cursor:hand">

											<span style="width:30px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											�μ���&nbsp;
                      <select name="part_code" iValue="<%=request.getParameter("part_code")%>">
                        <option value="">����û�� ������ ��ü</option>
											  <option value="00000">��û</option>
												<option value="99999">����û ��ü</option>
												<option value="02130">�ߺμҹ漭</option>
												<option value="02140">���μҹ漭</option>
												<option value="02190">��ȭ����ȸ��</option>
												<option value="02220">����깰���Ž�������繫��</option>
                        <option value="02240">������ϻ����</option>
                        <option value="00110">�߱�û</option>	
                        <option value="00140">����û</option>	
                        <option value="00170">����û</option>	
                        <option value="00200">�ϱ�û</option>	
                        <option value="00710">���ֱ�û</option>	
											</select>
										</td>
								    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="absmiddle">
                    </td>
									</tr>
						    </table>
					    </td>
				    </tr>
				    <tr> 
					    <td><img src="../img/box_bt.gif" width="964" height="10"></td>
				    </tr>
			    </table>
          </div>
				</td>
	    </tr>
		  <tr>
        <td width="18">&nbsp;</td>
        <td width="978" height="20"> &nbsp;</td>
		  </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="978">
					<%}%>
          <div id="pDiv">
           
					<table width="964" border="0" cellspacing="0" cellpadding="0">

					  <tr>
						  <td align="center"><b><font size="3"><%=part_name%> ���� ����ǥ
              <% if (semokTotalList.size() > 0 && semokTotalList != null) { %>
              (<%=request.getParameter("fis_year")%>)
              <%}%>
              </font></b></td> 
						</tr>
						<tr>
						  <td align="right"><b><font size="2">�����Ⱓ
              <% if (semokTotalList.size() > 0 && semokTotalList != null) { %>
								  <% if("99999".equals(request.getParameter("part_code")) || "00110".equals(request.getParameter("part_code")) || "00140".equals(request.getParameter("part_code")) || "00170".equals(request.getParameter("part_code")) || "00200".equals(request.getParameter("part_code")) || "00710".equals(request.getParameter("part_code")) ){ %>
								
								 : <%=eight_business_from_date.substring(0,4)%>-<%=eight_business_from_date.substring(4,6)%>-<%=eight_business_from_date.substring(6,8)%> ~    
									 <%=eight_business_to_date.substring(0,4)%>-<%=eight_business_to_date.substring(4,6)%>-<%=eight_business_to_date.substring(6,8)%> 
							  	<%} else {%>
								 : <%=TextHandler.formatDate(request.getParameter("from_date"),"yyyyMMdd","yyyy-MM-dd")%> ~  
								   <%=TextHandler.formatDate(request.getParameter("to_date"),"yyyyMMdd","yyyy-MM-dd")%>
								  <% } %>
								<%}%></font></b></td>
						</tr>
					</table>
			    <table id="dataList" width="964" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
	            <th class="fstleft">����</th>
							<th>�����</th>
		          <th>�Ⱓ�ߴ���</th>
		          <th>���ߴ���</th>
	          </tr>
					<%
          if (semokTotalList.size() > 0 && semokTotalList != null) { 
          %>
						<% 
            for (int i=0; i < semokTotalList.size(); i++) {
                CommonEntity rowData = (CommonEntity)semokTotalList.get(i);


                if(rowData.getString("M370_SEMOKNAME").equals("")){		
                    semok_name = "&nbsp;&nbsp;�� ��"; 
                }else{
                    semok_name = rowData.getString("M370_SEMOKNAME"); 
                }
                     
								if(rowData.getString("GUBUN").equals("")){					   
                     gubun = "�� �� ��"; 
                    semok_name = "��  ��"; 
                }else{
                    gubun = rowData.getString("GUBUN"); 
                }	
            %>
						 <tr> 
              <td class="fstleft">&nbsp;<%=gubun%></td>
							<td>&nbsp;<%=semok_name%></td>
              <td class="right">&nbsp;<%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></td>
              <td class="right">&nbsp;<%=StringUtil.formatNumber(rowData.getString("TO_AMT"))%></td>
	          </tr>
						 <% } %>		 
						<% }else{ %>
						<tr> 
              <td class="fstleft" colspan="4">&nbsp;</td>
	          </tr>
						<%}%>
						
					 </table>
           </div>
				</td>
		  </tr>
	    <tr> 
        <td width="15">&nbsp;</td>
        <td width="975">
				<%if(saveFile.equals("")){%>
          <div class="noprint">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr>
						  <td>
							  <img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						    <img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint()" style="cursor:hand" align="absmiddle">		  
					    </td> 
					  </tr>	
			    </table>
          </div>
		    </td>
	    </tr>
    </table>
    <div class="noprint">
    <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
    </div>
		<%}%>
  <iframe name="hidFrame" id="hidFrame" width="0" height="0"></iframe>
  </form>
  </body>
</html>

