<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR020211.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е�� ����
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity revWriteView = (CommonEntity)request.getAttribute("page.mn02.revWriteView");
  CommonEntity endChk = (CommonEntity)request.getAttribute("page.mn02.endChk");

  List<CommonEntity> revDeptList  = (List<CommonEntity>)request.getAttribute("page.mn02.revDeptList");	 
  List<CommonEntity> revAccNmList  = (List<CommonEntity>)request.getAttribute("page.mn02.revAccNmList");
	List<CommonEntity> revSemokList  = (List<CommonEntity>)request.getAttribute("page.mn02.revSemokList");

	int listSize = 0;
	if (revWriteView != null && revWriteView.size() != 0) {
		listSize = revWriteView.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn02.SucMsg");
	if (SucMsg == null) {
	  SucMsg = "";  
	}

  String FailMsg = (String)request.getAttribute("page.mn02.FailMsg");
	if (FailMsg == null) {
	  FailMsg = "";  
	}

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
	  <title>��걤���� ���� �� �ڱݹ��������ý���</title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../js/base.js"></script>
    <script language="javascript">
		  function init() {
		    typeInitialize();
      }

			function saveData()	{
  	    var form = document.sForm;
		    form.action = "IR020211.etax";
		    form.cmd.value = "revWriteUpdate";
		    form.target = "_self";
  	    eSubmit(form);
      }

			function chDept(acc_type) {
				var form = document.sForm;
				document.hidFrame.location = "IR020111.etax?cmd=sugibunList&acc_type="+acc_type+"&flag=0";
			}

			function chAcc(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR020111.etax?cmd=sugibunList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1";
			}

		  function chSemok(acc_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR020111.etax?cmd=sugibunList&acc_type="+form.acc_type.value+"&part_code="+form.part_code.value+"&acc_code="+acc_code;
			}
      
	  </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR020211.etax">
  <input type="hidden" name="cmd" value="revWriteUpdate">	
	<% if (revWriteView != null && revWriteView.size() > 0) {%>
  <input type="hidden" name="m030_seq" value="<%=revWriteView.getString("M030_SEQ")%>">
  <input type="hidden" name="fis_year" value="<%=revWriteView.getString("M030_YEAR")%>">
  <input type="hidden" name="fis_date" value="<%=revWriteView.getString("M030_DATE")%>">
  <% } %>
	  <table width="500" border="0" cellspacing="0" cellpadding="5">
		  <tr> 
        <td> 
          <table width="81%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
					    <td width="18">&nbsp;</td>
					    <td width="482" height="50"><img src="../img/title2_3.gif"></td>
				    </tr>
          </table>
			  </td>
      </tr>
	    <% if (revWriteView != null && revWriteView.size() > 0) {%>
      <tr>
        <td>
			    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						  <td>
							  <span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
								<b>ȸ�迬��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <%=revWriteView.getString("M030_YEAR")%>
								<br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
								<b>ȸ������&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <%=revWriteView.getString("M030_DATE").substring(0,4)%>-<%=revWriteView.getString("M030_DATE").substring(4,6)%>-<%=revWriteView.getString("M030_DATE").substring(6,8)%></b>
								<br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
								<b>ȸ�豸��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %> 
                   <%=revWriteView.getString("M030_ACCTYPE_NM")%>
                <% } else { %>
								<select name="acc_type" iValue="<%=revWriteView.getString("M030_ACCTYPE")%>" onchange="chDept(this.value)">
								  <option value="A">�Ϲ�ȸ��</option>
								  <option value="B">Ư��ȸ��</option>
									<option value="E">���</option>
								</select>
                <% 
                  }  
                %>
								<br><br>

                <span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
								<b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{  %>  
                   <%=revWriteView.getString("M350_PARTNAME")%>
                <% } else { %>
                <select name="part_code" iValue="<%=revWriteView.getString("M030_PARTCODE")%>" onchange="chAcc(this.value)">
                  <% for (int i=0; revDeptList != null && i < revDeptList.size(); i++) {
                    CommonEntity deptInfo = (CommonEntity)revDeptList.get(i); %>												    
									  <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									<% } %>
								</select>
                <% 
                  }  
                %>
								<br><br>

                <span style="width:50px"></span>
                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
								<b>ȸ&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{  %>  
                   <%=revWriteView.getString("M360_ACCNAME")%>
                <% } else { %>
                <select name="acc_code" iValue="<%=revWriteView.getString("M030_ACCCODE")%>" onchange="chSemok(this.value)">
								  <% for (int i=0; revAccNmList != null && i < revAccNmList.size(); i++) {
                    CommonEntity accCdInfo = (CommonEntity)revAccNmList.get(i); %>												    
										<option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									<% } %>
							  </select>
                <% 
                  }  
                %>
								<br><br>

								<span style="width:50px"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
								<b>��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{  %>  
                   <%=revWriteView.getString("M370_SEMOKNAME")%>
                <% } else { %>
								<select name="semok_code" iValue="<%=revWriteView.getString("M030_SEMOKCODE")%>">
								  <% for (int i=0; revSemokList != null && i < revSemokList.size(); i++) {
									  CommonEntity semokInfo = (CommonEntity)revSemokList.get(i); %>												    
										<option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									<% } %>
								</select>
                <% 
                  }  
                %>
								<br><br>
                
                <span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
								<b>�Է±���&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{  %>  
                   <%=revWriteView.getString("M030_INTYPE_NM")%>
                <% } else { %>
								<select name="intype" iValue="<%=revWriteView.getString("M030_INTYPE")%>">
                  <option value="I1">����</option>
                  <option value="I2">�ݳ�</option>
                  <option value="I3">����</option>
								</select>
                <% 
                  }  
                %>
								<br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>ä�ּ���&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <input type="text" name="order_name" class="box3" size="19" value="<%=revWriteView.getString("M030_ORDERNAME")%>">
								<br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>���޹�ȣ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <input type="text" name="order_no" class="box3" size="6" maxlength="4" value="<%=revWriteView.getString("M030_ORDERNO")%>" userType="number">
                <input type="hidden" name="org_no" value="<%=revWriteView.getString("M030_ORDERNO")%>">
								<br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{  %>  
                   <%=TextHandler.formatNumber(revWriteView.getString("M030_AMT"))%>&nbsp;��
                <% } else { %>
                <input type="text" name="amt" class="box3" size="15" value="<%=revWriteView.getString("M030_AMT")%>" required desc="�ݾ�" userType="money">&nbsp;��
                <% 
                  }  
                %>
						  </td>
            </tr>
					  <tr>
					    <td width="420" height="100"> 
							  <div align="center"><img src="../img/btn_modify.gif" alt="����" onClick="saveData()" style="cursor:hand"></div>
							</td>
            </tr>
          </table>
				</td>
			</tr>
		<% } %>
		</table>
	</form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	<% if (!"".equals(SucMsg)) { %>
	  <script>
	    alert("<%=SucMsg%>");
      opener.goSearch();
      //self.close();	
    </script>
	<% } %>
  <% if (!"".equals(FailMsg)) { %>
	  <script>
	    alert("<%=FailMsg%>");
    </script>
	<% } %>
  </body>
</html>