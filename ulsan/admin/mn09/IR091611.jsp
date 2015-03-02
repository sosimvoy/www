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

  List<CommonEntity>useSemokList =    (List<CommonEntity>)request.getAttribute("page.mn09.useSemokCdList");
	List<CommonEntity>nowIncomeList =    (List<CommonEntity>)request.getAttribute("page.mn09.nowincomeSemokCdList");
	List<CommonEntity>oldIncomeList =    (List<CommonEntity>)request.getAttribute("page.mn09.oldincomeSemokCdList");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/utility.js"></script>
<script language="JavaScript">
    
    //회계코드 정보 Object 정의
    function SemokInfo() {
        this.SemokCode;   //회계코드
        this.SemokName;    //회계이름
        this.name = getName; //회계명
        this.code = getCode; //회계코드
    }

    //text value 출력형태(회계명)
    function getName() {
        return this.SemokName;
    }

    function getCode() {
        return this.SemokCode.toString();
    }


    //사용세목 Object 를 담을 배열 정의
    var use_sesemokList = new Array();

    <%
    for( int i=0 ; useSemokList != null && i < useSemokList.size() ; i++ ) {
        CommonEntity sesemokInfo = (CommonEntity)useSemokList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.SemokCode = "<%=sesemokInfo.getString("M370_SEMOKCODE")%>";
    newObject.SemokName = "<%=sesemokInfo.getString("M370_SEMOKNAME")%>";
    //배열에 정보 Object 저장
    use_sesemokList[<%=i%>] = newObject;
    <%
    }
    %>
   
    //현년도예산세목 Object 를 담을 배열 정의
    var now_sesemokList = new Array();

    <%
    for( int i=0 ; nowIncomeList != null && i < nowIncomeList.size() ; i++ ) {
        CommonEntity sesemokInfo = (CommonEntity)nowIncomeList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.SemokCode = "<%=sesemokInfo.getString("M550_SEMOKCODE")%>";
    newObject.SemokName = "<%=sesemokInfo.getString("M550_SEMOKNAME")%>";
    //배열에 정보 Object 저장
    now_sesemokList[<%=i%>] = newObject;
    <%
    }
    %>

    //사용세목 Object 를 담을 배열 정의
    var old_sesemokList = new Array();

    <%
    for( int i=0 ; oldIncomeList != null && i < oldIncomeList.size() ; i++ ) {
        CommonEntity sesemokInfo = (CommonEntity)oldIncomeList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.SemokCode = "<%=sesemokInfo.getString("M550_SEMOKCODE")%>";
    newObject.SemokName = "<%=sesemokInfo.getString("M550_SEMOKNAME")%>";
    //배열에 정보 Object 저장
    old_sesemokList[<%=i%>] = newObject;
    <%
    }
    %>
    function window.onload() {
        /* 부모 select 객체에 회계 정보 생성 */
        
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
