/**********************************************************************************
 * ȭ �� �� �� : StringUtil.java
 * �ֱ� ������ : 2007-04-06
 * ���� �ۼ��� :
 * �ֱ� ������ : �ٸ��ν�
 * ��       �� : ������� ��ƿ��Ƽ
 **********************************************************************************/

package com.etax.util;

import java.io.*;
import java.text.*;
import java.util.*;

import com.initech.safedb.SimpleSafeDB;
import com.initech.safedb.common.SafeDBException;

import java.security.MessageDigest;

/* ���ڿ��� ���ۿ� ���õ� ������ Method�� ������ ���� ��ƿ��Ƽ Ŭ�����̴�. */
public class StringUtil {

	final static int rawOffset = 9 * 60 * 60 * 1000;

    /* ��ȣȭ */
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

	 /* ��ȣȭ */
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
	/* ������ ���ڸ� ������ ���̸�ŭ �ݺ��Ͽ� ���ڿ��� ������ ��, ��ȯ�Ѵ�.
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

	/* ������ ���ڿ��� ������ ���̸�ŭ �ݺ��Ͽ� ���ڿ��� ������ ��, ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ� �߿� Backslash ('\')�� �� Double-Quotes ���ڿ��� ���� ��� Double-Quotes�� �ѷ� �� ���ڿ��� ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ� �߿� Quote�� �ִ��� �˻��Ѵ�.
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

	/* �Է� ���ڿ��� �������� ������ ���ڸ� ������ ���̸�ŭ ä���� ��ȯ�Ѵ�.
	 * <p>��� ����,</p>
	 * <p><code>leftFill("12", 4, '0')</code> returns "0012", while</p>
	 * <p><code>leftFill("12345", 4, '0')</code> returns "12345".</p>
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


	/* �Է� ���ڿ��� �������� '0'�� ������ ���̸�ŭ ä�� ��ȯ�Ѵ�. leftFill() Method�� ����ϴ� �Ͱ� �����ϴ�.
	 * @param text ������ ���ڿ�
	 * @param length �ݺ��� ����
	 * @return �������� '0'�� ������ ���̸�ŭ ä�� ���ڿ�
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
    return rightFill(toFullChar(text), length/2, '��');
  }

  //�������� ��ȯ
  public static String toFullChar(String src)  {
        // �Էµ� ��Ʈ���� null �̸� null �� ����
        if (src == null)
            return null;
        // ��ȯ�� ���ڵ��� �׾Ƴ��� StringBuffer �� �����Ѵ�
        StringBuffer strBuf = new StringBuffer();
        char c = 0;
        int nSrcLength = src.length();
        for (int i = 0; i < nSrcLength; i++)  {
            c = src.charAt(i);
            //�����̰ų� Ư�� ���� �ϰ��.
            if (c >= 0x21 && c <= 0x7e)
            {
                c += 0xfee0;
            }
            //�����ϰ��
            else if (c == 0x20)
            {
                c = 0x3000;
            }
            // ���ڿ� ���ۿ� ��ȯ�� ���ڸ� �״´�
            strBuf.append(c);
        }
        return strBuf.toString();
    }

	/* �Է� ���ڿ����� �յ��� Whitespace(<code>Character.isWhitespace</code>��
	 * ���� �ڵ�)�� �߶� ��ȯ�Ѵ�. �̰��� <code>String.trim</code> Method��
	 * ���� �ʴ�. <code>String.trim</code>�� ���� ����(Space) �ڵ��� ASCII
	 * ������ Whitespace�� �ν��ϱ� �����̴�.
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

		while (left < text.length() && Character.isWhitespace(text.charAt(left))) {
			++left;
		}
		int right = text.length();

		while (right > left	&& Character.isWhitespace(text.charAt(right - 1))) {
			--right;
		}
		return text.substring(left, right);
	}

	/* �Է� ���ڿ� �߿��� Whitespace(<code>Character.isWhitespace</code>�� ���� �ڵ�)�� ����(Space)���� �ٲ� ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ� �߿��� Whitespace(<code>Character.isWhitespace</code>�� ���� �ڵ�)�� ��� �����Ͽ� ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ��� Integer���� �˻��Ѵ�. <code>Integer.parseInt</code>�� ���ܸ� �߻���Ű�� ���� ��츦 Integer�� �����ϰ�, <code>
	 * NumberFormatException</code>�� �߻��� ��� Integer�� �ƴ϶�� �����Ѵ�.
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

	/* �Է� ���ڿ��� Long���� �˻��Ѵ�. <code>Long.parseLong</code>�� ���ܸ� �߻���Ű�� ���� ��츦 Long���� �����ϰ�, <code>
	 * NumberFormatException</code>�� �߻��� ��� Long�� �ƴ϶�� �����Ѵ�.
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

  /* ������ ����� ��ȯ  */
  public static int plusNum(int num){
    if(num < 0) num = num * -1;
    return num;
  }

  public static long plusNum(long num){
    if(num < 0) num = num * -1;
    return num;
  }

	/* �Է� ���ڿ��� ������ �и��ڷ� �и��� ���ڿ� �迭�� ��ȯ�Ѵ�. �̶� Vector Ŭ������ �̿��Ѵ�.
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

	/* �Է� Enumeration�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
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

	/* �Է� Vector�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
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

	/* �Է� Vector�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 * @param vector �ϳ��� ���ڿ��� ��ĥ Vector
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
	 */
	public static String join(Vector<String> vector, String delim) {
		return join(vector, delim, 0, vector.size());
	}

	/* �Է� ���ڿ� �迭�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 * @param texts �ϳ��� ���ڿ��� ��ĥ ���ڿ� �迭
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @param begin ���� �ε���
	 * @param end ���� �ε���
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
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

	/* �Է� ���ڿ� �迭�� ������ �и��ڷ� �и��� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 * @param texts �ϳ��� ���ڿ��� ��ĥ ���ڿ� �迭
	 * @param delim ��ĥ ��� ������ ��Ҹ��� �и��ڷ� �־��� ����
	 * @return ������ �и��ڷ� �и��� �ϳ��� ���ڿ�
	 */
	public static String join(String[] texts, String delim) {
		return join(texts, delim, 0, texts.length);
	}

	/* �Է� ���ڿ� �迭�� �����Ͽ� �ϳ��� ���ڿ��� ��ȯ�Ѵ�.
	 * @param texts �ϳ��� ���ڿ��� ��ĥ ���ڿ� �迭
	 * @return ����� �ϳ��� ���ڿ�
	 */
	public static String join(String[] texts) {
		return join(texts, null);
	}

	/* Exception ��ü�ν� ������ ������ ���ڿ��� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * @param e ������ ������ Exception ��ü
	 * @return ���� ���� ������ ��� �ִ� ���ڿ�
	 */
	public static String stackTrace(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	/* �Է� ���ڿ����� Ư�� ���ڿ��� ������ ���ڿ��� �����Ͽ� ��ȯ�Ѵ�.
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

  /* �Էµ� �����͸� sourceEncode ���� targetEncode �� �ڵ庯ȯ �� ��ȯ
   * @param strSource �Էµ����� String
   * @param sourceEncode �ҽ����ڵ� String
   * @param targetEncode ������ڵ� String
   * @return String ��ȯ�� ������
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

  /* �Էµ� �����͸� 8859_1 ==> KSC5601
   * @param strSource �Էµ����� String
   * @return String ��ȯ�� ������
   */
  public static String uni2ksc(String strSource) {
    return encodingTo(strSource, "8859_1", "KSC5601");
  }

  /* �Էµ� �����͸� KSC5601 ==> 8859_1
   * @param strSource �Էµ����� String
   * @return String ��ȯ�� ������
   */
  public static String ksc2uni(String str) {
    return encodingTo(str, "KSC5601", "8859_1");
  }

	

  public static int lengthByte(String text) {
    return ksc2uni(text).length();
  }

  /* �Է� ���ڿ��� Unicode (Multi-Byte �ڵ�) ���ڿ��� �ջ����� �ʰ�(�ѱ� ���ڸ� �ڸ�) Byte ���� ���� �߶� ��ȯ�Ѵ�.
	 * @param text �ڸ� ���ڿ�
	 * @param start ���� �ε���
	 * @param end ���� �ε���
	 * @return �߶��� ���ڿ�
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

	/* �Է� ���ڿ��� ���� Character-Set ���ڿ��� ���ڵ� ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ��� �ѱ� Character-Set ���ڿ��� ���ڵ� ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ��� MD5�� ����Ͽ� ��ȯ�Ѵ�.
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
	static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/* �Է� Byte �迭�� HEX ���� ���ڿ��� ��ȯ�Ͽ� ��ȯ�Ѵ�.
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

	/* �Է� HEX ���ڿ��� Byte �迭�� ��ȯ�Ͽ� ��ȯ�Ѵ�.
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

	/* �Է� ���ڿ��� �ֹε�� ��ȣ �������� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ��� �ֹε�Ϲ�ȣ�� ���ڷθ� ������ 13�ڸ� �̾�� �Ѵ�.
	 * @param text ��ȯ�� ���ڿ�
	 * @return �߰��� '-'�� �� �ֹε�� ��ȣ ������ ���ڿ�
	 */
	public static String formatResidentNumber(String text) {
		if (text.length() == 13) {
			text = text.substring(0, 6) + "-" + text.substring(6);
		}
		return text;
	}

	/* �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 * @param obj ��ȭ �������� ��ȯ�� ���ڿ�
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
	 */
	public static String formatNumber(Object obj) {
		return formatNumber(obj.toString());
	}

	/* �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 * @param value ��ȭ �������� ��ȯ�� ���ڿ�
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
	 */
	public static String formatNumber(int value) {
		return formatNumber(String.valueOf(value));
	}

	/* �Է� ���ڿ��� ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�Ͽ� ��ȯ�Ѵ�. �Է� ���ڿ��� ���߸� �����Ǿ� �־�� �Ѵ�.
	 * @param text ��ȭ �������� ��ȯ�� ���ڿ�
	 * @return ��ȭ ����(3�ڸ����� ',' ���ڷ� �и�)���� ��ȯ�� ���ڿ�
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

	/* ��ȭ ������ �Է� ���ڿ��� �Ϲ� ���� �������� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * @param text ��ȭ ������ ���ڿ�
	 * @return �Ϲ� ���� ������ ���ڿ�
	 */
	public static String unformatNumber(String text) {
		return replace(text, ",", "");
	}

	/**
	 * �Է� ���ڿ��� ������ ���Ͽ� �°� �Ľ��Ͽ� Date ��ü�� ��´�. ����� Date ��ü�� ������ Format�� �°� ��¥ ǥ�� �������� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * @param text ��¥ ������ ���ڿ�
	 * @param pattern �Ľ��� ����
	 * @param format ���ο� Format ���ڿ�
	 * @return ���ο� ��¥ ǥ�� �������� ��ȯ�� ���ڿ�
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

	/* how�� Today�� �������� �Ϸ� ���� ���Ϸ��� -1, ��Ʋ ���� -2, ��Ʋ �Ĵ� 2�� �־��ش�.
   * ex1. getPrevDayDate("yyyyMMdd", -1)
   * ex2. getPrevDayDate("yyyy/MM/dd HH:mm:ss", 5)
	 */
  public static String getPrevDayDate(int how, String pattern) {
    Date date = new Date();
    long sum = how*24*60*60*1000;
    return formatDate(new Date(date.getTime()+sum), pattern);
  }

	/* �Է� Date ��ü�� ������ Format ���ڿ��� �°� ��¥ ǥ�� �������� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * @param date Date ��ü
	 * @param format ��¥ Format ���ڿ�
	 * @return ��¥ ǥ�� �������� ��ȯ�� ���ڿ�
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/* Ư�� ��-���� ������ ��¥�� ����Ͽ� ��´�.
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

	/* Ư�� ��-���� ������ ��¥�� ����Ͽ� ��´�.
	 * @param year Ư�� ��
	 * @param month Ư�� ��
	 * @return �ش� ��-���� ������ ��¥
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

	/* ��¥ ������ �Է� ���ڿ��� �м��Ͽ� ������ ���ڿ��� ��´�.
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
		return zeroFill(String.valueOf(year), 2) + "/" + zeroFill(String.valueOf(month), 2) + "/" + zeroFill(String.valueOf(date), 2);
	}

	/* ��¥ ������ �Է� ���ڿ��� �м��Ͽ� �ش� �� ���ڿ��� ��´�.
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

	/* ��¥ ������ �Է� ���ڿ��� �м��Ͽ� ������ ���ڿ��� ��´�.
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
		return zeroFill(String.valueOf(year), 2) + "/" + zeroFill(String.valueOf(month), 2) + "/" + zeroFill(String.valueOf(date), 2);
	}

	/* �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
	 * @param query ������ ���� ���ڿ�
	 * @param parameter Ư�� ����(����)
	 * @param value ���ϰ� ������ ��
	 * @param isText ���ڿ� �������� ���� (���ڿ��� ��� "'" ���� �߰�)
	 * @return ����� ���� ���ڿ�
	 */
	public static String replaceSQL(String query, String parameter, String value, boolean isText) {
		return isText
		? replace(query, parameter, "'" + value + "'")
		: replace(query, parameter, value);
	}

	/* �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
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

	/* �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
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

	/* �Է� �������� Ư���� ����(����)�� ã�� ���ϴ� ������ �����Ͽ� ��ȯ�Ѵ�.
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

	/* ���ϴ� �������� �����Ͽ� ��ȯ�Ѵ�.
	 * @param fmt ������ ����
	 * @param parame ������ ���� ���ڿ�
	 * @return ����� ���ڿ�
	 */
	public static String getAmtFormat(String format, double param) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(param);
	}

	/* NULL���� �������� �ٲ㼭 ��ȯ�Ѵ�.
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
        throw new NumberFormatException("���� [" + charData[i] + "]�� ���ڰ� �ƴմϴ�.");

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
   * HHmmss ������ �ð����� ��ȯ�Ѵ�.
   * @return                ��ȯ �ð�
   * @exception             
   * @since                 1.0
  **************************************************************************/
  public static String getCurrentTime(){
	  return	getFormatDate("HHmmss", 0, 0);
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
	
}