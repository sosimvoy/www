<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR071411.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ���Լ����ϰ����� ����������
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    //�������
    List<CommonEntity> revCdList = (List<CommonEntity>)request.getAttribute("page.mn07.revCdList");

    // �μ��ڵ�
    List partCdList = (List)request.getAttribute("page.mn07.partCdList");

    // ȸ���ڵ�
    List accCdList = (List)request.getAttribute("page.mn07.accCdList");

    // �����ڵ�
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
  <title>��걤���� ���� �� �ڱݹ��������ý���</title>
  <link href="../css/class.css" rel="stylesheet" type="text/css" />
  <script language="JavaScript" src="../js/utility.js"></script>
  <script>
    
    var gubun = "<%=gubun%>";
    var tax_type = "<%=tax_type%>";
    var acc_type = "<%=acc_type%>";
    
    //������� ���� Object ����
    function RevInfo() {
      this.revCode;   //ȸ���ڵ�
      this.revName;    //ȸ���̸�
      this.name = getName; //ȸ���
      this.code = getCode; //ȸ���ڵ�
    }
    
    //���������
    function getName() {
      return this.revName;
    }
    //��������ڵ�
    function getCode() {
      return this.revCode.toString();
    }
    
    //����������� Object �� ���� �迭 ����
    var revList = new Array();
    <%
    for( int i=0 ; revCdList != null && i < revCdList.size() ; i++ ) {
      CommonEntity revInfo = (CommonEntity)revCdList.get(i);
    %>
    var newObject = new RevInfo();
    newObject.revCode = "<%=revInfo.getString("REV_CD")%>";
    newObject.revName = "<%=revInfo.getString("REV_NM")%>";
    //�迭�� ���� Object ����
    revList[<%=i%>] = newObject;
    <%
    }
    %>


    //�μ� ���� Object ����
    function PartInfo() {
      this.partCode;   //�μ��ڵ�
      this.partName;    //�μ���
      this.pname = getPartName; //�μ���
      this.pcode = getPartCode; //�μ��ڵ�
    }
    
    //�μ���
    function getPartName() {
      return this.partName;
    }
    //�μ��ڵ�
    function getPartCode() {
      return this.partCode.toString();
    }
    
    //�μ����� Object �� ���� �迭 ����
    var partList = new Array();
    <%
    for( int i=0 ; partCdList != null && i < partCdList.size() ; i++ ) {
      CommonEntity partInfo = (CommonEntity)partCdList.get(i);
    %>
    var newObject = new PartInfo();
    newObject.partCode = "<%=partInfo.getString("PART_CD")%>";
    newObject.partName = "<%=partInfo.getString("PART_NM")%>";
    //�迭�� ���� Object ����
    partList[<%=i%>] = newObject;
    <%
    }
    %>


    //ȸ���ڵ� ���� Object ����
    function AccInfo() {
      this.accCode;   //ȸ���ڵ�
      this.accName;    //ȸ���
      this.aname = getAccName; //ȸ���
      this.acode = getAccCode; //ȸ���ڵ�
    }
    
    //ȸ���
    function getAccName() {
      return this.accName;
    }
    //ȸ���ڵ�
    function getAccCode() {
      return this.accCode.toString();
    }
    
    //ȸ���ڵ� Object �� ���� �迭 ����
    var accList = new Array();
    <%
    for( int i=0 ; accCdList != null && i < accCdList.size() ; i++ ) {
      CommonEntity accInfo = (CommonEntity)accCdList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.accCode = "<%=accInfo.getString("ACC_CD")%>";
    newObject.accName = "<%=accInfo.getString("ACC_NM")%>";
    //�迭�� ���� Object ����
    accList[<%=i%>] = newObject;
    <%
    }
    %>



    //�����ڵ� ���� Object ����
    function SemokInfo() {
      this.semokCode;   //�����ڵ�
      this.semokName;    //�����
      this.sname = getSemokName; //�����
      this.scode = getSemokCode; //�����ڵ�
    }
    
    //�����
    function getSemokName() {
      return this.semokName;
    }
    //�����ڵ�
    function getSemokCode() {
      return this.semokCode.toString();
    }
    
    //�����ڵ� Object �� ���� �迭 ����
    var semokList = new Array();
    <%
    for( int i=0 ; semokCdList != null && i < semokCdList.size() ; i++ ) {
      CommonEntity semokInfo = (CommonEntity)semokCdList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.semokCode = "<%=semokInfo.getString("SEMOK_CD")%>";
    newObject.semokName = "<%=semokInfo.getString("SEMOK_NM")%>";
    //�迭�� ���� Object ����
    semokList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
      /* �θ� select ��ü�� ȸ�� ���� ���� */
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
          // �⵵����
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "���⵵");
            addOption(parentSelect4, "Y2", "���⵵");
          }
        } else {
          // �⵵����
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "���⵵");
          }
        }        
      } else if (gubun == "B") {
        //�μ��ڵ�
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

        // ȸ���ڵ�
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


        // �����ڵ�
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
          // �⵵����
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "���⵵");
            addOption(parentSelect4, "Y2", "���⵵");
          }
        } else {
          // �⵵����
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "���⵵");
          }
        }        

      } else if (gubun == "C") {
        // ȸ���ڵ�
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


        // �����ڵ�
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

        // �����ڵ�
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
          // �⵵����
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "���⵵");
            addOption(parentSelect4, "Y2", "���⵵");
          }
        } else {
          // �⵵����
          var parentSelect4 = null;
          try {
            parentSelect4 = parent.document.sForm.year_type;
          } catch(e) {
            parentSelect4 = parent.document.uForm.year_type;
          }

          if( parentSelect4 != null ) {
            deleteOptions(parentSelect4);
            addOption(parentSelect4, "Y1", "���⵵");
          }
        }        
      }      
    }        
  </script>
