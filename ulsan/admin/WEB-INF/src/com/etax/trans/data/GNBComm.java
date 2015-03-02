/*****************************************************
* ���� ��Ÿ��ŷ�
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

public class GNBComm {
	Logger logger = Logger.getLogger("GNBComm");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBComm ������
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBComm() {
		data.put("detail01", StringUtil.blankFill(" ",   1));  //����
		data.put("detail02", StringUtil.blankFill(" ", 199));  //����
	}
	
	/****************************************************************************
   * ���� �ʱ�ȭ ����
   * @param msg             ����
   * @return                
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public void init(String msg) throws Exception{
		 
		data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp,   1)));  //���� 
		data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp, 199)));  //����
	}
		
	/****************************************************************************
   * �ؽ��ʿ� ���� ������ �������� �����
   * @return                ����
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend() {
		String sendMsg = "";
		data.put("detail01", StringUtil.blankFill(data.get("detail01"),   1));  //���� 
		data.put("detail02", StringUtil.blankFill(data.get("detail02"), 199));  //����

		for (int z=1; z<=2; z++) {
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