<%
/***************************************************************
* ������Ʈ��     : ���������� ������¹�ȣ ���ΰ����� �ý���
* ���α׷���     : dept.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2009-12-16
* ���α׷�����   : �ñ����� ���� �μ��ڵ�
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List deptCdList = (List)request.getAttribute("page.common.deptCdList");
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
        this.depCode;   //�������ڵ�
        this.depName;   //�������̸�
        this.text = getTextValue; //text �������
        this.value = getValue; //������ �������ڵ�
    }
    
    //text value �������(������-������)
    function getTextValue() {
        return this.depName;
    }

    function getValue() {
        return this.depCode.toString();
    }
    
    //������������ ���� Object �� ���� �迭 ����
    var DeptCdInfoList = new Array();

    <%
    for( int i=0 ; deptCdList != null && i < deptCdList.size() ; i++ ) {
        CommonEntity deptCdInfo = (CommonEntity)deptCdList.get(i);
    %>
    var newObject = new LawHangInfo();
    newObject.depCode = "<%=deptCdInfo.getString("M270_CODETYPE")%>";
    newObject.depName = "["+"<%=deptCdInfo.getString("M270_CODETYPE")%>"+"]"+"<%=deptCdInfo.getString("M270_CODENAME")%>";
    //�迭�� ���� Object ����
    DeptCdInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ������������ ���� ���� */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.dep_code;
        } catch(e) {
            parentSelect = parent.document.uForm.dep_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            addOption(parentSelect, "", "�����ϼ���");
            for( var i=0 ; i < DeptCdInfoList.length ; i++ ) {
                var object = DeptCdInfoList[i];
                addOption(parentSelect, object.value(), object.text());
            }
        }
    }        
</script>
