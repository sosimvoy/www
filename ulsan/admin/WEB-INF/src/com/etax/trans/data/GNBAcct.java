/*****************************************************
* 전문 수취조회거래
* @author	   			 (주)미르이즈
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
   * GNBAcct 생성자
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBAcct() {
        data.put("detail01", StringUtil.blankFill(" ",  15));  //계좌번호
        data.put("detail02", StringUtil.blankFill(" ",  13));  //주민(사업자)번호
        data.put("detail03", StringUtil.blankFill(" ", 12));  //예금주명
		data.put("detail04", StringUtil.blankFill(" ",   3));  //은행코드 
		data.put("detail05", StringUtil.blankFill(" ", 20));  //예금주명세부내역
		data.put("detail06", StringUtil.zeroFill ("0",  12));  //금액
        data.put("detail07", StringUtil.blankFill(" ",   2));  //압류방지
        data.put("detail08", StringUtil.blankFill(" ", 123));  //예비
	}
	
	/****************************************************************************
   * 전문 초기화 셋팅
   * @param msg             전문
   * @return                
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public void init(String msg) throws Exception{		 
        data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp,  15)));  //계좌번호
        data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp,  13)));  //주민(사업자)번호
        data.put("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp,  12)));  //예금주명
		data.put("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp,   3)));  //은행코드		 
		data.put("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  20)));  //예금주명세부내역
		data.put("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp,  12)));  //금액
		data.put("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp,   2)));  //압류방지여부
        data.put("detail08", StringUtil.substringByte(msg,sp,nextPoint(sp, 123)));  //예비
	}
		
	/****************************************************************************
   * 해쉬맵에 담은 정보를 전문으로 만들기
   * @return                전문
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend() {
		String sendMsg = "";
        data.put("detail01", StringUtil.blankFill(data.get("detail01"),  15));  //계좌번호
        data.put("detail02", StringUtil.blankFill(data.get("detail02"),  13));  //주민(사업자)번호
        data.put("detail03", StringUtil.blankFill(data.get("detail03"),  12));  //예금주명 
		data.put("detail04", StringUtil.blankFill(data.get("detail04"),   3));  //은행코드
		data.put("detail05", StringUtil.blankFill(data.get("detail05"),  20));  //예금주명세부내역
		data.put("detail06", StringUtil.zeroFill (data.get("detail06"),  12));  //금액
		data.put("detail07", StringUtil.blankFill(data.get("detail07"),   2));  //압류방지여부
        data.put("detail08", StringUtil.blankFill(data.get("detail08"), 123));  //예비

		for (int z=1; z<=8; z++) {
			sendMsg += data.get("detail" + StringUtil.zeroFill(z, 2));
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
	
	public HashMap<String, String> getMsgData() {
	  return data;
	}
	
	public List<HashMap<String, String>> getDataList() {
		
	  return initList;
	}
}