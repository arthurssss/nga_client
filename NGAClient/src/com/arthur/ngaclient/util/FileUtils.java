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
	 * 获取文件的大小
	 * 
	 * @param 文件全路径
	 * @return 文件大小
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
	 * 在SD卡上创建文件
	 * 
	 * @param 文件全路径
	 * @return 创建成功的文件
	 * @throws IOException
	 */
	public static File createFile(String fileNameWithPath) throws IOException {
		File file = new File(fileNameWithPath);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirNameWithPath
	 *            文件夹全路径
	 * @return 创建成功的文件夹
	 */
	public static File createDir(String dirNameWithPath) {
		File dir = new File(dirNameWithPath);
		dir.mkdir();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileNameWithPath
	 * @return 是否存在
	 */
	public static boolean isFileExist(String fileNameWithPath) {
		File file = new File(fileNameWithPath);
		return file.exists();
	}

	/**
	 * 获取临时文件的大小
	 * 
	 * @param urlStr
	 *            文件网络路径
	 * @param path
	 *            文件路径
	 * @param index
	 *            第几个临时文件
	 * @return 文件大小
	 */
	public static int getTmpFileLength(String orgNameWithPath, int index) {
		int length = (int) FileUtils.getFileSize(orgNameWithPath + ".tmp"
				+ index);
		return length;
	}

	/**
	 * 合并文件
	 * 
	 * @param 所生成的文件目录
	 * @param 所需合并的文件
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
	 * 批量删除文件
	 * 
	 * @param 文件路径
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
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 对文件进行加密
	 * 
	 * @param String
	 *            oldFile 原始要加密的文件
	 * @param String
	 *            newFile 加密后的文件
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
	 * 对文件进行解密
	 * 
	 * @param String
	 *            oldFile 原始要解密的文件
	 * @param String
	 *            newFile 解密后的文件
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
	 * 读取文件为一个内存字符串,保持文件原有的换行格式
	 * 
	 * @param filePath
	 *            文件路径
	 * @param charset
	 *            文件字符集编码
	 * @return 文件内容的字符串
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
			Log.e("", "读取文件为一个内存字符串失败，失败原因是使用了不支持的字符编码" + charset + "  "
					+ e.toString());
		} catch (FileNotFoundException e) {
			Log.e("", "读取文件为一个内存字符串失败，失败原因所给的文件" + file + "不存在！    " + e.toString());
		} catch (IOException e) {
			Log.e("", "读取文件为一个内存字符串失败，失败原因是读取文件异常！    " + e.toString());
		}
		return sb.toString();
	}

	/**
	 * 将字符串存储为一个文件，当文件不存在时候，自动创建该文件，当文件已存在时候，重写文件的内容，特定情况下，还与操作系统的权限有关。
	 * 
	 * @param text
	 *            字符串
	 * @param distFile
	 *            存储的目标文件
	 * @return 当存储正确无误时返回true，否则返回false
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
			char buf[] = new char[1024 * 64]; // 字符缓冲区
			int len;
			while ((len = br.read(buf)) != -1) {
				bw.write(buf, 0, len);
			}
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			flag = false;
			Log.e("", "将字符串写入文件发生异常！   " + e.toString());
		}
		return flag;
	}

	/**
	 * 修改文件名
	 * 
	 * @param oldFileName
	 *            ：需要修改的文件名（含路径）
	 * @param newFileName
	 *            ： 目标文件名（含路径）
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
	 * 删除指定文件夹：步骤：先删除文件夹下所有文件，再删除文件夹
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定文件夹下所有的文件
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
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
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
