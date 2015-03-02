<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070151.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-17
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입금일계표 > PDF 파일 저장
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

//String fileName = "세입금일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".pdf";
String fileName = "세입금일계표.pdf";
String gubun = "";
String semok_name = "";
String acc_year = request.getParameter("acc_year");
String acc_date = request.getParameter("acc_date");

//Document document = new Document();
Document document = new Document(PageSize.A4.rotate()); // A4 사이즈 설정

try {

	String filePath = request.getRealPath("/");
    // PDF 파일 생성경로 설정
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath+"upload/report/" + fileName));
    // PDF 파일 생성경로 설정
//    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home1/tmax_t/jeus6/webhome/app_home/admin/upload/report/" + fileName));

    // 한글깨짐방지
    BaseFont objBaseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
    Font objFont = new Font(objBaseFont, 6);
    
    document.open();
   //writer.addJavaScript("this.print(false);", false);    // auto print 
    
    /* TABLE START */
    PdfPTable t = new PdfPTable(7);
    t.setTotalWidth(216f);
    
    // 컬럼 사이즈
    float[] widths = { 1f, 2f, 1.5f , 1.5f, 1.5f , 1.5f, 1.5f };    
    t.setWidths(widths);
    
    //PdfPCell c1 = new PdfPCell(); 
    PdfPCell c1;

    // Title
    c1 = new PdfPCell(new Paragraph("울산광역시세 세입금 일계표", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBorderWidth(0);
	c1.setColspan(7);
    c1.setFixedHeight(10); // 셀 높이
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("("+acc_date+")", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBorderWidth(0);
	c1.setColspan(7);
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("회계년도:"+acc_year+"년도", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_LEFT);
    c1.setBorderWidth(0);
	c1.setColspan(3);
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("경남은행 울산광역시금고", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
    c1.setBorderWidth(0);
	c1.setColspan(4);
    t.addCell(c1);

    // Header 
    c1 = new PdfPCell(new Paragraph("구분", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);

    c1 = new PdfPCell(new Paragraph("세목명", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("금일수입", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("환부", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("환부누계", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("정정", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  

    c1 = new PdfPCell(new Paragraph("금일누계", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  
    t.setHeaderRows(4); // 타이틀 고정
    
    // Detail 
   for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(rowData.getString("GUBUN").equals("")){
            gubun = "합 계"; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

        if(rowData.getString("M370_SEMOKNAME").equals("")){
            semok_name = "소 계"; 
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME"); 
        }
         if(rowData.getString("GUBUN").equals("")){
            gubun = "합 계"; 
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