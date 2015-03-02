/*****************************************************
* 전문 세밀부
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

public class GNBDeal {
	Logger logger = Logger.getLogger("GNBDeal");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBDeal 생성자
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBDeal() {
		data.put("detail01", StringUtil.blankFill(" ", 15));  //계좌번호
		data.put("detail02", StringUtil.zeroFill ("0",  8));  //시작일
		data.put("detail03", StringUtil.zeroFill ("0",  8));  //종료일
		data.put("detail04", StringUtil.zeroFill ("0",  5));  //거래일련번호
		data.put("detail05", StringUtil.blankFill(" ",  8));  //처리중일자
		data.put("detail06", StringUtil.zeroFill ("20", 2));  //배열건수  /* 09월 17일 우리피스 통신단 요청에 의해 가변에서 고정으로 변경 */
		data.put("detail07", StringUtil.zeroFill ("0",  8));  //거래일자
		data.put("detail08", StringUtil.zeroFill ("0",  2));  //거래구분(01:입금, 02:지급, 03:입금취소, 04:지급취소)
		data.put("detail09", StringUtil.zeroFill ("0", 13));  //거래금액
		data.put("detail10", StringUtil.zeroFill ("0", 13));  //거래후잔액
		data.put("detail11", StringUtil.blankFill(" ",20));  //적요
		data.put("detail12", StringUtil.blankFill(" ", 34));  //예비
	}
	
	/****************************************************************************
   * 전문 초기화 셋팅
   * @param msg             전문
   * @return                
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public void init(String msg) throws Exception{
		 
		data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp, 15)));  //계좌번호 
		data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //시작일
		data.put("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //종료일
		data.put("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp,  5)));  //거래일련번호
		data.put("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //처리중일자
		data.put("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //배열건수
		
		List <HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		for (int i=0; i<Integer.parseInt(data.get("detail06")); i++) {
			HashMap<String, String> rowData = new HashMap<String, String>();
		  rowData.put("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //거래일자 
		  rowData.put("detail08", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //거래구분
		  rowData.put("detail09", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //거래금액
		  rowData.put("detail10", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //거래후잔액
			rowData.put("detail11", StringUtil.substringByte(msg,sp,nextPoint(sp, 20)));  //적요
		  rowList.add(rowData);
		  initList = rowList;
		}
		data.put("detail12", StringUtil.substringByte(msg,sp,nextPoint(sp,  34)));  //예비
	}
		
	/****************************************************************************
   * 해쉬맵에 담은 정보를 전문으로 만들기
   * @return                전문
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend() {	  
		String sendMsg = "";
		data.put("detail01", StringUtil.blankFill(data.get("detail01"), 15));  //계좌번호 
		data.put("detail02", StringUtil.zeroFill (data.get("detail02"),  8));  //시작일
		data.put("detail03", StringUtil.zeroFill (data.get("detail03"),  8));  //종료일
		data.put("detail04", StringUtil.zeroFill (data.get("detail04"),  5));  //거래일련번호
		data.put("detail05", StringUtil.zeroFill (data.get("detail05"),  8));  //처리중일자
		data.put("detail06", StringUtil.zeroFill (data.get("detail06"),  2));  //배열건수
    
		for (int a=1; a<=6; a++) {
			sendMsg+=data.get("detail" + StringUtil.zeroFill(a, 2));
		}
    
		for (int y=0; y<20; y++) {
		  data.put("detail07", StringUtil.blankFill(data.get("detail07"),  8));  //거래일자
		  data.put("detail08", StringUtil.zeroFill (data.get("detail08"),  2));  //거래구분
		  data.put("detail09", StringUtil.blankFill(data.get("detail09"), 13));  //거래금액
		  data.put("detail10", StringUtil.blankFill(data.get("detail10"), 13));  //거래후잔액
		  data.put("detail11", StringUtil.blankFill(data.get("detail11"), 20));  //적요

			for (int b=7; b<=11; b++) {
			  sendMsg+=data.get("detail" + StringUtil.zeroFill(b, 2));
		  }
    }

		sendMsg+=data.get("detail12");

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