package com.etax.trans.data;

import java.util.LinkedHashMap;

public class GiroBody extends _AbstractData {
    
    public GiroBody() {
        LinkedHashMap<String, String[]> fieldset = new LinkedHashMap<String, String[]>();

        fieldset.put("data01", new String[] { "업무구분"                                , "6"   , "C" });
        fieldset.put("data02", new String[] { "데이터구분"                              , "2"   , "C" });
        fieldset.put("data03", new String[] { "일련번호"                                , "7"   , "C" });
        fieldset.put("data04", new String[] { "발행기관분류"                            , "2"   , "C" });
        fieldset.put("data05", new String[] { "지로번호"                                , "7"   , "C" });
        fieldset.put("data06", new String[] { "금고은행코드"                            , "3"   , "C" });
        fieldset.put("data07", new String[] { "납부의무자 주민(법인,사업자)등록번호"    , "13"  , "C" });
        fieldset.put("data08", new String[] { "납세번호"                                , "32"  , "C" });
        fieldset.put("data09", new String[] { "전자납부번호"                            , "19"  , "C" });
        fieldset.put("data10", new String[] { "수납금액"                                , "12"  , "C" });
        fieldset.put("data11", new String[] { "출금은행점별코드"                        , "7"   , "C" });// 신용카드 납부인 경우, 카드사코드(3)+"0000"를 SET한다.
        fieldset.put("data12", new String[] { "납부자 주민(법인,사업자)등록번호"        , "13"  , "C" });
        fieldset.put("data13", new String[] { "납부이용시스템"                          , "1"   , "C" });
        fieldset.put("data14", new String[] { "납부형태구분"                            , "1"   , "C" });// Q:즉시납부, C:총괄납부, R:예약납부, D:장애수납
        fieldset.put("data15", new String[] { "신용카드승인번호"                        , "12"  , "C" });
        fieldset.put("data16", new String[] { "신용카드할부개월수"                      , "2"   , "C" });// 포인트 사용시: (60+ 할부개월)로 세팅 ※ 포인트 사용 일시불: 60사용(61은 사용하지 않음)
        fieldset.put("data17", new String[] { "납기내금액"                              , "12"  , "C" });
        fieldset.put("data18", new String[] { "납기후금액"                              , "12"  , "C" });
        fieldset.put("data19", new String[] { "납기일(납기내)"                          , "6"   , "C" });
        fieldset.put("data20", new String[] { "상세세목1금액"                           , "12"  , "C" });
        fieldset.put("data21", new String[] { "상세세목2금액"                           , "12"  , "C" });
        fieldset.put("data22", new String[] { "상세세목3금액"                           , "12"  , "C" });
        fieldset.put("data23", new String[] { "상세세목4금액"                           , "12"  , "C" });
        fieldset.put("data24", new String[] { "상세세목5금액"                           , "12"  , "C" });
        fieldset.put("data25", new String[] { "수표포함여부"                            , "1"   , "C" });
        fieldset.put("data26", new String[] { "대량납부고유번호"                        , "19"  , "C" });
        fieldset.put("data27", new String[] { "취급은행점별코드"                        , "7"   , "C" });
        fieldset.put("data28", new String[] { "창구단말번호"                            , "20"  , "C" });// 장애시 수납(D)인 경우만 사용
        fieldset.put("data29", new String[] { "FILLER"                                  , "24"  , "C" });

        initMsg("지방세-금융결제원", 0, 300, fieldset);
    }

}
