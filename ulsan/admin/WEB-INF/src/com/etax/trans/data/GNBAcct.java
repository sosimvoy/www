/*****************************************************
* ���� ������ȸ�ŷ�
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

public class GNBAcct {
	Logger logger = Logger.getLogger("GNBAcct");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBAcct ������
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBAcct() {
        data.put("detail01", StringUtil.blankFill(" ",  15));  //���¹�ȣ
        data.put("detail02", StringUtil.blankFill(" ",  13));  //�ֹ�(�����)��ȣ
        data.put("detail03", StringUtil.blankFill(" ", 12));  //�����ָ�
		data.put("detail04", StringUtil.blankFill(" ",   3));  //�����ڵ� 
		data.put("detail05", StringUtil.blankFill(" ", 20));  //�����ָ��γ���
		data.put("detail06", StringUtil.zeroFill ("0",  12));  //�ݾ�
        data.put("detail07", StringUtil.blankFill(" ",   2));  //�з�����
        data.put("detail08", StringUtil.blankFill(" ", 123));  //����
	}
	
	/****************************************************************************
   * ���� �ʱ�ȭ ����
   * @param msg             ����
   * @return                
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public void init(String msg) throws Exception{		 
        data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp,  15)));  //���¹�ȣ
        data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp,  13)));  //�ֹ�(�����)��ȣ
        data.put("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp,  12)));  //�����ָ�
		data.put("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp,   3)));  //�����ڵ�		 
		data.put("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  20)));  //�����ָ��γ���
		data.put("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp,  12)));  //�ݾ�
		data.put("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp,   2)));  //�з���������
        data.put("detail08", StringUtil.substringByte(msg,sp,nextPoint(sp, 123)));  //����
	}
		
	/****************************************************************************
   * �ؽ��ʿ� ���� ������ �������� �����
   * @return                ����
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend() {
		String sendMsg = "";
        data.put("detail01", StringUtil.blankFill(data.get("detail01"),  15));  //���¹�ȣ
        data.put("detail02", StringUtil.blankFill(data.get("detail02"),  13));  //�ֹ�(�����)��ȣ
        data.put("detail03", StringUtil.blankFill(data.get("detail03"),  12));  //�����ָ� 
		data.put("detail04", StringUtil.blankFill(data.get("detail04"),   3));  //�����ڵ�
		data.put("detail05", StringUtil.blankFill(data.get("detail05"),  20));  //�����ָ��γ���
		data.put("detail06", StringUtil.zeroFill (data.get("detail06"),  12));  //�ݾ�
		data.put("detail07", StringUtil.blankFill(data.get("detail07"),   2));  //�з���������
        data.put("detail08", StringUtil.blankFill(data.get("detail08"), 123));  //����

		for (int z=1; z<=8; z++) {
			sendMsg += data.get("detail" + StringUtil.zeroFill(z, 2));
		}
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