<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR030111.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-07-16
* ���α׷�����   : �μ��ڵ忡 ���� ȸ���
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> accNmList  = (List<CommonEntity>)request.getAttribute("page.mn01.accNmList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //������������ ���� Object ����
    function LawHangInfo() {
        this.acctCode;   //ȸ���ڵ�
        this.acctName;   //ȸ���
        this.text = getTextValue; //text �������
        this.value = getValue; // ȸ���ڵ�
    }
    
    //text value �������(������-������)
    function getTextValue() {
        return this.acctName;
    }

    function getValue() {
        return this.acctCode.toString();
    }
    
    //������������ ���� Object �� ���� �迭 ����
    var acctCdInfoList = new Array();

    <%
    for( int i=0 ; accNmList != null && i < accNmList.size() ; i++ ) {
        CommonEntity acctCdInfo = (CommonEntity)accNmList.get(i);
    %>
    var newObject = new LawHangInfo();
    newObject.acctCode = "<%=acctCdInfo.getString("M360_ACCCODE")%>";
    newObject.acctName = "<%=acctCdInfo.getString("M360_ACCNAME")%>";
    
		//�迭�� ���� Object ����
    acctCdInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ������������ ���� ���� */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.acc_code;
        } catch(e) {
            parentSelect = parent.document.uForm.acc_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            for( var i=0 ; i < acctCdInfoList.length ; i++ ) {
                var object = acctCdInfoList[i];
                addOption(parentSelect, object.value(), object.text());
            }
        }
    }        
</script>
