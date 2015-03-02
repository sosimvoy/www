<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061311.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 회계구분에 따른 회계 종류
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 회계코드 (회계구분에 속한)
    List acctList = (List)request.getAttribute("page.mn06.acctList");

    // 부서코드 (회계구분에 속한)
    List partList = (List)request.getAttribute("page.mn06.partList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //회계코드 정보 Object 정의
    function AccInfo() {
        this.acctCode;   //회계코드
        this.acctName;    //회계이름
        this.name = getName; //회계명
        this.code = getCode; //회계코드
    }


    //부서코드 정보 Object 정의
    function partInfo() {
        this.partCode;   //부서코드
        this.partName;    //부서이름
        this.name1 = getName1; //부서명
        this.code1 = getCode1; //부서코드
    }
    
    //text value 출력형태(회계명)
    function getName() {
        return this.acctName;
    }

    function getCode() {
        return this.acctCode.toString();
    }


    //text value 출력형태(회계명)
    function getName1() {
        return this.partName;
    }

    function getCode1() {
        return this.partCode.toString();
    }
    
    //회계 정보 Object 를 담을 배열 정의
    var acctList = new Array();

    <%
    for( int i=0 ; acctList != null && i < acctList.size() ; i++ ) {
        CommonEntity acctInfo = (CommonEntity)acctList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.acctCode = "<%=acctInfo.getString("M360_ACCCODE")%>";
    newObject.acctName = "<%=acctInfo.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    acctList[<%=i%>] = newObject;
    <%
    }
    %>


    //부서 정보 Object 를 담을 배열 정의
    var partList = new Array();

    <%
    for( int i=0 ; partList != null && i < partList.size() ; i++ ) {
        CommonEntity partInfo = (CommonEntity)partList.get(i);
    %>
    var newObject = new partInfo();
    newObject.partCode = "<%=partInfo.getString("M350_PARTCODE")%>";
    newObject.partName = "<%=partInfo.getString("M350_PARTNAME")%>";
    //배열에 정보 Object 저장
    partList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 회계 정보 생성 */
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
            //addOption(parentSelect, "", "전체선택");
            for( var i=0 ; i < acctList.length ; i++ ) {
                var object = acctList[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }
    }        
</script>
