<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR070101.jsp
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-29
* ���α׷�����   : ȸ�豸�п� ���� �μ��ڵ�� ȸ��� 
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> partList         = (List<CommonEntity>)request.getAttribute("page.mn07.partList");	 
    List<CommonEntity> accList          = (List<CommonEntity>)request.getAttribute("page.mn07.accList");
    List<CommonEntity> reportCodeList   = (List<CommonEntity>)request.getAttribute("page.mn07.reportCodeList");

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
        this.partCd;   //�μ��ڵ�
        this.partNm;   //�μ���
        this.text = getDeptTextValue; //text �������
        this.value = getDeptValue; //�μ���
    }

    // Object ����
    function ReportInfo() {
        this.reportCd;   //�����ڵ�
        this.reportNm;   //������
        this.text = getReportTextValue; //text �������
        this.value = getReportValue; //������
    }

    
    //text value �������(ȸ���ڵ�-ȸ���)
    function getAccTextValue() {
        return this.accNm;
    }

	//text value �������(�μ��ڵ�-�μ���)
    function getDeptTextValue() {
        return this.partNm;
    }

    //text value �������(�����ڵ�-������)
    function getReportTextValue() {
        return this.reportNm;
    }


    function getAccValue() {
        return this.accCd.toString();
    }

	function getDeptValue() {
        return this.partCd.toString();
    }

    function getReportValue() {
        return this.reportCd.toString();
    }
    
    //ȸ�� ���� Object �� ���� �迭 ����
    var accInfoList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accList.get(i);
    %>
        var newObject = new AccInfo();
        newObject.accCd = "<%=rowData.getString("ACCCODE")%>";
        newObject.accNm = "<%=rowData.getString("ACCNAME")%>";
        //�迭�� ���� Object ����
        accInfoList[<%=i%>] = newObject;
    <%}%> 

	//�μ� ���� Object �� ���� �迭 ����
    var partInfoList = new Array();

    <%
    for( int i=0 ; partList != null && i < partList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)partList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.partCd = "<%=rowData.getString("PARTCODE")%>";
    newObject.partNm = "<%=rowData.getString("PARTNAME")%>";
    //�迭�� ���� Object ����
    partInfoList[<%=i%>] = newObject;
    <%}%> 


    
	//���� ���� Object �� ���� �迭 ����
    var reportInfoList = new Array();

    <%
    for( int i=0 ; reportCodeList != null && i < reportCodeList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)reportCodeList.get(i);
    %>
    var newObject = new ReportInfo();
    newObject.reportCd = "<%=rowData.getString("REPORTCODE")%>"+"|"+"<%=rowData.getString("REPORTURL")%>";
    newObject.reportNm = "<%=rowData.getString("REPORTNAME")%>";
    //�迭�� ���� Object ����
    reportInfoList[<%=i%>] = newObject;
    <%}%> 

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
                    for( var i=0 ; i < partInfoList.length ; i++ ) {
                        var object = partInfoList[i];
                        addOption(parentSelect2, object.value(), object.text());
                    }
                }

				parentSelect1 = parent.document.sForm.acc_code;
				
				if( parentSelect1 != null ) {
                    deleteOptions(parentSelect1);
                    for( var i=0 ; i < accInfoList.length ; i++ ) {
                        var object = accInfoList[i];
                        addOption(parentSelect1, object.value(), object.text());
                    }
                }
			}		
				
			if (flag == "1") {

			    parentSelect1 = parent.document.sForm.acc_code;
				
				if( parentSelect1 != null ) {
                    deleteOptions(parentSelect1);
                    for( var i=0 ; i < accInfoList.length ; i++ ) {
                        var object = accInfoList[i];
                        addOption(parentSelect1, object.value(), object.text());
                    }
                }
			}
            
            // ������ ���� ��
            if (flag == "2") {

			    parentSelect3 = parent.document.sForm.report_code;
				
				if( parentSelect3 != null ) {
                    deleteOptions(parentSelect3);
                    for( var i=0 ; i < reportInfoList.length ; i++ ) {
                        var object = reportInfoList[i];
                        addOption(parentSelect3, object.value(), object.text());
                    }
                }
			}
						
        } catch(e) {
        		//
        }
    }        
</script>