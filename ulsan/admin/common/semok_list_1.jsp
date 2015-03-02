<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR020111.jsp
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

	 List<CommonEntity> accSemokList  = (List<CommonEntity>)request.getAttribute("page.mn01.accSemokList");
	 List<CommonEntity> accNmList  = (List<CommonEntity>)request.getAttribute("page.mn01.accNmList");
	 List<CommonEntity> accDeptList  = (List<CommonEntity>)request.getAttribute("page.mn01.accDeptList");	
	 
	 String array_row = (String)request.getParameter("array_row");
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
    for( int i=0 ; accNmList != null && i < accNmList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accNmList.get(i);
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
    for( int i=0 ; accDeptList != null && i < accDeptList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accDeptList.get(i);
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
    for( int i=0 ; accSemokList != null && i < accSemokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accSemokList.get(i);
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
			var flag = "<%=request.getParameter("flag")%>";
      try {

					if (flag == "0") {

					parentSelect1 = parent.document.sForm.acc_code;
				
				  if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            for( var i=0 ; i < accInfoList.length ; i++ ) {
              var object = accInfoList[i];
              addOption(parentSelect1, object.value(), object.text());
            }
          }
				}		
				

        parentSelectq = parent.document.sForm.semok_code;

					  <% if ("1".equals(array_row)) { %>
            parentSelect = parent.document.sForm.semok_code;
						if( parentSelect != null ) {
              deleteOptions(parentSelect);
              for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
              }
            }
						<% } else if (!"1".equals(array_row)) { 
							   for (int i=0; i<Integer.parseInt(array_row); i++) { 
						%>
            parentSelect = parent.document.sForm.semok_code[<%=i%>];
						if( parentSelect != null ) {
              deleteOptions(parentSelect);
              for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
              }
            }		 
						  <% } 
						   } %>
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
    }        
</script>