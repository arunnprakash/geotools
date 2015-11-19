package com.crana.qcontroller.domain;

/**
 * @author ArunPrakash
 *
 */
public class TxRxMessageBuilder extends TxRxMessageBuilderBase<TxRxMessageBuilder> {
	public static TxRxMessageBuilder txRxMessage() {
		return new TxRxMessageBuilder();
	}

	public TxRxMessageBuilder() {
		super(new TxRxMessage());
	}

	public TxRxMessage build() {
		return getInstance();
	}
}

class TxRxMessageBuilderBase<GeneratorT extends TxRxMessageBuilderBase<GeneratorT>> {
	private TxRxMessage instance;

	protected TxRxMessageBuilderBase(TxRxMessage aInstance) {
		instance = aInstance;
	}

	protected TxRxMessage getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withMessageId(Integer aValue) {
		instance.setMessageId(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withCommandId(Integer aValue) {
		instance.setCommandId(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withSender(String aValue) {
		instance.setSender(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withRecipient(String aValue) {
		instance.setRecipient(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withOriginalSender(String aValue) {
		instance.setOriginalSender(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withOriginalRecipient(String aValue) {
		instance.setOriginalRecipient(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withPayload(String aValue) {
		instance.setPayload(aValue);

		return (GeneratorT) this;
	}
}
