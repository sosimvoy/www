/* textarea 글자수 체크  */
function fc_chk_byte(aro_name,ari_max)
    {

    var ls_str     = aro_name.value; // 이벤트가 일어난 컨트롤의 value 값
    var li_str_len = ls_str.length;  // 전체길이
    
    // 변수초기화
    var li_max      = ari_max; // 제한할 글자수 크기
    var i           = 0;  // for문에 사용
    var li_byte     = 0;  // 한글일경우는 2 그밗에는 1을 더함
    var li_len      = 0;  // substring하기 위해서 사용
    var ls_one_char = ""; // 한글자씩 검사한다
    var ls_str2     = ""; // 글자수를 초과하면 제한할수 글자전까지만 보여준다.
    
    for(i=0; i< li_str_len; i++)
    {
        // 한글자추출
        ls_one_char = ls_str.charAt(i);
        
        // 한글이면 2를 더한다.
        if (escape(ls_one_char).length > 4)
        {
            li_byte += 2;
        }
        // 그밗의 경우는 1을 더한다.
        else
        {
            li_byte++;
        }
        
        // 전체 크기가 li_max를 넘지않으면
        if(li_byte <= li_max)
        {
            li_len = i + 1;
        }
    }
    // 전체길이를 초과하면
    if(li_byte > li_max)
    {
        alert( li_max + " 글자를 초과 입력할수 없습니다.");
        aro_name.focus(); 
        return false;
    }
    return true;

}