/*****************************************************
* ����shell ȣ��
* @author	   			 (��)�̸�����
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans;

import java.io.*;
import org.apache.log4j.Logger;

public class GNBDaemonCall {

	private static Logger logger = Logger.getLogger(GNBDaemonCall.class); // log4j ����	
 	
	/****************************************************************************
    * DaemonCall ������
    * @return                
    * @exception             
    * @since                 1.0
    ***************************************************************************/
    public static boolean setDaemonShellExecuteCommand(String paramInfo1, String paramInfo2, String paramInfo3, String paramInfo4) throws Exception {
      boolean retVal = false;
	  String shellName = "/home1/netguy/GNBDAEMON/REAL_DAEMON/GNB_SEND/shl/execHandler.sh";

     // paramInfo1 = "SendDepositor", "SendSurplus", "SendJanak";  //�ڱݹ���, �׿���, �뷮�ܾ���ȸ
     // paramInfo2 = "600";                                        //�α׹�ȣ(�ڱݹ���, �׿����ΰ�� : å���ڽ���, �뷮�ܾ���ȸ: ��ȸ����)
     // paramInfo3 = "IPGM", "BYUL", "JAN";                        //���ܱ���(�Ա�, ����), ������ȸ(JAN)
     // paramInfo4 = "0000";                                       //Ÿ�� ���� ���ŷ�����(�Ա�������ȣ)
      
	  if (!"SendDepositor".equals(paramInfo1) && !"SendSurplus".equals(paramInfo1) && !"SendJanak".equals(paramInfo1)){
		  logger.info("�۾������� �������� �ʽ��ϴ�.[setDaemonShellExecuteCommand]");
          return retVal;
      }

      if ("".equals(paramInfo2) || paramInfo2 == null){
		  logger.info("�α׹�ȣ�� �������� �ʽ��ϴ�.[setDaemonShellExecuteCommand]");
          return retVal;
      }
      
      if ("".equals(paramInfo3) || paramInfo3 == null){
		  logger.info("���ܱ����� �������� �ʽ��ϴ�.[setDaemonShellExecuteCommand]");
          return retVal;
      }

	  if (!"IPGM".equals(paramInfo3) && !"BYUL".equals(paramInfo3) && !"JAN".equals(paramInfo3)){
		  logger.info("���ܱ����� �������� �ʽ��ϴ�.[setDaemonShellExecuteCommand]");
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
		 logger.info("������ ����.");
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
      		 logger.info("ȣ����� ��.");

	  retVal = true;

      return retVal;
    }
}