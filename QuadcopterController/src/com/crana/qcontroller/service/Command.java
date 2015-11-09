/**
 * 
 */
package com.crana.qcontroller.service;

/**
 * @author ArunPrakash
 *
 */
public enum Command {
	INVITE(1),
	GET_GPS_LOCATION(2),
	START(3),
	INVITE_RESPONSE(35), 
	GPS_LOCATION_RESPONSE(36);

	private Integer commandId;

	private Command(Integer commandId) {
		this.commandId = commandId;
	}

	public Integer getCommandId() {
		return commandId;
	}

	public static Command getCommandByCommandId(Integer commandId) {
		for (Command command : values()) {
			if (command.getCommandId().equals(commandId)) {
				return command;
			}
		}
		throw new IllegalArgumentException("Command Not Found With CommandId::"+commandId);
	}
}
