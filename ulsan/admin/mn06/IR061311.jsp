<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR061311.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ȸ�豸�п� ���� ȸ�� ����
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // ȸ���ڵ� (ȸ�豸�п� ����)
    List acctList = (List)request.getAttribute("page.mn06.acctList");

    // �μ��ڵ� (ȸ�豸�п� ����)
    List partList = (List)request.getAttribute("page.mn06.partList");
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
        this.acctCode;   //ȸ���ڵ�
        this.acctName;    //ȸ���̸�
        this.name = getName; //ȸ���
        this.code = getCode; //ȸ���ڵ�
    }


    //�μ��ڵ� ���� Object ����
    function partInfo() {
        this.partCode;   //�μ��ڵ�
        this.partName;    //�μ��̸�
        this.name1 = getName1; //�μ���
        this.code1 = getCode1; //�μ��ڵ�
    }
    
    //text value �������(ȸ���)
    function getName() {
        return this.acctName;
    }

    function getCode() {
        return this.acctCode.toString();
    }


    //text value �������(ȸ���)
    function getName1() {
        return this.partName;
    }

    function getCode1() {
        return this.partCode.toString();
    }
    
    //ȸ�� ���� Object �� ���� �迭 ����
    var acctList = new Array();

    <%
    for( int i=0 ; acctList != null && i < acctList.size() ; i++ ) {
        CommonEntity acctInfo = (CommonEntity)acctList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.acctCode = "<%=acctInfo.getString("M360_ACCCODE")%>";
    newObject.acctName = "<%=acctInfo.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    acctList[<%=i%>] = newObject;
    <%
    }
    %>


    //�μ� ���� Object �� ���� �迭 ����
    var partList = new Array();

    <%
    for( int i=0 ; partList != null && i < partList.size() ; i++ ) {
        CommonEntity partInfo = (CommonEntity)partList.get(i);
    %>
    var newObject = new partInfo();
    newObject.partCode = "<%=partInfo.getString("M350_PARTCODE")%>";
    newObject.partName = "<%=partInfo.getString("M350_PARTNAME")%>";
    //�迭�� ���� Object ����
    partList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ȸ�� ���� ���� */
        var parentSelect1 = null;
        var parentSelect = null;
        try {
            <% if("".equals(request.getParameter("part_code")) )  {%>
            parentSelect1 = parent.document.sForm.part_code;
            <% } %>
            parentSelect = parent.document.sForm.acct_list;
        } catch(e) {
            parentSelect = parent.document.uForm.acct_list;
        }
        
        if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            for( var i=0 ; i < partList.length ; i++ ) {
                var object = partList[i];
                addOption(parentSelect1, object.code1(), object.name1());
            }
        }

        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            //addOption(parentSelect, "", "��ü����");
            for( var i=0 ; i < acctList.length ; i++ ) {
                var object = acctList[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }
    }        
</script>
