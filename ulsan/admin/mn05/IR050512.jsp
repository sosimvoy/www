<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050512.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : �ڱݹ��� > ���µ�� > �μ�, ȸ�踮��Ʈ
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn05.accList");
		List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");
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
    
    //text value �������(ȸ���ڵ�-ȸ���)
    function getAccTextValue() {
        return this.accNm;
    }

		//text value �������(�μ��ڵ�-�μ���)
    function getDeptTextValue() {
        return this.deptNm;
    }

    function getAccValue() {
        return this.accCd.toString();
    }

		function getDeptValue() {
        return this.deptCd.toString();
    }
    
    //ȸ�� ���� Object �� ���� �迭 ����
    var accInfoList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accList.get(i);
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

    function window.onload() {
      /* �θ� select ��ü�� ���� ���� ���� */
      var parentSelect1 = null;
			var parentSelect2 = null;
			var flag = "<%=request.getParameter("flag")%>";
      try {
				parentSelect1 = parent.document.sForm.acc_cd;
				
				if( parentSelect1 != null ) {
          deleteOptions(parentSelect1);
          for( var i=0 ; i < accInfoList.length ; i++ ) {
            var object = accInfoList[i];
            addOption(parentSelect1, object.value(), object.text());
          }
        }
				if (flag == "1") {
					parentSelect2 = parent.document.sForm.dept_cd;

				  if( parentSelect2 != null ) {
            deleteOptions(parentSelect2);
            for( var i=0 ; i < deptInfoList.length ; i++ ) {
              var object = deptInfoList[i];
              addOption(parentSelect2, object.value(), object.text());
            }
          }
				}						
      } catch(e) {
				//
      }
    }        
</script>