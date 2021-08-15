package com.saket.event.placeholder.producer.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saket.event.placeholder.producer.domain.LibraryEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibraryEventService {

	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	ObjectMapper libEventToJsonMapper;
	
	static final String DEFAULT_TOPIC = "library-events";
	
	/**
	 * 
	 * @param libraryEvent
	 * @throws JsonProcessingException
	 */
	public void sendLibraryEevent(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = libEventToJsonMapper.writeValueAsString(libraryEvent);

		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {

			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				try {
					throw ex;
				} catch (Throwable e) {

				}
			}
		});
	}

	/**
	 * 
	 * @param libraryEvent
	 * @return
	 * @throws JsonProcessingException
	 */
	public SendResult<Integer, String> sendLibraryEeventSynchronous(LibraryEvent libraryEvent)
			throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = libEventToJsonMapper.writeValueAsString(libraryEvent);
		SendResult<Integer, String> sendResult = null;

		try {
			sendResult = kafkaTemplate.sendDefault(key, value).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendResult;
	}
	
	/**
	 * 
	 * @param libraryEvent
	 * @return
	 * @throws JsonProcessingException
	 */
	public SendResult<Integer, String> sendLibraryEeventSyncTimeout(LibraryEvent libraryEvent)
			throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = libEventToJsonMapper.writeValueAsString(libraryEvent);
		SendResult<Integer, String> sendResult = null;

		try {
			sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendResult;
	}
	
	
	/**
	 * 
	 * @param libraryEvent
	 * @throws JsonProcessingException
	 */
	public void sendLibraryEventWithTopic(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = libEventToJsonMapper.writeValueAsString(libraryEvent);
       
		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, DEFAULT_TOPIC); 
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {

			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				try {
					throw ex;
				} catch (Throwable e) {

				}
			}
		});
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param topic
	 * @return
	 */
	private ProducerRecord<Integer,String> buildProducerRecord(Integer key, String value, String topic){
		return new ProducerRecord<>(topic, null, key, value, null);
	}
	
}
