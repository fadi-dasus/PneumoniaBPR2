package com.bachelor.utility.files;

import org.springframework.stereotype.Component;

@Component
public class FoldersPathtUtil {

	public static final String normalImagesFolderPath = "E:\\7 semester\\BP\\implementation\\database\\NORMAL";
	public static final String pneumoniaImagesFolderPath = "E:\\7 semester\\BP\\implementation\\database\\PNEUMONIA";
	public static final String temporaryFolderDestination = "E:\\7 semester\\BP\\implementation\\database\\temp";

	@Override
	public String toString() {
		return "normalImagesFolderPath=" + normalImagesFolderPath + "\npneumoniaImagesFolderPath="
				+ pneumoniaImagesFolderPath + "\ntemporaryFolderDestination=" + temporaryFolderDestination;
	}

}
