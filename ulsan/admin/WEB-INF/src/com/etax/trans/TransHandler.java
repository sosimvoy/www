/*****************************************************
* 보고서 소켓 핸들러
* @author	   	  (주)미르이즈
* @version         1.0
* @since           1.0
******************************************************/
package com.etax.trans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.etax.config.ETaxConfig;
import com.etax.db.trans.TransDAO;
import com.etax.trans.data.GNBCommon;
import com.etax.trans.data.GNBAcct;
import com.etax.trans.data.GNBComm;
import com.etax.trans.data.GNBJanAk;
import com.etax.trans.data.GNBLog;
import com.etax.trans.data.GNBDeal;
import com.etax.entity.CommonEntity;
public class TransHandler {
	static Logger logger = Logger.getLogger("TransHandler");

	/****************************************************************************
    * 보고서 소켓 실행
    * @param findData      조회조건 
    * @return                
    * @exception             Exception, SocketTimeoutException
    * @since                 1.0
    ***************************************************************************/
    public static String execute (Connection conn, HashMap<String, String> findData, String flag) throws Exception {
		List<CommonEntity> fList = new ArrayList<CommonEntity>();
		return execute (conn, findData, fList, flag);
	}
	public static String execute (Connection conn, HashMap<String, String> findData, List<CommonEntity> findList, String flag)
	        throws Exception {
		
		String bank_ip = ETaxConfig.getString("woori.ip");
		int bank_port= ETaxConfig.getInt("woori.port");
		int time_out = ETaxConfig.getInt("woori.timeout");
		Socket socket =  null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
      
        String sendStr = "";
		String recpStr = "";
		try {
		    logger.info("bank_ip : " + bank_ip +", bank_port : " + bank_port);
		    socket = new Socket(bank_ip, bank_port);
		    socket.setSoTimeout(time_out);
		    /***************************/
		    String ACCOUNT_NO = ""; //계좌번호
		    String BANK_CD = ""; //은행코드
		    String TRANS_AMT = ""; //거래금액
		    String TBL_NAME = ""; //테이블명(작업명)
		    HashMap<String, String> sndHeader = new HashMap<String, String>();//공통부 전송해쉬맵
		    HashMap<String, String> sndData = new HashMap<String, String>();//개별부 전송해쉬맵
		    HashMap<String, String> rcvHeader = new HashMap<String, String>();//공통부 수신해쉬맵
            HashMap<String, String> rcvData = new HashMap<String, String>();//개별부 수신해쉬맵
		    /**************************/
		    GNBCommon common = new GNBCommon();
		    sendStr = common.process(findData);
		    sndHeader = common.getMsgData();
			if ("ACCT".equals(flag)) {
				GNBAcct detail = new GNBAcct();
				sendStr += detail.process(findData);
				sndData = detail.getMsgData();
				ACCOUNT_NO = sndData.get("detail01").trim(); //계좌번호
	            BANK_CD = sndData.get("detail04").trim(); //은행코드
	            //TRANS_AMT = data.get("detail06").trim(); //거래금액
	            TBL_NAME = "웹계좌검증"; //테이블명(작업명)
			} else if ("COMM".equals(flag)) {
				GNBComm detail = new GNBComm();
				sendStr += detail.process(findData);
				sndData = detail.getMsgData();
				TBL_NAME = "테스트콜"; //테이블명(작업명)
			} else if ("JAN".equals(flag)) {
				GNBJanAk detail = new GNBJanAk();
				sendStr += detail.process(findData, findList);
				sndData = detail.getMsgData();
				ACCOUNT_NO = sndData.get("detail03").trim(); //계좌번호
                BANK_CD = "039"; //은행코드
                //TRANS_AMT = "0"; //거래금액
                TBL_NAME = "웹잔액조회"; //테이블명(작업명)
			} else if ("DEAL".equals(flag)) {
				GNBDeal detail = new GNBDeal();
				sendStr += detail.process(findData);
				sndData = detail.getMsgData();
                ACCOUNT_NO = sndData.get("detail01").trim(); //계좌번호
                BANK_CD = "039"; //은행코드
                //TRANS_AMT = "0"; //거래금액
                TBL_NAME = "웹거래내역조회"; //테이블명(작업명)
			}
			osw = new OutputStreamWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));            

            osw.write(sendStr);
            osw.flush();
			
			logger.info("보낸전문[" + sendStr.getBytes().length+ "][" + sendStr + "]");
			
			GNBLog log = new GNBLog();
			if ("ACCT".equals(flag)) {
			    log.gnbCommonLog(sendStr);
			    log.gnbAcctLog(sendStr);
			} else if ("COMM".equals(flag)) {
				log.gnbCommonLog(sendStr);
				log.gnbCommLog(sendStr);
			} else if ("JAN".equals(flag)) {
				log.gnbCommonLog(sendStr);
				log.gnbJanAkLog(sendStr);
			} else if ("DEAL".equals(flag)) {
				log.gnbCommonLog(sendStr);
				log.gnbDealLog(sendStr);
			}
			TransDAO.insertLog(conn, sndHeader, sendStr, ACCOUNT_NO, BANK_CD, TBL_NAME);
			int i = 0;
            int readData  = 0;
            String length = "";       

            while(true) {
                readData = br.read();
                recpStr += (char)readData;
                
                if(i < 4) {
                    length += (char)readData;
                }
                i++;
                if(i > 4 && recpStr.getBytes().length == Integer.parseInt(length)) {
                    break;
                }
            }

            recpStr = recpStr.replaceAll("\r?\n$", ""); // 캐리지리턴, 라인피드 제거
            
            br.close();
            osw.close();
            socket.close();
			
            logger.info("받은전문[" + recpStr.getBytes().length+ "][" + recpStr + "]");
            
            GNBCommon rcvCommon = new GNBCommon();
            rcvCommon.init(recpStr);
            rcvHeader = rcvCommon.getMsgData();
			if ("ACCT".equals(flag)) {
			    GNBAcct rcvBody = new GNBAcct();
			    log.gnbCommonLog(recpStr);
			    log.gnbAcctLog(recpStr);
			    rcvBody.init(recpStr);
			    rcvData = rcvBody.getMsgData();
			    TRANS_AMT = rcvData.get("detail06");
			} else if ("COMM".equals(flag)) {
			    GNBComm rcvBody = new GNBComm();
				log.gnbCommonLog(recpStr);
				log.gnbCommLog(recpStr);
				rcvBody.init(recpStr);
                rcvData = rcvBody.getMsgData();
			} else if ("JAN".equals(flag)) {
			    GNBJanAk rcvBody = new GNBJanAk();
				log.gnbCommonLog(recpStr);
				log.gnbJanAkLog(recpStr);
				rcvBody.init(recpStr);
                rcvData = rcvBody.getMsgData();
                TRANS_AMT = rcvData.get("detail04");
			} else if ("DEAL".equals(flag)) {
			    GNBDeal rcvBody = new GNBDeal();
				log.gnbCommonLog(recpStr);
				log.gnbDealLog(recpStr);
				rcvBody.init(recpStr);
                rcvData = rcvBody.getMsgData();
                TRANS_AMT = rcvData.get("detail09");
			}
			TransDAO.updateLog(conn, rcvHeader, recpStr, TRANS_AMT);
		} catch (Exception e) {
		    throw new Exception("전문송수신에러(" + e + ") / 수신길이 [" + recpStr.length()+"] 수신전문 [" + recpStr +"]");
		} finally {
		    br.close();
            osw.close();
            socket.close();
		}		
		return recpStr;
	}
}