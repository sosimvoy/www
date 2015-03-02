<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.etax.util.FileDownloader" %>
<%
  if( request.getParameter("fileName") == null || "".equals(request.getParameter("fileName")) ) {
    return;
  }

  FileDownloader downloader = new FileDownloader();
  downloader.setFileName(request.getParameter("fileName"));
  downloader.setDir(request.getParameter("dir"));
  downloader.download(response);
%>