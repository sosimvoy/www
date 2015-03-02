<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR091911.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2012-02-10
* 프로그램내용   : 회계구분에 따른 회계 종류
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
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="../js/utility.js"></script>
    <script language="JavaScript">
    
    //코드 정보 Object 정의
    function getInfo() {
        this.setCode;    //코드
        this.setName;    //코드명
        this.name = getName; //코드명
        this.code = getCode; //코드
    }

    //text value 출력형태(코드명)
    function getName() {
        return this.setName;
    }

    function getCode() {
        return this.setCode.toString();
    }


    //문서정보 Object 를 담을 배열 정의
    var doc_List = new Array();

    <%
    for( int i=0 ; docList != null && i < docList.size() ; i++ ) {
        CommonEntity doc = (CommonEntity)docList.get(i);
    %>
    var parentObject = new getInfo();
    parentObject.setCode = "<%=doc.getString("M230_ACCYN")%><%=doc.getString("M230_REPORTCODE")%>";
    parentObject.setName = "<%=doc.getString("M230_REPORTNAME")%>";
    //배열에 정보 Object 저장
    doc_List[<%=i%>] = parentObject;
    <%
    }
    %>

    //회계정보 Object 를 담을 배열 정의
    var acc_List = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity acc = (CommonEntity)accList.get(i);
    %>
    var parentObject1 = new getInfo();
    parentObject1.setCode = "<%=acc.getString("M360_ACCCODE")%>";
    parentObject1.setName = "<%=acc.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    acc_List[<%=i%>] = parentObject1;
    <%
    }
    %>
   
    function window.onload() {
        /* 부모 select 객체에 회계 정보 생성 */
        
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