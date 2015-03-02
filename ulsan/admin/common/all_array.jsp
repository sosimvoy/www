<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : semok_array.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ȸ�豸�п� ���� ���񸮽�Ʈ ����
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn01.specDeptList");
		List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn01.specNameList");
		List<CommonEntity> semokList = (List<CommonEntity>)request.getAttribute("page.mn01.specSemokList");
		String array_val = (String)request.getParameter("array_val");
		String array_row = (String)request.getParameter("array_row");
		String rowCount  = (String)request.getParameter("rowCount");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    
    function deptInfo() {
        this.deptCd;   //�μ��ڵ�
        this.deptNm;   //�μ���
        this.text  = getDeptTextValue; //text �������
        this.value = getDeptValue; //�μ���
    }

		function accInfo() {
        this.accCd;   //ȸ���ڵ�
        this.accNm;   //ȸ���
        this.text  = getAccTextValue; //text �������
        this.value = getAccValue; //ȸ���
    }

		function semokInfo() {
        this.semokCd;   //�����ڵ�
        this.semokNm;   //�����
        this.text  = getSemokTextValue; //text �������
        this.value = getSemokValue; //�����
    }

		//text value �������(�μ��ڵ�-�μ���)
    function getDeptTextValue() {
        return this.deptNm;
    }

    function getDeptValue() {
        return this.deptCd.toString();
    }

		//text value �������(ȸ���ڵ�-ȸ���)
    function getAccTextValue() {
        return this.accNm;
    }

    function getAccValue() {
        return this.accCd.toString();
    }

		//text value �������(�����ڵ�-�����)
    function getSemokTextValue() {
        return this.semokNm;
    }

    function getSemokValue() {
        return this.semokCd.toString();
    }
    
    //�μ� ���� Object �� ���� �迭 ����
    var deptInfoList = new Array();

    <%
    for( int i=0 ; deptList != null && i < deptList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)deptList.get(i);
    %>
    var newObject = new deptInfo();
    newObject.deptCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //�迭�� ���� Object ����
    deptInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//ȸ�� ���� Object �� ���� �迭 ����
    var accInfoList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accList.get(i);
    %>
    var newObject = new accInfo();
    newObject.accCd = "<%=rowData.getString("M360_ACCCODE")%>";
    newObject.accNm = "<%=rowData.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    accInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//���� ���� Object �� ���� �迭 ����
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new semokInfo();
    newObject.semokCd = "<%=rowData.getString("M370_SEMOKCODE")%>";
    newObject.semokNm = "<%=rowData.getString("M370_SEMOKNAME")%>";
    //�迭�� ���� Object ����
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ���� ���� ���� */
        var parentSelect1 = null;
				var parentSelect2 = null;
				var parentSelect3 = null;
				var bonus = "<%=request.getParameter("bonus")%>";
        try {
					<% if ("1".equals(rowCount) && "0".equals(array_val)) { %>
						if (bonus == "0")	{
						  parentSelect1 = parent.document.sForm.part_code;
							parentSelect2 = parent.document.sForm.acc_code;
						}
						if (bonus == "1")	{
					    parentSelect2 = parent.document.sForm.acc_code;
						}
						parentSelect3 = parent.document.sForm.semok_code;
					<% } else if ("0".equals(array_val) && !"1".equals(rowCount)) { %>
						if (bonus == "0")	{
						  parentSelect1 = parent.document.sForm.part_code[0];
							parentSelect2 = parent.document.sForm.acc_code[0];
						}
						if (bonus == "1")	{
						  parentSelect2 = parent.document.sForm.acc_code[0];
						}
						parentSelect3 = parent.document.sForm.semok_code[0];
					<% } else { %>
						if (bonus == "0")	{
						  parentSelect1 = parent.document.sForm.part_code[<%=array_row%>];
							parentSelect2 = parent.document.sForm.acc_code[<%=array_row%>];
						}
						if (bonus == "1")	{
						  parentSelect2 = parent.document.sForm.acc_code[<%=array_row%>];
						}
						parentSelect3 = parent.document.sForm.semok_code[<%=array_row%>];
					<% } %>
        } catch(e) {

        }
        if( parentSelect1 != null ) {
          deleteOptions(parentSelect1);
          for( var i=0 ; i < deptInfoList.length ; i++ ) {
            var object = deptInfoList[i];
            addOption(parentSelect1, object.value(), object.text());
          }
        }

				if( parentSelect2 != null ) {
          deleteOptions(parentSelect2);
          for( var i=0 ; i < accInfoList.length ; i++ ) {
            var object = accInfoList[i];
            addOption(parentSelect2, object.value(), object.text());
          }
        }

				if( parentSelect3 != null ) {
          deleteOptions(parentSelect3);
          for( var i=0 ; i < semokInfoList.length ; i++ ) {
            var object = semokInfoList[i];
            addOption(parentSelect3, object.value(), object.text());
          }
        }
    }        
</script>