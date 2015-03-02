<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR091611.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ȸ�豸�п� ���� ȸ�� ����
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%

  List<CommonEntity>usePartList    = (List<CommonEntity>)request.getAttribute("page.mn09.usePartList");
  List<CommonEntity>useAcccodeList = (List<CommonEntity>)request.getAttribute("page.mn09.useAcccodeList");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/utility.js"></script>
<script language="JavaScript">
    
    //ȸ���ڵ� ���� Object ����
    function CodeInfo() {
        this.CodeCode;   //�ڵ�
        this.CodeName;    //�ڵ��
        this.name = getName; //�ڵ��
        this.code = getCode; //�ڵ�
    }

    //text value �������(ȸ���)
    function getName() {
        return this.CodeName;
    }

    function getCode() {
        return this.CodeCode.toString();
    }


    //���μ� Object �� ���� �迭 ����
    var use_partList = new Array();

    <%
    for( int i=0 ; usePartList != null && i < usePartList.size() ; i++ ) {
        CommonEntity partInfo = (CommonEntity)usePartList.get(i);
    %>
    var partObject = new CodeInfo();
    partObject.CodeCode = "<%=partInfo.getString("M350_PARTCODE")%>";
    partObject.CodeName = "<%=partInfo.getString("M350_PARTNAME")%>";
    //�迭�� ���� Object ����
    use_partList[<%=i%>] = partObject;
    <%
    }
    %>
   
    //���⵵���꼼�� Object �� ���� �迭 ����
    var use_accountList = new Array();

    <%
    for( int i=0 ; useAcccodeList != null && i < useAcccodeList.size() ; i++ ) {
        CommonEntity accountInfo = (CommonEntity)useAcccodeList.get(i);
    %>
    var accountObject = new CodeInfo();
    accountObject.CodeCode = "<%=accountInfo.getString("M360_ACCCODE")%>";
    accountObject.CodeName = "<%=accountInfo.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    use_accountList[<%=i%>] = accountObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ȸ�� ���� ���� */
        
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.sysPartcode;
        } catch(e) {
            parentSelect = parent.document.uForm.sysPartcode;
        }
        
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            for( var i=0 ; i < use_partList.length ; i++ ) {
                var object = use_partList[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }
        var parentSelect1 = null;
        try {
            parentSelect1 = parent.document.sForm.sysAccount;
        } catch(e) {
            parentSelect1 = parent.document.uForm.sysAccount;
        }
        
        if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            for( var i=0 ; i < use_accountList.length ; i++ ) {
                var object = use_accountList[i];
                addOption(parentSelect1, object.code(), object.name());
            }
        }
    }        
</script>
