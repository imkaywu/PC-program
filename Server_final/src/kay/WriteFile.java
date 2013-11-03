package kay;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WriteFile implements Runnable {
	private String key, value,path="",tempPath="";
	private int num;
	private HashMap<String, String> hashMap = new HashMap<String, String>();

	public WriteFile(int num, HashMap<String, String> hashMap) {
		this.num = num;
		this.hashMap = hashMap;
		
		tempPath=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			tempPath=URLDecoder.decode(tempPath,"utf-8");
		} catch (UnsupportedEncodingException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		path=tempPath.substring(0,tempPath.lastIndexOf("/"))+ "/file/";
	}

	@Override
	public void run() {
		writeAnswerToFile(hashMap);
	}

	private void writeAnswerToFile(HashMap<String, String> hashMap) {
		Iterator<Entry<String, String>> iter = hashMap.entrySet().iterator();
		File file = new File(path + "FileAnswer" + num + ".txt");

		if (file.exists()) {
			BufferedWriter bufferedWriter = null;
			try {
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true)));
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					key = (String) entry.getKey();
					value = (String) entry.getValue();
					//System.out.println(key + ":" + value);
					bufferedWriter.write(key + ":" + value);
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			FileWriter fw;
			try {
				fw = new FileWriter(path + "FileAnswer" + num + ".txt");
				BufferedWriter bw = new BufferedWriter(fw);
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					key = (String) entry.getKey();
					value = (String) entry.getValue();
					bw.write(key + ":" + value);
					bw.newLine();
				}
				bw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 建立FileWrite对象,并设定由fw对象变量引用
		}
	}
}
