package com.bachelor.service.jms;

import com.bachelor.model.Image;

public interface IJMSService {
	void sendToService(Image image);

	void sendToUser(Image image);

}
