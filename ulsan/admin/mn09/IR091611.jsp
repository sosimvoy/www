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

  List<CommonEntity>useSemokList =    (List<CommonEntity>)request.getAttribute("page.mn09.useSemokCdList");
	List<CommonEntity>nowIncomeList =    (List<CommonEntity>)request.getAttribute("page.mn09.nowincomeSemokCdList");
	List<CommonEntity>oldIncomeList =    (List<CommonEntity>)request.getAttribute("page.mn09.oldincomeSemokCdList");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/utility.js"></script>
<script language="JavaScript">
    
    //ȸ���ڵ� ���� Object ����
    function SemokInfo() {
        this.SemokCode;   //ȸ���ڵ�
        this.SemokName;    //ȸ���̸�
        this.name = getName; //ȸ���
        this.code = getCode; //ȸ���ڵ�
    }

    //text value �������(ȸ���)
    function getName() {
        return this.SemokName;
    }

    function getCode() {
        return this.SemokCode.toString();
    }


    //��뼼�� Object �� ���� �迭 ����
    var use_sesemokList = new Array();

    <%
    for( int i=0 ; useSemokList != null && i < useSemokList.size() ; i++ ) {
        CommonEntity sesemokInfo = (CommonEntity)useSemokList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.SemokCode = "<%=sesemokInfo.getString("M370_SEMOKCODE")%>";
    newObject.SemokName = "<%=sesemokInfo.getString("M370_SEMOKNAME")%>";
    //�迭�� ���� Object ����
    use_sesemokList[<%=i%>] = newObject;
    <%
    }
    %>
   
    //���⵵���꼼�� Object �� ���� �迭 ����
    var now_sesemokList = new Array();

    <%
    for( int i=0 ; nowIncomeList != null && i < nowIncomeList.size() ; i++ ) {
        CommonEntity sesemokInfo = (CommonEntity)nowIncomeList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.SemokCode = "<%=sesemokInfo.getString("M550_SEMOKCODE")%>";
    newObject.SemokName = "<%=sesemokInfo.getString("M550_SEMOKNAME")%>";
    //�迭�� ���� Object ����
    now_sesemokList[<%=i%>] = newObject;
    <%
    }
    %>

    //��뼼�� Object �� ���� �迭 ����
    var old_sesemokList = new Array();

    <%
    for( int i=0 ; oldIncomeList != null && i < oldIncomeList.size() ; i++ ) {
        CommonEntity sesemokInfo = (CommonEntity)oldIncomeList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.SemokCode = "<%=sesemokInfo.getString("M550_SEMOKCODE")%>";
    newObject.SemokName = "<%=sesemokInfo.getString("M550_SEMOKNAME")%>";
    //�迭�� ���� Object ����
    old_sesemokList[<%=i%>] = newObject;
    <%
    }
    %>
    function window.onload() {
        /* �θ� select ��ü�� ȸ�� ���� ���� */
        
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.sysSemokcode;
        } catch(e) {
            parentSelect = parent.document.uForm.sysSemokcode;
        }
        
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            for( var i=0 ; i < use_sesemokList.length ; i++ ) {
                var object = use_sesemokList[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }
        var parentSelect1 = null;
        try {
            parentSelect1 = parent.document.sForm.nowincomeSemok;
        } catch(e) {
            parentSelect1 = parent.document.uForm.nowincomeSemok;
        }
        
        if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            for( var i=0 ; i < now_sesemokList.length ; i++ ) {
                var object = now_sesemokList[i];
                addOption(parentSelect1, object.code(), object.name());
            }
        }
        var parentSelect2 = null;
        try {
            parentSelect2 = parent.document.sForm.oldincomeSemok;
        } catch(e) {
            parentSelect2 = parent.document.uForm.oldincomeSemok;
        }
        
        if( parentSelect2 != null ) {
            deleteOptions(parentSelect2);
            for( var i=0 ; i < old_sesemokList.length ; i++ ) {
                var object = old_sesemokList[i];
                addOption(parentSelect2, object.code(), object.name());
            }
        }
    }        
</script>
