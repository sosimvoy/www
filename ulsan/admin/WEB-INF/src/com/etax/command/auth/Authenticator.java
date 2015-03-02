/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : Authenticator.java
* 프로그램작성자	: 
* 프로그램작성일	: 2010-08-04
* 프로그램내용	  : 책임자 여부, 로그인, 인증세션
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

	private static Logger logger = Logger.getLogger(Authenticator.class);	// log4j 설정

  public Authenticator() {

	}
  private String message = null;   //메시지
  private String serial = null;    //인증서 고유번호
  private String subjectDN = null; //발급대상 -- 1년 만기후 갱신되어도 변하지 않음
  private String user_id = null;
	private String user_pw = null;

  public void certify(HttpServletRequest request, HttpServletResponse response) {
    
		IniPlugin m_IP = new IniPlugin(request,response,"/home1/tmax/initech/iniplugin/properties/IniPlugin.properties");  //주민번호프로퍼티
    String crlConfig = "/home1/tmax/initech/iniplugin/properties/CRL.properties";  //crl 리스트
    String INIdata = request.getParameter("INIpluginData");
		 if (INIdata == null) {  
      logger.info(" INIdata is Null " );
	  } else {
	    try {
        m_IP.init();
	    } catch(Exception e) {
        logger.info("초기화 에러" + e.getMessage() );
      }
    }
   
    //CheckCRL ccrl = null;         //crl 체크
	  boolean returnFlag = false;   //체크 결과
    X509Certificate cert = null;  
	  IDVerifier idv = null;

    //인증이 필요한 페이지 인지 체크
		user_id = m_IP.getParameter("userid");        //아이디 추출
		user_pw = m_IP.getParameter("userpw");        //비밀번호 추출
    cert = m_IP.getClientCertificate(); 
    serial = cert.getSerialNumber().toString(10);  //시리얼
    subjectDN = cert.getSubjectDN().toString();    //발급대상
		try {
			idv = new IDVerifier();
     // ccrl = new CheckCRL();
		//	ccrl.init(crlConfig);		
		//	returnFlag = ccrl.isValid(cert); //유효한 인증서인지 검사.
      
      if ( returnFlag ) {
        logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL 검증 : 유효한 인증서입니다.     <<<<<<<<<<<<<<<<<");
		    logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL 결과 =[" + returnFlag + "]      <<<<<<<<<<<<<<<<<");
		    message = "CHECKOK";
      } else {
        logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL 검증 : 유효하지 않은 인증서입니다.   <<<<<<<<<<<<");
	      logger.info( ">>>>>>>>>>>>>>>>>>>>>>>> CRL 결과=[" + returnFlag + "]            <<<<<<<<<<<<");
        message = "CHECKFAIL";
	    }
		} catch(Exception e) {
      logger.info("로그인 중입니다.");
		}
  }
  
  /* 메시지 */
  public String getMessage() {
    return this.message;
  }

	/* 사용자ID */
  public String getUserId() {
    return this.user_id;
  }

	/* 사용자PW */
  public String getUserPw() {
    return this.user_pw;
  }

  /* 시리얼 */
  public String getSerial() {
    return this.serial;
  }

  /* 발급대상 */
  public String getSubjectDN() {
    return this.subjectDN;
  }
}