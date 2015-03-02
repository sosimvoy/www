/**********************************************************************************
 * 화 일 이 름 : StringUtil.java
 * 최근 변경일 : 2007-04-06
 * 최초 작성자 :
 * 최근 변경자 : 바리두스
 * 설       명 : 전문통신 유틸리티
 **********************************************************************************/

package com.etax.util;

import java.io.*;
import java.text.*;
import java.util.*;

import com.initech.safedb.SimpleSafeDB;
import com.initech.safedb.common.SafeDBException;

import java.security.MessageDigest;

/* 문자열에 조작에 관련된 유용한 Method를 정의해 놓은 유틸리티 클래스이다. */
public class StringUtil {

	final static int rawOffset = 9 * 60 * 60 * 1000;

    /* 암호화 */
    public static String encrypt(String tablename, String columnname, String data)	throws SafeDBException	{
		SimpleSafeDB ssdb = SimpleSafeDB.getInstance();
    if ("".equals(data)) {
      return "";
    }else {
		  if(ssdb.login()) {
			  byte[] plainData = data.getBytes();
				
			  byte[] encryptedData = ssdb.encrypt("SAFEPOLICY", tablename, columnname, plainData);
			  return new String(encryptedData);
		  }		
    }
		return null;
	}

	 /* 복호화 */
    public static String decrypt(String tablename, String columnname, String data)	throws SafeDBException	{
		SimpleSafeDB ssdb = SimpleSafeDB.getInstance();
    if ("".equals(data)) {
      return "";
    }else {
  		if(ssdb.login()) {
	  		byte[] encryptedData = data.getBytes();
			
		  	byte[] decryptedData = ssdb.decrypt("SAFEPOLICY", tablename, columnname, encryptedData);
			  return new String(decryptedData);
		  }		
    }
		return null;
	}
	/* 지정한 문자를 지정된 길이만큼 반복하여 문자열을 생성한 후, 반환한다.
	 * @param letter 반복할 문자
	 * @param length 반복할 길이
	 * @return 지정한 문자를 지정된 길이만큼 반복하여 생성된 문자열
	 */
	public static String repeat(char letter, int length) {
		StringBuffer buffer = new StringBuffer(length);

		for (int i = 0; i < length; ++i) {
			buffer.append(letter);
		}
		return buffer.toString();
	}

	/* 지정한 문자열을 지정된 길이만큼 반복하여 문자열을 생성한 후, 반환한다.
	 * @param text 반복할 문자열
	 * @param length 반복할 길이
	 * @return 지정한 문자열을 지정된 길이만큼 반복하여 생성된 문자열
	 */
	public static String repeat(String text, int length) {
		StringBuffer buffer = new StringBuffer(text.length() * length);

		while (length-- > 0) {
			buffer.append(text);
		}
		return buffer.toString();
	}

	/* 입력 문자열 중에 Backslash ('\')로 된 Double-Quotes 문자열이 있을 경우 Double-Quotes로 둘러 싼 문자열을 반환한다.
	 * @param text Double-Quotes로 둘러 쌀 문자열
	 * @return Double-Quotes로 둘러 싼 문자열
	 * @throws NullPointerException 입력 문자열이 NULL일 경우 발생
	 */
	public static String quote(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		final char quote = '"';
		StringBuffer buffer = new StringBuffer();
		buffer.append(quote);

		for (int i = 0; i < text.length(); ++i) {
			char letter = text.charAt(i);

			if (letter == quote) {
				buffer.append('\\');
			}
			buffer.append(letter);
		}
		buffer.append(quote);
		return buffer.toString();
	}

	/* 입력 문자열 중에 Quote가 있는지 검사한다.
	 * @param text 검사할 문자열
	 * @return Quote 검사 결과
	 * @throws NullPointerException 입력 문자열이 NULL일 경우 발생
	 */
	public static boolean search(String text) {
		boolean result = false;

		if (text == null) {
			throw new NullPointerException();
		}
		final char quote = '\'';

		for (int i = 0; i < text.length(); i++) {
			char letter = text.charAt(i);

			if (letter == quote) {
				result = true;
			}
		}
		return result;
	}

	/* 입력 문자열의 왼쪽으로 지정된 문자를 지정된 길이만큼 채워서 반환한다.
	 * <p>사용 예제,</p>
	 * <p><code>leftFill("12", 4, '0')</code> returns "0012", while</p>
	 * <p><code>leftFill("12345", 4, '0')</code> returns "12345".</p>
	 * @param text 가공할 문자열
	 * @param length 반복할 길이
	 * @param letter 반복할 문자
	 * @return 왼쪽으로 지정된 문자를 지정된 길이만큼 채운 문자열
	 * @throws NullPointerException 입력 문자열이 NULL일 경우 발생
	 * @throws IllegalArgumentException 길이가 음수일 경우 발생
	 */
	public static String leftFill(String text, int length, char letter) {
		if (text == null) {
			throw new NullPointerException();
		}

    if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative");
		}
    StringBuffer buffer = new StringBuffer();
		length -= text.length();

    while(length-- > 0) {
			buffer.append(letter);
		}
    buffer.append(text);
		return buffer.toString();
	}

 
	public static String rightFill(String text, int length, char letter) {
		if (text == null) {
			throw new NullPointerException();
		}

    if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative");
		}
    StringBuffer buffer = new StringBuffer();
    buffer.append(text);
		length -= text.length();

    while(length-- > 0) {
			buffer.append(letter);
		}
		return buffer.toString();
	}


	/* 입력 문자열의 왼쪽으로 '0'을 지정된 길이만큼 채워 반환한다. leftFill() Method를 사용하는 것과 동일하다.
	 * @param text 가공할 문자열
	 * @param length 반복할 길이
	 * @return 왼쪽으로 '0'을 지정된 길이만큼 채운 문자열
	 * @see com.moneta.bill.util.TextHandler#leftFill
	 */
	public static String zeroFill(String text, int length) {
		return leftFill(text, length, '0');
	}

	public static String zeroFill(int val, int length) {
    String text = String.valueOf(val);
		return leftFill(text, length, '0');
	}

	public static String zeroFill(long val, int length) {
    String text = String.valueOf(val);
		return leftFill(text, length, '0');
	}

  public static String blankFill(String text, int length) {
    return uni2ksc(rightFill(ksc2uni(text), length, ' '));
  }
	

	public static String hexFill(String text, int length) {
    if (text == null) text = "";
    return rightFill(toFullChar(text), length/2, '　');
  }

  //정각문자 변환
  public static String toFullChar(String src)  {
        // 입력된 스트링이 null 이면 null 을 리턴
        if (src == null)
            return null;
        // 변환된 문자들을 쌓아놓을 StringBuffer 를 마련한다
        StringBuffer strBuf = new StringBuffer();
        char c = 0;
        int nSrcLength = src.length();
        for (int i = 0; i < nSrcLength; i++)  {
            c = src.charAt(i);
            //영문이거나 특수 문자 일경우.
            if (c >= 0x21 && c <= 0x7e)
            {
                c += 0xfee0;
            }
            //공백일경우
            else if (c == 0x20)
            {
                c = 0x3000;
            }
            // 문자열 버퍼에 변환된 문자를 쌓는다
            strBuf.append(c);
        }
        return strBuf.toString();
    }

	/* 입력 문자열에서 앞뒤의 Whitespace(<code>Character.isWhitespace</code>에
	 * 허용된 코드)를 잘라 반환한다. 이것은 <code>String.trim</code> Method와
	 * 같지 않다. <code>String.trim</code>은 오직 공백(Space) 코드의 ASCII
	 * 값만을 Whitespace로 인식하기 때문이다.
	 * @param text Whitespace를 잘라낼 문자열
	 * @return 앞뒤 Whitespace가 제거된 문자열
	 * @throws NullPointerException 입력 문자열이 NULL일 경우 발생
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		if (text.length() == 0) {
			return text;
		}
		int left = 0;

		while (left < text.length() && Character.isWhitespace(text.charAt(left))) {
			++left;
		}
		int right = text.length();

		while (right > left	&& Character.isWhitespace(text.charAt(right - 1))) {
			--right;
		}
		return text.substring(left, right);
	}

	/* 입력 문자열 중에서 Whitespace(<code>Character.isWhitespace</code>에 허용된 코드)를 공백(Space)으로 바꿔 반환한다.
	 * @param text Whitespace를 제거할 문자열
	 * @return Whitespace가 공백(Space)로 변경된 문자열
	 * @see java.lang.Character#isWhitespace
	 */
	public static String cleanWhitespace(String text) {
		boolean whitespace = false;
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < text.length(); ++i) {
			char letter = text.charAt(i);

			if (!Character.isWhitespace(letter)) {
				whitespace = false;
				buffer.append(letter);
			} else if (!whitespace) {
				whitespace = true;
				buffer.append(' ');
			}
		}
		return trimWhitespace(buffer.toString());
	}

	/* 입력 문자열 중에서 Whitespace(<code>Character.isWhitespace</code>에 허용된 코드)를 모두 제거하여 반환한다.
	 * @param text Whitespace를 제거할 문자열
	 * @return Whitespace가 제거된 문자열
	 * @throws NullPointerException 입력 문자열이 NULL일 경우 발생
	 * @see java.lang.Character#isWhitespace
	 */
	public static String stripWhitespace(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < text.length(); ++i) {
			if (!Character.isWhitespace(text.charAt(i))) {
				buffer.append(text.charAt(i));
			}
		}
		return buffer.toString();
	}

	/* 입력 문자열이 Integer인지 검사한다. <code>Integer.parseInt</code>가 예외를 발생시키지 않을 경우를 Integer로 결정하고, <code>
	 * NumberFormatException</code>이 발생할 경우 Integer가 아니라고 결정한다.
	 * @param text 검사할 문자열
	 * @return 검사 결과
	 * @see java.lang.Integer#parseInt
	 */
	public static boolean isInteger(String text) {
		boolean result = false;

		try {
			Integer.parseInt(text);
			result = true;
		} catch (NumberFormatException e) {
		}
		return result;
	}

	/* 입력 문자열이 Long인지 검사한다. <code>Long.parseLong</code>가 예외를 발생시키지 않을 경우를 Long으로 결정하고, <code>
	 * NumberFormatException</code>이 발생할 경우 Long이 아니라고 결정한다.
	 * @param text 검사할 문자열
	 * @return 검사 결과
	 * @see java.lang.Long#parseLong
	 */
	public static boolean isLong(String text) {
		boolean result = false;

		try {
			Long.parseLong(text);
			result = true;
		} catch (NumberFormatException e) {
		}
		return result;
	}

  public static boolean isEmpty(String key) {
    if(key == null || key.equals("")) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isNumber(String strData) {
    try {
    } catch(NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /* 음수를 양수로 변환  */
  public static int plusNum(int num){
    if(num < 0) num = num * -1;
    return num;
  }

  public static long plusNum(long num){
    if(num < 0) num = num * -1;
    return num;
  }

	/* 입력 문자열을 지정된 분리자로 분리된 문자열 배열로 반환한다. 이때 Vector 클래스를 이용한다.
	 * @param text 배열로 분리할 문자열
	 * @param delim 분리자
	 * @return 분리된 문자열 배열
	 */
	public static String[] split(String text, String delim) {
		if (text.startsWith(delim)) {
			text = text.substring(1);
		}
		StringTokenizer tokenizer = new StringTokenizer(text, delim);
    Vector<String> split = new Vector<String>(tokenizer.countTokens());
	
		while (tokenizer.hasMoreTokens()) {
			split.addElement(tokenizer.nextToken());
		}
		String[] result = new String[split.size()];
	
		for (int i = 0; i < split.size(); ++i) {
			result[i] = split.elementAt(i);
		}
		return result;
	}

	/* 입력 Enumeration을 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 * @param enum 하나의 문자열로 합칠 Enumeration
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(Enumeration<String> em, String delim) {
		StringBuffer buffer = new StringBuffer();

		while (em.hasMoreElements()) {
			buffer.append(em.nextElement().toString());
			if (delim != null) {
				buffer.append(delim);
			}
		}

		if (buffer.length() > 0 && delim != null) {
			buffer.setLength(buffer.length() - delim.length());
		}
		return buffer.toString();
	}

	/* 입력 Vector를 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 * @param vector 하나의 문자열로 합칠 Vector
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @param begin 시작 인덱스
	 * @param end 종료 인덱스
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(Vector<String> vector, String delim, int begin, int end) {
		StringBuffer buffer = new StringBuffer();

		for (int i = begin; i < end; ++i) {
			buffer.append(vector.elementAt(i).toString());
			if (delim != null) {
				buffer.append(delim);
			}
		}

		if (buffer.length() > 0 && delim != null) {
			buffer.setLength(buffer.length() - delim.length());
		}
		return buffer.toString();
	}

	/* 입력 Vector를 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 * @param vector 하나의 문자열로 합칠 Vector
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(Vector<String> vector, String delim) {
		return join(vector, delim, 0, vector.size());
	}

	/* 입력 문자열 배열을 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 * @param texts 하나의 문자열로 합칠 문자열 배열
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @param begin 시작 인덱스
	 * @param end 종료 인덱스
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(String[] texts, String delim, int begin, int end) {
		StringBuffer buffer = new StringBuffer();

		for (int i = begin; i < end; ++i) {
			buffer.append(texts[i]);
			if (delim != null) {
				buffer.append(delim);
			}
		}

		if (buffer.length() > 0 && delim != null) {
			buffer.setLength(buffer.length() - delim.length());
		}
		return buffer.toString();
	}

	/* 입력 문자열 배열을 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 * @param texts 하나의 문자열로 합칠 문자열 배열
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(String[] texts, String delim) {
		return join(texts, delim, 0, texts.length);
	}

	/* 입력 문자열 배열을 연결하여 하나의 문자열로 반환한다.
	 * @param texts 하나의 문자열로 합칠 문자열 배열
	 * @return 연결된 하나의 문자열
	 */
	public static String join(String[] texts) {
		return join(texts, null);
	}

	/* Exception 객체로써 스택을 추적해 문자열로 변환하여 반환한다.
	 * @param e 스택을 추적할 Exception 객체
	 * @return 스택 추적 내용을 담고 있는 문자열
	 */
	public static String stackTrace(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	/* 입력 문자열에서 특정 문자열을 지정된 문자열로 변경하여 반환한다.
	 * @param text 변환할 문자열
	 * @param from 변환 대상 문자열
	 * @param to 변경할 문자열
	 * @return 변환된 문자열
	 */
	public static String replace(String text, String from, String to) {
		int index = 0;
		int start = 0;
		StringBuffer buffer = new StringBuffer();

		if(text == null) {
			return "";
		}

		while ((index = text.indexOf(from, start)) != -1) {
			buffer.append(text.substring(start, index) + to);
			start = index + from.length();
		}

		if (start < text.length()) {
			buffer.append(text.substring(start, text.length()));
		}
		return buffer.toString();
	}

  /* 입력된 데이터를 sourceEncode 에서 targetEncode 로 코드변환 후 반환
   * @param strSource 입력데이터 String
   * @param sourceEncode 소스인코딩 String
   * @param targetEncode 출력인코딩 String
   * @return String 변환된 데이터
   */
  public static String encodingTo(String strSource, String sourceEncode, String targetEncode) {
    if ( strSource == null )
      return null;

    try {
      return new String(strSource.getBytes(sourceEncode), targetEncode);
    } catch(Exception e) {
      return null;
    }
  }

  /* 입력된 데이터를 8859_1 ==> KSC5601
   * @param strSource 입력데이터 String
   * @return String 변환된 데이터
   */
  public static String uni2ksc(String strSource) {
    return encodingTo(strSource, "8859_1", "KSC5601");
  }

  /* 입력된 데이터를 KSC5601 ==> 8859_1
   * @param strSource 입력데이터 String
   * @return String 변환된 데이터
   */
  public static String ksc2uni(String str) {
    return encodingTo(str, "KSC5601", "8859_1");
  }

	

  public static int lengthByte(String text) {
    return ksc2uni(text).length();
  }

  /* 입력 문자열을 Unicode (Multi-Byte 코드) 문자열을 손상하지 않고(한글 앞자리 자름) Byte 값에 따라 잘라 반환한다.
	 * @param text 자를 문자열
	 * @param start 시작 인덱스
	 * @param end 종료 인덱스
	 * @return 잘라진 문자열
	 */
  public static String substringByte(String str, int start, int end) {
      if (str == null) {
          return "";
      }else if (start > end) { 
          return "";
      } else if (start >= str.getBytes().length) {    
          return "";
      } else if (end > str.getBytes().length) { 
          end = str.getBytes().length;
      } 
      
      String retStr = ""; 
      int rStart = 0;
      int rEnd = 0;
      String oneChar;
      
      
      for(int i = 0; i < end; i++){
          oneChar = "" + str.charAt(i);
          
          if (end < (rEnd + oneChar.getBytes().length)) break; 
          
          if(rStart == start) {
              retStr = retStr + oneChar;
              
              if(oneChar.getBytes().length == 1){
                  rEnd++; 
              }else if(oneChar.getBytes().length == 2){
                  rEnd = rEnd + 2;
              }       
          }

          if(rEnd == end)break;
          
          if (rStart != start) {
              if (oneChar.getBytes().length == 1) {
                  rStart++;
                  rEnd++;
              } else if (oneChar.getBytes().length == 2) {
                  rStart = rStart + 2;
                  rEnd = rEnd + 2;
              } 
          }
      }       
      
      return retStr;
  }

	/** 영어 Character-Set으로 인코딩할 Character-Set 문자열 지정 */
	private static final String[] encodingToEnglish = {"KSC5601", "8859_1"};
	/** 한글 Character-Set으로 인코딩할 Character-Set 문자열 지정 */
	private static final String[] encodingToKorean = {"8859_1", "KSC5601"};
	/** 영어로 인코딩할지 여부 */
	private static boolean isToEnglish;
	/** 한글로 인코딩할지 여부 */
	private static boolean isToKorean;

	// 인코딩 여부를 검사후 결정
	static {
		isToEnglish = encodingToEnglish[0].equals(encodingToEnglish[1]);
		isToKorean = encodingToKorean[0].equals(encodingToKorean[1]);
	}

	/* 입력 문자열을 영어 Character-Set 문자열로 인코딩 변환한다.
	 * @param text 인코딩할 문자열
	 * @return 영어 Character-Set으로 인코딩된 문자열
	 */
	public static final String encodeEnglish(String text) {
		try {
			if (isToEnglish) {
				return text;
			}
			if (text == null) {
				return null;
			}
			return new String(
			text.getBytes(encodingToEnglish[0]), encodingToEnglish[1]);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/* 입력 문자열을 한글 Character-Set 문자열로 인코딩 변환한다.
	 * @param text 인코딩할 문자열
	 * @return 한글 Character-Set으로 인코딩된 문자열
	 */
	public static final String encodeKorean(String text) {
		try {
			if (isToKorean) {
				return text;
			}
			if (text == null) {
				return null;
			}
			return new String(
				text.getBytes(encodingToKorean[0]), encodingToKorean[1]);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/* 입력 문자열을 MD5로 축약하여 반환한다.
	 * @param text MD5로 축약할 문자열
	 * @return MD5로 축약된 문자열
	 */
	public static String encodeMD5(String text) {
		String value = "";

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(text.getBytes());
			byte[] bytes = messageDigest.digest();
			value = encodeHex(bytes);
		} catch(Exception e) {
		}
		return value;
	}

	/** Byte 값 변환을 위한 HEX Character Code 집합 정의 */
	static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/* 입력 Byte 배열의 HEX 값을 문자열로 변환하여 반환한다.
	 * @param bytes HEX 값 문자열로 변환할 Byte 배열
	 * @return HEX 값 문자열로 변환된 문자열
	 */
	public static String encodeHex(byte[] bytes) {
		StringBuffer buffer = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			buffer.append(digits[(b & 0xf0) >> 4]);
			buffer.append(digits[b & 0x0f]);
		}
		return buffer.toString();
	}

	/* 입력 HEX 문자열을 Byte 배열로 변환하여 반환한다.
	 * @param text Byte 배열로 변환할 HEX 문자열
	 * @return Byte 배열로 변환된 HEX 문자열
	 * @exception IllegalArgumentException 입력 문자열이 HEX 값이 아닐 경우 발생
	 */
	public static byte[] decodeHex(String text)
		throws IllegalArgumentException {
		try {
			int len = text.length();
			byte[] bytes = new byte[len / 2];

			for (int i = 0; i < bytes.length; i++) {
				int digit1 = text.charAt(i * 2);
				int digit2 = text.charAt(i * 2 + 1);

				if ((digit1 >= '0') && (digit1 <= '9')) {
					digit1 -= '0';
				} else if ((digit1 >= 'a') && (digit1 <= 'f')) {
					digit1 -= 'a' - 10;
				}

				if ((digit2 >= '0') && (digit2 <= '9')) {
					digit2 -= '0';
				} else if ((digit2 >= 'a') && (digit2 <= 'f')) {
					digit2 -= 'a' - 10;
				}
				bytes[i] = (byte) ((digit1 << 4) + digit2);
			}
			return bytes;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid input");
		}
	}

	/* 입력 문자열을 주민등록 번호 형식으로 변환하여 반환한다. 입력 문자열은 주민등록번호의 숫자로만 구성된 13자리 이어야 한다.
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 주민등록 번호 형식의 문자열
	 */
	public static String formatResidentNumber(String text) {
		if (text.length() == 13) {
			text = text.substring(0, 6) + "-" + text.substring(6);
		}
		return text;
	}

	/* 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다. 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 * @param obj 통화 형식으로 변환할 문자열
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(Object obj) {
		return formatNumber(obj.toString());
	}

	/* 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다. 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 * @param value 통화 형식으로 변환할 문자열
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(int value) {
		return formatNumber(String.valueOf(value));
	}

	/* 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다. 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 * @param text 통화 형식으로 변환할 문자열
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(String text) {
		String number = text;

		if (isLong(unformatNumber(number))) {
			NumberFormat formatter = NumberFormat.getInstance(Locale.KOREAN);
			number = formatter.format(Long.parseLong(number));
		} else if (number == null || number.length() == 0) {
			number = "0";
		} else {
			number = text;
		}
		return number;
	}

	/* 통화 형식의 입력 문자열을 일반 숫자 형식으로 변환하여 반환한다.
	 * @param text 통화 형식의 문자열
	 * @return 일반 숫자 형식의 문자열
	 */
	public static String unformatNumber(String text) {
		return replace(text, ",", "");
	}

	/**
	 * 입력 문자열을 지정된 패턴에 맞게 파싱하여 Date 객체를 얻는다. 얻어진 Date 객체를 지정된 Format에 맞게 날짜 표시 형식으로 변환하여 반환한다.
	 * @param text 날짜 형식의 문자열
	 * @param pattern 파싱할 패턴
	 * @param format 새로운 Format 문자열
	 * @return 새로운 날짜 표시 형식으로 변환된 문자열
	 */
	public static String formatDate(String text, String pattern, String format) {
		if (!(text.startsWith("1") || text.startsWith("2")) && text.length() == 14) {
			format = replace(format, "yyyy", text.substring(0, 4));
			format = replace(format, "MM", text.substring(4, 6));
			format = replace(format, "dd", text.substring(6, 8));
			format = replace(format, "HH", text.substring(8, 10));
			format = replace(format, "mm", text.substring(10, 12));
			format = replace(format, "ss", text.substring(12, 14));
			text = format;
		} else if (!(text.startsWith("1") || text.startsWith("2")) && text.length() == 8) {
			format = replace(format, "yyyy", text.substring(0, 4));
			format = replace(format, "MM", text.substring(4, 6));
			format = replace(format, "dd", text.substring(6, 8));
			format = replace(format, "HH", "00");
			format = replace(format, "mm", "00");
			format = replace(format, "ss", "00");
			text = format;
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);

			try {
				Date date = formatter.parse(text);
				formatter.applyPattern(format);
				text = formatter.format(date);
			} catch (ParseException e) {
			}
		}
		return text;
	}

  public static String getDate() {
    Calendar calendar = Calendar.getInstance();
    String year = String.valueOf((calendar.get(Calendar.YEAR)));
    String month = zeroFill(String.valueOf(calendar.get(Calendar.MONTH)+1), 2);
    String day = zeroFill(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2);
    String hour = zeroFill(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2);
    String minute = zeroFill(String.valueOf(calendar.get(Calendar.MINUTE)), 2);
    String second = zeroFill(String.valueOf(calendar.get(Calendar.SECOND)), 2);
    return year+month+day+hour+minute+second;
  }

	/* how는 Today를 기준으로 하루 전을 구하려면 -1, 이틀 전은 -2, 이틀 후는 2를 넣어준다.
   * ex1. getPrevDayDate("yyyyMMdd", -1)
   * ex2. getPrevDayDate("yyyy/MM/dd HH:mm:ss", 5)
	 */
  public static String getPrevDayDate(int how, String pattern) {
    Date date = new Date();
    long sum = how*24*60*60*1000;
    return formatDate(new Date(date.getTime()+sum), pattern);
  }

	/* 입력 Date 객체를 지정된 Format 문자열에 맞게 날짜 표시 형식으로 변환하여 반환한다.
	 * @param date Date 객체
	 * @param format 날짜 Format 문자열
	 * @return 날짜 표시 형식으로 변환된 문자열
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/* 특정 년-월의 마지막 날짜를 계산하여 얻는다.
	 * @param year 특정 년
	 * @param month 특정 월
	 * @return 해당 년-월의 마직막 날짜
	 */
	public static String getDaysOfMonth(String year, String month) {
		String result = "";

		if (isInteger(year) && isInteger(month)) {
			result = String.valueOf(getDaysOfMonth(
				Integer.parseInt(year), Integer.parseInt(month)));
		}
		return result;
	}

	/* 특정 년-월의 마지막 날짜를 계산하여 얻는다.
	 * @param year 특정 년
	 * @param month 특정 월
	 * @return 해당 년-월의 마직막 날짜
	 */
	public static int getDaysOfMonth(int year, int month) {
		int day = 0;

		if (month == 4 || month == 6 || month == 9 || month == 11) {
					day = 30;
		} else if (month == 2 && !(year % 4 == 0)) {
			day = 28;
		} else if (month == 2 && year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					day = 29;
				} else {
					day = 28;
				}
			} else {
				day = 29;
			}
		} else {
			day = 31;
		}
		return day;
	}

	/* 날짜 형식의 입력 문자열을 분석하여 이전달 문자열을 얻는다.
	 * @param text 날짜 형식의 입력 문자열
	 * @param format 입력 문자열의 패턴
	 * @return 이전달 문자열
	 */
	public static String getPrevMonthDate(String text, String format) {
		int year = Integer.parseInt(formatDate(text, format, "yyyy"));
		int month = Integer.parseInt(formatDate(text, format, "MM"));
		int date = Integer.parseInt(formatDate(text, format, "dd"));

		if (month == 1) {
			year--;
			month = 12;
		} else {
			month--;
		}

		if (getDaysOfMonth(year, month) < date) {
			date = getDaysOfMonth(year, month);
		}
		return zeroFill(String.valueOf(year), 2) + "/" + zeroFill(String.valueOf(month), 2) + "/" + zeroFill(String.valueOf(date), 2);
	}

	/* 날짜 형식의 입력 문자열을 분석하여 해당 일 문자열을 얻는다.
	 * @param text 날짜 형식의 입력 문자열
	 * @param format 입력 문자열의 패턴
	 * @param day 조정일
	 * @return 조정된 날짜 문자열
	 */
	public static String adjustDate(String text, String format, int day) {
		long dayTime = 1000 * 60 * 60 * 24;
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		try {
			Date date = formatter.parse(text);
			date.setTime(date.getTime() + dayTime * day);
			formatter.applyPattern(format);
			text = formatter.format(date);
		} catch (ParseException e) {
		}
		return text;
	}

	/* 날짜 형식의 입력 문자열을 분석하여 다음달 문자열을 얻는다.
	 * @param text 날짜 형식의 입력 문자열
	 * @param format 입력 문자열의 패턴
	 * @return 다음달 문자열
	 */
	public static String getNextMonthDate(String text, String format) {
		int year = Integer.parseInt(formatDate(text, format, "yyyy"));
		int month = Integer.parseInt(formatDate(text, format, "MM"));
		int date = Integer.parseInt(formatDate(text, format, "dd"));

		if (month == 12) {
			year++;
			month = 1;
		} else {
			month++;
		}

		if (getDaysOfMonth(year, month) < date) {
			date = getDaysOfMonth(year, month);
		}
		return zeroFill(String.valueOf(year), 2) + "/" + zeroFill(String.valueOf(month), 2) + "/" + zeroFill(String.valueOf(date), 2);
	}

	/* 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 * @param query 변경할 쿼리 문자열
	 * @param parameter 특정 패턴(인자)
	 * @param value 패턴과 변경할 값
	 * @param isText 문자열 형식인지 여부 (문자열일 경우 "'" 문자 추가)
	 * @return 변경된 쿼리 문자열
	 */
	public static String replaceSQL(String query, String parameter, String value, boolean isText) {
		return isText
		? replace(query, parameter, "'" + value + "'")
		: replace(query, parameter, value);
	}

	/* 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 * @param query 변경할 쿼리 문자열
	 * @param parameter 특정 패턴(인자)
	 * @param value 패턴과 변경할 값
	 * @param isText 문자열 형식인지 여부 (문자열일 경우 "'" 문자 추가)
	 * @return 변경된 쿼리 문자열
	 */
	public static String replaceSQL(String query, String parameter,
		int value, boolean isText) {
		return replaceSQL(query, parameter, String.valueOf(value), isText);
	}

	/* 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 * @param query 변경할 쿼리 문자열
	 * @param parameter 특정 패턴(인자)
	 * @param value 패턴과 변경할 값
	 * @param isText 문자열 형식인지 여부 (문자열일 경우 "'" 문자 추가)
	 * @return 변경된 쿼리 문자열
	 */
	public static String replaceSQL(String query, String parameter,
		long value, boolean isText) {
		return replaceSQL(query, parameter, String.valueOf(value), isText);
	}

	/* 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 * @param query 변경할 쿼리 문자열
	 * @param parameter 특정 패턴(인자)
	 * @param value 패턴과 변경할 값
	 * @param isText 문자열 형식인지 여부 (문자열일 경우 "'" 문자 추가)
	 * @return 변경된 쿼리 문자열
	 */
	public static String replaceSQL(String query, String parameter,
		float value, boolean isText) {
		return replaceSQL(query, parameter, String.valueOf(value), isText);
	}

	/* 원하는 패턴으로 변경하여 반환한다.
	 * @param fmt 변경할 패턴
	 * @param parame 변경할 쿼리 문자열
	 * @return 변경된 문자열
	 */
	public static String getAmtFormat(String format, double param) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(param);
	}

	/* NULL값을 공백으로 바꿔서 반환한다.
	 * @param param
	 * @return
	 */
  public static String nullToEmpty(String param) {
    if(param == null) {
      return "";
    } else {
      return param;
    }
  }

  /* NumberUtil.java */
  public static int sumOfAllDigit(String strData) {
    int intSum = 0;
    if(!StringUtil.isEmpty(strData) && isNumber(strData)) {
      intSum = sumOfAllElement(toIntArray(strData));
    }
    return intSum;
  }

  public static int[] toIntArray(String strData) throws NumberFormatException {
    char charData[] = strData.toCharArray();
    int intData[] = new int[charData.length];
    for(int i = 0; i < charData.length; i++)
      if(Character.isDigit(charData[i]))
        intData[i] = Character.getNumericValue(charData[i]);
      else
        throw new NumberFormatException("문자 [" + charData[i] + "]는 숫자가 아닙니다.");

     return intData;
  }

  public static int sumOfAllElement(int intData[]) {
    int intSum = 0;
    for(int i = 0; i < intData.length; i++)
      intSum += intData[i];

    return intSum;
  }

  public static int[] multiplyByWeight(int intSource[], int intWeight[]) {
    int intResult[] = new int[intSource.length];
    for(int i = 0; i < intSource.length; i++)
      intResult[i] = intSource[i] * intWeight[i % intWeight.length];

    return intResult;
  }


public static String getCurrentDate(){
	return	getFormatDate("yyyyMMdd", 0, 0);
}	

  /****************************************************************************
   * HHmmss 형식의 시간으로 변환한다.
   * @return                변환 시간
   * @exception             
   * @since                 1.0
  **************************************************************************/
  public static String getCurrentTime(){
	  return	getFormatDate("HHmmss", 0, 0);
  }	



/**
 * 일자포맷에 해당하는 값 갖오기
 * @param format	: date format
 * @param field
 * @param offset
 * @return 일자포맷된 값
 */
public static String getFormatDate(String format, 
								   int field, 
								   int offset)
 {
	SimpleTimeZone stz = new SimpleTimeZone(rawOffset, "STANDARD_TIME");
	Calendar rightNow = Calendar.getInstance(stz);
	if (offset != 0) {
		rightNow.add(field, offset);
	}
	Date rightDate = rightNow.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	sdf.setTimeZone(stz);
	return	sdf.format(rightDate);
}	
	
}