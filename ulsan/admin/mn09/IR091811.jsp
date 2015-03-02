<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR091611.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 회계구분에 따른 회계 종류
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
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/utility.js"></script>
<script language="JavaScript">
    
    //회계코드 정보 Object 정의
    function CodeInfo() {
        this.CodeCode;   //코드
        this.CodeName;    //코드명
        this.name = getName; //코드명
        this.code = getCode; //코드
    }

    //text value 출력형태(회계명)
    function getName() {
        return this.CodeName;
    }

    function getCode() {
        return this.CodeCode.toString();
    }


    //사용부서 Object 를 담을 배열 정의
    var use_partList = new Array();

    <%
    for( int i=0 ; usePartList != null && i < usePartList.size() ; i++ ) {
        CommonEntity partInfo = (CommonEntity)usePartList.get(i);
    %>
    var partObject = new CodeInfo();
    partObject.CodeCode = "<%=partInfo.getString("M350_PARTCODE")%>";
    partObject.CodeName = "<%=partInfo.getString("M350_PARTNAME")%>";
    //배열에 정보 Object 저장
    use_partList[<%=i%>] = partObject;
    <%
    }
    %>
   
    //현년도예산세목 Object 를 담을 배열 정의
    var use_accountList = new Array();

    <%
    for( int i=0 ; useAcccodeList != null && i < useAcccodeList.size() ; i++ ) {
        CommonEntity accountInfo = (CommonEntity)useAcccodeList.get(i);
    %>
    var accountObject = new CodeInfo();
    accountObject.CodeCode = "<%=accountInfo.getString("M360_ACCCODE")%>";
    accountObject.CodeName = "<%=accountInfo.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    use_accountList[<%=i%>] = accountObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 회계 정보 생성 */
        
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
