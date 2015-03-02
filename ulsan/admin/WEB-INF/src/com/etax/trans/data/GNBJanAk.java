/*****************************************************
* ���� ���к�
* @author	   			 (��)�̸�����
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.entity.*;
import com.etax.util.StringUtil;

public class GNBJanAk {
	Logger logger = Logger.getLogger("GNBJanAk");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBJanAk ������
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBJanAk() {
		data.put("detail01", StringUtil.zeroFill ("0",   8));  //��������
		data.put("detail02", StringUtil.zeroFill ("0",   2));  //�迭�Ǽ�
		data.put("detail03", StringUtil.blankFill(" ", 15));  //���¹�ȣ
		data.put("detail04", StringUtil.zeroFill ("0", 13));  //�����ܾ�
		data.put("detail05", StringUtil.blankFill(" ",  4));  //ó������ڵ�
		data.put("detail06", StringUtil.blankFill(" ",24));  //ó������޽���
		data.put("detail07", StringUtil.blankFill(" ", 60));  //����
	}
	
	/****************************************************************************
    * ���� �ʱ�ȭ ����
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void init(String msg) throws Exception{		 
		data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //�������� 
		data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //�迭�Ǽ�
		
		List <HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		for (int i=0; i<Integer.parseInt(data.get("detail02")); i++) {
			HashMap<String, String> rowData = new HashMap<String, String>();
			rowData.put("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp, 15)));  //���¹�ȣ 
			rowData.put("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //�����ܾ�
			rowData.put("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  4)));  //ó������ڵ�
			rowData.put("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp, 24)));  //ó������޽���
			rowList.add(rowData);
			initList = rowList;
		}
		data.put("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp, 60)));  //����
	}
		
	/****************************************************************************
   * �ؽ��ʿ� ���� ������ �������� �����
   * @return                ����
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend(List<CommonEntity> sendList) {	  
		String sendMsg = "";
		data.put("detail01", StringUtil.zeroFill (data.get("detail01"),  8));  //�������� 
		data.put("detail02", StringUtil.zeroFill (sendList.size()+"",  2));  //�迭�Ǽ�
		for (int a=1; a<=2; a++) {
			sendMsg+=data.get("detail" + StringUtil.zeroFill(a, 2));
		}
		if (sendList.size() <= 30) {
      for (int x=0; x<sendList.size(); x++) {
			  CommonEntity rowData = (CommonEntity)sendList.get(x);
		    data.put("detail03", StringUtil.blankFill(rowData.getString("M300_ACCOUNTNO"), 15));  //���¹�ȣ
		    data.put("detail04", StringUtil.zeroFill (data.get("detail04"), 13));  //�����ܾ�
		    data.put("detail05", StringUtil.blankFill(data.get("detail05"),  4));  //ó������ڵ�
		    data.put("detail06", StringUtil.blankFill(data.get("detail06"), 24));  //ó������޽���
		  
		    for (int b=3; b<=6; b++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(b, 2));
		    }
		  }

      for (int y=0; y<30-sendList.size(); y++) {
		    data.put("detail03", StringUtil.blankFill(" ",  15));  //���¹�ȣ
		    data.put("detail04", StringUtil.zeroFill ("0",  13));  //�����ܾ�
		    data.put("detail05", StringUtil.blankFill(" ",   4));  //ó������ڵ�
		    data.put("detail06", StringUtil.blankFill (" ", 24));  //ó������޽���
		  
		    for (int c=3; c<=6; c++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(c, 2));
		    }
	 }

      
    } else {
      for (int x=0; x<30; x++) {
			  CommonEntity rowData = (CommonEntity)sendList.get(x);
		    data.put("detail03", StringUtil.blankFill(rowData.getString("M300_ACCOUNTNO"), 15));  //���¹�ȣ
		    data.put("detail04", StringUtil.zeroFill (data.get("detail04"), 13));  //�����ܾ�
		    data.put("detail05", StringUtil.blankFill(data.get("detail05"),  4));  //ó������ڵ�
		    data.put("detail06", StringUtil.blankFill(data.get("detail06"), 24));  //ó������޽���
		  
		    for (int b=3; b<=6; b++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(b, 2));
		    }
		  }

      for (int y=0; y<sendList.size() - 30; y++) {
		    data.put("detail03", StringUtil.blankFill(" ",  15));  //���¹�ȣ
		    data.put("detail04", StringUtil.zeroFill ("0",  13));  //�����ܾ�
		    data.put("detail05", StringUtil.blankFill(" ",   4));  //ó������ڵ�
		    data.put("detail06", StringUtil.blankFill (" ", 24));  //ó������޽���
		  
		    for (int c=3; c<=6; c++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(c, 2));
		    }
		  }

    }
		

		

		data.put("detail07", StringUtil.blankFill(data.get("detail07"), 60));  //����4   

		sendMsg += data.get("detail07");
		return sendMsg;
	}
	
	/****************************************************************************
   * byte������ �ڸ��� ���� ���� �ε��� ���ϱ�
   * @param startPoint      �����ε���      
   * @param term            ����
   * @return                ���� �ε���
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/
	private int nextPoint(int startPoint, int term) throws Exception {
	  sp = startPoint + term;

	  return sp;
	}
	
	/****************************************************************************
   * ��ȸ������ �ؽ��ʿ� ��Ƽ� ������ �߰���
   * @param sendData        ��ȸ����           
   * @return                ���� ����
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public String process(HashMap<String,String> sendData, List<CommonEntity> sendList) throws Exception {
	  String sendStr = "";

	  data.putAll(sendData);

	  sendStr = this.dataSend(sendList);

	  return sendStr;
	}
	
	public HashMap<String, String> getMsgData() {
	  return data;
	}
	
	public List<HashMap<String, String>> getDataList() {
		
	  return initList;
	}
}