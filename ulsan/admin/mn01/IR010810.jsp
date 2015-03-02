<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR010810.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ������Ȯ�μ� ��ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%
  List<CommonEntity> accDateList  = (List<CommonEntity>)request.getAttribute("page.mn01.accDateList");
	List<CommonEntity> semokList    = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
	List<CommonEntity> partList     = (List<CommonEntity>)request.getAttribute("page.mn01.partList");

	int listSize = 0;
	if (accDateList != null && accDateList.size() != 0) {
		listSize = accDateList.size();
	}

  String start_acc         = request.getParameter("start_acc");           //����ȸ������
  String end_acc           = request.getParameter("end_acc");             //����ȸ������
	String start_sunap       = request.getParameter("start_sunap");				  //���ۼ�������
	String end_sunap         = request.getParameter("end_sunap");						//�����������

	if (start_acc.equals("")) {
    start_acc  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  }  
  if (end_acc.equals("")) {
    end_acc  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  } 
	if (start_sunap.equals("")) {
	  start_sunap  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  }
	if (end_sunap.equals("")) {
	  end_sunap  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  } 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<style>
    @media print {
      .noprint {
	     display:none;
	    }
    }
    </style>
    <script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
    <script language="javascript" src="../js/tax_common.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function goSearch() {
				var form = document.sForm;
				if (form.start_acc.value == "" && form.end_acc.value == "" && form.start_sunap.value == "" && form.end_sunap.value == "") {
					alert("ȸ������ �Ǵ� �������ڴ� �ʼ��Է��Դϴ�.");
					form.start_acc.focus();
					return;
				}

				if ((form.start_acc.value == "" && form.end_acc.value !="") || (form.start_sunap.value == "" && form.end_sunap.value !="") )	{
					alert("�������� �ԷµǸ� �����ϵ� �ԷµǾ�� �մϴ�.");
					return;
				}
				form.action = "IR010810.etax"; 
				form.cmd.value ="accDateList";
				form.target = "_self";
				eSubmit(form);
			}

			function changeAcc(semok) {
				document.hidFrame.location = "IR010811.etax?cmd=accDateList&acc_code="+semok;
			}

			function goView(b){
			  var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("��ȸ������ �����ϴ�.");
			    return;
		    }  
				window.open('IR010812.etax', 'seipPop' ,'height=400,width=544,top=10,left=100,scrollbars=no');
				form.file.value = b;
		    form.action = "IR010812.etax";
		    form.cmd.value = "receivedView";
		    form.target = "seipPop";
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
  <form name="sForm" method="post" action="IR010810.etax">
	<input type="hidden" name="cmd" value="accDateList">
	<input type="hidden" name="file" value="">
    <table width="1001" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
         <td width="978" height="40"><div class="noprint"><img src="../img/title1_8.gif"></div></td> 
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
									    ȸ������&nbsp; 
											<input type="text" name="start_acc" class="box3" value="<%=start_acc%>" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','start_acc');" style="cursor:hand"> -
        			      	<input type="text" name="end_acc" class="box3" value="<%=end_acc%>" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_acc');" style="cursor:hand">
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											ȸ���&nbsp;
									    <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="changeAcc(this.value)">
											  <option value="">��ü</option>
										    <option value="31">�Ϲ�ȸ��</option>
										    <option value="51">������</option>
									    </select>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											�����&nbsp; 
											<select name="semok_code" iValue="<%=request.getParameter("semok_code")%>">
											  <option value="">��ü</option>
								        <% for (int i=0; semokList != null && i < semokList.size(); i++) {
									         CommonEntity semokInfo = (CommonEntity)semokList.get(i); %>												    
										       <option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											�ΰ���ȣ&nbsp; <input type="text" name="gwase_no" class="box3" size="4" value="" userType="number" maxlength="6">
											<br><br>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											������&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="text" name="start_sunap" class="box3" value="" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','start_sunap');" style="cursor:hand"> -
        			      	<input type="text" name="end_sunap" class="box3" value="" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_sunap');" style="cursor:hand">
											<span style="width:30px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    ��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp; <input type="text" name="amt" class="box3" size="15" value="" userType="money">&nbsp;��
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											�μ���&nbsp; 
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>">
											  <option value="">��ü</option>
								        <% for (int i=0; partList != null && i < partList.size(); i++) {
									         CommonEntity partInfo = (CommonEntity)partList.get(i); %>												    
										       <option value="<%=partInfo.getString("M410_SYSTEMPARTCODE")%>"><%=partInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:30px"></span>
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
        <td width="975" height="20"> &nbsp;</td>
		  </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975">
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
					    <th class="fstleft" width="10%">ȸ������</th>
					    <th width="10%">ȸ�豸��</th>
					    <th width="18%">�μ�</th>
					    <th width="10%">ȸ���</th>
					    <th width="18%">�����</th>
					    <th width="10%">�ΰ���ȣ</th>
							<th width="10%">������</th>
					    <th width="14%">�ݾ�</th>		
				    </tr>
						<%
						  long tot_amt = 0L;

					    for (int i=0; accDateList != null && i < accDateList.size(); i++) {
						    CommonEntity data = (CommonEntity)accDateList.get(i);
	
						  tot_amt += data.getLong("AMT");
				    %>
				    <tr>
					    <td class="fstleft">&nbsp;<a href="javascript:goView('<%=data.getString("ETC_PATH")%>');"><!-- <a href="http://193.1.2.27:8088/report<%=data.getString("ETC_PATH")%>" target="blank"> --><b><%=data.getString("ETC_ACCDATE").substring(0,4)%>-<%=data.getString("ETC_ACCDATE").substring(4,6)%>-<%=data.getString("ETC_ACCDATE").substring(6,8)%></b></a></td>
					    <td class="center">&nbsp;<%=data.getString("ACCTYPE")%></td>
					    <td class="center">&nbsp;<%=data.getString("PARTNAME")%></td>	
					    <td class="center">&nbsp;<%=data.getString("ACCNAME")%></td>
					    <td class="center">&nbsp;<%=data.getString("SEMOKNAME")%></td> 
					    <td class="center">&nbsp;<%=data.getString("ETC_GWASENO")%></td>
							<td class="center">&nbsp;
							<%=data.getString("ETC_SUNAPDATE").substring(0,4)%>-<%=data.getString("ETC_SUNAPDATE").substring(4,6)%>-<%=data.getString("ETC_SUNAPDATE").substring(6,8)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("AMT"))%></td>
            </tr>
				    <%				 
					   } if (accDateList == null || accDateList.size() == 0) { 
				    %>
						<tr>
						  <td class="fstleft" colspan="9">&nbsp;</td>
						</tr>            
						<%
					  	} else {
						%>
						<tr>
					    <td class="fstleft" colspan="2">�� �� ��</td>
					    <td class="center" colspan="2">&nbsp;<%=listSize%></td>
					    <td class="center" colspan="2">�� �� ��</td>	
					    <td class="center" colspan="2">&nbsp;<%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
						<%}%>
			    </table>
			  </td>
		  </tr>
	    <tr> 
        <td width="15">&nbsp;</td>
        <td width="975">
          <div class="noprint">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr>
						  <td>
						    <img src="../img/btn_print.gif" alt="�μ�" onClick="goPrint()" style="cursor:hand" align="absmiddle">
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
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
  </body>
</html>