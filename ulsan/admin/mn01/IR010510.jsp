<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR010510.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����� ��ȸ/����/����
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%	
	List<CommonEntity> expWriteList = (List<CommonEntity>)request.getAttribute("page.mn01.expWriteList");
	List<CommonEntity> deptList  =  (List<CommonEntity>)request.getAttribute("page.mn01.deptList");
  List<CommonEntity> accCdList  = (List<CommonEntity>)request.getAttribute("page.mn01.accCdList");
  List<CommonEntity> semokList  = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
  List<CommonEntity> gigwanList  = (List<CommonEntity>)request.getAttribute("page.mn01.gigwanList");
  CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date");  //ȸ������
  
	String delMsg =  (String)request.getAttribute("page.mn01.delMsg");
	if (delMsg == null) {
		delMsg = "";
	}

  int listSize = 0;
	if (expWriteList != null && expWriteList.size() != 0) {
		listSize = expWriteList.size();
	}

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
  
  String fis_date = request.getParameter("fis_date");
  if (fis_date == null || "".equals(fis_date)) { 
    fis_date = TextHandler.formatDate(dateInfo.getString("FIS_DATE"),"yyyyMMdd","yyyy-MM-dd");
  }

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
		<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      function init() {
		    typeInitialize();
      }	
	    
			function goSearch() {
	      var form = document.sForm;
		    form.action = "IR010510.etax";
		    form.cmd.value = "expWriteList2";
		    form.target = "_self";
		    eSubmit(form);
	    }

			function goDelete(){
		    var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("��ȸ������ �����ϴ�.");
			    return;
		    }
		    if (listSize == 1) {
		      if (!form.chk_yn.checked) {
			    alert("������ ���� �����ϼ���");
			    return;
			    }
		    } else if (listSize > 1) {
		      for (var i=0; i<listSize; i++) {
			      if (form.chk_yn[i].checked) {
            cnt++;
			      }  
			    }
			 
			    if (cnt == 0) {
			      alert("������ ���� �����ϼ���");
		  	    return;
			    }
		    }
  	  form.action = "IR010510.etax";  
      form.cmd.value ="expWriteDelete";
			form.target = "_self";
		  eSubmit(form);
      }

			function goView(){
        var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("��ȸ������ �����ϴ�.");
			    return;
		    }

		    if (listSize == 1) {
	        if (!form.chk_yn.checked) {
			      alert("������ ���� �����ϼ���");
				    return;
			    }

          form.pop_acc_type.value = form.chk_acc_type.value;  
          form.pop_part_code.value = form.chk_part_code.value;
          form.pop_acc_code.value = form.chk_acc_code.value;

		    } else if (listSize > 1) {
			      for (var i=0; i<listSize; i++) {
				      if (form.chk_yn[i].checked) {
                cnt++;
                form.pop_acc_type.value = form.chk_acc_type[i].value;  
                form.pop_part_code.value = form.chk_part_code[i].value;
                form.pop_acc_code.value = form.chk_acc_code[i].value;
			        }
			      }
			 
			      if (cnt == 0) {
				      alert("������ ���� �����ϼ���");
				      return;
			      }

			      if (cnt > 1) {
				      alert("�����ϰ����ϴ� ���� �ϳ��� �����ϼ���.");
				      return;
			      }
		      }
         window.open('IR010511.etax', 'popView' ,'height=500,width=400,top=100,left=100,scrollbars=no');
		     form.action = "IR010511.etax"
		     form.cmd.value = "expWriteView";
		     form.target = "popView";
		     eSubmit(form);
        }	
																																																									
      function chDept(acc_type) {
				var form = document.sForm;
				var acc_code;
				if (acc_type == "A")	{
					acc_code = "11";
				}	else if (acc_type == "B")	{
					acc_code = "04";
				} else if (acc_type == "E") {
          acc_code = "01"
				}
				document.hidFrame.location = "IR010512.etax?cmd=expWriteList&acc_type="+acc_type+"&acc_code="+acc_code+"&flag=0";
			}

			function chAcc(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR010512.etax?cmd=expWriteList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1";
			}

		  function chSemok(acc_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR010512.etax?cmd=expWriteList&acc_type="+form.acc_type.value+"&part_code="+form.part_code.value+"&acc_code="+acc_code;
			}

	  </script>
  </head>
	<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
	<form name="sForm" method="post" action="IR010510.etax">
	<input type="hidden" name="cmd" value="expWriteList2">
	<input type="hidden" name="m010_seq" value="">
	<input type="hidden" name="org_year" value="">  
  <!-- �˾��� ������ �ڵ尪 -->                                               
	<input type="hidden" name="pop_acc_type" value="">
	<input type="hidden" name="pop_part_code" value="">
	<input type="hidden" name="pop_acc_code" value="">
  
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_6.gif"></td>
      </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="975" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							    <tr>
								    <td>
									    <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    ȸ�迬��&nbsp;
									    <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>" userType="number">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
									    </select>
									    
                      <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									    ȸ������&nbsp; <input type="text" name="fis_date" class="box3" size="8" value="<%=fis_date%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
											
                      <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �Է±���&nbsp;
										  <select name="intype" iValue="<%=request.getParameter("intype")%>">
											  <option value="">��ü</option>
								        <option value="I1">����</option>
											  <option value="I2">������</option>
											  <option value="I3">����</option>
										  </select>
											
                      <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                      �������&nbsp;
                      <select name="gigwan" iValue="<%=request.getParameter("gigwan")%>">
                        <option value="">��ü</option>
								        <% for (int i=0; gigwanList != null && i < gigwanList.size(); i++) {
									         CommonEntity gigwanInfo = (CommonEntity)gigwanList.get(i); %>												    
										       <option value="<%=gigwanInfo.getString("M430_SUNAPGIGWANCODE")%>"><%=gigwanInfo.getString("M430_SUNAPGIGWANNAME")%></option>
									      <% } %>
								      </select>

                      <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
								      ȸ�豸��&nbsp;
									    <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
											  <option value="">��ü</option>
										    <option value="A">�Ϲ�ȸ��</option>
										    <option value="B">Ư��ȸ��</option>
										    <option value="E">���</option>
									    </select>
											<span style="width:30px"></span>
                      <img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="middle">
											<br><br>
											
                      <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �μ�&nbsp; 
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="chAcc(this.value)">
											  <option value="">��ü</option>
								        <% for (int i=0; deptList != null && i < deptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)deptList.get(i); %>												    
										       <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
											
                      <span style="width:10px"></span>
								      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
							       	ȸ���&nbsp; 
								      <select name="acc_code" ivalue="<%=request.getParameter("acc_code")%>" onchange="chSemok(this.value)">
											  <option value="">��ü</option>
								        <% for (int i=0; accCdList != null && i < accCdList.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)accCdList.get(i); %>												    
								        <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
								      </select>
											
                      <span style="width:10px"></span>
					  	        <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
							        �����&nbsp;	
								      <select name="semok_code" iValue="<%=request.getParameter("semok_code")%>">
											  <option value="">��ü</option>
								        <% for (int i=0; semokList != null && i < semokList.size(); i++) {
									         CommonEntity semokInfo = (CommonEntity)semokList.get(i); %>												    
										    <option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									      <% } %>
								      </select>
											
                      <span style="width:10px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
									    �ݾ�&nbsp; <input type="text" name="amt" class="box3" size="15" value="" userType="money">&nbsp;��
								    </td>
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
        <td width="15">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
		  </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
					    <th class="fstleft" width="4%">����</th>
							<th width="7%">ȸ�迬��</th>
							<th width="8%">ȸ������</th>
							<th width="7%">�Է±���</th>
							<th width="7%">�⵵����</th>
							<th width="9%">�������</th>
					    <th width="8%">ȸ�豸��</th>
							<th width="12%">�μ�</th>
					    <th width="8%">ȸ���</th>
							<th width="13%">�����</th>
							<th width="12%">�ݾ�</th>
					    <th width="5%">�Ǽ�</th>
				    </tr>
						<%
					    for (int i=0; expWriteList != null && i < expWriteList.size(); i++) {
					  	  CommonEntity data = (CommonEntity)expWriteList.get(i);
				    %>
				    <tr> 
              <input type="hidden" name="chk_acc_type" value="<%=data.getString("M010_ACCTYPE")%>">  
              <input type="hidden" name="chk_part_code" value="<%=data.getString("M010_PARTCODE")%>">
              <input type="hidden" name="chk_acc_code" value="<%=data.getString("M010_ACCCODE")%>">
			  	    <td class="fstleft">&nbsp;<input type="checkbox" name="chk_yn" value="<%=i%>"></td>
					    <td class="center">&nbsp;<%=data.getString("M010_YEAR")%></td>
              <td class="center">&nbsp;
							<%=data.getString("M010_DATE").substring(0,4)%>-<%=data.getString("M010_DATE").substring(4,6)%>-<%=data.getString("M010_DATE").substring(6,8)%></td>
              <td class="center">&nbsp;<%=data.getString("M010_INTYPE_NM")%></td>
							<td class="center">&nbsp;<%=data.getString("M010_YEARTYPE_NM")%></td>
							<td class="center">&nbsp;<%=data.getString("M430_SUNAPGIGWANNAME")%></td>
              <td class="center">&nbsp;<%=data.getString("M010_ACCTYPE_NM")%></td>
              <td class="center">&nbsp;<%=data.getString("M350_PARTNAME")%></td>
              <td class="center">&nbsp;<%=data.getString("M360_ACCNAME")%></td>
              <td class="center">&nbsp;<%=data.getString("M370_SEMOKNAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M010_AMT"))%>��</td>
							<td class="right">&nbsp;<%=data.getString("M010_CNT")%>��</td>
							<input type="hidden" name="year_list" value="<%=data.getString("M010_YEAR")%>">
					    <input type="hidden" name="seq_list" value="<%=data.getString("M010_SEQ")%>">
				    </tr>
				    <%				 
				      } if (expWriteList == null || expWriteList.size() == 0) { 
				    %>
				    <tr> 
              <td class="fstleft" colspan="12">&nbsp;</td>
            </tr>
				    <% } %>
			    </table>
			  </td>
		  </tr>
	    <tr> 
        <td width="15">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
                <img src="../img/btn_modify.gif" alt="����" onClick="goView()" style="cursor:hand" align="absmiddle">
						    <img src="../img/btn_delete.gif" alt="����" onClick="goDelete()" style="cursor:hand" align="absmiddle">
					    </td>
				    </tr>
			    </table>
		    </td>
	    </tr>
		</table>
		<table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	  <% if (!"".equals(delMsg)) { %>
	    <script>
	    alert("<%=delMsg%>");
		  </script>
	  <% } %>
  </body>
</html>