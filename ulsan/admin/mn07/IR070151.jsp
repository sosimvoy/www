<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070151.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-17
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Ա��ϰ�ǥ > PDF ���� ����
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"                         %>
<%@ page import = "com.etax.entity.*"                   %>
<%@ page import = "com.etax.util.*"                     %>

<%@ page import = "java.awt.Color"	                    %>
<%@ page import = "java.io.FileOutputStream"            %>
<%@ page import = "com.lowagie.text.Chapter"            %>
<%@ page import = "com.lowagie.text.Document"           %>
<%@ page import = "com.lowagie.text.DocumentException"	%>
<%@ page import = "com.lowagie.text.Font"	            %>
<%@ page import = "com.lowagie.text.FontFactory"	    %>
<%@ page import = "com.lowagie.text.PageSize"           %>
<%@ page import = "com.lowagie.text.Paragraph"          %>
<%@ page import = "com.lowagie.text.Section"            %>
<%@ page import = "com.lowagie.text.Element"            %>

<%@ page import = "com.lowagie.text.pdf.PdfPTable"      %>
<%@ page import = "com.lowagie.text.pdf.BaseFont"       %>
<%@ page import = "com.lowagie.text.pdf.PdfWriter"      %>
<%@ page import = "com.lowagie.text.pdf.PdfPCell"       %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax"  %>
<%

List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");

int listSize = reportList.size();
//out.println("::::iTextStart::::"+listSize);

//String fileName = "���Ա��ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".pdf";
String fileName = "���Ա��ϰ�ǥ.pdf";
String gubun = "";
String semok_name = "";
String acc_year = request.getParameter("acc_year");
String acc_date = request.getParameter("acc_date");

//Document document = new Document();
Document document = new Document(PageSize.A4.rotate()); // A4 ������ ����

try {

	String filePath = request.getRealPath("/");
    // PDF ���� ������� ����
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath+"upload/report/" + fileName));
    // PDF ���� ������� ����
//    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home1/tmax_t/jeus6/webhome/app_home/admin/upload/report/" + fileName));

    // �ѱ۱�������
    BaseFont objBaseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
    Font objFont = new Font(objBaseFont, 6);
    
    document.open();
   //writer.addJavaScript("this.print(false);", false);    // auto print 
    
    /* TABLE START */
    PdfPTable t = new PdfPTable(7);
    t.setTotalWidth(216f);
    
    // �÷� ������
    float[] widths = { 1f, 2f, 1.5f , 1.5f, 1.5f , 1.5f, 1.5f };    
    t.setWidths(widths);
    
    //PdfPCell c1 = new PdfPCell(); 
    PdfPCell c1;

    // Title
    c1 = new PdfPCell(new Paragraph("��걤���ü� ���Ա� �ϰ�ǥ", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBorderWidth(0);
	c1.setColspan(7);
    c1.setFixedHeight(10); // �� ����
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("("+acc_date+")", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBorderWidth(0);
	c1.setColspan(7);
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("ȸ��⵵:"+acc_year+"�⵵", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_LEFT);
    c1.setBorderWidth(0);
	c1.setColspan(3);
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("�泲���� ��걤���ñݰ�", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
    c1.setBorderWidth(0);
	c1.setColspan(4);
    t.addCell(c1);

    // Header 
    c1 = new PdfPCell(new Paragraph("����", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("�����", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("���ϼ���", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("ȯ��", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("ȯ�δ���", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("����", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("���ϴ���", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  
    t.setHeaderRows(4); // Ÿ��Ʋ ����
    
    // Detail 
   for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(rowData.getString("GUBUN").equals("")){
            gubun = "�� ��"; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

        if(rowData.getString("M370_SEMOKNAME").equals("")){
            semok_name = "�� ��"; 
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME"); 
        }
         if(rowData.getString("GUBUN").equals("")){
            gubun = "�� ��"; 
            semok_name = ""; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

        c1 = new PdfPCell(new Paragraph(gubun, objFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c1);

        c1 = new PdfPCell(new Paragraph(semok_name, objFont));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		t.addCell(c1);

        c1 = new PdfPCell(new Paragraph(StringUtil.formatNumber(rowData.getString("M220_AMTSEIP")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);

        c1 = new PdfPCell(new Paragraph(StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAP")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);

        c1 = new PdfPCell(new Paragraph(StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAPTOT")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);

        c1 = new PdfPCell(new Paragraph(StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONGJEONG")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);

        c1 = new PdfPCell(new Paragraph(StringUtil.formatNumber(rowData.getString("TODAY_TOT")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
    }
    /* TABLE END */
    

    document.add(t);

    //System.out.println("-----------" + document);
} catch (DocumentException de) {
    System.err.println(de.getMessage());
} catch (IOException ioe) {
    System.err.println(ioe.getMessage());
}
document.close();

%>

<html>
<head>
<script language="javascript">
function fileDown(name) {
    var form = document.downForm;
    form.fileName.value = name;
    form.submit();
}
</script>
</head>

<body onload="fileDown('<%=fileName%>')">

<form name="downForm" action="../common/file_download.etax">
      <input type="hidden" name="fileName" value="<%=fileName%>">
      <input type="hidden" name="dir" value="upload/report">
</form>

</body>
</html>