<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR071411.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 세입세출일계정정 히든프레임
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    //수납기관
    List<CommonEntity> revCdList = (List<CommonEntity>)request.getAttribute("page.mn07.revCdList");

    // 부서코드
    List partCdList = (List)request.getAttribute("page.mn07.partCdList");

    // 회계코드
    List accCdList = (List)request.getAttribute("page.mn07.accCdList");

    // 세목코드
    List semokCdList = (List)request.getAttribute("page.mn07.semokCdList");

    String gubun = request.getParameter("gubun");
    String tax_type = request.getParameter("tax_type");
    if (tax_type == null) {
      tax_type = "";
    }

    String acc_type = request.getParameter("acc_type");
    if (acc_type == null) {
      acc_type = "";
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  <title>울산광역시 세입 및 자금배정관리시스템</title>
  <link href="../css/class.css" rel="stylesheet" type="text/css" />
  <script language="JavaScript" src="../js/utility.js"></script>
  <script>
    
    var gubun = "<%=gubun%>";
    var tax_type = "<%=tax_type%>";
    var acc_type = "<%=acc_type%>";
    
    //수납기관 정보 Object 정의
    function RevInfo() {
      this.revCode;   //회계코드
      this.revName;    //회계이름
      this.name = getName; //회계명
      this.code = getCode; //회계코드
    }
    
    //수납기관명
    function getName() {
      return this.revName;
    }
    //수납기관코드
    function getCode() {
      return this.revCode.toString();
    }
    
    //수납기관정보 Object 를 담을 배열 정의
    var revList = new Array();
    <%
    for( int i=0 ; revCdList != null && i < revCdList.size() ; i++ ) {
      CommonEntity revInfo = (CommonEntity)revCdList.get(i);
    %>
    var newObject = new RevInfo();
    newObject.revCode = "<%=revInfo.getString("REV_CD")%>";
    newObject.revName = "<%=revInfo.getString("REV_NM")%>";
    //배열에 정보 Object 저장
    revList[<%=i%>] = newObject;
    <%
    }
    %>


    //부서 정보 Object 정의
    function PartInfo() {
      this.partCode;   //부서코드
      this.partName;    //부서명
      this.pname = getPartName; //부서명
      this.pcode = getPartCode; //부서코드
    }
    
    //부서명
    function getPartName() {
      return this.partName;
    }
    //부서코드
    function getPartCode() {
      return this.partCode.toString();
    }
    
    //부서정보 Object 를 담을 배열 정의
    var partList = new Array();
    <%
    for( int i=0 ; partCdList != null && i < partCdList.size() ; i++ ) {
      CommonEntity partInfo = (CommonEntity)partCdList.get(i);
    %>
    var newObject = new PartInfo();
    newObject.partCode = "<%=partInfo.getString("PART_CD")%>";
    newObject.partName = "<%=partInfo.getString("PART_NM")%>";
    //배열에 정보 Object 저장
    partList[<%=i%>] = newObject;
    <%
    }
    %>


    //회계코드 정보 Object 정의
    function AccInfo() {
      this.accCode;   //회계코드
      this.accName;    //회계명
      this.aname = getAccName; //회계명
      this.acode = getAccCode; //회계코드
    }
    
    //회계명
    function getAccName() {
      return this.accName;
    }
    //회계코드
    function getAccCode() {
      return this.accCode.toString();
    }
    
    //회계코드 Object 를 담을 배열 정의
    var accList = new Array();
    <%
    for( int i=0 ; accCdList != null && i < accCdList.size() ; i++ ) {
      CommonEntity accInfo = (CommonEntity)accCdList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.accCode = "<%=accInfo.getString("ACC_CD")%>";
    newObject.accName = "<%=accInfo.getString("ACC_NM")%>";
    //배열에 정보 Object 저장
    accList[<%=i%>] = newObject;
    <%
    }
    %>



    //세목코드 정보 Object 정의
    function SemokInfo() {
      this.semokCode;   //세목코드
      this.semokName;    //세목명
      this.sname = getSemokName; //세목명
      this.scode = getSemokCode; //세목코드
    }
    
    //세목명
    function getSemokName() {
      return this.semokName;
    }
    //세목코드
    function getSemokCode() {
      return this.semokCode.toString();
    }
    
    //세목코드 Object 를 담을 배열 정의
    var semokList = new Array();
    <%
    for( int i=0 ; semokCdList != null && i < semokCdList.size() ; i++ ) {
      CommonEntity semokInfo = (CommonEntity)semokCdList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.semokCode = "<%=semokInfo.getString("SEMOK_CD")%>";
    newObject.semokName = "<%=semokInfo.getString("SEMOK_NM")%>";
    //배열에 정보 Object 저장
    semokList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
      /* 부모 select 객체에 회계 정보 생성 */
      if (gubun == "A") {
        var parentSelect = null;
        try {
          parentSelect = parent.document.sForm.rev_cd;
        } catch(e) {
          parentSelect = parent.document.uForm.rev_cd;
        }

        if( parentSelect != null ) {
          deleteOptions(parentSelect);
          for( var i=0 ; i < revList.length ; i++ ) {
            var object = revList[i];
            addOption(parentSelect, object.code(), object.name());
          }
        }

        if (tax_type == "T1" && acc_type == "B") {
          // 년도구분
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "현년도");
            addOption(parentSelect4, "Y2", "과년도");
          }
        } else {
          // 년도구분
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "현년도");
          }
        }        
      } else if (gubun == "B") {
        //부서코드
        var parentSelect = null;
        try {
          parentSelect = parent.document.sForm.part_code;
        } catch(e) {
          parentSelect = parent.document.uForm.part_code;
        }

        if( parentSelect != null ) {
          deleteOptions(parentSelect);
          for( var i=0 ; i < partList.length ; i++ ) {
            var object = partList[i];
            addOption(parentSelect, object.pcode(), object.pname());
          }
        }

        // 회계코드
        var parentSelect1 = null;
        try {
          parentSelect1 = parent.document.sForm.acc_code;
        } catch(e) {
          parentSelect1 = parent.document.uForm.acc_code;
        }

        if( parentSelect1 != null ) {
          deleteOptions(parentSelect1);
          for( var i=0 ; i < accList.length ; i++ ) {
            var object = accList[i];
            addOption(parentSelect1, object.acode(), object.aname());
          }
        }


        // 세목코드
        var parentSelect2 = null;
        try {
          parentSelect2 = parent.document.sForm.semok_code;
        } catch(e) {
          parentSelect2 = parent.document.uForm.semok_code;
        }

        if( parentSelect2 != null ) {
          deleteOptions(parentSelect2);
          for( var i=0 ; i < semokList.length ; i++ ) {
            var object = semokList[i];
            addOption(parentSelect2, object.scode(), object.sname());
          }
        }

        if (tax_type == "T1" && acc_type == "B") {
          // 년도구분
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "현년도");
            addOption(parentSelect4, "Y2", "과년도");
          }
        } else {
          // 년도구분
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "현년도");
          }
        }        

      } else if (gubun == "C") {
        // 회계코드
        var parentSelect1 = null;
        try {
          parentSelect1 = parent.document.sForm.acc_code;
        } catch(e) {
          parentSelect1 = parent.document.uForm.acc_code;
        }

        if( parentSelect1 != null ) {
          deleteOptions(parentSelect1);
          for( var i=0 ; i < accList.length ; i++ ) {
            var object = accList[i];
            addOption(parentSelect1, object.acode(), object.aname());
          }
        }


        // 세목코드
        var parentSelect2 = null;
        try {
          parentSelect2 = parent.document.sForm.semok_code;
        } catch(e) {
          parentSelect2 = parent.document.uForm.semok_code;
        }

        if( parentSelect2 != null ) {
          deleteOptions(parentSelect2);
          for( var i=0 ; i < semokList.length ; i++ ) {
            var object = semokList[i];
            addOption(parentSelect2, object.scode(), object.sname());
          }
        }
      } else if (gubun == "D") {

        // 세목코드
        var parentSelect2 = null;
        try {
          parentSelect2 = parent.document.sForm.semok_code;
        } catch(e) {
          parentSelect2 = parent.document.uForm.semok_code;
        }

        if( parentSelect2 != null ) {
          deleteOptions(parentSelect2);
          for( var i=0 ; i < semokList.length ; i++ ) {
            var object = semokList[i];
            addOption(parentSelect2, object.scode(), object.sname());
          }
        }
      } else if (gubun == "E") {
        if (tax_type == "T1" && acc_type == "B") {
          // 년도구분
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "현년도");
            addOption(parentSelect4, "Y2", "과년도");
          }
        } else {
          // 년도구분
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "현년도");
          }
        }        
      }      
    }        
  </script>
