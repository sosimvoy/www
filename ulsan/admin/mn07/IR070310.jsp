<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	    : IR070310.jsp
* ���α׷��ۼ���    : (��)�̸����� 
* ���α׷��ۼ���    : 2010-10-01
* ���α׷�����	    : �ϰ�/���� > ȸ�迬���̿�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
	List<CommonEntity> transList = (List<CommonEntity>)request.getAttribute("page.mn07.transList");
	String retMsg = (String)request.getAttribute("page.mn07.retMsg");
    //out.println("transList::"+transList);

    String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
    String next_year = request.getParameter("next_year");

    if(next_year.equals("")){
        next_year = String.valueOf(Integer.parseInt(this_year) + 1);
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

    function getRadioValue(){
        var form = document.sForm;
        var len = form.chk.length;
        var radioValue = "";

        if (len == undefined) {
                radioValue = form.chk.value;
        } else {
            for(i=0; i<len; i++){
                 if(form.chk[i].checked){
                    radioValue = form.chk[i].value;
                    break;
                 }
            }
        }
        return radioValue;
    }


    function chYear(acc_year){  // �̿�ȸ��⵵ = ������ ȸ��⵵ + 1
        var form = document.sForm;
        form.next_year.value = parseInt(acc_year) + 1;
    }

    function goSearch(){
        var form = document.sForm;
        form.cmd.value = "nextYearList";
        eSubmit(form);
    }

    function goInsert(){
        var form = document.sForm;
        var transData = getRadioValue();
        
        if (transData == '' ){
            alert('���õ� �̿��ڷᰡ �������� �ʽ��ϴ�.');
            return;
        }
        
        if(form.today.value < transData.substring(2,10)){
            alert("�������� �����Ϻ��� ū ��� �̿��� �Ұ� �մϴ�");
            return;
        }else if (transData.substring(10,11) == 'N'){
            alert("�̸��� ���� �Դϴ�.\n������ �ڷḸ �̿� �����մϴ�.");
            return;
        }else if (transData.substring(11,12) == 'Y'){
            alert("�̿��� �ڷ� �Դϴ�.");
            return;
        }
        form.trans_data.value = transData;
        form.cmd.value = "nextYearProc";
        eSubmit(form);
    }

    function goCancel(){
        var form = document.sForm;
        var transData = getRadioValue();
        
        if (transData == '' ){
            alert('���õ� �̿��ڷᰡ �������� �ʽ��ϴ�.');
            return;
        }
        
        if(form.today.value < transData.substring(2,10)){
            alert("�������� �����Ϻ��� ū ��� ��� �Ұ� �մϴ�");
            return;
        }else if (transData.substring(10,11) == 'N'){
            alert("�̸��� ���� �Դϴ�.\n������ �ڷḸ �̿� �����մϴ�.");
            return;
        }else if (transData.substring(11,12) == 'N'){
            alert("�̿� �� �Ǵ� ��ҵ� �ڷ� �Դϴ�.\n�̿� �� ��� �����մϴ�.");
            return;
        }
        form.trans_data.value = transData;
        form.cmd.value = "nextYearProcCancel";
        eSubmit(form);
    }

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
<form name="sForm" method="post" action="IR070310.etax">
<input type="hidden" name="cmd"             value="nextYearList">
<input type="hidden" name="work_log"        value="A07">
<input type="hidden" name="trans_gubun"     value="131">
<input type="hidden" name="today"           value="<%=TextHandler.getCurrentDate()%>">
<input type="hidden" name="trans_data"      value="">
<table width="1000" border="0" cellspacing="0" cellpadding="5">
    <tr> 
	    <td> 
		    <table width="81%" border="0" cellspacing="0" cellpadding="0">
			    <tr>
                    <td width="15">&nbsp;</td>
                    <td width="975" height="40"><img src="../img/title7_3.gif"></td>
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
                								<span style="width:20px"></span>
                                                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                								������� ȸ�迬�� 
                                                <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>" onChange="chYear(this.value)">
                                                    <option value="<%=this_year%>"><%=this_year%></option>
                                                    <option value="<%=last_year%>"><%=last_year%></option>
                                                </select>

                								<br><br>
                								<span style="width:20px"></span>
                                                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                								�̿� ȸ�迬�� 
                                                <input type="text" name="next_year" value="<%=next_year%>" size="4" readonly>

                                                <span style="width:50px"></span>
                                                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									            ȸ�豸��
                                                <select name="acc_type" desc="ȸ�豸��" iValue="<%=request.getParameter("acc_type")%>">
                                                    <option value="A">�Ϲ�ȸ��</option>
                                                    <option value="B">Ư��ȸ��</option>
                                                    <!-- <option value="C">�����Ư��ȸ��</option> -->
                                                    <option value="D">���Լ��������</option>
                                                    <option value="E">���</option>
                                                </select>

                								<span style="width:400px"> </span>
                								<img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" valign="middle" align="absmiddle">
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
                    <td width="18">&nbsp;</td>
                    <td width="975" height="20"> &nbsp;</td>
	            </tr>
                <tr> 
                    <td width="18">&nbsp;</td>
                    <td width="975"> 
                        <table width="975" border="0" cellspacing="0" cellpadding="0" class="list">
                    		<tr>
                    		    <th width="60" class="fstleft">����</th>
                    			<th>ȸ���</th>
                    			<th width="100">������</th>
                    			<th width="80">��������</th>
                    			<th width="80">�̿�����</th>
                    			<th width="120">�׿����Ѿ�</th>
                    			<th width="120">�����Ծ�</th>
                    			<th width="120">�������Ծ�</th>
                    		</tr>
                            <%
                            if(transList != null && transList.size() > 0){
                                for (int i=0; i < transList.size(); i++) {
                                    CommonEntity rowData = (CommonEntity)transList.get(i);
                            %>
                    		<tr>
                                <input type="hidden" name="workendstate" value="<%=rowData.getString("WORKENDSTATE")%>">
                                <input type="hidden" name="acctransferyn" value="<%=rowData.getString("ACCTRANSFERYN")%>">
                    			<td class="fstleft">
                                    <!-- 12 = ȸ���ڵ�(2) + ������(8) + ��������(1) + �̿�����(1) -->
                                    <input type="radio" name="chk" value="<%=rowData.getString("ACCCODE")+rowData.getString("ACCDATE")+rowData.getString("WORKENDSTATE")+rowData.getString("ACCTRANSFERYN")%>" <%=(i==0)? "checked ":""%>>
                                </td>
                    			<td class="left">[<%=rowData.getString("ACCCODE")%>] <%=rowData.getString("ACCNAME")%></td>
                    			<td><%=TextHandler.formatDate(rowData.getString("ACCDATE"),"yyyyMMdd","yyyy-MM-dd")%></td>
                    			<td><%=("Y".equals(rowData.getString("WORKENDSTATE")))? "����":"�̸���" %></td>
                    			<td><%=("Y".equals(rowData.getString("ACCTRANSFERYN")))? "�̿�":"���̿�" %></td>
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("ING_AMT"))%></td>
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("PRE_SUR_AMT"))%></td>
                    			<% if ("D".equals(request.getParameter("acc_type")) ) { %>  
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("ING_AMT"))%></td>
                    			<% } else { %>
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_SUR_AMT"))%></td>
                    			<% } %>
                    		</tr>
                            <%  
                                }
                            }else{
                            %>
                    		<tr>
                                <td colspan="8">��ȸ�� �ڷᰡ �����ϴ�.</td>
                    		</tr>
                            <%}%>
                    	</table>
                    </td>
                </tr>
                <tr> 
                    <td width="18">&nbsp;</td>
                    <td width="975"> 
                        <table width="975" border="0" cellspacing="0" cellpadding="0" class="btn">
                            <tr> 
                                <td>
                            		<img src="../img/btn_carry.gif" alt="�̿�" onClick="goInsert()" style="cursor:hand" align="absmiddle">
                            		<img src="../img/btn_cancel.gif" alt="���" onClick="goCancel()" style="cursor:hand" align="absmiddle">
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
        </td>
    </tr>
</table>
</form>
</body>
</html>


<% if (!"".equals(retMsg) && retMsg != null) { %>
<script language=javascript>
    alert('<%=retMsg%>');
</script>
<% } %>
