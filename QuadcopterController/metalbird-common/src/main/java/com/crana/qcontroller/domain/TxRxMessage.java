/**
 * 
 */
package com.crana.qcontroller.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ArunPrakash
 *
 */
@JsonInclude(Include.NON_NULL)
public class TxRxMessage implements Serializable {
	@JsonProperty("mId")
	private Integer messageId;
	@JsonProperty("cId")
	private Integer commandId;
	@JsonProperty("from")
	private String sender;
	@JsonProperty("to")
	private String recipient;
	@JsonProperty("oFrom")
	private String originalSender;
	@JsonProperty("oTo")
	private String originalRecipient;
	@JsonProperty("pay")
	private String payload;
	@JsonIgnore
	private String originalMessage;
	
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public Integer getCommandId() {
		return commandId;
	}
	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getOriginalSender() {
		return originalSender;
	}
	public void setOriginalSender(String originalSender) {
		this.originalSender = originalSender;
	}
	public String getOriginalRecipient() {
		return originalRecipient;
	}
	public void setOriginalRecipient(String originalRecipient) {
		this.originalRecipient = originalRecipient;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getOriginalMessage() {
		return originalMessage;
	}
	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}
}
