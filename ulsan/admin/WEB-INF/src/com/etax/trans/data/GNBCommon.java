/*****************************************************
* ���������
* @author	   			 (��)�̸�����
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans.data;

import java.util.*;
import org.apache.log4j.*;
import com.etax.util.StringUtil;

public class GNBCommon {
	Logger logger = Logger.getLogger("GNBCommon");
	private int sp = 0;
	String nowDate = StringUtil.getCurrentDate();
	String nowTime = StringUtil.getCurrentTime();
	private HashMap<String, String> data = new HashMap<String, String>();
	

	/****************************************************************************
    * GNBCommon ������
    * @return                
    * @exception             
    * @since                 1.0
    ***************************************************************************/
	public GNBCommon(){
		data.put("common01", StringUtil.zeroFill ("0",         4)); /* ��������       */
	    data.put("common02", StringUtil.blankFill(" ",        12)); /* ��ü��ȣ       */
	    data.put("common03", StringUtil.zeroFill ("39",        2)); /* �����ڵ�       */
	    data.put("common04", StringUtil.blankFill(" ",         4)); /* ���������ڵ�   */
	    data.put("common05", StringUtil.blankFill(" ",         3)); /* ���������ڵ�   */
		data.put("common06", StringUtil.zeroFill ("0",         1)); /* �۽�ȸ��       */
		data.put("common07", StringUtil.zeroFill ("0",         6)); /* ������ȣ       */
	    data.put("common08", StringUtil.zeroFill (nowDate,     8)); /* ������������   */
	    data.put("common09", StringUtil.zeroFill (nowTime,     6)); /* �������۽ð�   */
	    data.put("common10", StringUtil.blankFill(" ",         4)); /* �����ڵ�       */
	    data.put("common11", StringUtil.blankFill(" ",       24)); /* ���䳻��       */
	    data.put("common12", StringUtil.zeroFill ("0",         3)); /* ����ȣ         */
	    data.put("common13", StringUtil.zeroFill ("0",         3)); /* �ܸ���ȣ       */
	    data.put("common14", StringUtil.blankFill(" ",         1)); /* �ϰ��Աݿ���   */
	    data.put("common15", StringUtil.blankFill(" ",        19)); /* ���񿵿�       */
	}
	
	/****************************************************************************
    * ���� �ʱ�ȭ ����
    * @param msg             ����
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void init(String msg) throws Exception {
		data.put("common01", StringUtil.substringByte(msg, sp, nextPoint(sp,  4)));
		data.put("common02", StringUtil.substringByte(msg, sp, nextPoint(sp, 12)));
		data.put("common03", StringUtil.substringByte(msg, sp, nextPoint(sp,  2)));
		data.put("common04", StringUtil.substringByte(msg, sp, nextPoint(sp,  4)));
		data.put("common05", StringUtil.substringByte(msg, sp, nextPoint(sp,  3)));
		data.put("common06", StringUtil.substringByte(msg, sp, nextPoint(sp,  1)));
        data.put("common07", StringUtil.substringByte(msg, sp, nextPoint(sp,  6)));
        data.put("common08", StringUtil.substringByte(msg, sp, nextPoint(sp,  8)));
        data.put("common09", StringUtil.substringByte(msg, sp, nextPoint(sp,  6)));
        data.put("common10", StringUtil.substringByte(msg, sp, nextPoint(sp,  4)));
        data.put("common11", StringUtil.substringByte(msg, sp, nextPoint(sp, 24)));
        data.put("common12", StringUtil.substringByte(msg, sp, nextPoint(sp,  3)));
        data.put("common13", StringUtil.substringByte(msg, sp, nextPoint(sp,  3)));
        data.put("common14", StringUtil.substringByte(msg, sp, nextPoint(sp,  1)));
        data.put("common15", StringUtil.substringByte(msg, sp, nextPoint(sp, 19)));
	}
	

	/****************************************************************************
    * �ؽ��ʿ� ���� ������ �������� �����
    * @return                ����
    * @exception             
    * @since                 1.0
    ***************************************************************************/
	public String dataSend() {
		data.put("common01", StringUtil.zeroFill (data.get("common01"),  4)); 
		data.put("common02", StringUtil.blankFill(data.get("common02"), 12)); 
		data.put("common03", StringUtil.zeroFill (data.get("common03"),  2));     
		data.put("common04", StringUtil.blankFill(data.get("common04"),  4));    
		data.put("common05", StringUtil.blankFill(data.get("common05"),  3));     
	    data.put("common06", StringUtil.zeroFill (data.get("common06"),  1)); 
	    data.put("common07", StringUtil.zeroFill (data.get("common07"),  6)); 
	    data.put("common08", StringUtil.zeroFill (data.get("common08"),  8)); 
	    data.put("common09", StringUtil.zeroFill (data.get("common09"),  6)); 
	    data.put("common10", StringUtil.blankFill(data.get("common10"),  4)); 
	    data.put("common11", StringUtil.blankFill(data.get("common11"), 24)); 
	    data.put("common12", StringUtil.zeroFill (data.get("common12"),  3)); 
	    data.put("common13", StringUtil.zeroFill(data.get("common13"),  3)); 
	    data.put("common14", StringUtil.blankFill(data.get("common14"),  1));  
	    data.put("common15", StringUtil.blankFill(data.get("common15"), 19));  
		
		String sendMsg = "";
		for(int i = 1; i <= 15; i++) {
		    sendMsg += data.get("common" + StringUtil.zeroFill(i, 2));
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
	
	public HashMap<String, String> getMsgData() {
	  return data;
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
  
	/****************************************************************************
   * ���ڿ� �޾Ƽ� ������ �߰�
   * @param recvMsg         �Է� ���ڿ�
   * @return                ��ȯ�� ����
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public String process(String recvMsg) throws Exception {
	  String recvStr = "";
    this.init(recvMsg);
	  recvStr = this.dataSend();

	  return recvStr;
	}
}
