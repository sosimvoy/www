<%
/***************************************************************
* ������Ʈ��     : ��걤���� �ڱݹ��������ý���
* ���α׷���     : IR061511.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ȸ�迬���� ���� ȸ�� �ڵ�/��
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List accList = (List)request.getAttribute("page.mn06.accList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //ȸ���ڵ� ���� Object ����
    function AccInfo() {
        this.accCode;   //ȸ���ڵ�
        this.accName;    //ȸ���̸�
        this.name = getName; //name �������
        this.code = getCode; //ȸ���
    }
    
    //name code �������(ȸ���)
    function getName() {
        return this.accName;
    }

    function getCode() {
        return this.accCode.toString();
    }
    
    //ȸ�� ���� Object �� ���� �迭 ����
    var accArrayList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity acctInfo = (CommonEntity)accList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.accCode = "<%=acctInfo.getString("M360_VALUE")%>";
    newObject.accName = "<%=acctInfo.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    accArrayList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ȸ�� ���� ���� */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.acc_code;
        } catch(e) {
            parentSelect = parent.document.uForm.acc_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
						addOption(parentSelect, "", "��ü����");
            for( var i=0 ; i < accArrayList.length ; i++ ) {
                var object = accArrayList[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }
    }        
</script>
