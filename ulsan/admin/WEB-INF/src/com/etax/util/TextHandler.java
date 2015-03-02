/*
 * @(#)TextHandler.java
 */
package com.etax.util;

import java.sql.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;


/**
 * 문자열에 조작에 관련된 유용한 Method를 정의해 놓은 유틸리티 클래스이다.
 *
 * @author
 * @version 1.0 2003/05/06 신규 작성
 * @since JDK 1.3
 */
public class TextHandler {

    final static int rawOffset = 9 * 60 * 60 * 1000;
	/**
	 * 클래스의 인스턴스를 생성하지 못하도록 생성자를 private 으로 정의한다.
	 */
	private TextHandler() {
	}

	/**
	 * 지정한 문자를 지정된 길이만큼 반복하여 문자열을 생성한 후, 반환한다.
	 *
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

	/**
	 * 지정한 문자열을 지정된 길이만큼 반복하여 문자열을 생성한 후, 반환한다.
	 *
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

	/**
	 * 입력 문자열 중에 Backslash ('\')로 된 Double-Quotes 문자열이 있을 경우
	 * Double-Quotes로 둘러 싼 문자열을 반환한다.
	 *
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

	/**
	 * 입력 문자열 중에 Quote가 있는지 검사한다.
	 *
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

	/**
	 * 입력 문자열의 왼쪽으로 지정된 문자를 지정된 길이만큼 채워서 반환한다.
	 * <p>사용 예제,</p>
	 * <p><code>leftFill("12", 4, '0')</code> returns "0012", while</p>
	 * <p><code>leftFill("12345", 4, '0')</code> returns "12345".</p>
	 *
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
		while (length-- > 0) {
			buffer.append(letter);
		}
		buffer.append(text);

		return buffer.toString();
	}

	/**
	 * 입력 문자열의 왼쪽으로 '0'을 지정된 길이만큼 채워 반환한다. leftFill()
	 * Method를 사용하는 것과 동일하다.
	 *
	 * @param text 가공할 문자열
	 * @param length 반복할 길이
	 * @return 왼쪽으로 '0'을 지정된 길이만큼 채운 문자열
	 * @see com.moneta.bill.util.TextHandler#leftFill
	 */
	public static String zeroFill(String text, int length) {
		return leftFill(text, length, '0');
	}

	/**
	 * 입력 문자열에서 앞뒤의 Whitespace(<code>Character.isWhitespace</code>에
	 * 허용된 코드)를 잘라 반환한다. 이것은 <code>String.trim</code> Method와
	 * 같지 않다. <code>String.trim</code>은 오직 공백(Space) 코드의 ASCII
	 * 값만을 Whitespace로 인식하기 때문이다.
	 *
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
		while (left < text.length()
			&& Character.isWhitespace(text.charAt(left))) {
			++left;
		}

		int right = text.length();
		while (right > left
			&& Character.isWhitespace(text.charAt(right - 1))) {
			--right;
		}

		return text.substring(left, right);
	}

	/**
	 * 입력 문자열 중에서 Whitespace(<code>Character.isWhitespace</code>에
	 * 허용된 코드)를 공백(Space)으로 바꿔 반환한다.
	 *
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

	/**
	 * 입력 문자열 중에서 Whitespace(<code>Character.isWhitespace</code>에
	 * 허용된 코드)를 모두 제거하여 반환한다.
	 *
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

	/**
	 * 입력 문자열이 Integer인지 검사한다. <code>Integer.parseInt</code>가
	 * 예외를 발생시키지 않을 경우를 Integer로 결정하고, <code>
	 * NumberFormatException</code>이 발생할 경우 Integer가 아니라고 결정한다.
	 *
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

	/**
	 * 입력 문자열이 Long인지 검사한다. <code>Long.parseLong</code>가
	 * 예외를 발생시키지 않을 경우를 Long으로 결정하고, <code>
	 * NumberFormatException</code>이 발생할 경우 Long이 아니라고 결정한다.
	 *
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

	/**
	 * 입력 문자열을 지정된 분리자로 분리된 문자열 배열로 반환한다. 이때 Vector
	 * 클래스를 이용한다.
	 *
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

	/**
	 * 입력 Enumeration을 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 *
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

	/**
	 * 입력 Vector를 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 *
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

	/**
	 * 입력 Vector를 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 *
	 * @param vector 하나의 문자열로 합칠 Vector
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(Vector<String> vector, String delim) {
		return join(vector, delim, 0, vector.size());
	}

	/**
	 * 입력 문자열 배열을 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 *
	 * @param texts 하나의 문자열로 합칠 문자열 배열
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @param begin 시작 인덱스
	 * @param end 종료 인덱스
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(String[] texts, String delim, int begin,
			int end) {
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

	/**
	 * 입력 문자열 배열을 지정된 분리자로 분리된 하나의 문자열로 반환한다.
	 *
	 * @param texts 하나의 문자열로 합칠 문자열 배열
	 * @param delim 합칠 경우 각각의 요소마다 분리자로 넣어줄 문자
	 * @return 지정된 분리자로 분리된 하나의 문자열
	 */
	public static String join(String[] texts, String delim) {
		return join(texts, delim, 0, texts.length);
	}

	/**
	 * 입력 문자열 배열을 연결하여 하나의 문자열로 반환한다.
	 *
	 * @param texts 하나의 문자열로 합칠 문자열 배열
	 * @return 연결된 하나의 문자열
	 */
	public static String join(String[] texts) {
		return join(texts, null);
	}

	/**
	 * Exception 객체로써 스택을 추적해 문자열로 변환하여 반환한다.
	 *
	 * @param e 스택을 추적할 Exception 객체
	 * @return 스택 추적 내용을 담고 있는 문자열
	 */
	public static String stackTrace(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));

		return writer.toString();
	}

	/**
	 * 입력 문자열에서 특정 문자열을 지정된 문자열로 변경하여 반환한다.
	 *
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

	/**
	 * 입력 문자열을 Unicode (Multi-Byte 코드) 문자열을 손상하지 않고 Byte
	 * 값에 따라 잘라 반환한다.
	 *
	 * @param text 자를 문자열
	 * @param start 시작 인덱스
	 * @param end 종료 인덱스
	 * @return 잘라진 문자열
	 */
	public static String substringByte(String text, int start, int end) {
		int stringLength = text.getBytes().length; // 바이트의 길이 계산

		int byteIndex    = 0; // Byte 인덱스
		int currentIndex = 0; // 현재 인덱스
		int skipIndex    = 0;

		if (start > end) { // 잘못된 입력값
			return "";
		} else if (start >= stringLength) {	// 시작 위치가 범위를 벗어남
			return "";
		} else if (end > stringLength) { // 종료 인덱스 수정
			end = stringLength;
		}

		end--;

		while (byteIndex < start) { // 시작 인덱스까지 순환
			byteIndex++;
			currentIndex++;

			//	해당 위치 코드가 Unicode일 경우
			if (text.charAt(currentIndex) > 255) {
				byteIndex++;
			}
		}

		skipIndex = currentIndex;

		// 종료 인덱스보다 작을때까지 순환
		while (byteIndex < end) {
			byteIndex++;
			currentIndex++;
			if (currentIndex < text.length()
				&& text.charAt(currentIndex) > 255) {
				byteIndex++;
			}
		}

		return text.substring(skipIndex, currentIndex);
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

	/**
	 * 입력 문자열을 영어 Character-Set 문자열로 인코딩 변환한다.
	 *
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

	/**
	 * 입력 문자열을 한글 Character-Set 문자열로 인코딩 변환한다.
	 *
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

	/**
	 * 입력 문자열을 MD5로 축약하여 반환한다.
	 *
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
	static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * 입력 Byte 배열의 HEX 값을 문자열로 변환하여 반환한다.
	 *
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

	/**
	 * 입력 HEX 문자열을 Byte 배열로 변환하여 반환한다.
	 *
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

	/**
	 * 입력 문자열을 주민등록 번호 형식으로 변환하여 반환한다. 입력 문자열은
	 * 주민등록번호의 숫자로만 구성된 13자리 이어야 한다.
	 *
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 주민등록 번호 형식의 문자열
	 */
	public static String formatResidentNumber(String text) {
		if (text.length() == 13) {
			text = text.substring(0, 6) + "-" + text.substring(6);
		}

		return text;
	}

  /**
	 * 입력 문자열을 주민등록 번호 형식과 뒷번호는 *로 대변한다. 
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 주민등록 번호 형식의 문자열
	 */
	public static String formatResidentSecret(String text) {
		if (text.length() == 13) {
			text = text.substring(0, 6) + "-" + text.substring(6,7) + "XXXXXX";
		}

		return text;
	}
  

  /**
	 * 입력 문자열을 경남은행가상계좌 번호 형식으로 대변한다. 
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 계좌 번호 형식의 문자열
	 */
	public static String formatAccountNo(String text) {
    String var_txt = replace(text, " ", ""); 
		if (var_txt.length() == 12) {
			var_txt = var_txt.substring(0, 3) + "-" + var_txt.substring(3,5) + "-" + var_txt.substring(5);
		}

		return var_txt;
	}


	/**
	 * 입력 문자열을 전자납부 번호 형식으로 변환하여 반환한다. 입력 문자열은
	 * 전자납부번호의 숫자로만 구성된 17자리 이어야 한다.
	 *
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 전자납부번호 번호 형식의 문자열
	 */
	public static String formatElecNumber(String text) {
		if (text.length() == 17) {
			text = text.substring(0, 2) + "-" + text.substring(2,8)+ "-" + text.substring(8,10)+ "-" + text.substring(10,17);
		}

		return text;
	}

  /**
	 * 입력 문자열을 납세번호 번호 형식으로 변환하여 반환한다. 입력 문자열은
	 * 납세번호의 숫자로만 구성된 22자리 이어야 한다.
	 *
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 전자납부번호 번호 형식의 문자열
	 */
	public static String formatNabseNumber(String text) {
		if (text.length() == 22) {
			text = text.substring(0, 6) + "-" + text.substring(6,13)+ "-" + text.substring(13,16)+ "-" + text.substring(16,22);
		}

		return text;
	}

  /* 상수도고객번호 */
  public static String formatAdminNo(String text) {
		if (text.length() == 8) {
			text = text.substring(0, 2) + "-" + text.substring(2);
		}

		return text;
	}

  /* 상수도수용가번호 */
  public static String formatSuyongNo(String text) {
		if (text.length() == 20) {
			text = text.substring(0, 2) + "-" + text.substring(2, 4) + "-" + text.substring(4, 7) + "-" 
           + text.substring(7, 10) + "-" + text.substring(10, 13) + "-" + text.substring(13, 17) + "-" 
           + text.substring(17, 19) + "-" + text.substring(19);
		}

		return text;
	}

  /* 상수도과세키번호 */
  public static String formatWaterKey(String text) {
		if (text.length() == 19) {
			text = text.substring(0, 1) + "-" + text.substring(1, 7) + "-" + text.substring(7);
		}

		return text;
	}

	/**
	 * 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다.
	 * 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 *
	 * @param obj 통화 형식으로 변환할 문자열
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(Object obj) {
		return formatNumber(obj.toString());
	}

	/**
	 * 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다.
	 * 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 *
	 * @param value 통화 형식으로 변환할 int 형
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(int value) {
		return formatNumber(String.valueOf(value));
	}

	/**
	 * 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다.
	 * 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 *
	 * @param value 통화 형식으로 변환할 long 형
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(long value) {
		return formatNumber(String.valueOf(value));
	}

	/**
	 * 입력 문자열을 통화 형식(3자리마다 ',' 문자로 분리)으로 변환하여 반환한다.
	 * 입력 문자열은 숫잘만 구성되어 있어야 한다.
	 *
	 * @param text 통화 형식으로 변환할 문자열
	 * @return 통화 형식(3자리마다 ',' 문자로 분리)으로 변환된 문자열
	 */
	public static String formatNumber(String text) {
    return formatNumber(text, "0");
	}

	public static String formatNumber(String text, String flag) {
		String number = text;

		if (isLong(unformatNumber(number))) {
			NumberFormat formatter = NumberFormat.getInstance(Locale.KOREAN);
			number = formatter.format(Long.parseLong(number));
		} else if (number == null || number.length() == 0) {
		  number = flag;
		} else {
			number = text;
		}

		return number;
	}

	/**
	 * 통화 형식의 입력 문자열을 일반 숫자 형식으로 변환하여 반환한다.
	 *
	 * @param text 통화 형식의 문자열
	 * @return 일반 숫자 형식의 문자열
	 */
	public static String unformatNumber(String text) {
		return replace(text, ",", "");
	}

	/**
	 * 전화번호 형식의 입력 문자열을 일반 숫자 형식으로 변환하여 반환한다.
	 *
	 * @param text 전화번호 형식의 문자열
	 * @return 일반 숫자 형식의 문자열
	 */
	public static String unformatTelNumber(String text) {
		return replace(text, "-", "");
	}

	/**
	 * 입력 문자열을 지정된 패턴에 맞게 파싱하여 Date 객체를 얻는다. 얻어진 Date
	 * 객체를 지정된 Format에 맞게 날짜 표시 형식으로 변환하여 반환한다.
	 *
	 * @param text 날짜 형식의 문자열
	 * @param pattern 파싱할 패턴
	 * @param format 새로운 Format 문자열
	 * @return 새로운 날짜 표시 형식으로 변환된 문자열
	 */
	public static String formatDate(String text, String pattern,
		String format) {
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

	/**
	 * how는 Today를 기준으로 하루 전을 구하려면 -1, 이틀 전은 -2, 이틀 후는 2를 넣어준다.
     * ex1. getPrevDayDate(-1, "yyyyMMdd")
     * ex2. getPrevDayDate(5, "yyyy/MM/dd HH:mm:ss")
	 */
    public static String getPrevDayDate(int how, String pattern) {
        Date date = new Date();
        long sum = how*24*60*60*1000;

        return formatDate(new Date(date.getTime()+sum), pattern);
    }

  // 휴일 다음날 구하기
  public static String getBusinessDate(Connection conn, String input_date) {
    String ret_date = "";
    String date_cmp = input_date;
    
    try {
      while (true) {
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
            ret_date = date_cmp;
            break;
        }

        date_cmp = addDays(date_cmp, 1);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }

  // 영업일체크해서 n영업일(TextHandler.java)
  // input_day는 기준일자 2010-10-29 by 강원모
  public static String getndayafterBusinessDate(Connection conn, String input_date, int nday) {
    String ret_date = "";
    String date_cmp = input_date;
    int    procdays = 0;
    
    if (nday < 1 || nday > 30) {
    	return ret_date;
    }
    try {
      while (true) {
      	date_cmp = addDays(date_cmp, +1);
        
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
        	  procdays++;
        	  if(procdays == nday) {
              ret_date = date_cmp;
              break;
            }
        }

      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }
  public static String getndaybeforeBusinessDate(Connection conn, String input_date, int nday) {
    String ret_date = "";
    String date_cmp = input_date;
    int    procdays = 0;
    
    if (nday < 1 || nday > 30) {
    	return ret_date;
    }
    try {
      while (true) {
      	date_cmp = addDays(date_cmp, -1);
        
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
        	  procdays++;
        	  if(procdays == nday) {
              ret_date = date_cmp;
              break;
            }
        }

      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }

  // 영업일체크해서 전날로
  public static String getAgoBusinessDate(Connection conn, String input_date) {
    String ret_date = "";
    String date_cmp = input_date;
    
    try {
      while (true) {
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
            ret_date = date_cmp;
            break;
        }

        date_cmp = addDays(date_cmp, -1);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }
  

  /* 영업일체크해서 다음날로
   * 전창수
   */
  public static String getAfterBusinessDate(Connection conn, String input_date) {
    String ret_date = "";
    String date_cmp = input_date;
    
    try {
      while (true) {
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
            ret_date = date_cmp;
            break;
        }

        date_cmp = addDays(date_cmp, 1);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }

 // 휴일 다음날 구하기
  public static String getAfterDate(Connection conn, String input_date) {
    String ret_date = "";
    String date_cmp = addDays(input_date, 1);
    
    try {
      while (true) {
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
            ret_date = date_cmp;
            break;
        }

        date_cmp = addDays(date_cmp, 1);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }

	// 휴일 전날 구하기
  public static String getBeforeDate(Connection conn, String input_date) {
    String ret_date = "";
    String date_cmp = addDays(input_date, -1);
    
    try {
      while (true) {
        if (isWeekend(date_cmp)== false && isHoliday(conn, date_cmp) == false) {
            ret_date = date_cmp;
            break;
        }

        date_cmp = addDays(date_cmp, -1);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ret_date;
  }

  public static String addDays(String s, int day) {
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA);
    java.util.Date date = check(s, "yyyyMMdd");

    date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60 * 24));
    return formatter.format(date);
  }

  public static java.util.Date check(String s, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.KOREA);
    java.util.Date date = null;
  
    try {
      date = formatter.parse(s);
    } catch(ParseException e) {
      e.printStackTrace();
    }
    return date;
 }
 
  //주말체크
  public static boolean isWeekend(String s) {
    boolean isWeekend = false;
    int year = Integer.parseInt(s.substring(0,4));
    int month = Integer.parseInt(s.substring(4,6));
    int day = Integer.parseInt(s.substring(6,8));
    
    if (month == 1 || month == 2) year--;
    month = (month + 9) % 12 + 1; 
    int y = year % 100; 
    int century = year / 100; 
    int week = ((13 * month - 1) / 5 + day + y + y/4 + century/4 - 2*century) % 7; 
    if (week < 0) week = (week + 7) % 7; 
    
    String ch_week = ""; 
    switch(week) { 
       case 0: 
          ch_week = "SUN"; 
          break; 
       case 1: 
          ch_week = "MON"; 
          break; 
       case 2: 
          ch_week = "TUE"; 
          break; 
       case 3: 
          ch_week = "WED"; 
          break; 
       case 4: 
          ch_week = "THU"; 
          break; 
       case 5: 
          ch_week = "FRI"; 
          break; 
       case 6: 
          ch_week = "SAT"; 
          break; 
    }
    
    if (ch_week.equals("SAT") || ch_week.equals("SUN")) {
      isWeekend = true; 
    } else {
    }
    
    return isWeekend;
  }

  // 입력한 날짜가 휴일이면 true/평일이면 false
  public static boolean isHoliday(Connection conn, String strDate) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT M290_DATE FROM M290_HOLIDAY_T WHERE M290_DATE = '").append(strDate).append("'");
    String query = sb.toString();
    PreparedStatement pstmt = conn.prepareStatement(query);
    ResultSet rs = pstmt.executeQuery();
    boolean rtnData = false;

    if(rs.next()) {
      rtnData = true;
    } else {
      rtnData = false;
    }
    rs.close();
    pstmt.close();
    return rtnData;
  }
  
    
    
  
    //문자열 포함하는지 여부 판단
    public static String isDiffy(Connection conn, String org_text, String new_text) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT CASE WHEN '"+org_text+"' LIKE '%"+new_text+"%' \n");
        sb.append("             THEN '0'                                  \n");
        sb.append("             WHEN '"+new_text+"' LIKE '%"+org_text+"%' \n");
        sb.append("             THEN '0'                                  \n");
        sb.append("             ELSE '1' END DIFFY                        \n");
        sb.append("   FROM DUAL                                           \n");
        String query = sb.toString();
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        String rtnData = "1";

        if(rs.next()) {
            rtnData = rs.getString("DIFFY");
        }
        rs.close();
        pstmt.close();
        return rtnData;
    }
  


	/**
	 * 입력 Date 객체를 지정된 Format 문자열에 맞게 날짜 표시 형식으로 변환하여
	 * 반환한다.
	 *
	 * @param date Date 객체
	 * @param format 날짜 Format 문자열
	 * @return 날짜 표시 형식으로 변환된 문자열
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(date);
	}

	/**
	 * 특정 년-월의 마지막 날짜를 계산하여 얻는다.
	 *
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

	/**
	 * 특정 년-월의 마지막 날짜를 계산하여 얻는다.
	 *
	 * @param year 특정 년
	 * @param month 특정 월
	 * @return 해당 년-월의 마직막 날짜
	 */
	public static int getDaysOfMonth(int year, int month) {
		int day = 0;

		if (month == 4 || month == 6
				|| month == 9 || month == 11) {
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

	/**
	 * 날짜 형식의 입력 문자열을 분석하여 이전달 문자열을 얻는다.
	 *
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

		return zeroFill(String.valueOf(year), 2) + zeroFill(String.valueOf(month), 2) + zeroFill(String.valueOf(date), 2);
	}

	/**
	 * 날짜 형식의 입력 문자열을 분석하여 해당 일 문자열을 얻는다.
	 *
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

	/**
	 * 날짜 형식의 입력 문자열을 분석하여 다음달 문자열을 얻는다.
	 *
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

		return zeroFill(String.valueOf(year), 2) + zeroFill(String.valueOf(month), 2) + zeroFill(String.valueOf(date), 2);
	}


	/**
	 * 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 *
	 * @param query 변경할 쿼리 문자열
	 * @param parameter 특정 패턴(인자)
	 * @param value 패턴과 변경할 값
	 * @param isText 문자열 형식인지 여부 (문자열일 경우 "'" 문자 추가)
	 * @return 변경된 쿼리 문자열
	 */
	public static String replaceSQL(String query, String parameter,
			String value, boolean isText) {
		return isText
			? replace(query, parameter, "'" + value + "'")
			: replace(query, parameter, value);
	}

	/**
	 * 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 *
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

	/**
	 * 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 *
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

	/**
	 * 입력 쿼리에서 특정한 패턴(인자)을 찾아 원하는 값으로 변경하여 반환한다.
	 *
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

	/**
	 * 원하는 패턴으로 변경하여 반환한다.
	 *
	 * @param fmt 변경할 패턴
	 * @param parame 변경할 쿼리 문자열
	 * @return 변경된 문자열
	 */
	public static String getAmtFormat(String format, double param)
	{
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(param);
	}

	/**
	 * NULL값을 공백으로 바꿔서 반환한다.
	 *
	 * @param param
	 * @return
	 */
    public static String nullToEmpty(String param)
    {
        if(param == null) {
            return "";
        } else {
            return param;
        }
    }

	/**
	 * NULL값을 공백으로 바꿔서 반환한다.
	 *
	 * @param param
	 * @return
	 */
	public static String[] nullToEmpty(String[] param)
	{
		if(param == null)
			return new String[0];
		else
		{
			for(int cnt = 0; cnt < param.length; cnt++)
			{
				param[cnt] = nullToEmpty(param[cnt]);
			}
			return param;
		}
	}

  /**
	 * oracle의 줄바꿈을  <br>태그로 바꿔서 반환한다.
	 *
	 * @param str 변경할 텍스트
	 * @return    변경된 문자열
	 */
	public static String nl2br(String str)
	{
		str = str.replaceAll("\r\n", "<br>");
		str = str.replaceAll("\r", "<br>");
		str = str.replaceAll("\n", "<br>");
		return str;
  }

  /**
	 * oracle 텍스트 입력시 (')를 (‘)로 반환한다.
	 *
	 * @param str 변경할 텍스트
	 * @return    변경된 문자열
	 */
	public static String quotation(String str)
	{
		str = str.replaceAll("'", "‘");
		return str;
  }

  /** 지방세
	 * 입력 문자열을 가상계좌 번호 형식으로 변환하여 반환한다. 입력 문자열은
	 * 숫자로만 구성된 가상계좌 번호 14자리 이어야 한다.
	 *
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 가상계좌 번호 형식의 문자열
	 */
	public static String formatVasAccountNo(String text) {
		if (text.length() == 14) {
			text = text.substring(0, 3) + "-" + text.substring(3,6) + "-"  + text.substring(6,10) + "-"  + text.substring(10,14);
		}
		return text;
	}

  /** 세외수입
	 * 입력 문자열을 가상계좌 번호 형식으로 변환하여 반환한다. 입력 문자열은
	 * 숫자로만 구성된 가상계좌 번호 14자리 이어야 한다.
	 *
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 가상계좌 번호 형식의 문자열
	 */
	public static String extFormatVasAccountNo(String text) {
		if (text.length() == 14) {
			text = text.substring(0, 3) + "-" + text.substring(3,6) + "-"  + text.substring(6,14);
		}
		return text;
	}
  public static String getCurrentDate(){
    return	getFormatDate("yyyyMMdd", 0, 0);
  }

  public static String getCurrentTime(){
    return	getFormatDate("yyyyMMddHHmmss", 0, 0);
  }

  /** 세외수입
	 * 입력 문자열을 납부번호 형식으로 변환하여 반환한다. 입력 문자열은
	 * 숫자로만 구성된 납부번호 31자리 이어야 한다.
	 *
	 * @param text 변환할 문자열
	 * @return 중간에 '-'이 들어간 가상계좌 번호 형식의 문자열
	 */
	public static String formatLvyKey(String text) {
		if (text.length() == 31) {
			text = text.substring(0, 11) + "-" + text.substring(11,15) + "-"  + text.substring(15,17) + "-"  + text.substring(17,23) 
					+ "-"  + text.substring(23,31);
		}
		return text;
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


   /**
	 * 입력 은행코드를 은행명으로 변경한다. 
	 *
	 * @param text 변환할 문자열
	 */
	public static String formatBankName(String text) {
    String bankNM = "";
    String bankVar = "";
    if (text.length() <= 3) {
      bankVar = text;
    } else {
      bankVar = text.substring(0,3);
    }

		if (!"099".equals(bankVar)) {
			if (bankVar.equals("001")) {
        bankNM = "한국";
      } else if (bankVar.equals("002")) {
        bankNM = "산업";
      } else if (bankVar.equals("003")) {
        bankNM = "기업";
      } else if (bankVar.equals("004")) {
        bankNM = "국민";
      } else if (bankVar.equals("005")) {
        bankNM = "외환";
      } else if (bankVar.equals("006")) {
        bankNM = "국민";
      } else if (bankVar.equals("007")) {
        bankNM = "수협";
      } else if (bankVar.equals("008")) {
        bankNM = "수출입";
      } else if (bankVar.equals("009")) {
        bankNM = "국민";
      } else if (bankVar.equals("010")) {
        bankNM = "농협중";
      } else if (bankVar.equals("011")) {
        bankNM = "농협중";
      } else if (bankVar.equals("012")) {
        bankNM = "농협";
      } else if (bankVar.equals("013")) {
        bankNM = "농협";
      } else if (bankVar.equals("014")) {
        bankNM = "농협";
      } else if (bankVar.equals("015")) {
        bankNM = "농협";
      } else if (bankVar.equals("016")) {
        bankNM = "농협중";
      } else if (bankVar.equals("017")) {
        bankNM = "농협중";
      } else if (bankVar.equals("019")) {
        bankNM = "국민";
      } else if (bankVar.equals("020")) {
        bankNM = "우리";
      } else if (bankVar.equals("021")) {
        bankNM = "신한";
      } else if (bankVar.equals("022")) {
        bankNM = "우리";
      } else if (bankVar.equals("023")) {
        bankNM = "제일";
      } else if (bankVar.equals("024")) {
        bankNM = "우리";
      } else if (bankVar.equals("025")) {
        bankNM = "하나";
      } else if (bankVar.equals("026")) {
        bankNM = "신한";
      } else if (bankVar.equals("027")) {
        bankNM = "씨티";
      } else if (bankVar.equals("028")) {
        bankNM = "신한";
      } else if (bankVar.equals("029")) {
        bankNM = "국민";
      } else if (bankVar.equals("030")) {
        bankNM = "국민";
      } else if (bankVar.equals("031")) {
        bankNM = "대구";
      } else if (bankVar.equals("032")) {
        bankNM = "부산";
      } else if (bankVar.equals("033")) {
        bankNM = "하나";
      } else if (bankVar.equals("034")) {
        bankNM = "광주";
      } else if (bankVar.equals("035")) {
        bankNM = "제주";
      } else if (bankVar.equals("036")) {
        bankNM = "씨티";
      } else if (bankVar.equals("037")) {
        bankNM = "전북";
      } else if (bankVar.equals("038")) {
        bankNM = "신한";
      } else if (bankVar.equals("039")) {
        bankNM = "경남";
      } else if (bankVar.equals("040")) {
        bankNM = "신한";
      } else if (bankVar.equals("045")) {
        bankNM = "새마을";
      } else if (bankVar.equals("046")) {
        bankNM = "새마을";
      } else if (bankVar.equals("048")) {
        bankNM = "신협";
      } else if (bankVar.equals("049")) {
        bankNM = "신협";
      } else if (bankVar.equals("050")) {
        bankNM = "상호저축";
      } else if (bankVar.equals("051")) {
        bankNM = "외국계";
      } else if (bankVar.equals("053")) {
        bankNM = "씨티";
      } else if (bankVar.equals("054")) {
        bankNM = "HSBC";
      } else if (bankVar.equals("055")) {
        bankNM = "도이치";
      } else if (bankVar.equals("056")) {
        bankNM = "ABN";
      } else if (bankVar.equals("057")) {
        bankNM = "상와";
      } else if (bankVar.equals("058")) {
        bankNM = "미즈호코";
      } else if (bankVar.equals("059")) {
        bankNM = "UFJ";
      } else if (bankVar.equals("060")) {
        bankNM = "BOA";
      } else if (bankVar.equals("071")) {
        bankNM = "우체국";
      } else if (bankVar.equals("072")) {
        bankNM = "우체국";
      } else if (bankVar.equals("073")) {
        bankNM = "우체국";
      } else if (bankVar.equals("074")) {
        bankNM = "우체국";
      } else if (bankVar.equals("075")) {
        bankNM = "우체국";
      } else if (bankVar.equals("076")) {
        bankNM = "신보";
      } else if (bankVar.equals("077")) {
        bankNM = "기신";
      } else if (bankVar.equals("081")) {
        bankNM = "하나";
      } else if (bankVar.equals("082")) {
        bankNM = "하나";
      } else if (bankVar.equals("083")) {
        bankNM = "우리";
      } else if (bankVar.equals("084")) {
        bankNM = "우리";
      } else if (bankVar.equals("085")) {
        bankNM = "새마을";
      } else if (bankVar.equals("086")) {
        bankNM = "새마을";
      } else if (bankVar.equals("088")) {
        bankNM = "신한";
      } else if (bankVar.equals("094")) {
        bankNM = "BC카드";
      } else if (bankVar.equals("095")) {
        bankNM = "현대카드";
      } else if (bankVar.equals("096")) {
        bankNM = "롯데카드";
      } else if (bankVar.equals("097")) {
        bankNM = "삼성카드";
      } else if (bankVar.equals("098")) {
        bankNM = "신한카드";
      } else {
        bankNM = "신한";
      }
    } else if("099".equals(bankVar)) {
      if (text.equals("0999712")) {
        bankNM = "한국";
      } else if (text.equals("0999713")) {
        bankNM = "산업";
      } else if (text.equals("0999714")) {
        bankNM = "기업";
      } else if (text.equals("0999715")) {
        bankNM = "외환";
      } else if (text.equals("0999716")) {
        bankNM = "국민";
      } else if (text.equals("0999717")) {
        bankNM = "수협";
      } else if (text.equals("0999718")) {
        bankNM = "수출입";
      } else if (text.equals("0999719")) {
        bankNM = "국민";
      } else if (text.equals("0999720")) {
        bankNM = "농협";
      } else if (text.equals("0999721")) {
        bankNM = "농협";
      } else if (text.equals("0999722")) {
        bankNM = "농협";
      } else if (text.equals("0999723")) {
        bankNM = "농협";
      } else if (text.equals("0999724")) {
        bankNM = "농협";
      } else if (text.equals("0999725")) {
        bankNM = "농협";
      } else if (text.equals("0999726")) {
        bankNM = "농협";
      } else if (text.equals("0999727")) {
        bankNM = "국민";
      } else if (text.equals("0999728")) {
        bankNM = "우리";
      } else if (text.equals("0999729")) {
        bankNM = "신한";
      } else if (text.equals("0999730")) {
        bankNM = "우리";
      } else if (text.equals("0999731")) {
        bankNM = "제일";
      } else if (text.equals("0999732")) {
        bankNM = "우리";
      } else if (text.equals("0999733")) {
        bankNM = "하나";
      } else if (text.equals("0999734")) {
        bankNM = "씨티";
      } else if (text.equals("0999735")) {
        bankNM = "신한";
      } else if (text.equals("0999736")) {
        bankNM = "국민";
      } else if (text.equals("0999737")) {
        bankNM = "대동";
      } else if (text.equals("0999738")) {
        bankNM = "대구";
      } else if (text.equals("0999739")) {
        bankNM = "부산";
      } else if (text.equals("0999740")) {
        bankNM = "하나";
      } else if (text.equals("0999741")) {
        bankNM = "광주";
      } else if (text.equals("0999742")) {
        bankNM = "제주";
      } else if (text.equals("0999743")) {
        bankNM = "씨티";
      } else if (text.equals("0999744")) {
        bankNM = "전북";
      } else if (text.equals("0999745")) {
        bankNM = "조흥";
      } else if (text.equals("0999746")) {
        bankNM = "경남";
      } else if (text.equals("0999747")) {
        bankNM = "새마을";
      } else if (text.equals("0999748")) {
        bankNM = "새마을";
      } else if (text.equals("0999749")) {
        bankNM = "새마을";
      } else if (text.equals("0999750")) {
        bankNM = "신협";
      } else if (text.equals("0999751")) {
        bankNM = "상호";
      } else if (text.equals("0999752")) {
        bankNM = "외환";
      } else if (text.equals("0999753")) {
        bankNM = "새마을";
      } else if (text.equals("0999754")) {
        bankNM = "씨티";
      } else if (text.equals("0999755")) {
        bankNM = "HSBC";
      } else if (text.equals("0999756")) {
        bankNM = "도이치";
      } else if (text.equals("0999757")) {
        bankNM = "ABN";
      } else if (text.equals("0999758")) {
        bankNM = "UFJ";
      } else if (text.equals("0999759")) {
        bankNM = "미즈호크";
      } else if (text.equals("0999760")) {
        bankNM = "UFJ";
      } else if (text.equals("0999761")) {
        bankNM = "BOA";
      } else if (text.equals("0999762")) {
        bankNM = "우체국";
      } else if (text.equals("0999763")) {
        bankNM = "우체국";
      } else if (text.equals("0999764")) {
        bankNM = "우체국";
      } else if (text.equals("0999765")) {
        bankNM = "우체국";
      } else if (text.equals("0999766")) {
        bankNM = "우체국";
      } else if (text.equals("0999767")) {
        bankNM = "신보";
      } else if (text.equals("0999768")) {
        bankNM = "기신";
      } else if (text.equals("0999769")) {
        bankNM = "하나";
      } else if (text.equals("0999770")) {
        bankNM = "하나";
      } else if (text.equals("0999771")) {
        bankNM = "우리";
      } else if (text.equals("0999772")) {
        bankNM = "우리";
      } else if (text.equals("0999773")) {
        bankNM = "KT";
      } else if (text.equals("0999774")) {
        bankNM = "새마을";
      } else if (text.equals("0999775")) {
        bankNM = "새마을";
      } else if (text.equals("0999776")) {
        bankNM = "새마을";
      } else if (text.equals("0999777")) {
        bankNM = "신협";
      } else {
        bankNM = text;
      }
    } else {
      bankNM = text;
    }

		return bankNM;
	}


  
    //책임자 승인코드 한글 변환(경남은행)
  public static String getCodeName(String text) {
		String rtnName = "";
		if ("G44".equals(text)) {
			rtnName = "고액 지급 거래";
		} else if ("G48".equals(text)) {
			rtnName = "고액 입금 거래";
		} else if ("G49".equals(text)) {
			rtnName = "단말 전결한도 초과";
		} else if ("G45".equals(text)) {
			rtnName = "지급가능잔액1초과";
		} else if ("G46".equals(text)) {
			rtnName = "지급가능잔액2초과";
		} else if ("G47".equals(text)) {
			rtnName = "지급가능잔액3초과";
		} else {
			rtnName = text;
		}
		return rtnName;
	}
	
	// 책임자 승인코드 3글자씩 자르기
	public static String getPmsName(String text) {
		int idx = 0;
		String orgTxt = replace(text, " ", "");
		idx = orgTxt.getBytes().length;
		String pmsName = "";
		int y = 0;
		if (idx%3 == 0) {
			for (int i=0; i< idx/3; i++) {			
				if ("".equals(pmsName)) {
					pmsName = getCodeName(orgTxt.substring(y, y+3));
				} else {
					pmsName = pmsName + ", " + getCodeName(orgTxt.substring(y, y+3));
				}
				y=y+3;
			}			
		} else {
			pmsName = text;
		}
		
		return pmsName;
	}

}