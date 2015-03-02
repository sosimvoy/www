<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR091911.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2012-02-10
* ���α׷�����   : ȸ�豸�п� ���� ȸ�� ����
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
  List<CommonEntity>docList    = (List<CommonEntity>)request.getAttribute("page.mn09.docList");
  List<CommonEntity>accList    = (List<CommonEntity>)request.getAttribute("page.mn09.accList");

  String reportgubun = "";
  if (request.getParameter("reportgubun") != null) {
    reportgubun = request.getParameter("reportgubun");
  }

  String accgubun = "";
  if (request.getParameter("accgubun") != null) {
    accgubun = request.getParameter("accgubun");
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="../js/utility.js"></script>
    <script language="JavaScript">
    
    //�ڵ� ���� Object ����
    function getInfo() {
        this.setCode;    //�ڵ�
        this.setName;    //�ڵ��
        this.name = getName; //�ڵ��
        this.code = getCode; //�ڵ�
    }

    //text value �������(�ڵ��)
    function getName() {
        return this.setName;
    }

    function getCode() {
        return this.setCode.toString();
    }


    //�������� Object �� ���� �迭 ����
    var doc_List = new Array();

    <%
    for( int i=0 ; docList != null && i < docList.size() ; i++ ) {
        CommonEntity doc = (CommonEntity)docList.get(i);
    %>
    var parentObject = new getInfo();
    parentObject.setCode = "<%=doc.getString("M230_ACCYN")%><%=doc.getString("M230_REPORTCODE")%>";
    parentObject.setName = "<%=doc.getString("M230_REPORTNAME")%>";
    //�迭�� ���� Object ����
    doc_List[<%=i%>] = parentObject;
    <%
    }
    %>

    //ȸ������ Object �� ���� �迭 ����
    var acc_List = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity acc = (CommonEntity)accList.get(i);
    %>
    var parentObject1 = new getInfo();
    parentObject1.setCode = "<%=acc.getString("M360_ACCCODE")%>";
    parentObject1.setName = "<%=acc.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    acc_List[<%=i%>] = parentObject1;
    <%
    }
    %>
   
    function window.onload() {
        /* �θ� select ��ü�� ȸ�� ���� ���� */
        
        var parentSelect = null;
        var parentSelect1 = null;
        <% if (!"".equals(reportgubun)) { %>
        try {
            parentSelect = parent.document.sForm.reportcode;
        } catch(e) {
            parentSelect = parent.document.uForm.reportcode;
        }
        
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            for( var i=0 ; i < doc_List.length ; i++ ) {
                var object = doc_List[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }

        <% } %>

        <% if (!"".equals(accgubun)) { %>
        try {
            parentSelect1 = parent.document.sForm.acccode;
        } catch(e) {
            parentSelect1 = parent.document.uForm.acccode;
        }
        
        if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            for( var i=0 ; i < acc_List.length ; i++ ) {
                var object = acc_List[i];
                addOption(parentSelect1, object.code(), object.name());
            }
        }
        <% } %>
        
    }        
</script>
</head>
<body>
</body>
</html>