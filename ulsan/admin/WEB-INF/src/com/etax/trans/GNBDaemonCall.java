/*****************************************************
* 데몬shell 호출
* @author	   			 (주)미르이즈
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans;

import java.io.*;
import org.apache.log4j.Logger;

public class GNBDaemonCall {

	private static Logger logger = Logger.getLogger(GNBDaemonCall.class); // log4j 설정	
 	
	/****************************************************************************
    * DaemonCall 생성자
    * @return                
    * @exception             
    * @since                 1.0
    ***************************************************************************/
    public static boolean setDaemonShellExecuteCommand(String paramInfo1, String paramInfo2, String paramInfo3, String paramInfo4) throws Exception {
      boolean retVal = false;
	  String shellName = "/home1/netguy/GNBDAEMON/REAL_DAEMON/GNB_SEND/shl/execHandler.sh";

     // paramInfo1 = "SendDepositor", "SendSurplus", "SendJanak";  //자금배정, 잉여금, 대량잔액조회
     // paramInfo2 = "600";                                        //로그번호(자금배정, 잉여금인경우 : 책임자승인, 대량잔액조회: 조회일자)
     // paramInfo3 = "IPGM", "BYUL", "JAN";                        //별단구분(입금, 별단), 전액조회(JAN)
     // paramInfo4 = "0000";                                       //타행 별단 원거래전문(입금전문번호)
      
	  if (!"SendDepositor".equals(paramInfo1) && !"SendSurplus".equals(paramInfo1) && !"SendJanak".equals(paramInfo1)){
		  logger.info("작업구분이 존재하지 않습니다.[setDaemonShellExecuteCommand]");
          return retVal;
      }

      if ("".equals(paramInfo2) || paramInfo2 == null){
		  logger.info("로그번호가 존재하지 않습니다.[setDaemonShellExecuteCommand]");
          return retVal;
      }
      
      if ("".equals(paramInfo3) || paramInfo3 == null){
		  logger.info("별단구분이 존재하지 않습니다.[setDaemonShellExecuteCommand]");
          return retVal;
      }

	  if (!"IPGM".equals(paramInfo3) && !"BYUL".equals(paramInfo3) && !"JAN".equals(paramInfo3)){
		  logger.info("별단구분이 존재하지 않습니다.[setDaemonShellExecuteCommand]");
          return retVal;
      }

      String[] execName = {"/bin/sh","-c", shellName + " " + paramInfo1 + " " + paramInfo2 + " " + paramInfo3 + " " + paramInfo4};

	  //String execName = "bin/sh" + shellName + " " + paramInfo1 + " " + paramInfo2 + " " + paramInfo3 + " " + paramInfo4;
      Runtime r = Runtime.getRuntime();



//  InputStream inputStream = null;
//  BufferedReader bufferedReader = null;
  
		  logger.info("execName["+execName+"]");
	  try{
        Process process = r.exec(execName);
        process.getInputStream();
		 logger.info("시작은 했음.");
/*        
    inputStream = process.getInputStream() ; 
    bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) ) ; 

    while(true) { 
     String info = bufferedReader.readLine() ;
	 
	 logger.info("info=["+info+"]");
     if( info == null || info.equals( "" ) ){
      break ; 
     }
     logger.info( info ) ; 
    }
*/
		Thread.sleep(2000);

      } catch (IOException e) {
        e.printStackTrace();
		return retVal;
} finally {
//   try{if(inputStream!=null)inputStream.close();}catch(Exception e){}
//   try{if(bufferedReader!=null)bufferedReader.close();}catch(Exception e){}
  }  
      		 logger.info("호출로직 끝.");

	  retVal = true;

      return retVal;
    }
}