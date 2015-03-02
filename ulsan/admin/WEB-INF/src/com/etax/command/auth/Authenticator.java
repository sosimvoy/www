/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : Authenticator.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-08-04
* ���α׷�����	  : å���� ����, �α���, ��������
*****************************************************************/
package com.etax.command.auth;

import java.net.*;
import java.io.*;
import java.util.*;
import java.security.cert.*;
import java.security.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

import com.initech.iniplugin.*;
import com.initech.iniplugin.vid.*;
//import com.initech.iniplugin.crl.exception.*;
//import com.initech.iniplugin.crl.CheckCRL;

import com.etax.framework.exception.AuthException;
import com.etax.entity.CommonEntity;

 
public class Authenticator {

	private static Logger logger = Logger.getLogger(Authenticator.class);	// log4j ����

  public Authenticator() {

	}
  private String message = null;   //�޽���
  private String serial = null;    //������ ������ȣ
  private String subjectDN = null; //�߱޴�� -- 1�� ������ ���ŵǾ ������ ����
  private String user_id = null;
	private String user_pw = null;

  public void certify(HttpServletRequest request, HttpServletResponse response) {
    
		IniPlugin m_IP = new IniPlugin(request,response,"/home1/tmax/initech/iniplugin/properties/IniPlugin.properties");  //�ֹι�ȣ������Ƽ
    String crlConfig = "/home1/tmax/initech/iniplugin/properties/CRL.properties";  //crl ����Ʈ
    String INIdata = request.getParameter("INIpluginData");
		 if (INIdata == null) {  
      logger.info(" INIdata is Null " );
	  } else {
	    try {
        m_IP.init();
	    } catch(Exception e) {
        logger.info("�ʱ�ȭ ����" + e.getMessage() );
      }
    }
   
    //CheckCRL ccrl = null;         //crl üũ
	  boolean returnFlag = false;   //üũ ���
    X509Certificate cert = null;  
	  IDVerifier idv = null;

    //������ �ʿ��� ������ ���� üũ
		user_id = m_IP.getParameter("userid");        //���̵� ����
		user_pw = m_IP.getParameter("userpw");        //��й�ȣ ����
    cert = m_IP.getClientCertificate(); 
    serial = cert.getSerialNumber().toString(10);  //�ø���
    subjectDN = cert.getSubjectDN().toString();    //�߱޴��
		try {
			idv = new IDVerifier();
     // ccrl = new CheckCRL();
		//	ccrl.init(crlConfig);		
		//	returnFlag = ccrl.isValid(cert); //��ȿ�� ���������� �˻�.
      
      if ( returnFlag ) {
        logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL ���� : ��ȿ�� �������Դϴ�.     <<<<<<<<<<<<<<<<<");
		    logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL ��� =[" + returnFlag + "]      <<<<<<<<<<<<<<<<<");
		    message = "CHECKOK";
      } else {
        logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL ���� : ��ȿ���� ���� �������Դϴ�.   <<<<<<<<<<<<");
	      logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL ���=[" + returnFlag + "]            <<<<<<<<<<<<");
        message = "CHECKFAIL";
	    }
		} catch(Exception e) {
      logger.info("�α��� ���Դϴ�.");
		}
  }
  
  /* �޽��� */
  public String getMessage() {
    return this.message;
  }

	/* �����ID */
  public String getUserId() {
    return this.user_id;
  }

	/* �����PW */
  public String getUserPw() {
    return this.user_pw;
  }

  /* �ø��� */
  public String getSerial() {
    return this.serial;
  }

  /* �߱޴�� */
  public String getSubjectDN() {
    return this.subjectDN;
  }
}