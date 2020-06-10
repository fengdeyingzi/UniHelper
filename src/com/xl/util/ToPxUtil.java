package com.xl.util;

import com.xl.game.math.Str;

public class ToPxUtil {

	public static String topx(String text){
		StringBuffer buffer = new StringBuffer();
		int start = 0;
		int end = 0;
		int type = 0;
		int tempi = 0;
		String[] items = text.split("\n");
		for(int i=0;i<text.length();i++){
			char c = text.charAt(i);
			System.out.println(c);
			switch (type) {
			case 0:
				if(searchTextLine(text, i, "rpx")){
					start = text.substring(i).indexOf("rpx")+i;
					tempi = start+2;
					System.out.println("start = "+start);
					for(int ii=start-1;ii>0;ii--){
						if(text.charAt(ii)>='0' && text.charAt(ii)<='9'){
							System.out.println("------");
						}
						else{
							start = ii+1;
							break;
						}
						
					}
					try{
						if(start>0)
						buffer.append(text.substring(i, start));
						else{
							System.out.println("转换出错："+i+" "+start);
						}
					}catch(Exception e){
						System.out.println("转换出错："+i+" "+start);
					}
					System.out.println("atoi "+start+" "+text.charAt(start));
					int num = Str.atoi(text.substring(start));
					buffer.append(""+num/2);
					buffer.append("px");
					i = tempi;
				}
				else {
					buffer.append(c);
				}
				break;

			default:
				break;
			}
			
			
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	
	//判断当前行是否存在指定文字
		public static boolean searchTextLine(String text,int index, String searchText){
			String lineText = text.substring(index);
			return lineText.indexOf(searchText)>=0;
		}
		
		//获取当前行的文字
		public static String getLine(String text,int index){
			int start = index;
			int end = 0;
			System.out.println("sta = "+start);
			for(int i=index;i>=0;i--){
				char c = text.charAt(i);
				if(c=='\n'){
					start = i+1;
					break;
				}
				if(i==1){
					start = i;
				}
			}
			System.out.println("start = "+start);
			for(int i=start;i<text.length();i++){
				char c = text.charAt(i);
				if(c=='\n' || (text.length()-1==i)){
					end = i;
					break;
				}
			}
			System.out.println("substring "+start+" "+end);
			return text.substring(start,end);
		}
		//从当前位置开始读取json代码
		public static String getJSONCode(String text, int index){
			int start = index;
			int end = 0;
			int leve = 0;
			boolean isLine=false;
			boolean isChar = false;
			StringBuffer buffer = new StringBuffer();
			for(int i=index;i>=0;i++){
				
				char c = text.charAt(i);
				System.out.println(c);
				if(c=='{'){
					start = i+1;
					leve = 1;
					buffer.append(c);
					buffer.append("\n");
					break;
					
				}
			}
			
			for(int i=start;i<text.length();i++){
				char c = text.charAt(i);
				
				if(c=='{'){
					leve++;
					buffer.append("{");
				}
				else if(c=='}'){
					leve--;
					buffer.append("}");
				}
				else if(c=='\"'){
					if(!isChar)
					isChar = true;
					else
						isChar = false;
				}
				else if(isChar && ((c>='a' && c<='z') || (c>='A' && c<='Z'))){
					buffer.append(c);
					isLine = false;
				}
				else if(c==':'){
					buffer.append(c);
//					buffer.append("\"\"");
				}
				else if(c=='\n' || c=='/' || Str.checkCh(""+c)){
					if(!isLine){
						buffer.append(",\n");
						isLine = true;
					}
					else{
						
					}
					
					
				}
				System.out.println("c="+c+" leve="+leve);
				if(leve==0){
					end = i;
					break;
				}
				
				
				if(c=='#' || c=='*'){
					if(leve>0){
						for(int ii=0;ii<leve;ii++){
							
							buffer.append("    }");
						}
					}
					end = i;break;
				}
			}
			System.out.println("getJson "+start+ " "+end);
			return buffer.toString();
		}
		
		//提取当前行的文字 并去除*
		public static String getLineTitle(String text,int index){
			String temp = getLine(text,index);
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<temp.length();i++){
				char c = temp.charAt(i);
				if(c != '*' && c!='\\'){
					buffer.append(c);
				}
				
			}
			return buffer.toString();
		}
}
