/*****************************************************
* �����α�
* @author	   			 (��)�̸�����
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
    * ����� �α�
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/

	public void gnbCommonLog(String msg) throws Exception {
	    GNBCommon gnbData = new GNBCommon();
	    gnbData.init(msg);

	    HashMap<String, String> clog = gnbData.getMsgData();
	    logger.info("��������            [" + clog.get("common01") + "]");
	    logger.info("��ü��ȣ            [" + clog.get("common02") + "]");
	    logger.info("�����ڵ�            [" + clog.get("common03") + "]");
	    logger.info("���������ڵ�        [" + clog.get("common04") + "]");
	    logger.info("���������ڵ�        [" + clog.get("common05") + "]");
	    logger.info("�۽�ȸ��            [" + clog.get("common06") + "]");
	    logger.info("������ȣ            [" + clog.get("common07") + "]");
	    logger.info("��������            [" + clog.get("common08") + "]");
	    logger.info("���۽ð�            [" + clog.get("common09") + "]");
	    logger.info("�����ڵ�            [" + clog.get("common10") + "]");
	    logger.info("���䳻��            [" + clog.get("common11") + "]");
	    logger.info("����ȣ              [" + clog.get("common12") + "]");
	    logger.info("�ܸ���ȣ            [" + clog.get("common13") + "]");
	    logger.info("�ϰ��Աݿ���        [" + clog.get("common14") + "]");
	    logger.info("���񿵿�            [" + clog.get("common15") + "]");
	}


	/****************************************************************************
    * ������ȸ �α�
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/

	public void gnbAcctLog(String msg) throws Exception {
	    GNBAcct gnbData = new GNBAcct();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();
    
        logger.info("���¹�ȣ            [" + dlog.get("detail01") + "]");
        logger.info("�ֹ�(�����)��ȣ    [" + dlog.get("detail02") + "]");
        logger.info("�����ָ�            [" + dlog.get("detail03") + "]");
	    logger.info("�Ա������ڵ�        [" + dlog.get("detail04") + "]");
	    logger.info("�����ָ��γ���    [" + dlog.get("detail05") + "]");
	    logger.info("�ݾ�                [" + dlog.get("detail06") + "]");
        logger.info("�з���������        [" + dlog.get("detail07") + "]");
		logger.info("����                [" + dlog.get("detail08") + "]");
	}


	/****************************************************************************
    * ��Ÿ����� �α�
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/

	public void gnbCommLog(String msg) throws Exception {
	    GNBComm gnbData = new GNBComm();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();

	    logger.info("����                [" + dlog.get("detail01") + "]");
	    logger.info("����                [" + dlog.get("detail02") + "]");    
	}


    /****************************************************************************
    * �ܾ���ȸ �α�
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void gnbJanAkLog(String msg) throws Exception {
	    GNBJanAk gnbData = new GNBJanAk();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();

		List<HashMap<String, String>> dataList = gnbData.getDataList();

	    logger.info("��������            [" + dlog.get("detail01") + "]");
	    logger.info("�迭�Ǽ�            [" + dlog.get("detail02") + "]");

        for (int i =0 ; i<30; i++ ) {
            if (i < dataList.size()) {
                HashMap<String, String> alog = dataList.get(i);
			    logger.info("���¹�ȣ            [" + alog.get("detail03") + "]");
	            logger.info("�����ܾ�            [" + alog.get("detail04") + "]");
	            logger.info("ó������ڵ�        [" + alog.get("detail05") + "]");
		        logger.info("ó������޽���      [" + alog.get("detail06") + "]");
            } else if (i >= dataList.size()) {
                logger.info("���¹�ȣ            [" + dlog.get("detail03") + "]");
	            logger.info("�����ܾ�            [" + dlog.get("detail04") + "]");
	            logger.info("ó������ڵ�        [" + dlog.get("detail05") + "]");
		        logger.info("ó������޽���      [" + dlog.get("detail06") + "]");
            }
			
        }
	  
	    logger.info("����                [" + dlog.get("detail07") + "]");
	}


	/****************************************************************************
    * �ŷ��� �α�
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void gnbDealLog(String msg) throws Exception {
	    GNBDeal gnbData = new GNBDeal();
	    gnbData.init(msg);
	    HashMap<String, String> dlog = gnbData.getMsgData();

		List<HashMap<String, String>> dataList = gnbData.getDataList();

	    logger.info("���¹�ȣ            [" + dlog.get("detail01") + "]");
	    logger.info("������              [" + dlog.get("detail02") + "]");
		logger.info("������              [" + dlog.get("detail03") + "]");
	    logger.info("�ŷ��Ϸù�ȣ        [" + dlog.get("detail04") + "]");
		logger.info("ó��������          [" + dlog.get("detail05") + "]");
		logger.info("�迭�Ǽ�            [" + dlog.get("detail06") + "]");

        for (int i =0 ; i<dataList.size(); i++ ) {
			HashMap<String, String> alog = dataList.get(i);
			logger.info("�ŷ�����            [" + alog.get("detail07") + "]");
	        logger.info("�ŷ�����            [" + alog.get("detail08") + "]");
	        logger.info("�ŷ��ݾ�            [" + alog.get("detail09") + "]");
		    logger.info("�ŷ����ܾ�          [" + alog.get("detail10") + "]");
			logger.info("����                [" + alog.get("detail11") + "]");
        }
	  
	    logger.info("����                [" + dlog.get("detail12") + "]");
	}
}