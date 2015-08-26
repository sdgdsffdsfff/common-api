package org.rency.common.mq.service;

import org.rency.common.mq.beans.MQRequest;
import org.rency.common.mq.exception.MQException;

public interface MQService {

	public boolean send(MQRequest request) throws MQException;
	
}