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

import com.etax.util.StringUtil;

public class GNBDeal {
	Logger logger = Logger.getLogger("GNBDeal");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBDeal ������
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBDeal() {
		data.put("detail01", StringUtil.blankFill(" ", 15));  //���¹�ȣ
		data.put("detail02", StringUtil.zeroFill ("0",  8));  //������
		data.put("detail03", StringUtil.zeroFill ("0",  8));  //������
		data.put("detail04", StringUtil.zeroFill ("0",  5));  //�ŷ��Ϸù�ȣ
		data.put("detail05", StringUtil.blankFill(" ",  8));  //ó��������
		data.put("detail06", StringUtil.zeroFill ("20", 2));  //�迭�Ǽ�  /* 09�� 17�� �츮�ǽ� ��Ŵ� ��û�� ���� �������� �������� ���� */
		data.put("detail07", StringUtil.zeroFill ("0",  8));  //�ŷ�����
		data.put("detail08", StringUtil.zeroFill ("0",  2));  //�ŷ�����(01:�Ա�, 02:����, 03:�Ա����, 04:�������)
		data.put("detail09", StringUtil.zeroFill ("0", 13));  //�ŷ��ݾ�
		data.put("detail10", StringUtil.zeroFill ("0", 13));  //�ŷ����ܾ�
		data.put("detail11", StringUtil.blankFill(" ",20));  //����
		data.put("detail12", StringUtil.blankFill(" ", 34));  //����
	}
	
	/****************************************************************************
   * ���� �ʱ�ȭ ����
   * @param msg             ����
   * @return                
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public void init(String msg) throws Exception{
		 
		data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp, 15)));  //���¹�ȣ 
		data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //������
		data.put("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //������
		data.put("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp,  5)));  //�ŷ��Ϸù�ȣ
		data.put("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //ó��������
		data.put("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //�迭�Ǽ�
		
		List <HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		for (int i=0; i<Integer.parseInt(data.get("detail06")); i++) {
			HashMap<String, String> rowData = new HashMap<String, String>();
		  rowData.put("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //�ŷ����� 
		  rowData.put("detail08", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //�ŷ�����
		  rowData.put("detail09", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //�ŷ��ݾ�
		  rowData.put("detail10", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //�ŷ����ܾ�
			rowData.put("detail11", StringUtil.substringByte(msg,sp,nextPoint(sp, 20)));  //����
		  rowList.add(rowData);
		  initList = rowList;
		}
		data.put("detail12", StringUtil.substringByte(msg,sp,nextPoint(sp,  34)));  //����
	}
		
	/****************************************************************************
   * �ؽ��ʿ� ���� ������ �������� �����
   * @return                ����
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend() {	  
		String sendMsg = "";
		data.put("detail01", StringUtil.blankFill(data.get("detail01"), 15));  //���¹�ȣ 
		data.put("detail02", StringUtil.zeroFill (data.get("detail02"),  8));  //������
		data.put("detail03", StringUtil.zeroFill (data.get("detail03"),  8));  //������
		data.put("detail04", StringUtil.zeroFill (data.get("detail04"),  5));  //�ŷ��Ϸù�ȣ
		data.put("detail05", StringUtil.zeroFill (data.get("detail05"),  8));  //ó��������
		data.put("detail06", StringUtil.zeroFill (data.get("detail06"),  2));  //�迭�Ǽ�
    
		for (int a=1; a<=6; a++) {
			sendMsg+=data.get("detail" + StringUtil.zeroFill(a, 2));
		}
    
		for (int y=0; y<20; y++) {
		  data.put("detail07", StringUtil.blankFill(data.get("detail07"),  8));  //�ŷ�����
		  data.put("detail08", StringUtil.zeroFill (data.get("detail08"),  2));  //�ŷ�����
		  data.put("detail09", StringUtil.blankFill(data.get("detail09"), 13));  //�ŷ��ݾ�
		  data.put("detail10", StringUtil.blankFill(data.get("detail10"), 13));  //�ŷ����ܾ�
		  data.put("detail11", StringUtil.blankFill(data.get("detail11"), 20));  //����

			for (int b=7; b<=11; b++) {
			  sendMsg+=data.get("detail" + StringUtil.zeroFill(b, 2));
		  }
    }

		sendMsg+=data.get("detail12");

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

	public String process(HashMap<String,String> sendData) throws Exception {
	  String sendStr = "";

	  data.putAll(sendData);

	  sendStr = this.dataSend();

	  return sendStr;
	}
	
	public HashMap<String, String> getMsgData() {
	  return data;
	}
	
	public List<HashMap<String, String>> getDataList() {
		
	  return initList;
	}
}