/*****************************************************
* 전문공통부
* @author	   			 (주)미르이즈
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
    * GNBCommon 생성자
    * @return                
    * @exception             
    * @since                 1.0
    ***************************************************************************/
	public GNBCommon(){
		data.put("common01", StringUtil.zeroFill ("0",         4)); /* 전문길이       */
	    data.put("common02", StringUtil.blankFill(" ",        12)); /* 업체번호       */
	    data.put("common03", StringUtil.zeroFill ("39",        2)); /* 은행코드       */
	    data.put("common04", StringUtil.blankFill(" ",         4)); /* 전문구분코드   */
	    data.put("common05", StringUtil.blankFill(" ",         3)); /* 업무구분코드   */
		data.put("common06", StringUtil.zeroFill ("0",         1)); /* 송신회수       */
		data.put("common07", StringUtil.zeroFill ("0",         6)); /* 전문번호       */
	    data.put("common08", StringUtil.zeroFill (nowDate,     8)); /* 전문전송일자   */
	    data.put("common09", StringUtil.zeroFill (nowTime,     6)); /* 전문전송시각   */
	    data.put("common10", StringUtil.blankFill(" ",         4)); /* 응답코드       */
	    data.put("common11", StringUtil.blankFill(" ",       24)); /* 응답내용       */
	    data.put("common12", StringUtil.zeroFill ("0",         3)); /* 점번호         */
	    data.put("common13", StringUtil.zeroFill ("0",         3)); /* 단말번호       */
	    data.put("common14", StringUtil.blankFill(" ",         1)); /* 일괄입금여부   */
	    data.put("common15", StringUtil.blankFill(" ",        19)); /* 예비영역       */
	}
	
	/****************************************************************************
    * 전문 초기화 셋팅
    * @param msg             전문
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
    * 해쉬맵에 담은 정보를 전문으로 만들기
    * @return                전문
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
   * byte단위로 자르기 위해 다음 인덱스 구하기
   * @param startPoint      시작인덱스      
   * @param term            길이
   * @return                다음 인덱스
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
   * 조회조건을 해쉽맵에 담아서 전문에 추가함
   * @param sendData        조회조건           
   * @return                보낼 전문
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
   * 문자열 받아서 전문에 추가
   * @param recvMsg         입력 문자열
   * @return                변환된 전문
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
