/*****************************************************
* 전문로그
* @author	   			 (주)미르이즈
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans.data;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class GNBLog {
	Logger logger = Logger.getLogger("GNBLog");
	
	/****************************************************************************
    * 공통부 로그
    * @param msg             전문
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/

	public void gnbCommonLog(String msg) throws Exception {
	    GNBCommon gnbData = new GNBCommon();
	    gnbData.init(msg);

	    HashMap<String, String> clog = gnbData.getMsgData();
	    logger.info("전문길이            [" + clog.get("common01") + "]");
	    logger.info("업체번호            [" + clog.get("common02") + "]");
	    logger.info("은행코드            [" + clog.get("common03") + "]");
	    logger.info("전문구분코드        [" + clog.get("common04") + "]");
	    logger.info("업무구분코드        [" + clog.get("common05") + "]");
	    logger.info("송신회수            [" + clog.get("common06") + "]");
	    logger.info("전문번호            [" + clog.get("common07") + "]");
	    logger.info("전송일자            [" + clog.get("common08") + "]");
	    logger.info("전송시각            [" + clog.get("common09") + "]");
	    logger.info("응답코드            [" + clog.get("common10") + "]");
	    logger.info("응답내용            [" + clog.get("common11") + "]");
	    logger.info("점번호              [" + clog.get("common12") + "]");
	    logger.info("단말번호            [" + clog.get("common13") + "]");
	    logger.info("일괄입금여부        [" + clog.get("common14") + "]");
	    logger.info("예비영역            [" + clog.get("common15") + "]");
	}


	/****************************************************************************
    * 수취조회 로그
    * @param msg             전문
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/

	public void gnbAcctLog(String msg) throws Exception {
	    GNBAcct gnbData = new GNBAcct();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();
    
        logger.info("계좌번호            [" + dlog.get("detail01") + "]");
        logger.info("주민(사업자)번호    [" + dlog.get("detail02") + "]");
        logger.info("예금주명            [" + dlog.get("detail03") + "]");
	    logger.info("입금은행코드        [" + dlog.get("detail04") + "]");
	    logger.info("예금주명세부내역    [" + dlog.get("detail05") + "]");
	    logger.info("금액                [" + dlog.get("detail06") + "]");
        logger.info("압류방지여부        [" + dlog.get("detail07") + "]");
		logger.info("예비                [" + dlog.get("detail08") + "]");
	}


	/****************************************************************************
    * 통신망업무 로그
    * @param msg             전문
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/

	public void gnbCommLog(String msg) throws Exception {
	    GNBComm gnbData = new GNBComm();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();

	    logger.info("구분                [" + dlog.get("detail01") + "]");
	    logger.info("예비                [" + dlog.get("detail02") + "]");    
	}


    /****************************************************************************
    * 잔액조회 로그
    * @param msg             전문
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void gnbJanAkLog(String msg) throws Exception {
	    GNBJanAk gnbData = new GNBJanAk();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();

		List<HashMap<String, String>> dataList = gnbData.getDataList();

	    logger.info("기준일자            [" + dlog.get("detail01") + "]");
	    logger.info("배열건수            [" + dlog.get("detail02") + "]");

        for (int i =0 ; i<30; i++ ) {
            if (i < dataList.size()) {
                HashMap<String, String> alog = dataList.get(i);
			    logger.info("계좌번호            [" + alog.get("detail03") + "]");
	            logger.info("계좌잔액            [" + alog.get("detail04") + "]");
	            logger.info("처리결과코드        [" + alog.get("detail05") + "]");
		        logger.info("처리결과메시지      [" + alog.get("detail06") + "]");
            } else if (i >= dataList.size()) {
                logger.info("계좌번호            [" + dlog.get("detail03") + "]");
	            logger.info("계좌잔액            [" + dlog.get("detail04") + "]");
	            logger.info("처리결과코드        [" + dlog.get("detail05") + "]");
		        logger.info("처리결과메시지      [" + dlog.get("detail06") + "]");
            }
			
        }
	  
	    logger.info("예비                [" + dlog.get("detail07") + "]");
	}


	/****************************************************************************
    * 거래명세 로그
    * @param msg             전문
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void gnbDealLog(String msg) throws Exception {
	    GNBDeal gnbData = new GNBDeal();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();

		List<HashMap<String, String>> dataList = gnbData.getDataList();

	    logger.info("계좌번호            [" + dlog.get("detail01") + "]");
	    logger.info("시작일              [" + dlog.get("detail02") + "]");
		logger.info("종료일              [" + dlog.get("detail03") + "]");
	    logger.info("거래일련번호        [" + dlog.get("detail04") + "]");
		logger.info("처리중일자          [" + dlog.get("detail05") + "]");
		logger.info("배열건수            [" + dlog.get("detail06") + "]");

        for (int i =0 ; i<dataList.size(); i++ ) {
			HashMap<String, String> alog = dataList.get(i);
			logger.info("거래일자            [" + alog.get("detail07") + "]");
	        logger.info("거래구분            [" + alog.get("detail08") + "]");
	        logger.info("거래금액            [" + alog.get("detail09") + "]");
		    logger.info("거래후잔액          [" + alog.get("detail10") + "]");
			logger.info("적요                [" + alog.get("detail11") + "]");
        }
	  
	    logger.info("예비                [" + dlog.get("detail12") + "]");
	}
}