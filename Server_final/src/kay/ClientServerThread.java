package kay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;

class ClientServerThread implements Runnable {// Thread or Runnable?
	private static boolean state = true,firstRead=true,firstTime=true;// state true means there is no popcanvas,firstTime true means the first time pop idontknow
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private String path,tempPath;
	private ArrayList<String> userList = new ArrayList<String>();
	private int num = 1,totalNum=50,percent=50;
	private static PopCanvas popCanvas;
	private static CanvasRec canvas;

	public ClientServerThread() throws IOException {
	}

	public void setSocket(Socket socket) throws IOException {
		client = socket;
		in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
		out = new PrintWriter(client.getOutputStream(), true);
		
		tempPath=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			tempPath=URLDecoder.decode(tempPath,"utf-8");
		} catch (UnsupportedEncodingException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		path=tempPath.substring(0,tempPath.lastIndexOf("/"))+ "/file/";
	}

	public void run() {
		String flag = "", id = "", answer = "", question = "";
		ArrayList<String> arrayListS = new ArrayList<String>();
		if(firstRead){
			initValues();
			firstRead=!firstRead;
		}
		try {
			arrayListS = decodeString(in.readLine());// obtain flag,id,answer
														// and question
			flag = arrayListS.get(0);
			id = arrayListS.get(1);
			answer = arrayListS.get(2);
			question = arrayListS.get(3);
			//System.out.println(flag + ":" + id);

			if ("answer".equals(flag)) {
				printCanvas(id, answer);
			} else if ("question".equals(flag)) {
				writeQuestionToFile(id + ":" + question);
			} else if ("account".equals(flag)) {
				writeUserToFile(id);
			} else if ("idontknow".equals(flag)){
				initValues();
				if(userList.size()>=2/*>totalNum*percent/100*/){
					//pop up a window to remind the teacher!
					if(firstTime){
						new IDontKnowWin(userList);
						setFirstTime();
					}
				}
				//System.out.println(""+userList.size());
			}
			out.println("TRANSFER_SUCCESS");
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initValues() {
		Properties property = new Properties();
		String trait = null;
		try {
			property.load(new FileInputStream("properties"));
			if(property.containsKey("totalNum")){
				trait = property.getProperty("totalNum");
				if (!"".equals(trait)) {//have key and value
					totalNum = Integer.parseInt(trait);
				}
			}
			if(property.containsKey("percent")){
				trait = property.getProperty("percent");
				if (!"".equals(trait)) {//have key and value
					percent = Integer.parseInt(trait);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> decodeString(String str) {
		String flag, id, answer = null, question = null;
		ArrayList<String> al = new ArrayList<String>();
		
		int firstColon = str.indexOf(':');
		int secondColon = str.indexOf(':', firstColon + 1);
		flag = str.substring(0, firstColon);
		id = str.substring(firstColon + 1, secondColon);
		if(flag.equals("idontknow")){
			if(!userList.contains(id)){
				userList.add(id);
			}
		}
		else if(flag.equals("answer")){
			answer = str.substring(secondColon + 1);
		}
		else if(flag.equals("question")){
			question = str.substring(secondColon + 1);
		}
		
		al.add(flag);
		al.add(id);
		al.add(answer);
		al.add(question);
		return al;
	}

	private void printCanvas(String id, String answer)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Integer> al = new ArrayList<Integer>();
		ArrayList<String> aL = new ArrayList<String>();
		if (state) {
			popCanvas = new PopCanvas(num);
			setState();
			num++;
		}
		canvas = popCanvas.getCanvas();
		synchronized (this) {
			al = popCanvas.dataHandler(id, answer);
			aL = popCanvas.getList();
			canvas.setValue(al, aL);
			canvas.computeValue();
			canvas.repaint();
		}
	}

	private void writeQuestionToFile(String str) {
		File file = new File(path + "FileQuestion.txt");
		if (file.exists()) {
			try {
				BufferedWriter bufferedWriter = null;
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true)));
				bufferedWriter.write(str);
				bufferedWriter.newLine();
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
				fw = new FileWriter(path + "FileQuestion.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(str);
				bw.newLine();
				bw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 建立FileWrite对象,并设定由fw对象变量引用
		}
	}

	private void writeUserToFile(String id) {
		File file = new File(path + "FileUser.txt");
		if (file.exists()) {
			BufferedWriter bufferedWriter = null;
			try {
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true)));
				bufferedWriter.write(""+id);
				bufferedWriter.newLine();
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
				fw = new FileWriter(path + "FileUser.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(id);
				bw.newLine();
				bw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 建立FileWrite对象,并设定由fw对象变量引用
		}
	}

	static boolean getState() {
		return state;
	}

	static void setState() {
		state = !state;
	}
	
	static void setFirstTime(){
		firstTime=!firstTime;
	}

}
