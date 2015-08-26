package org.rency.common.mq.service;

public interface MessageHandler {

	public void handler(Object message);
}