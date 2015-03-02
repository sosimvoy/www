/*****************************************************
* 전문 통신망거래
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

public class GNBComm {
	Logger logger = Logger.getLogger("GNBComm");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBComm 생성자
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBComm() {
		data.put("detail01", StringUtil.blankFill(" ",   1));  //구분
		data.put("detail02", StringUtil.blankFill(" ", 199));  //예비
	}
	
	/****************************************************************************
   * 전문 초기화 셋팅
   * @param msg             전문
   * @return                
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public void init(String msg) throws Exception{
		 
		data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp,   1)));  //구분 
		data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp, 199)));  //예비
	}
		
	/****************************************************************************
   * 해쉬맵에 담은 정보를 전문으로 만들기
   * @return                전문
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend() {
		String sendMsg = "";
		data.put("detail01", StringUtil.blankFill(data.get("detail01"),   1));  //구분 
		data.put("detail02", StringUtil.blankFill(data.get("detail02"), 199));  //예비

		for (int z=1; z<=2; z++) {
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