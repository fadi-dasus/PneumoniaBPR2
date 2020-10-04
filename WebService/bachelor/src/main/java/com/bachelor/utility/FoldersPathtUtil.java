package com.bachelor.utility;

import org.springframework.stereotype.Component;

@Component
public class FoldersPathtUtil {

	public static String normalImagesFolderPath = "E:\\7 semester\\BP\\implementation\\database\\NORMAL";
	public static String pneumoniaImagesFolderPath = "E:\\7 semester\\BP\\implementation\\database\\PNEUMONIA";
	public static String temporaryFolderDestination = "E:\\7 semester\\BP\\implementation\\database\\temp";

	

	@Override
	public String toString() {
		return "normalImagesFolderPath=" + normalImagesFolderPath+ "\npneumoniaImagesFolderPath="
				+ pneumoniaImagesFolderPath + "\ntemporaryFolderDestination=" + temporaryFolderDestination ;
	}

}
