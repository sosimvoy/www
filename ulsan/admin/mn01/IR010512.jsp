<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR010512.jsp
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ȸ�豸�п� ���� �μ��ڵ�� ȸ��� 
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)

  List<CommonEntity> deptList  =  (List<CommonEntity>)request.getAttribute("page.mn01.deptList");	 
  List<CommonEntity> accCdList  = (List<CommonEntity>)request.getAttribute("page.mn01.accCdList");
  List<CommonEntity> semokList  = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    // Object ����
    function AccInfo() {
        this.accCd;   //ȸ���ڵ�
        this.accNm;   //ȸ���
        this.text = getAccTextValue; //text �������
        this.value = getAccValue; //ȸ���
    }

		// Object ����
    function DeptInfo() {
        this.deptCd;   //�μ��ڵ�
        this.deptNm;   //�μ���
        this.text = getDeptTextValue; //text �������
        this.value = getDeptValue; //�μ���
    }

		// Object ����
		function SemokInfo() {
        this.semokCd;   //�����ڵ�
        this.semokNm;   //�����
        this.text = getSemokTextValue; //text �������
        this.value = getSemokValue; //�μ���
    }
    
    //text value �������(ȸ���ڵ�-ȸ���)
    function getAccTextValue() {
        return this.accNm;
    }

		//text value �������(�μ��ڵ�-�μ���)
    function getDeptTextValue() {
        return this.deptNm;
    }

		//text value �������(�����ڵ�-�����)
    function getSemokTextValue() {
        return this.semokNm;
    }

    function getAccValue() {
        return this.accCd.toString();
    }

		function getDeptValue() {
        return this.deptCd.toString();
    }

		function getSemokValue() {
        return this.semokCd.toString();
    }
    
    //ȸ�� ���� Object �� ���� �迭 ����
    var accInfoList = new Array();

    <%
    for( int i=0 ; accCdList != null && i < accCdList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accCdList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.accCd = "<%=rowData.getString("M360_ACCCODE")%>";
    newObject.accNm = "<%=rowData.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    accInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//�μ� ���� Object �� ���� �迭 ����
    var deptInfoList = new Array();

    <%
    for( int i=0 ; deptList != null && i < deptList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)deptList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.deptCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //�迭�� ���� Object ����
    deptInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//���� ���� Object �� ���� �迭 ����
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.deptCd = "<%=rowData.getString("M370_SEMOKCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M370_SEMOKNAME")%>";
    //�迭�� ���� Object ����
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>


    function window.onload() {
      /* �θ� select ��ü�� ���� ���� ���� */
      var parentSelect1 = null;
			var parentSelect2 = null;
			var parentSelect3 = null;
			var flag = "<%=request.getParameter("flag")%>";
      try {

				if (flag == "0") {

					parentSelect2 = parent.document.sForm.part_code;

				  if( parentSelect2 != null ) {
            deleteOptions(parentSelect2);
            for( var i=0 ; i < deptInfoList.length ; i++ ) {
              var object = deptInfoList[i];
              addOption(parentSelect2, object.value(), object.text());
            }
          }

					parentSelect1 = parent.document.sForm.acc_code;
				
				  if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            addOption(parentSelect1, "", "��ü");
            for( var i=0 ; i < accInfoList.length ; i++ ) {
              var object = accInfoList[i];
              addOption(parentSelect1, object.value(), object.text());
            }
          }

          parentSelect3 = parent.document.sForm.semok_code;
				
				  if( parentSelect1 != null ) {
            deleteOptions(parentSelect3);
            addOption(parentSelect3, "", "��ü");
            for( var i=0 ; i < semokInfoList.length ; i++ ) {
              var object = semokInfoList[i];
              addOption(parentSelect3, object.value(), object.text());
            }
          }
				}		
				
				if (flag == "1") {

					parentSelect1 = parent.document.sForm.acc_code;
				
				  if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            addOption(parentSelect1, "", "��ü");
            for( var i=0 ; i < accInfoList.length ; i++ ) {
              var object = accInfoList[i];
              addOption(parentSelect1, object.value(), object.text());
            }
          }
				}

				parentSelect3 = parent.document.sForm.semok_code;

				if( parentSelect3 != null ) {
          deleteOptions(parentSelect3);
          addOption(parentSelect3, "", "��ü");
          for( var i=0 ; i < semokInfoList.length ; i++ ) {
            var object = semokInfoList[i];
            addOption(parentSelect3, object.value(), object.text());
          }
        }
						
      } catch(e) {
				//
      }
    }        
</script>