<%
/***************************************************************
* 프로젝트명     : 대전광역시 가상계좌번호 납부관리자 시스템
* 프로그램명     : dept.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2009-12-16
* 프로그램내용   : 시군구에 따른 부서코드
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List deptCdList = (List)request.getAttribute("page.common.deptCdList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //법정동행정동 정보 Object 정의
    function LawHangInfo() {
        this.depCode;   //법정동코드
        this.depName;   //법정동이름
        this.text = getTextValue; //text 출력형태
        this.value = getValue; //행정동 법정동코드
    }
    
    //text value 출력형태(행정동-법정동)
    function getTextValue() {
        return this.depName;
    }

    function getValue() {
        return this.depCode.toString();
    }
    
    //법정동행정동 정보 Object 를 담을 배열 정의
    var DeptCdInfoList = new Array();

    <%
    for( int i=0 ; deptCdList != null && i < deptCdList.size() ; i++ ) {
        CommonEntity deptCdInfo = (CommonEntity)deptCdList.get(i);
    %>
    var newObject = new LawHangInfo();
    newObject.depCode = "<%=deptCdInfo.getString("M270_CODETYPE")%>";
    newObject.depName = "["+"<%=deptCdInfo.getString("M270_CODETYPE")%>"+"]"+"<%=deptCdInfo.getString("M270_CODENAME")%>";
    //배열에 정보 Object 저장
    DeptCdInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 법정동행정동 정보 생성 */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.dep_code;
        } catch(e) {
            parentSelect = parent.document.uForm.dep_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            addOption(parentSelect, "", "선택하세요");
            for( var i=0 ; i < DeptCdInfoList.length ; i++ ) {
                var object = DeptCdInfoList[i];
                addOption(parentSelect, object.value(), object.text());
            }
        }
    }        
</script>
