package com.jh;

public class Dealer implements Dealable{

	@Override
	public String dealChar(int color) {
		int alpha = (color >> 24) & 0xFF;
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;
//		System.out.println(red);
		
		int value = (red+blue+green)/3*(alpha/0xFF);
		int gray = (red*299+green*587+blue*114)/1000;
		char[] ch = "@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".toCharArray();
		int[] indexCh = new int[ch.length];
		for(int i = 0;i < indexCh.length;i++) {
			indexCh[i] = (int)((255.0/indexCh.length)*(i));
		}
		
		// TODO Auto-generated method stub
//		System.out.println(color.);
		/*
		 * if(value > 220) { return "�� "; } if(value > 190) { return "-"; } if(value >
		 * 160) { return "+"; } if(value > 130) { return "="; } if(value > 100) { return
		 * "$"; } if(value > 70) { return "%"; } if(value > 40) { return "#"; } return
		 * "@";
		 */
		char[] c = {' ', '`', '.', '^', ',', ':', '~', '"', '<', '!', 'c', 't', '+', '{', 'i', '7', '?','u', '3', '0', 'p', 'w', '4', 'A', '8', 'D', 'X', '%', '#', 'H', 'W', 'M'};
		int[] indexC = {0, 5, 7, 9, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43,45, 47, 49, 51, 53, 55, 59, 61, 63, 66, 68, 70};
		for(int i = 0;i < indexCh.length;i++) {
			if(indexCh[i] >= gray) {
				return ch[i]+"";
			}
//			System.out.println(red);
//			System.out.println(green);
//			System.out.println(blue);
		}
		return ch[ch.length-1]+"";
	}

}
