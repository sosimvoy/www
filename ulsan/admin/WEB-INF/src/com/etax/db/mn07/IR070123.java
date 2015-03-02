/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070123.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입세출외현금 일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070123 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
       
		sb.append("SELECT LIST.*                                                                                                                    \n");
		sb.append("  FROM (                                                                                                                         \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN                                                                                                  \n");
		sb.append("		       FROM (                                                                                                                 \n"); 
    sb.append(" SELECT  A.M270_YEAR, A.M270_DATE, B.M390_ACCCODE, C.M360_ACCNAME                                                                \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBJEON),0)       M270_BOJEUNGJEONGGIIBJEON                                                 \n");                                             
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANIBJEON),0)       M270_BOJEUNGBYULDANIBJEON                                                 \n");               
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMIBJEON),0)      M270_BOJEUNGGONGGEUMIBJEON                                                \n");               
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBJEON),0)        M270_BOGWANJEONGGIIBJEON                                                  \n");               
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANIBJEON),0)        M270_BOGWANBYULDANIBJEON                                                  \n");              
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMIBJEON),0)       M270_BOGWANGONGGEUMIBJEON                                                 \n");             
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBJEON),0)       M270_JABJONGJEONGGIIBJEON                                                 \n");            
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANIBJEON),0)       M270_JABJONGBYULDANIBJEON                                                 \n");             
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMIBJEON),0)      M270_JABJONGGONGGEUMIBJEON                                                \n");            
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBJEON),0)        M270_BUGASEJEONGGIIBJEON                                                  \n");           
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANIBJEON),0)        M270_BUGASEBYULDANIBJEON                                                  \n");         
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMIBJEON),0)       M270_BUGASEGONGGEUMIBJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBGEUM),0)       M270_BOJEUNGJEONGGIIBGEUM                                                 \n");         
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANIBGEUM),0)       M270_BOJEUNGBYULDANIBGEUM                                                 \n");         
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMIBGEUM),0)      M270_BOJEUNGGONGGEUMIBGEUM                                                \n");        
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBGEUM),0)        M270_BOGWANJEONGGIIBGEUM                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANIBGEUM),0)        M270_BOGWANBYULDANIBGEUM                                                  \n");        
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMIBGEUM),0)       M270_BOGWANGONGGEUMIBGEUM                                                 \n");        
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBGEUM),0)       M270_JABJONGJEONGGIIBGEUM                                                 \n");        
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANIBGEUM),0)       M270_JABJONGBYULDANIBGEUM                                                 \n");        
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMIBGEUM),0)      M270_JABJONGGONGGEUMIBGEUM                                                \n");        
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBGEUM),0)        M270_BUGASEJEONGGIIBGEUM                                                  \n");        
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANIBGEUM),0)        M270_BUGASEBYULDANIBGEUM                                                  \n");         
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMIBGEUM),0)       M270_BUGASEGONGGEUMIBGEUM                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBJEONG),0)      M270_BOJEUNGJEONGGIIBJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANIBJEONG),0)      M270_BOJEUNGBYULDANIBJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMIBJEONG),0)     M270_BOJEUNGGONGGEUMIBJEONG                                               \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBJEONG),0)       M270_BOGWANJEONGGIIBJEONG                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANIBJEONG),0)       M270_BOGWANBYULDANIBJEONG                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMIBJEONG),0)      M270_BOGWANGONGGEUMIBJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBJEONG),0)      M270_JABJONGJEONGGIIBJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANIBJEONG),0)      M270_JABJONGBYULDANIBJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMIBJEONG),0)     M270_JABJONGGONGGEUMIBJEONG                                               \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBJEONG),0)       M270_BUGASEJEONGGIIBJEONG                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANIBJEONG),0)       M270_BUGASEBYULDANIBJEONG                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMIBJEONG),0)      M270_BUGASEGONGGEUMIBJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIJEON),0)       M270_BOJEUNGJEONGGIJIJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANJIJEON),0)       M270_BOJEUNGBYULDANJIJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMJIJEON),0)      M270_BOJEUNGGONGGEUMJIJEON                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIJEON),0)        M270_BOGWANJEONGGIJIJEON                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANJIJEON),0)        M270_BOGWANBYULDANJIJEON                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMJIJEON),0)       M270_BOGWANGONGGEUMJIJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIJEON),0)       M270_JABJONGJEONGGIJIJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANJIJEON),0)       M270_JABJONGBYULDANJIJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMJIJEON),0)      M270_JABJONGGONGGEUMJIJEON                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIJEON),0)        M270_BUGASEJEONGGIJIJEON                                                  \n");           
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANJIJEON),0)        M270_BUGASEBYULDANJIJEON                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMJIJEON),0)       M270_BUGASEGONGGEUMJIJEON                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIGEUB),0)       M270_BOJEUNGJEONGGIJIGEUB                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANJIGEUB),0)       M270_BOJEUNGBYULDANJIGEUB                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMJIGEUB),0)      M270_BOJEUNGGONGGEUMJIGEUB                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIGEUB),0)        M270_BOGWANJEONGGIJIGEUB                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANJIGEUB),0)        M270_BOGWANBYULDANJIGEUB                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMJIGEUB),0)       M270_BOGWANGONGGEUMJIGEUB                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIGEUB),0)       M270_JABJONGJEONGGIJIGEUB                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANJIGEUB),0)       M270_JABJONGBYULDANJIGEUB                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMJIGEUB),0)      M270_JABJONGGONGGEUMJIGEUB                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIGEUB),0)        M270_BUGASEJEONGGIJIGEUB                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANJIGEUB),0)        M270_BUGASEBYULDANJIGEUB                                                  \n");          
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMJIGEUB),0)       M270_BUGASEGONGGEUMJIGEUB                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIJEONG),0)      M270_BOJEUNGJEONGGIJIJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANJIJEONG),0)      M270_BOJEUNGBYULDANJIJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMJIJEONG),0)     M270_BOJEUNGGONGGEUMJIJEONG                                               \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIJEONG),0)       M270_BOGWANJEONGGIJIJEONG                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANJIJEONG),0)       M270_BOGWANBYULDANJIJEONG                                                 \n");          
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMJIJEONG),0)      M270_BOGWANGONGGEUMJIJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIJEONG),0)      M270_JABJONGJEONGGIJIJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANJIJEONG),0)      M270_JABJONGBYULDANJIJEONG                                                \n");          
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMJIJEONG),0)     M270_JABJONGGONGGEUMJIJEONG                                               \n");         
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIJEONG),0)       M270_BUGASEJEONGGIJIJEONG                                                 \n");   
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANJIJEONG),0)       M270_BUGASEBYULDANJIJEONG                                                 \n");   
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMJIJEONG),0)      M270_BUGASEGONGGEUMJIJEONG                                                \n");   
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBJEON  + A.M270_BOJEUNGJEONGGIIBGEUM  -  A.M270_BOJEUNGJEONGGIIBJEONG),0)    G1C1D1       \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANIBJEON  + A.M270_BOJEUNGBYULDANIBGEUM  -  A.M270_BOJEUNGBYULDANIBJEONG),0)    G1C1D2       \n");   
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMIBJEON + A.M270_BOJEUNGGONGGEUMIBGEUM -  A.M270_BOJEUNGGONGGEUMIBJEONG),0)   G1C1D3       \n");   
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBJEON   + A.M270_BOGWANJEONGGIIBGEUM   -  A.M270_BOGWANJEONGGIIBJEONG),0)     G1C2D1       \n");   
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANIBJEON   + A.M270_BOGWANBYULDANIBGEUM   -  A.M270_BOGWANBYULDANIBJEONG),0)     G1C2D2       \n");   
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMIBJEON  + A.M270_BOGWANGONGGEUMIBGEUM  -  A.M270_BOGWANGONGGEUMIBJEONG),0)    G1C2D3       \n");   
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBJEON  + A.M270_JABJONGJEONGGIIBGEUM  -  A.M270_JABJONGJEONGGIIBJEONG),0)    G1C3D1       \n");   
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANIBJEON  + A.M270_JABJONGBYULDANIBGEUM  -  A.M270_JABJONGBYULDANIBJEONG),0)    G1C3D2       \n");   
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMIBJEON + A.M270_JABJONGGONGGEUMIBGEUM -  A.M270_JABJONGGONGGEUMIBJEONG),0)   G1C3D3       \n");   
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBJEON   + A.M270_BUGASEJEONGGIIBGEUM   -  A.M270_BUGASEJEONGGIIBJEONG),0)     G1C4D1       \n");   
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANIBJEON   + A.M270_BUGASEBYULDANIBGEUM   -  A.M270_BUGASEBYULDANIBJEONG),0)     G1C4D2       \n");   
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMIBJEON  + A.M270_BUGASEGONGGEUMIBGEUM  -  A.M270_BUGASEGONGGEUMIBJEONG),0)    G1C4D3       \n");   
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIJEON    + A.M270_BOJEUNGJEONGGIJIGEUB  -  A.M270_BOJEUNGJEONGGIJIJEONG),0)     G2C1D1    \n");      
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANJIJEON    + A.M270_BOJEUNGBYULDANJIGEUB  -  A.M270_BOJEUNGBYULDANJIJEONG),0)     G2C1D2    \n");      
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMJIJEON + A.M270_BOJEUNGGONGGEUMJIGEUB -  A.M270_BOJEUNGGONGGEUMJIJEONG),0)   G2C1D3       \n");   
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIJEON    + A.M270_BOGWANJEONGGIJIGEUB   -  A.M270_BOGWANJEONGGIJIJEONG),0)      G2C2D1     \n");     
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANJIJEON    + A.M270_BOGWANBYULDANJIGEUB   -  A.M270_BOGWANBYULDANJIJEONG),0)      G2C2D2     \n");     
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMJIJEON    + A.M270_BOGWANGONGGEUMJIGEUB  -  A.M270_BOGWANGONGGEUMJIJEONG),0)     G2C2D3    \n");        
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIJEON    + A.M270_JABJONGJEONGGIJIGEUB  -  A.M270_JABJONGJEONGGIJIJEONG),0)     G2C3D1    \n");      
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANJIJEON    + A.M270_JABJONGBYULDANJIGEUB  -  A.M270_JABJONGBYULDANJIJEONG),0)     G2C3D2    \n");     
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMJIJEON + A.M270_JABJONGGONGGEUMJIGEUB -  A.M270_JABJONGGONGGEUMJIJEONG),0)   G2C3D3       \n");     
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIJEON    + A.M270_BUGASEJEONGGIJIGEUB   -  A.M270_BUGASEJEONGGIJIJEONG),0)      G2C4D1     \n");     
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANJIJEON    + A.M270_BUGASEBYULDANJIGEUB   -  A.M270_BUGASEBYULDANJIJEONG),0)      G2C4D2     \n");     
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMJIJEON    + A.M270_BUGASEGONGGEUMJIGEUB  -  A.M270_BUGASEGONGGEUMJIJEONG),0)     G2C4D3    \n");      
																																																																	
		sb.append("        ,NVL(SUM((A.M270_BOJEUNGJEONGGIIBJEON + A.M270_BOJEUNGBYULDANIBJEON + A.M270_BOJEUNGGONGGEUMIBJEON) -               	    \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIJIJEON  + A.M270_BOJEUNGBYULDANJIJEON + A.M270_BOJEUNGGONGGEUMJIJEON)),0) BEFOREC1TOT         \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBJEON  -  A.M270_BOJEUNGJEONGGIJIJEON),0)  BEFOREC1D1                                     \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGBYULDANIBJEON  -  A.M270_BOJEUNGBYULDANJIJEON),0)  BEFOREC1D2                                     \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGGONGGEUMIBJEON -  A.M270_BOJEUNGGONGGEUMJIJEON),0) BEFOREC1D3                                     \n");
		sb.append("        ,NVL(SUM((A.M270_BOGWANJEONGGIIBJEON  + A.M270_BOGWANBYULDANIBJEON  + A.M270_BOGWANGONGGEUMIBJEON) -                	    \n");
		sb.append("                (A.M270_BOGWANJEONGGIJIJEON   + A.M270_BOGWANBYULDANJIJEON     + A.M270_BOGWANGONGGEUMJIJEON)),0) BEFOREC2TOT    \n");    
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBJEON   -  A.M270_BOGWANJEONGGIJIJEON),0)   BEFOREC2D1                                     \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANBYULDANIBJEON   -  A.M270_BOGWANBYULDANJIJEON),0)   BEFOREC2D2                                     \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANGONGGEUMIBJEON  -  A.M270_BOGWANGONGGEUMJIJEON),0)  BEFOREC2D3                                     \n");
		sb.append("        ,NVL(SUM((A.M270_JABJONGJEONGGIIBJEON + A.M270_JABJONGBYULDANIBJEON + A.M270_JABJONGGONGGEUMIBJEON) -               	    \n");
		sb.append("             (A.M270_JABJONGJEONGGIJIJEON  + A.M270_JABJONGBYULDANJIJEON + A.M270_JABJONGGONGGEUMJIJEON)),0) BEFOREC3TOT         \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBJEON  -  A.M270_JABJONGJEONGGIJIJEON),0)  BEFOREC3D1                                     \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGBYULDANIBJEON  -  A.M270_JABJONGBYULDANJIJEON),0)  BEFOREC3D2                                     \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGGONGGEUMIBJEON -  A.M270_JABJONGGONGGEUMJIJEON),0) BEFOREC3D3                                     \n");
		sb.append("        ,NVL(SUM((A.M270_BUGASEJEONGGIIBJEON  + A.M270_BUGASEBYULDANIBJEON  + A.M270_BUGASEGONGGEUMIBJEON) -                	    \n");
		sb.append("                (A.M270_BUGASEJEONGGIJIJEON    + A.M270_BUGASEBYULDANJIJEON  + A.M270_BUGASEGONGGEUMJIJEON)),0) BEFOREC4TOT      \n");     
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBJEON   -  A.M270_BUGASEJEONGGIJIJEON),0)   BEFOREC4D1                                     \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEBYULDANIBJEON   -  A.M270_BUGASEBYULDANJIJEON),0)   BEFOREC4D2                                     \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEGONGGEUMIBJEON  -  A.M270_BUGASEGONGGEUMJIJEON),0)  BEFOREC4D3                                     \n");
		
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBJEON + A.M270_BOJEUNGBYULDANIBJEON + A.M270_BOJEUNGGONGGEUMIBJEON),0) G1C1TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBJEON  + A.M270_BOGWANBYULDANIBJEON  + A.M270_BOGWANGONGGEUMIBJEON),0)  G1C2TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBJEON + A.M270_JABJONGBYULDANIBJEON + A.M270_JABJONGGONGGEUMIBJEON),0) G1C3TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBJEON  + A.M270_BUGASEBYULDANIBJEON  + A.M270_BUGASEGONGGEUMIBJEON),0)  G1C4TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIJEON + A.M270_BOJEUNGBYULDANJIJEON + A.M270_BOJEUNGGONGGEUMJIJEON),0) G2C1TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIJEON  + A.M270_BOGWANBYULDANJIJEON  + A.M270_BOGWANGONGGEUMJIJEON),0)  G2C2TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIJEON + A.M270_JABJONGBYULDANJIJEON + A.M270_JABJONGGONGGEUMJIJEON),0) G2C3TOT            \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIJEON  + A.M270_BUGASEBYULDANJIJEON  + A.M270_BUGASEGONGGEUMJIJEON),0)  G2C4TOT            \n");
		
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBGEUM + A.M270_BOJEUNGBYULDANIBGEUM  + A.M270_BOJEUNGGONGGEUMIBGEUM),0) G1C1IB            \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBGEUM  + A.M270_BOGWANBYULDANIBGEUM   + A.M270_BOGWANGONGGEUMIBGEUM),0)  G1C2IB            \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBGEUM + A.M270_JABJONGBYULDANIBGEUM  + A.M270_JABJONGGONGGEUMIBGEUM),0) G1C3IB            \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBGEUM  + A.M270_BUGASEBYULDANIBGEUM   + A.M270_BUGASEGONGGEUMIBGEUM),0)  G1C4IB            \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIGEUB + A.M270_BOJEUNGBYULDANJIGEUB  + A.M270_BOJEUNGGONGGEUMJIGEUB),0) G2C1IB            \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIGEUB  + A.M270_BOGWANBYULDANJIGEUB   + A.M270_BOGWANGONGGEUMJIGEUB),0)  G2C2IB            \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIGEUB + A.M270_JABJONGBYULDANJIGEUB  + A.M270_JABJONGGONGGEUMJIGEUB),0) G2C3IB            \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIGEUB  + A.M270_BUGASEBYULDANJIGEUB   + A.M270_BUGASEGONGGEUMJIGEUB),0)  G2C4IB            \n");
		
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIIBJEONG + A.M270_BOJEUNGBYULDANIBJEONG + A.M270_BOJEUNGGONGGEUMIBJEONG),0) G1C1BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIIBJEONG  + A.M270_BOGWANBYULDANIBJEONG  + A.M270_BOGWANGONGGEUMIBJEONG),0)  G1C2BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIIBJEONG + A.M270_JABJONGBYULDANIBJEONG + A.M270_JABJONGGONGGEUMIBJEONG),0) G1C3BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIIBJEONG  + A.M270_BUGASEBYULDANIBJEONG  + A.M270_BUGASEGONGGEUMIBJEONG),0)  G1C4BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_BOJEUNGJEONGGIJIJEONG + A.M270_BOJEUNGBYULDANJIJEONG + A.M270_BOJEUNGGONGGEUMJIJEONG),0) G2C1BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_BOGWANJEONGGIJIJEONG  + A.M270_BOGWANBYULDANJIJEONG  + A.M270_BOGWANGONGGEUMJIJEONG),0)  G2C2BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_JABJONGJEONGGIJIJEONG + A.M270_JABJONGBYULDANJIJEONG + A.M270_JABJONGGONGGEUMJIJEONG),0) G2C3BAN         \n");
		sb.append("        ,NVL(SUM(A.M270_BUGASEJEONGGIJIJEONG  + A.M270_BUGASEBYULDANJIJEONG  + A.M270_BUGASEGONGGEUMJIJEONG),0)  G2C4BAN         \n");
		
		sb.append("        ,NVL(SUM((A.M270_BOJEUNGJEONGGIIBGEUM + A.M270_BOJEUNGBYULDANIBGEUM  + A.M270_BOJEUNGGONGGEUMIBGEUM)+                    \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIIBJEON + A.M270_BOJEUNGBYULDANIBJEON + A.M270_BOJEUNGGONGGEUMIBJEON) -                        \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIIBJEONG + A.M270_BOJEUNGBYULDANIBJEONG + A.M270_BOJEUNGGONGGEUMIBJEONG)),0) G1C1NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_BOGWANJEONGGIIBGEUM   + A.M270_BOGWANBYULDANIBGEUM   + A.M270_BOGWANGONGGEUMIBGEUM)+                    \n");
		sb.append("             (A.M270_BOGWANJEONGGIIBJEON  + A.M270_BOGWANBYULDANIBJEON  + A.M270_BOGWANGONGGEUMIBJEON) -                         \n");
		sb.append("             (A.M270_BOGWANJEONGGIIBJEONG  + A.M270_BOGWANBYULDANIBJEONG  + A.M270_BOGWANGONGGEUMIBJEONG)),0)  G1C2NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_JABJONGJEONGGIIBGEUM  + A.M270_JABJONGBYULDANIBGEUM  + A.M270_JABJONGGONGGEUMIBGEUM)+                   \n");
		sb.append("             (A.M270_JABJONGJEONGGIIBJEON + A.M270_JABJONGBYULDANIBJEON + A.M270_JABJONGGONGGEUMIBJEON)-                         \n");
		sb.append("             (A.M270_JABJONGJEONGGIIBJEONG + A.M270_JABJONGBYULDANIBJEONG + A.M270_JABJONGGONGGEUMIBJEONG)),0) G1C3NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_BUGASEJEONGGIIBGEUM   + A.M270_BUGASEBYULDANIBGEUM   + A.M270_BUGASEGONGGEUMIBGEUM) +                   \n");
		sb.append("             (A.M270_BUGASEJEONGGIIBJEON  + A.M270_BUGASEBYULDANIBJEON  + A.M270_BUGASEGONGGEUMIBJEON) -                         \n");
		sb.append("             (A.M270_BUGASEJEONGGIIBJEONG  + A.M270_BUGASEBYULDANIBJEONG  + A.M270_BUGASEGONGGEUMIBJEONG)),0)  G1C4NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_BOJEUNGJEONGGIJIGEUB  + A.M270_BOJEUNGBYULDANJIGEUB  + A.M270_BOJEUNGGONGGEUMJIGEUB)+                   \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIJIJEON + A.M270_BOJEUNGBYULDANJIJEON + A.M270_BOJEUNGGONGGEUMJIJEON) -                        \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIJIJEONG + A.M270_BOJEUNGBYULDANJIJEONG + A.M270_BOJEUNGGONGGEUMJIJEONG)),0) G2C1NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_BOGWANJEONGGIJIGEUB   + A.M270_BOGWANBYULDANJIGEUB   + A.M270_BOGWANGONGGEUMJIGEUB)+                    \n");
		sb.append("             (A.M270_BOGWANJEONGGIJIJEON  + A.M270_BOGWANBYULDANJIJEON     + A.M270_BOGWANGONGGEUMJIJEON) -                      \n");
		sb.append("             (A.M270_BOGWANJEONGGIJIJEONG  + A.M270_BOGWANBYULDANJIJEONG  + A.M270_BOGWANGONGGEUMJIJEONG)),0)  G2C2NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_JABJONGJEONGGIJIGEUB  + A.M270_JABJONGBYULDANJIGEUB  + A.M270_JABJONGGONGGEUMJIGEUB)+                   \n");
		sb.append("             (A.M270_JABJONGJEONGGIJIJEON + A.M270_JABJONGBYULDANJIJEON + A.M270_JABJONGGONGGEUMJIJEON) -                        \n");
		sb.append("             (A.M270_JABJONGJEONGGIJIJEONG + A.M270_JABJONGBYULDANJIJEONG + A.M270_JABJONGGONGGEUMJIJEONG)),0) G2C3NUGYE         \n");
		sb.append("        ,NVL(SUM((A.M270_BUGASEJEONGGIJIGEUB   + A.M270_BUGASEBYULDANJIGEUB   + A.M270_BUGASEGONGGEUMJIGEUB)+                    \n");
		sb.append("             (A.M270_BUGASEJEONGGIJIJEON     + A.M270_BUGASEBYULDANJIJEON  + A.M270_BUGASEGONGGEUMJIJEON)  -                     \n");
		sb.append("             (A.M270_BUGASEJEONGGIJIJEONG  + A.M270_BUGASEBYULDANJIJEONG  + A.M270_BUGASEGONGGEUMJIJEONG)),0)  G2C4NUGYE         \n");
		
		sb.append("        ,NVL(SUM((A.M270_BOJEUNGJEONGGIIBJEON  + A.M270_BOJEUNGJEONGGIIBGEUM  -  A.M270_BOJEUNGJEONGGIIBJEONG) -                 \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIJIJEON    + A.M270_BOJEUNGJEONGGIJIGEUB  -  A.M270_BOJEUNGJEONGGIJIJEONG)),0)    G1G2C1D1     \n"); 
		sb.append("        ,NVL(SUM((A.M270_BOJEUNGBYULDANIBJEON  + A.M270_BOJEUNGBYULDANIBGEUM  -  A.M270_BOJEUNGBYULDANIBJEONG) -                 \n");
		sb.append("             (A.M270_BOJEUNGBYULDANJIJEON    + A.M270_BOJEUNGBYULDANJIGEUB  -  A.M270_BOJEUNGBYULDANJIJEONG)),0)    G1G2C1D2     \n"); 
		sb.append("        ,NVL(SUM((A.M270_BOJEUNGGONGGEUMIBJEON + A.M270_BOJEUNGGONGGEUMIBGEUM -  A.M270_BOJEUNGGONGGEUMIBJEONG)    -             \n");  
		sb.append("             (A.M270_BOJEUNGGONGGEUMJIJEON + A.M270_BOJEUNGGONGGEUMJIGEUB -  A.M270_BOJEUNGGONGGEUMJIJEONG)),0)     G1G2C1D3     \n");
		sb.append("        ,NVL(SUM((A.M270_BOGWANJEONGGIIBJEON   + A.M270_BOGWANJEONGGIIBGEUM   -  A.M270_BOGWANJEONGGIIBJEONG) -                  \n");
		sb.append("             (A.M270_BOGWANJEONGGIJIJEON      + A.M270_BOGWANJEONGGIJIGEUB   -  A.M270_BOGWANJEONGGIJIJEONG)),0)       G1G2C2D1  \n");  
		sb.append("        ,NVL(SUM((A.M270_BOGWANBYULDANIBJEON   + A.M270_BOGWANBYULDANIBGEUM   -  A.M270_BOGWANBYULDANIBJEONG)-                   \n");
		sb.append("             (A.M270_BOGWANBYULDANJIJEON      + A.M270_BOGWANBYULDANJIGEUB   -  A.M270_BOGWANBYULDANJIJEONG)),0)       G1G2C2D2  \n");  
		sb.append("        ,NVL(SUM((A.M270_BOGWANGONGGEUMIBJEON  + A.M270_BOGWANGONGGEUMIBGEUM  -  A.M270_BOGWANGONGGEUMIBJEONG) -                 \n");
		sb.append("             (A.M270_BOGWANGONGGEUMJIJEON    + A.M270_BOGWANGONGGEUMJIGEUB  -  A.M270_BOGWANGONGGEUMJIJEONG)),0)    G1G2C2D3     \n");   
		sb.append("        ,NVL(SUM((A.M270_JABJONGJEONGGIIBJEON  + A.M270_JABJONGJEONGGIIBGEUM  -  A.M270_JABJONGJEONGGIIBJEONG)-                  \n");
		sb.append("             (A.M270_JABJONGJEONGGIJIJEON    + A.M270_JABJONGJEONGGIJIGEUB  -  A.M270_JABJONGJEONGGIJIJEONG)),0)    G1G2C3D1     \n"); 
		sb.append("        ,NVL(SUM((A.M270_JABJONGBYULDANIBJEON  + A.M270_JABJONGBYULDANIBGEUM  -  A.M270_JABJONGBYULDANIBJEONG)-                  \n");
		sb.append("             (A.M270_JABJONGBYULDANJIJEON    + A.M270_JABJONGBYULDANJIGEUB  -  A.M270_JABJONGBYULDANJIJEONG)),0)   G1G2C3D2      \n");
		sb.append("        ,NVL(SUM((A.M270_JABJONGGONGGEUMIBJEON + A.M270_JABJONGGONGGEUMIBGEUM -  A.M270_JABJONGGONGGEUMIBJEONG)-                 \n");
		sb.append("             (A.M270_JABJONGGONGGEUMJIJEON + A.M270_JABJONGGONGGEUMJIGEUB -  A.M270_JABJONGGONGGEUMJIJEONG)),0)     G1G2C3D3     \n"); 
		sb.append("        ,NVL(SUM((A.M270_BUGASEJEONGGIIBJEON   + A.M270_BUGASEJEONGGIIBGEUM   -  A.M270_BUGASEJEONGGIIBJEONG)-                   \n");
		sb.append("             (A.M270_BUGASEJEONGGIJIJEON      + A.M270_BUGASEJEONGGIJIGEUB   -  A.M270_BUGASEJEONGGIJIJEONG)),0)       G1G2C4D1  \n");  
		sb.append("        ,NVL(SUM((A.M270_BUGASEBYULDANIBJEON   + A.M270_BUGASEBYULDANIBGEUM   -  A.M270_BUGASEBYULDANIBJEONG)-                   \n");
		sb.append("             (A.M270_BUGASEBYULDANJIJEON      + A.M270_BUGASEBYULDANJIGEUB   -  A.M270_BUGASEBYULDANJIJEONG)),0)       G1G2C4D2  \n");  
		sb.append("        ,NVL(SUM((A.M270_BUGASEGONGGEUMIBJEON  + A.M270_BUGASEGONGGEUMIBGEUM  -  A.M270_BUGASEGONGGEUMIBJEONG)-                  \n");
		sb.append("             (A.M270_BUGASEGONGGEUMJIJEON    + A.M270_BUGASEGONGGEUMJIGEUB  -  A.M270_BUGASEGONGGEUMJIJEONG)),0)    G1G2C4D3     \n"); 
		
		sb.append("        ,NVL(SUM(((A.M270_BOJEUNGJEONGGIIBGEUM + A.M270_BOJEUNGBYULDANIBGEUM  + A.M270_BOJEUNGGONGGEUMIBGEUM)+                   \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIIBJEON + A.M270_BOJEUNGBYULDANIBJEON + A.M270_BOJEUNGGONGGEUMIBJEON) -                        \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIIBJEONG + A.M270_BOJEUNGBYULDANIBJEONG + A.M270_BOJEUNGGONGGEUMIBJEONG))-                     \n");
		sb.append("             ((A.M270_BOJEUNGJEONGGIJIGEUB  + A.M270_BOJEUNGBYULDANJIGEUB  + A.M270_BOJEUNGGONGGEUMJIGEUB)+                      \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIJIJEON + A.M270_BOJEUNGBYULDANJIJEON + A.M270_BOJEUNGGONGGEUMJIJEON) -                        \n");
		sb.append("             (A.M270_BOJEUNGJEONGGIJIJEONG + A.M270_BOJEUNGBYULDANJIJEONG + A.M270_BOJEUNGGONGGEUMJIJEONG))),0) G1G2C1NUGYE      \n");
		sb.append("        ,NVL(SUM(((A.M270_BOGWANJEONGGIIBGEUM   + A.M270_BOGWANBYULDANIBGEUM   + A.M270_BOGWANGONGGEUMIBGEUM)+                   \n");
		sb.append("             (A.M270_BOGWANJEONGGIIBJEON  + A.M270_BOGWANBYULDANIBJEON  + A.M270_BOGWANGONGGEUMIBJEON) -                         \n");
		sb.append("             (A.M270_BOGWANJEONGGIIBJEONG  + A.M270_BOGWANBYULDANIBJEONG  + A.M270_BOGWANGONGGEUMIBJEONG))-                      \n");
		sb.append("             ((A.M270_BOGWANJEONGGIJIGEUB   + A.M270_BOGWANBYULDANJIGEUB   + A.M270_BOGWANGONGGEUMJIGEUB)+                       \n");
		sb.append("             (A.M270_BOGWANJEONGGIJIJEON  + A.M270_BOGWANBYULDANJIJEON     + A.M270_BOGWANGONGGEUMJIJEON) -                      \n");
		sb.append("             (A.M270_BOGWANJEONGGIJIJEONG  + A.M270_BOGWANBYULDANJIJEONG  + A.M270_BOGWANGONGGEUMJIJEONG))),0) G1G2C2NUGYE       \n");
		sb.append("        ,NVL(SUM(((A.M270_JABJONGJEONGGIIBGEUM  + A.M270_JABJONGBYULDANIBGEUM  + A.M270_JABJONGGONGGEUMIBGEUM)+                  \n");
		sb.append("             (A.M270_JABJONGJEONGGIIBJEON + A.M270_JABJONGBYULDANIBJEON + A.M270_JABJONGGONGGEUMIBJEON)-                         \n");
		sb.append("             (A.M270_JABJONGJEONGGIIBJEONG + A.M270_JABJONGBYULDANIBJEONG + A.M270_JABJONGGONGGEUMIBJEONG)) -                    \n");
		sb.append("             ((A.M270_JABJONGJEONGGIJIGEUB  + A.M270_JABJONGBYULDANJIGEUB  + A.M270_JABJONGGONGGEUMJIGEUB)+                      \n");
		sb.append("             (A.M270_JABJONGJEONGGIJIJEON + A.M270_JABJONGBYULDANJIJEON + A.M270_JABJONGGONGGEUMJIJEON) -                        \n");
		sb.append("             (A.M270_JABJONGJEONGGIJIJEONG + A.M270_JABJONGBYULDANJIJEONG + A.M270_JABJONGGONGGEUMJIJEONG))),0) G1G2C3NUGYE      \n");
		sb.append("        ,NVL(SUM(((A.M270_BUGASEJEONGGIIBGEUM   + A.M270_BUGASEBYULDANIBGEUM   + A.M270_BUGASEGONGGEUMIBGEUM) +                  \n");
		sb.append("              (A.M270_BUGASEJEONGGIIBJEON  + A.M270_BUGASEBYULDANIBJEON  + A.M270_BUGASEGONGGEUMIBJEON) -                        \n");
		sb.append("              (A.M270_BUGASEJEONGGIIBJEONG  + A.M270_BUGASEBYULDANIBJEONG  + A.M270_BUGASEGONGGEUMIBJEONG))-                     \n");
		sb.append("             ((A.M270_BUGASEJEONGGIJIGEUB   + A.M270_BUGASEBYULDANJIGEUB   + A.M270_BUGASEGONGGEUMJIGEUB)+                       \n");
		sb.append("              (A.M270_BUGASEJEONGGIJIJEON     + A.M270_BUGASEBYULDANJIJEON  + A.M270_BUGASEGONGGEUMJIJEON)  -                    \n");
		sb.append("              (A.M270_BUGASEJEONGGIJIJEONG  + A.M270_BUGASEBYULDANJIJEONG  + A.M270_BUGASEGONGGEUMJIJEONG))),0) G1G2C4NUGYE      \n");
		sb.append("   FROM  M270_TAXCASHDAY_T A 												                                                                            \n");
		sb.append("        ,M390_USESEMOKCODE_T B												                                                                            \n");
		sb.append("        ,M360_ACCCODE_T C													                                                                              \n");
		sb.append("  WHERE A.M270_YEAR (+)= B.M390_YEAR												                                                                      \n");
		sb.append("    AND A.M270_ACCCODE(+) = B.M390_ACCCODE											                                                                  \n");
        sb.append("    AND C.M360_YEAR = B.M390_YEAR												                                                                      \n");
		sb.append("    AND C.M360_ACCCODE = B.M390_ACCCODE											                                                                    \n");
		sb.append("    AND C.M360_ACCGUBUN = B.M390_ACCGUBUN											                                                                  \n");
		sb.append("    AND B.M390_WORKGUBUN = '4'												                                                                            \n");
		sb.append("    AND B.M390_ACCGUBUN  = 'D'												                                                                            \n");
		sb.append("    AND B.M390_YEAR = ?                                                                                                       \n"); 
		sb.append("    AND A.M270_DATE(+) = ?                                                                                                       \n"); 
    if(!paramInfo.getString("part_code").equals("")){
      sb.append("	 AND B.M390_PARTCODE = ?                                                                                                      \n"); 
    }
    if(!paramInfo.getString("acc_code").equals("")){
      sb.append("	AND B.M390_ACCCODE = ?                                                                                                        \n"); 
    }
    sb.append(" GROUP BY B.M390_ACCCODE,C.M360_ACCNAME ,A.M270_YEAR, A.M270_DATE                                                                \n"); 
    sb.append("		            ) ORG                                                                                                             \n");
		sb.append("		      WHERE ROWNUM <= ?                                                                                                       \n");
	  sb.append("       ) LIST                                                                                                                    \n");
		sb.append(" WHERE LIST.RN > ?                                                                                                               \n");
    	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		
		if(!paramInfo.getString("part_code").equals("")){
		parameters.setString(idx++, paramInfo.getString("part_code"));
		}
    if(!paramInfo.getString("acc_code").equals("")){
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
	  parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));
		
		return template.getList(conn, parameters);
	}


    /* 보고서 총 건수 조회 */
	public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
    
		sb.append(" SELECT  SUM(COUNT(DISTINCT B.M390_ACCCODE)) CNT                   \n");                   
        sb.append("   FROM  M270_TAXCASHDAY_T A                                       \n");                                                                                               
        sb.append("        ,M390_USESEMOKCODE_T B                                     \n");                                                                                                
        sb.append("        ,M360_ACCCODE_T C                                          \n");                    
    	sb.append(" WHERE A.M270_YEAR (+)= B.M390_YEAR                                \n");                                                                                               
        sb.append("   AND A.M270_ACCCODE(+) = B.M390_ACCCODE                          \n");                                                                                             
        sb.append("   AND C.M360_ACCCODE = B.M390_ACCCODE                             \n");                                                                                            
        sb.append("   AND C.M360_ACCGUBUN = B.M390_ACCGUBUN                           \n");                                                                                            
    	sb.append("   AND C.M360_YEAR = B.M390_YEAR								      \n");
        sb.append("   AND B.M390_WORKGUBUN = '4'                                      \n");                                                                                               
        sb.append("   AND B.M390_ACCGUBUN  = 'D'                                      \n");                                                                                               
        sb.append("   AND B.M390_YEAR = ?                                          \n");                                                                    
        sb.append("   AND A.M270_DATE(+) = ?                                          \n");                            
  	
		if(!paramInfo.getString("part_code").equals("")){
            sb.append("   AND B.M390_PARTCODE = ?                                       \n");
        }
        if(!paramInfo.getString("acc_code").equals("")){
            sb.append("   AND B.M390_ACCCODE = ?                                        \n");
        }                                      
                                                                                 
        sb.append(" GROUP BY B.M390_ACCCODE,C.M360_ACCNAME ,A.M270_YEAR, A.M270_DATE  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		if(!paramInfo.getString("part_code").equals("")){
		    parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        if(!paramInfo.getString("acc_code").equals("")){
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		return template.getData(conn, parameters);
	}
}
