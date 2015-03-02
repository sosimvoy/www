<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR010811.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-07-16
* ���α׷�����   : ȸ��� ���� �����ڵ�
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �����ڵ� (�ñ����� ����)
    List<CommonEntity> semokList  = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
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
        this.semokCode;   //�����ڵ�
        this.semokName;   //ȸ���
        this.text = getTextValue; //text �������
        this.value = getValue; // ȸ���ڵ�
    }
    
    //text value �������(������-������)
    function getTextValue() {
        return this.semokName;
    }

    function getValue() {
        return this.semokCode.toString();
    }
    
    //������������ ���� Object �� ���� �迭 ����
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity semokInfo = (CommonEntity)semokList.get(i);
    %>
    var newObject = new LawHangInfo();
    newObject.semokCode = "<%=semokInfo.getString("M370_SEMOKCODE")%>";
    newObject.semokName = "<%=semokInfo.getString("M370_SEMOKNAME")%>";
    
		//�迭�� ���� Object ����
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ������������ ���� ���� */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.semok_code;
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            addOption(parentSelect, "", "��ü");
            for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
            }
        }
    }        
</script>
