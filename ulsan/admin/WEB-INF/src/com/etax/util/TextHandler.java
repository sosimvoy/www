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
 * ���ڿ��� ���ۿ� ���õ� ������ Method�� ������ ���� ��ƿ��Ƽ Ŭ�����̴�.
 *
 * @author
 * @version 1.0 2003/05/06 �ű� �ۼ�
 * @since JDK 1.3
 */
public class TextHandler {

    final static int rawOffset = 9 * 60 * 60 * 1000;
	/**
	 * Ŭ������ �ν��Ͻ��� �������� ���ϵ��� �����ڸ� private ���� �����Ѵ�.
	 */
	private TextHandler() {
	}

	/**
	 * ������ ���ڸ� ������ ���̸�ŭ �ݺ��Ͽ� ���ڿ��� ������ ��, ��ȯ�Ѵ�.
	 *
	 * @param letter �ݺ��� ����
	 * @param length �ݺ��� ����
	 * @return ������ ���ڸ� ������ ���̸�ŭ �ݺ��Ͽ� ������ ���ڿ�
	 */
	public static String repeat(char letter, int length) {
		StringBuffer buffer = new StringBuffer(length);

		for (int i = 0; i < length; ++i) {
			buffer.append(letter);
		}

		return buffer.toString();
	}

	/**
	 * ������ ���ڿ��� ������ ���̸�ŭ �ݺ��Ͽ� ���ڿ��� ������ ��, ��ȯ�Ѵ�.
	 *
	 * @param text �ݺ��� ���ڿ�
	 * @param length �ݺ��� ����
	 * @return ������ ���ڿ��� ������ ���̸�ŭ �ݺ��Ͽ� ������ ���ڿ�
	 */
	public static String repeat(String text, int length) {
		StringBuffer buffer = new StringBuffer(text.length() * length);

		while (length-- > 0) {
			buffer.append(text);
		}

		return buffer.toString();
	}

	/**
	 * �Է� ���ڿ� �߿� Backslash ('\')�� �� Double-Quotes ���ڿ��� ���� ���
	 * Double-Quotes�� �ѷ� �� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param text Double-Quotes�� �ѷ� �� ���ڿ�
	 * @return Double-Quotes�� �ѷ� �� ���ڿ�
	 * @throws NullPointerException �Է� ���ڿ��� NULL�� ��� �߻�
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
	 * �Է� ���ڿ� �߿� Quote�� �ִ��� �˻��Ѵ�.
	 *
	 * @param text �˻��� ���ڿ�
	 * @return Quote �˻� ���
	 * @throws NullPointerException �Է� ���ڿ��� NULL�� ��� �߻�
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
	 * �Է� ���ڿ��� �������� ������ ���ڸ� ������ ���̸�ŭ ä���� ��ȯ�Ѵ�.
	 * <p>��� ����,</p>
	 * <p><code>leftFill("12", 4, '0')</code> returns "0012", while</p>
	 * <p><code>leftFill("12345", 4, '0')</code> returns "12345".</p>
	 *
	 * @param text ������ ���ڿ�
	 * @param length �ݺ��� ����
	 * @param letter �ݺ��� ����
	 * @return �������� ������ ���ڸ� ������ ���̸�ŭ ä�� ���ڿ�
	 * @throws NullPointerException �Է� ���ڿ��� NULL�� ��� �߻�
	 * @throws IllegalArgumentException ���̰� ������ ��� �߻�
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
	 * �Է� ���ڿ��� �������� '0'�� ������ ���̸�ŭ ä�� ��ȯ�Ѵ�. leftFill()
	 * Method�� ����ϴ� �Ͱ� �����ϴ�.
	 *
	 * @param text ������ ���ڿ�
	 * @param length �ݺ��� ����
	 * @return �������� '0'�� ������ ���̸�ŭ ä�� ���ڿ�
	 * @see com.moneta.bill.util.TextHandler#leftFill
	 */
	public static String zeroFill(String text, int length) {
		return leftFill(text, length, '0');
	}

	/**
	 * �Է� ���ڿ����� �յ��� Whitespace(<code>Character.isWhitespace</code>��
	 * ���� �ڵ�)�� �߶� ��ȯ�Ѵ�. �̰��� <code>String.trim</code> Method��
	 * ���� �ʴ�. <code>String.trim</code>�� ���� ����(Space) �ڵ��� ASCII
	 * ������ Whitespace�� �ν��ϱ� �����̴�.
	 *
	 * @param text Whitespace�� �߶� ���ڿ�
	 * @return �յ� Whitespace�� ���ŵ� ���ڿ�
	 * @throws NullPointerException �Է� ���ڿ��� NULL�� ��� �߻�
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
	 * �Է� ���ڿ� �߿��� Whitespace(<code>Character.isWhitespace</code>��
	 * ���� �ڵ�)�� ����(Space)���� �ٲ� ��ȯ�Ѵ�.
	 *
	 * @param text Whitespace�� ������ ���ڿ�
	 * @return Whitespace�� ����(Space)�� ����� ���ڿ�
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
	 * �Է� ���ڿ� �߿��� Whitespace(<code>Character.isWhitespace</code>��
	 * ���� �ڵ�)�� ��� �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text Whitespace�� ������ ���ڿ�
	 * @return Whitespace�� ���ŵ� ���ڿ�
	 * @throws NullPointerException �Է� ���ڿ��� NULL�� ��� �߻�
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
	 * �Է� ���ڿ��� Integer���� �˻��Ѵ�. <code>Integer.parseInt</code>��
	 * ���ܸ� �߻���Ű�� ���� ��츦 Integer�� �����ϰ�, <code>
	 * NumberFormatException</code>�� �߻��� ��� Integer�� �ƴ϶�� �����Ѵ�.
	 *
	 * @param text �˻��� ���ڿ�
	 * @return �˻� ���
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
	 * �Է� ���ڿ��� Long���� �˻��Ѵ�. <code>Long.parseLong</code>��
	 * ���ܸ� �߻���Ű�� ���� ��츦 Long���� �����ϰ�, <code>
	 * NumberFormatException</code>�� �߻��� ��� Long�� �ƴ϶�� �����Ѵ�.
	 *
	 * @param text �˻��� ���ڿ�
	 * @return �˻� ���
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
	 * �Է� ���ڿ��� ������ �и��ڷ� �и��� ���ڿ� �迭�� ��ȯ�Ѵ�. �̶� Vector
	 * Ŭ������ �̿��Ѵ�.
	 *
	 * @param text �迭�� �и��� ���ڿ�
	 * @param delim �и���
	 * @return �и��� ���ڿ� �迭
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
	 * �Է� Enumeration�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param enum �ϳ��� ���ڿ��� ��ĥ Enumeration
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
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
	 * �Է� Vector�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param vector �ϳ��� ���ڿ��� ��ĥ Vector
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @param begin ���� �ε���
	 * @param end ���� �ε���
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
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
	 * �Է� Vector�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param vector �ϳ��� ���ڿ��� ��ĥ Vector
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
	 */
	public static String join(Vector<String> vector, String delim) {
		return join(vector, delim, 0, vector.size());
	}

	/**
	 * �Է� ���ڿ� �迭�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param texts �ϳ��� ���ڿ��� ��ĥ ���ڿ� �迭
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @param begin ���� �ε���
	 * @param end ���� �ε���
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
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
	 * �Է� ���ڿ� �迭�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param texts �ϳ��� ���ڿ��� ��ĥ ���ڿ� �迭
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
	 */
	public static String join(String[] texts, String delim) {
		return join(texts, delim, 0, texts.length);
	}

	/**
	 * �Է� ���ڿ� �迭�� �����Ͽ� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 *
	 * @param texts �ϳ��� ���ڿ��� ��ĥ ���ڿ� �迭
	 * @return ����� �ϳ��� ���ڿ�
	 */
	public static String join(String[] texts) {
		return join(texts, null);
	}

	/**
	 * Exception ��ü�ν� ������ ������ ���ڿ��� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param e ������ ������ Exception ��ü
	 * @return ���� ���� ������ ��� �ִ� ���ڿ�
	 */
	public static String stackTrace(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));

		return writer.toString();
	}

	/**
	 * �Է� ���ڿ����� Ư�� ���ڿ��� ������ ���ڿ��� �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @param from ��ȯ ��� ���ڿ�
	 * @param to ������ ���ڿ�
	 * @return ��ȯ�� ���ڿ�
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
	 * �Է� ���ڿ��� Unicode (Multi-Byte �ڵ�) ���ڿ��� �ջ����� �ʰ� Byte
	 * ���� ���� �߶� ��ȯ�Ѵ�.
	 *
	 * @param text �ڸ� ���ڿ�
	 * @param start ���� �ε���
	 * @param end ���� �ε���
	 * @return �߶��� ���ڿ�
	 */
	public static String substringByte(String text, int start, int end) {
		int stringLength = text.getBytes().length; // ����Ʈ�� ���� ���

		int byteIndex    = 0; // Byte �ε���
		int currentIndex = 0; // ���� �ε���
		int skipIndex    = 0;

		if (start > end) { // �߸��� �Է°�
			return "";
		} else if (start >= stringLength) {	// ���� ��ġ�� ������ ���
			return "";
		} else if (end > stringLength) { // ���� �ε��� ����
			end = stringLength;
		}

		end--;

		while (byteIndex < start) { // ���� �ε������� ��ȯ
			byteIndex++;
			currentIndex++;

			//	�ش� ��ġ �ڵ尡 Unicode�� ���
			if (text.charAt(currentIndex) > 255) {
				byteIndex++;
			}
		}

		skipIndex = currentIndex;

		// ���� �ε������� ���������� ��ȯ
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

	/** ���� Character-Set���� ���ڵ��� Character-Set ���ڿ� ���� */
	private static final String[] encodingToEnglish = {"KSC5601", "8859_1"};
	/** �ѱ� Character-Set���� ���ڵ��� Character-Set ���ڿ� ���� */
	private static final String[] encodingToKorean = {"8859_1", "KSC5601"};

	/** ����� ���ڵ����� ���� */
	private static boolean isToEnglish;
	/** �ѱ۷� ���ڵ����� ���� */
	private static boolean isToKorean;

	// ���ڵ� ���θ� �˻��� ����
	static {
		isToEnglish = encodingToEnglish[0].equals(encodingToEnglish[1]);
		isToKorean = encodingToKorean[0].equals(encodingToKorean[1]);
	}

	/**
	 * �Է� ���ڿ��� ���� Character-Set ���ڿ��� ���ڵ� ��ȯ�Ѵ�.
	 *
	 * @param text ���ڵ��� ���ڿ�
	 * @return ���� Character-Set���� ���ڵ��� ���ڿ�
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
	 * �Է� ���ڿ��� �ѱ� Character-Set ���ڿ��� ���ڵ� ��ȯ�Ѵ�.
	 *
	 * @param text ���ڵ��� ���ڿ�
	 * @return �ѱ� Character-Set���� ���ڵ��� ���ڿ�
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
	 * �Է� ���ڿ��� MD5�� ����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text MD5�� ����� ���ڿ�
	 * @return MD5�� ���� ���ڿ�
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

	/** Byte �� ��ȯ�� ���� HEX Character Code ���� ���� */
	static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * �Է� Byte �迭�� HEX ���� ���ڿ��� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param bytes HEX �� ���ڿ��� ��ȯ�� Byte �迭
	 * @return HEX �� ���ڿ��� ��ȯ�� ���ڿ�
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
	 * �Է� HEX ���ڿ��� Byte �迭�� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text Byte �迭�� ��ȯ�� HEX ���ڿ�
	 * @return Byte �迭�� ��ȯ�� HEX ���ڿ�
	 * @exception IllegalArgumentException �Է� ���ڿ��� HEX ���� �ƴ� ��� �߻�
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
	 * �Է� ���ڿ��� �ֹε�� ��ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ���
	 * �ֹε�Ϲ�ȣ�� ���ڷθ� ������ 13�ڸ� �̾�� �Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� �ֹε�� ��ȣ ������ ���ڿ�
	 */
	public static String formatResidentNumber(String text) {
		if (text.length() == 13) {
			text = text.substring(0, 6) + "-" + text.substring(6);
		}

		return text;
	}

  /**
	 * �Է� ���ڿ��� �ֹε�� ��ȣ ���İ� �޹�ȣ�� *�� �뺯�Ѵ�. 
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� �ֹε�� ��ȣ ������ ���ڿ�
	 */
	public static String formatResidentSecret(String text) {
		if (text.length() == 13) {
			text = text.substring(0, 6) + "-" + text.substring(6,7) + "XXXXXX";
		}

		return text;
	}
  

  /**
	 * �Է� ���ڿ��� �泲���డ����� ��ȣ �������� �뺯�Ѵ�. 
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� ���� ��ȣ ������ ���ڿ�
	 */
	public static String formatAccountNo(String text) {
    String var_txt = replace(text, " ", ""); 
		if (var_txt.length() == 12) {
			var_txt = var_txt.substring(0, 3) + "-" + var_txt.substring(3,5) + "-" + var_txt.substring(5);
		}

		return var_txt;
	}


	/**
	 * �Է� ���ڿ��� ���ڳ��� ��ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ���
	 * ���ڳ��ι�ȣ�� ���ڷθ� ������ 17�ڸ� �̾�� �Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� ���ڳ��ι�ȣ ��ȣ ������ ���ڿ�
	 */
	public static String formatElecNumber(String text) {
		if (text.length() == 17) {
			text = text.substring(0, 2) + "-" + text.substring(2,8)+ "-" + text.substring(8,10)+ "-" + text.substring(10,17);
		}

		return text;
	}

  /**
	 * �Է� ���ڿ��� ������ȣ ��ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ���
	 * ������ȣ�� ���ڷθ� ������ 22�ڸ� �̾�� �Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� ���ڳ��ι�ȣ ��ȣ ������ ���ڿ�
	 */
	public static String formatNabseNumber(String text) {
		if (text.length() == 22) {
			text = text.substring(0, 6) + "-" + text.substring(6,13)+ "-" + text.substring(13,16)+ "-" + text.substring(16,22);
		}

		return text;
	}

  /* ���������ȣ */
  public static String formatAdminNo(String text) {
		if (text.length() == 8) {
			text = text.substring(0, 2) + "-" + text.substring(2);
		}

		return text;
	}

  /* ��������밡��ȣ */
  public static String formatSuyongNo(String text) {
		if (text.length() == 20) {
			text = text.substring(0, 2) + "-" + text.substring(2, 4) + "-" + text.substring(4, 7) + "-" 
           + text.substring(7, 10) + "-" + text.substring(10, 13) + "-" + text.substring(13, 17) + "-" 
           + text.substring(17, 19) + "-" + text.substring(19);
		}

		return text;
	}

  /* ���������Ű��ȣ */
  public static String formatWaterKey(String text) {
		if (text.length() == 19) {
			text = text.substring(0, 1) + "-" + text.substring(1, 7) + "-" + text.substring(7);
		}

		return text;
	}

	/**
	 * �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 *
	 * @param obj ��ȭ �������� ��ȯ�� ���ڿ�
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
	 */
	public static String formatNumber(Object obj) {
		return formatNumber(obj.toString());
	}

	/**
	 * �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 *
	 * @param value ��ȭ �������� ��ȯ�� int ��
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
	 */
	public static String formatNumber(int value) {
		return formatNumber(String.valueOf(value));
	}

	/**
	 * �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 *
	 * @param value ��ȭ �������� ��ȯ�� long ��
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
	 */
	public static String formatNumber(long value) {
		return formatNumber(String.valueOf(value));
	}

	/**
	 * �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 *
	 * @param text ��ȭ �������� ��ȯ�� ���ڿ�
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
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
	 * ��ȭ ������ �Է� ���ڿ��� �Ϲ� ���� �������� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text ��ȭ ������ ���ڿ�
	 * @return �Ϲ� ���� ������ ���ڿ�
	 */
	public static String unformatNumber(String text) {
		return replace(text, ",", "");
	}

	/**
	 * ��ȭ��ȣ ������ �Է� ���ڿ��� �Ϲ� ���� �������� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text ��ȭ��ȣ ������ ���ڿ�
	 * @return �Ϲ� ���� ������ ���ڿ�
	 */
	public static String unformatTelNumber(String text) {
		return replace(text, "-", "");
	}

	/**
	 * �Է� ���ڿ��� ������ ���Ͽ� �°� �Ľ��Ͽ� Date ��ü�� ��´�. ����� Date
	 * ��ü�� ������ Format�� �°� ��¥ ǥ�� �������� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param text ��¥ ������ ���ڿ�
	 * @param pattern �Ľ��� ����
	 * @param format ���ο� Format ���ڿ�
	 * @return ���ο� ��¥ ǥ�� �������� ��ȯ�� ���ڿ�
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
	 * how�� Today�� �������� �Ϸ� ���� ���Ϸ��� -1, ��Ʋ ���� -2, ��Ʋ �Ĵ� 2�� �־��ش�.
     * ex1. getPrevDayDate(-1, "yyyyMMdd")
     * ex2. getPrevDayDate(5, "yyyy/MM/dd HH:mm:ss")
	 */
    public static String getPrevDayDate(int how, String pattern) {
        Date date = new Date();
        long sum = how*24*60*60*1000;

        return formatDate(new Date(date.getTime()+sum), pattern);
    }

  // ���� ������ ���ϱ�
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

  // ������üũ�ؼ� n������(TextHandler.java)
  // input_day�� �������� 2010-10-29 by ������
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

  // ������üũ�ؼ� ������
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
  

  /* ������üũ�ؼ� ��������
   * ��â��
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

 // ���� ������ ���ϱ�
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

	// ���� ���� ���ϱ�
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
 
  //�ָ�üũ
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

  // �Է��� ��¥�� �����̸� true/�����̸� false
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
  
    
    
  
    //���ڿ� �����ϴ��� ���� �Ǵ�
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
	 * �Է� Date ��ü�� ������ Format ���ڿ��� �°� ��¥ ǥ�� �������� ��ȯ�Ͽ�
	 * ��ȯ�Ѵ�.
	 *
	 * @param date Date ��ü
	 * @param format ��¥ Format ���ڿ�
	 * @return ��¥ ǥ�� �������� ��ȯ�� ���ڿ�
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(date);
	}

	/**
	 * Ư�� ��-���� ������ ��¥�� ����Ͽ� ��´�.
	 *
	 * @param year Ư�� ��
	 * @param month Ư�� ��
	 * @return �ش� ��-���� ������ ��¥
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
	 * Ư�� ��-���� ������ ��¥�� ����Ͽ� ��´�.
	 *
	 * @param year Ư�� ��
	 * @param month Ư�� ��
	 * @return �ش� ��-���� ������ ��¥
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
	 * ��¥ ������ �Է� ���ڿ��� �м��Ͽ� ������ ���ڿ��� ��´�.
	 *
	 * @param text ��¥ ������ �Է� ���ڿ�
	 * @param format �Է� ���ڿ��� ����
	 * @return ������ ���ڿ�
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
	 * ��¥ ������ �Է� ���ڿ��� �м��Ͽ� �ش� �� ���ڿ��� ��´�.
	 *
	 * @param text ��¥ ������ �Է� ���ڿ�
	 * @param format �Է� ���ڿ��� ����
	 * @param day ������
	 * @return ������ ��¥ ���ڿ�
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
	 * ��¥ ������ �Է� ���ڿ��� �м��Ͽ� ������ ���ڿ��� ��´�.
	 *
	 * @param text ��¥ ������ �Է� ���ڿ�
	 * @param format �Է� ���ڿ��� ����
	 * @return ������ ���ڿ�
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
	 * �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param query ������ ���� ���ڿ�
	 * @param parameter Ư�� ����(����)
	 * @param value ���ϰ� ������ ��
	 * @param isText ���ڿ� �������� ���� (���ڿ��� ��� "'" ���� �߰�)
	 * @return ����� ���� ���ڿ�
	 */
	public static String replaceSQL(String query, String parameter,
			String value, boolean isText) {
		return isText
			? replace(query, parameter, "'" + value + "'")
			: replace(query, parameter, value);
	}

	/**
	 * �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param query ������ ���� ���ڿ�
	 * @param parameter Ư�� ����(����)
	 * @param value ���ϰ� ������ ��
	 * @param isText ���ڿ� �������� ���� (���ڿ��� ��� "'" ���� �߰�)
	 * @return ����� ���� ���ڿ�
	 */
	public static String replaceSQL(String query, String parameter,
			int value, boolean isText) {
		return replaceSQL(query, parameter, String.valueOf(value), isText);
	}

	/**
	 * �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param query ������ ���� ���ڿ�
	 * @param parameter Ư�� ����(����)
	 * @param value ���ϰ� ������ ��
	 * @param isText ���ڿ� �������� ���� (���ڿ��� ��� "'" ���� �߰�)
	 * @return ����� ���� ���ڿ�
	 */
	public static String replaceSQL(String query, String parameter,
			long value, boolean isText) {
		return replaceSQL(query, parameter, String.valueOf(value), isText);
	}

	/**
	 * �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param query ������ ���� ���ڿ�
	 * @param parameter Ư�� ����(����)
	 * @param value ���ϰ� ������ ��
	 * @param isText ���ڿ� �������� ���� (���ڿ��� ��� "'" ���� �߰�)
	 * @return ����� ���� ���ڿ�
	 */
	public static String replaceSQL(String query, String parameter,
			float value, boolean isText) {
		return replaceSQL(query, parameter, String.valueOf(value), isText);
	}

	/**
	 * ���ϴ� �������� �����Ͽ� ��ȯ�Ѵ�.
	 *
	 * @param fmt ������ ����
	 * @param parame ������ ���� ���ڿ�
	 * @return ����� ���ڿ�
	 */
	public static String getAmtFormat(String format, double param)
	{
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(param);
	}

	/**
	 * NULL���� �������� �ٲ㼭 ��ȯ�Ѵ�.
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
	 * NULL���� �������� �ٲ㼭 ��ȯ�Ѵ�.
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
	 * oracle�� �ٹٲ���  <br>�±׷� �ٲ㼭 ��ȯ�Ѵ�.
	 *
	 * @param str ������ �ؽ�Ʈ
	 * @return    ����� ���ڿ�
	 */
	public static String nl2br(String str)
	{
		str = str.replaceAll("\r\n", "<br>");
		str = str.replaceAll("\r", "<br>");
		str = str.replaceAll("\n", "<br>");
		return str;
  }

  /**
	 * oracle �ؽ�Ʈ �Է½� (')�� (��)�� ��ȯ�Ѵ�.
	 *
	 * @param str ������ �ؽ�Ʈ
	 * @return    ����� ���ڿ�
	 */
	public static String quotation(String str)
	{
		str = str.replaceAll("'", "��");
		return str;
  }

  /** ���漼
	 * �Է� ���ڿ��� ������� ��ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ���
	 * ���ڷθ� ������ ������� ��ȣ 14�ڸ� �̾�� �Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� ������� ��ȣ ������ ���ڿ�
	 */
	public static String formatVasAccountNo(String text) {
		if (text.length() == 14) {
			text = text.substring(0, 3) + "-" + text.substring(3,6) + "-"  + text.substring(6,10) + "-"  + text.substring(10,14);
		}
		return text;
	}

  /** ���ܼ���
	 * �Է� ���ڿ��� ������� ��ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ���
	 * ���ڷθ� ������ ������� ��ȣ 14�ڸ� �̾�� �Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� ������� ��ȣ ������ ���ڿ�
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

  /** ���ܼ���
	 * �Է� ���ڿ��� ���ι�ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ���
	 * ���ڷθ� ������ ���ι�ȣ 31�ڸ� �̾�� �Ѵ�.
	 *
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� ������� ��ȣ ������ ���ڿ�
	 */
	public static String formatLvyKey(String text) {
		if (text.length() == 31) {
			text = text.substring(0, 11) + "-" + text.substring(11,15) + "-"  + text.substring(15,17) + "-"  + text.substring(17,23) 
					+ "-"  + text.substring(23,31);
		}
		return text;
	} 

    /**
     * �������˿� �ش��ϴ� �� ������
     * @param format	: date format
     * @param field
     * @param offset
     * @return �������˵� ��
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
	 * �Է� �����ڵ带 ��������� �����Ѵ�. 
	 *
	 * @param text ��ȯ�� ���ڿ�
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
        bankNM = "�ѱ�";
      } else if (bankVar.equals("002")) {
        bankNM = "���";
      } else if (bankVar.equals("003")) {
        bankNM = "���";
      } else if (bankVar.equals("004")) {
        bankNM = "����";
      } else if (bankVar.equals("005")) {
        bankNM = "��ȯ";
      } else if (bankVar.equals("006")) {
        bankNM = "����";
      } else if (bankVar.equals("007")) {
        bankNM = "����";
      } else if (bankVar.equals("008")) {
        bankNM = "������";
      } else if (bankVar.equals("009")) {
        bankNM = "����";
      } else if (bankVar.equals("010")) {
        bankNM = "������";
      } else if (bankVar.equals("011")) {
        bankNM = "������";
      } else if (bankVar.equals("012")) {
        bankNM = "����";
      } else if (bankVar.equals("013")) {
        bankNM = "����";
      } else if (bankVar.equals("014")) {
        bankNM = "����";
      } else if (bankVar.equals("015")) {
        bankNM = "����";
      } else if (bankVar.equals("016")) {
        bankNM = "������";
      } else if (bankVar.equals("017")) {
        bankNM = "������";
      } else if (bankVar.equals("019")) {
        bankNM = "����";
      } else if (bankVar.equals("020")) {
        bankNM = "�츮";
      } else if (bankVar.equals("021")) {
        bankNM = "����";
      } else if (bankVar.equals("022")) {
        bankNM = "�츮";
      } else if (bankVar.equals("023")) {
        bankNM = "����";
      } else if (bankVar.equals("024")) {
        bankNM = "�츮";
      } else if (bankVar.equals("025")) {
        bankNM = "�ϳ�";
      } else if (bankVar.equals("026")) {
        bankNM = "����";
      } else if (bankVar.equals("027")) {
        bankNM = "��Ƽ";
      } else if (bankVar.equals("028")) {
        bankNM = "����";
      } else if (bankVar.equals("029")) {
        bankNM = "����";
      } else if (bankVar.equals("030")) {
        bankNM = "����";
      } else if (bankVar.equals("031")) {
        bankNM = "�뱸";
      } else if (bankVar.equals("032")) {
        bankNM = "�λ�";
      } else if (bankVar.equals("033")) {
        bankNM = "�ϳ�";
      } else if (bankVar.equals("034")) {
        bankNM = "����";
      } else if (bankVar.equals("035")) {
        bankNM = "����";
      } else if (bankVar.equals("036")) {
        bankNM = "��Ƽ";
      } else if (bankVar.equals("037")) {
        bankNM = "����";
      } else if (bankVar.equals("038")) {
        bankNM = "����";
      } else if (bankVar.equals("039")) {
        bankNM = "�泲";
      } else if (bankVar.equals("040")) {
        bankNM = "����";
      } else if (bankVar.equals("045")) {
        bankNM = "������";
      } else if (bankVar.equals("046")) {
        bankNM = "������";
      } else if (bankVar.equals("048")) {
        bankNM = "����";
      } else if (bankVar.equals("049")) {
        bankNM = "����";
      } else if (bankVar.equals("050")) {
        bankNM = "��ȣ����";
      } else if (bankVar.equals("051")) {
        bankNM = "�ܱ���";
      } else if (bankVar.equals("053")) {
        bankNM = "��Ƽ";
      } else if (bankVar.equals("054")) {
        bankNM = "HSBC";
      } else if (bankVar.equals("055")) {
        bankNM = "����ġ";
      } else if (bankVar.equals("056")) {
        bankNM = "ABN";
      } else if (bankVar.equals("057")) {
        bankNM = "���";
      } else if (bankVar.equals("058")) {
        bankNM = "����ȣ��";
      } else if (bankVar.equals("059")) {
        bankNM = "UFJ";
      } else if (bankVar.equals("060")) {
        bankNM = "BOA";
      } else if (bankVar.equals("071")) {
        bankNM = "��ü��";
      } else if (bankVar.equals("072")) {
        bankNM = "��ü��";
      } else if (bankVar.equals("073")) {
        bankNM = "��ü��";
      } else if (bankVar.equals("074")) {
        bankNM = "��ü��";
      } else if (bankVar.equals("075")) {
        bankNM = "��ü��";
      } else if (bankVar.equals("076")) {
        bankNM = "�ź�";
      } else if (bankVar.equals("077")) {
        bankNM = "���";
      } else if (bankVar.equals("081")) {
        bankNM = "�ϳ�";
      } else if (bankVar.equals("082")) {
        bankNM = "�ϳ�";
      } else if (bankVar.equals("083")) {
        bankNM = "�츮";
      } else if (bankVar.equals("084")) {
        bankNM = "�츮";
      } else if (bankVar.equals("085")) {
        bankNM = "������";
      } else if (bankVar.equals("086")) {
        bankNM = "������";
      } else if (bankVar.equals("088")) {
        bankNM = "����";
      } else if (bankVar.equals("094")) {
        bankNM = "BCī��";
      } else if (bankVar.equals("095")) {
        bankNM = "����ī��";
      } else if (bankVar.equals("096")) {
        bankNM = "�Ե�ī��";
      } else if (bankVar.equals("097")) {
        bankNM = "�Ｚī��";
      } else if (bankVar.equals("098")) {
        bankNM = "����ī��";
      } else {
        bankNM = "����";
      }
    } else if("099".equals(bankVar)) {
      if (text.equals("0999712")) {
        bankNM = "�ѱ�";
      } else if (text.equals("0999713")) {
        bankNM = "���";
      } else if (text.equals("0999714")) {
        bankNM = "���";
      } else if (text.equals("0999715")) {
        bankNM = "��ȯ";
      } else if (text.equals("0999716")) {
        bankNM = "����";
      } else if (text.equals("0999717")) {
        bankNM = "����";
      } else if (text.equals("0999718")) {
        bankNM = "������";
      } else if (text.equals("0999719")) {
        bankNM = "����";
      } else if (text.equals("0999720")) {
        bankNM = "����";
      } else if (text.equals("0999721")) {
        bankNM = "����";
      } else if (text.equals("0999722")) {
        bankNM = "����";
      } else if (text.equals("0999723")) {
        bankNM = "����";
      } else if (text.equals("0999724")) {
        bankNM = "����";
      } else if (text.equals("0999725")) {
        bankNM = "����";
      } else if (text.equals("0999726")) {
        bankNM = "����";
      } else if (text.equals("0999727")) {
        bankNM = "����";
      } else if (text.equals("0999728")) {
        bankNM = "�츮";
      } else if (text.equals("0999729")) {
        bankNM = "����";
      } else if (text.equals("0999730")) {
        bankNM = "�츮";
      } else if (text.equals("0999731")) {
        bankNM = "����";
      } else if (text.equals("0999732")) {
        bankNM = "�츮";
      } else if (text.equals("0999733")) {
        bankNM = "�ϳ�";
      } else if (text.equals("0999734")) {
        bankNM = "��Ƽ";
      } else if (text.equals("0999735")) {
        bankNM = "����";
      } else if (text.equals("0999736")) {
        bankNM = "����";
      } else if (text.equals("0999737")) {
        bankNM = "�뵿";
      } else if (text.equals("0999738")) {
        bankNM = "�뱸";
      } else if (text.equals("0999739")) {
        bankNM = "�λ�";
      } else if (text.equals("0999740")) {
        bankNM = "�ϳ�";
      } else if (text.equals("0999741")) {
        bankNM = "����";
      } else if (text.equals("0999742")) {
        bankNM = "����";
      } else if (text.equals("0999743")) {
        bankNM = "��Ƽ";
      } else if (text.equals("0999744")) {
        bankNM = "����";
      } else if (text.equals("0999745")) {
        bankNM = "����";
      } else if (text.equals("0999746")) {
        bankNM = "�泲";
      } else if (text.equals("0999747")) {
        bankNM = "������";
      } else if (text.equals("0999748")) {
        bankNM = "������";
      } else if (text.equals("0999749")) {
        bankNM = "������";
      } else if (text.equals("0999750")) {
        bankNM = "����";
      } else if (text.equals("0999751")) {
        bankNM = "��ȣ";
      } else if (text.equals("0999752")) {
        bankNM = "��ȯ";
      } else if (text.equals("0999753")) {
        bankNM = "������";
      } else if (text.equals("0999754")) {
        bankNM = "��Ƽ";
      } else if (text.equals("0999755")) {
        bankNM = "HSBC";
      } else if (text.equals("0999756")) {
        bankNM = "����ġ";
      } else if (text.equals("0999757")) {
        bankNM = "ABN";
      } else if (text.equals("0999758")) {
        bankNM = "UFJ";
      } else if (text.equals("0999759")) {
        bankNM = "����ȣũ";
      } else if (text.equals("0999760")) {
        bankNM = "UFJ";
      } else if (text.equals("0999761")) {
        bankNM = "BOA";
      } else if (text.equals("0999762")) {
        bankNM = "��ü��";
      } else if (text.equals("0999763")) {
        bankNM = "��ü��";
      } else if (text.equals("0999764")) {
        bankNM = "��ü��";
      } else if (text.equals("0999765")) {
        bankNM = "��ü��";
      } else if (text.equals("0999766")) {
        bankNM = "��ü��";
      } else if (text.equals("0999767")) {
        bankNM = "�ź�";
      } else if (text.equals("0999768")) {
        bankNM = "���";
      } else if (text.equals("0999769")) {
        bankNM = "�ϳ�";
      } else if (text.equals("0999770")) {
        bankNM = "�ϳ�";
      } else if (text.equals("0999771")) {
        bankNM = "�츮";
      } else if (text.equals("0999772")) {
        bankNM = "�츮";
      } else if (text.equals("0999773")) {
        bankNM = "KT";
      } else if (text.equals("0999774")) {
        bankNM = "������";
      } else if (text.equals("0999775")) {
        bankNM = "������";
      } else if (text.equals("0999776")) {
        bankNM = "������";
      } else if (text.equals("0999777")) {
        bankNM = "����";
      } else {
        bankNM = text;
      }
    } else {
      bankNM = text;
    }

		return bankNM;
	}


  
    //å���� �����ڵ� �ѱ� ��ȯ(�泲����)
  public static String getCodeName(String text) {
		String rtnName = "";
		if ("G44".equals(text)) {
			rtnName = "��� ���� �ŷ�";
		} else if ("G48".equals(text)) {
			rtnName = "��� �Ա� �ŷ�";
		} else if ("G49".equals(text)) {
			rtnName = "�ܸ� �����ѵ� �ʰ�";
		} else if ("G45".equals(text)) {
			rtnName = "���ް����ܾ�1�ʰ�";
		} else if ("G46".equals(text)) {
			rtnName = "���ް����ܾ�2�ʰ�";
		} else if ("G47".equals(text)) {
			rtnName = "���ް����ܾ�3�ʰ�";
		} else {
			rtnName = text;
		}
		return rtnName;
	}
	
	// å���� �����ڵ� 3���ھ� �ڸ���
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