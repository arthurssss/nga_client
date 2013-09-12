package com.arthur.ngaclient.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.os.Environment;
import android.util.Log;

public class FileUtils {
	static final byte[] KEYVALUE = "6^)(9-p35@%3#4S!4S0)$Y%%^&5(j.&^&o(*0)$Y%!#O@*GpG@=+@j.&6^)(0-=+"
			.getBytes();
	static final int BUFFERLEN = 1024;

	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/";

	/**
	 * ��ȡ�ļ��Ĵ�С
	 * 
	 * @param �ļ�ȫ·��
	 * @return �ļ���С
	 */
	public static long getFileSize(String fileNameWithPath) {
		File file = new File(fileNameWithPath);
		if (file.exists()) {
			return file.length();
		} else {
			return 0;
		}
	}

	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @param �ļ�ȫ·��
	 * @return �����ɹ����ļ�
	 * @throws IOException
	 */
	public static File createFile(String fileNameWithPath) throws IOException {
		File file = new File(fileNameWithPath);
		file.createNewFile();
		return file;
	}

	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirNameWithPath
	 *            �ļ���ȫ·��
	 * @return �����ɹ����ļ���
	 */
	public static File createDir(String dirNameWithPath) {
		File dir = new File(dirNameWithPath);
		dir.mkdir();
		return dir;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 * 
	 * @param fileNameWithPath
	 * @return �Ƿ����
	 */
	public static boolean isFileExist(String fileNameWithPath) {
		File file = new File(fileNameWithPath);
		return file.exists();
	}

	/**
	 * ��ȡ��ʱ�ļ��Ĵ�С
	 * 
	 * @param urlStr
	 *            �ļ�����·��
	 * @param path
	 *            �ļ�·��
	 * @param index
	 *            �ڼ�����ʱ�ļ�
	 * @return �ļ���С
	 */
	public static int getTmpFileLength(String orgNameWithPath, int index) {
		int length = (int) FileUtils.getFileSize(orgNameWithPath + ".tmp"
				+ index);
		return length;
	}

	/**
	 * �ϲ��ļ�
	 * 
	 * @param �����ɵ��ļ�Ŀ¼
	 * @param ����ϲ����ļ�
	 */
	public static void mergeFiles(String outFile, String[] files) {
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(outFile).getChannel();
			for (String f : files) {
				@SuppressWarnings("resource")
				FileChannel fc = new FileInputStream(f).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(1024 * 20);
				while (fc.read(bb) != -1) {
					bb.flip();
					outChannel.write(bb);
					bb.clear();
				}
				fc.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

	/**
	 * ����ɾ���ļ�
	 * 
	 * @param �ļ�·��
	 */
	public static boolean deleteFiles(String[] files) {
		boolean flag = true;
		for (int i = 0; i < files.length; i++) {
			if (!deleteFile(files[i])) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param sPath
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * ���ļ����м���
	 * 
	 * @param String
	 *            oldFile ԭʼҪ���ܵ��ļ�
	 * @param String
	 *            newFile ���ܺ���ļ�
	 * @return
	 */
	public static void encryptFile(String oldFile, String newFile)
			throws Exception {
		FileInputStream in = new FileInputStream(oldFile);
		File file = new File(newFile);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c, pos, keylen;
		pos = 0;
		keylen = KEYVALUE.length;
		byte buffer[] = new byte[BUFFERLEN];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++) {
				buffer[i] ^= KEYVALUE[pos];
				out.write(buffer[i]);
				pos++;
				if (pos == keylen)
					pos = 0;
			}
		}
		in.close();
		out.close();
	}

	/**
	 * ���ļ����н���
	 * 
	 * @param String
	 *            oldFile ԭʼҪ���ܵ��ļ�
	 * @param String
	 *            newFile ���ܺ���ļ�
	 * @return
	 */
	public static void decryptFile(String oldFile, String newFile)
			throws Exception {
		FileInputStream in = new FileInputStream(oldFile);
		File file = new File(newFile);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c, pos, keylen;
		pos = 0;
		keylen = KEYVALUE.length;
		byte buffer[] = new byte[BUFFERLEN];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++) {
				buffer[i] ^= KEYVALUE[pos];
				out.write(buffer[i]);
				pos++;
				if (pos == keylen)
					pos = 0;
			}
		}
		in.close();
		out.close();
	}

	/**
	 * ��ȡ�ļ�Ϊһ���ڴ��ַ���,�����ļ�ԭ�еĻ��и�ʽ
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param charset
	 *            �ļ��ַ�������
	 * @return �ļ����ݵ��ַ���
	 */
	public static String file2String(String filePath, String charset) {
		File file = new File(filePath);
		StringBuffer sb = new StringBuffer();
		try {
			@SuppressWarnings("resource")
			LineNumberReader reader = new LineNumberReader(new BufferedReader(
					new InputStreamReader(new FileInputStream(file), charset)));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
		} catch (UnsupportedEncodingException e) {
			Log.e("", "��ȡ�ļ�Ϊһ���ڴ��ַ���ʧ�ܣ�ʧ��ԭ����ʹ���˲�֧�ֵ��ַ�����" + charset + "  "
					+ e.toString());
		} catch (FileNotFoundException e) {
			Log.e("", "��ȡ�ļ�Ϊһ���ڴ��ַ���ʧ�ܣ�ʧ��ԭ���������ļ�" + file + "�����ڣ�    " + e.toString());
		} catch (IOException e) {
			Log.e("", "��ȡ�ļ�Ϊһ���ڴ��ַ���ʧ�ܣ�ʧ��ԭ���Ƕ�ȡ�ļ��쳣��    " + e.toString());
		}
		return sb.toString();
	}

	/**
	 * ���ַ����洢Ϊһ���ļ������ļ�������ʱ���Զ��������ļ������ļ��Ѵ���ʱ����д�ļ������ݣ��ض�����£��������ϵͳ��Ȩ���йء�
	 * 
	 * @param text
	 *            �ַ���
	 * @param distFile
	 *            �洢��Ŀ���ļ�
	 * @return ���洢��ȷ����ʱ����true�����򷵻�false
	 */
	public static boolean string2File(String text, File distFile) {
		if (!distFile.getParentFile().exists())
			distFile.getParentFile().mkdirs();
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean flag = true;
		try {
			br = new BufferedReader(new StringReader(text));
			bw = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024 * 64]; // �ַ�������
			int len;
			while ((len = br.read(buf)) != -1) {
				bw.write(buf, 0, len);
			}
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			flag = false;
			Log.e("", "���ַ���д���ļ������쳣��   " + e.toString());
		}
		return flag;
	}

	/**
	 * �޸��ļ���
	 * 
	 * @param oldFileName
	 *            ����Ҫ�޸ĵ��ļ�������·����
	 * @param newFileName
	 *            �� Ŀ���ļ�������·����
	 * @return
	 */
	public static boolean renameFile(String oldFileName, String newFileName) {
		File oldFile = new File(oldFileName);
		while (!oldFile.renameTo(new File(newFileName))) {
			oldFile.renameTo(new File(newFileName));
		}
		return true;
	}

	/**
	 * ɾ��ָ���ļ��У����裺��ɾ���ļ����������ļ�����ɾ���ļ���
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ��ָ���ļ��������е��ļ�
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = true;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}

	public static boolean delAllFileInFolders(String[] folders) {
		boolean flag = true;
		for (int i = 0; i < folders.length; i++) {
			if (!delAllFile(folders[i])) {
				flag = false;
			}
		}
		return flag;
	}
}
