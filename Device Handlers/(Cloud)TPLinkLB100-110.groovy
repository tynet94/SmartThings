/*
(BETA) TP-Link LB100 and LB110 Cloud-connect Device Handler

Copyright 2017 Dave Gutheinz

Licensed under the Apache License, Version 2.0 (the "License"); you 
may not use this  file except in compliance with the License. You may 
obtain a copy of the License at:

		http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
implied. See the License for the specific language governing 
permissions and limitations under the License.

##### Discalimer:  This Service Manager and the associated Device 
Handlers are in no way sanctioned or supported by TP-Link.  All  
development is based upon open-source data on the TP-Link devices; 
primarily various users on GitHub.com.

##### Notes #####
1.	This DH is a child device to 'beta' 'TP-Link Connect'.
2.	This device handler supports the TP-Link LB100 and LB110  
	functions.
3.	Please direct comments to the SmartThings community thread 
	'Cloud TP-Link Device SmartThings Integration'.

##### History #####
07-26-2017	-	Initial Prototype Release
07-28-2017	-	Added uninstalled() to Service Manager to delete 
			device
07-28-2017	-	Beta Release
08-01-2017	-	Updated mode tile to always display circadian, turn 
			color when circadian.
08-06-2017	-	Editorial changes.  Added annotations for device 
			applicability to LB130 EM - the master for other LB 
			device handlers.
*/

metadata {
	definition (name: "TP-LinkLB100-110", namespace: "beta", author: "Dave Gutheinz") {
		capability "Switch"
		capability "Switch Level"
		capability "Sensor"
		capability "Actuator"
		capability "refresh"
	}
	tiles(scale:2) {
		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.light.on", backgroundColor:"#00a0dc",
				nextState:"waiting"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#ffffff",
				nextState:"waiting"
				attributeState "waiting", label:'${name}', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#15EE10",
				nextState:"waiting"
				attributeState "commsError", label: 'Comms Error', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#e86d13",
				nextState:"waiting"
			}
			tileAttribute ("deviceError", key: "SECONDARY_CONTROL") {
				attributeState "deviceError", label: '${currentValue}'
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", label: "Brightness: ${currentValue}", action:"switch level.setLevel"
			}
		}
		standardTile("refresh", "capability.refresh", width: 2, height: 2,  decoration: "flat") {
			state ("default", label:"Refresh", action:"refresh.refresh", icon:"st.secondary.refresh")
		}		 
		main("switch")
		details("switch", "refresh")
	}
}

def installed() {
	updated()
}

def updated() {
	unschedule()
	runEvery15Minutes(refresh)
	runIn(2, refresh)
}

void uninstalled() {
	def alias = device.label
	log.debug "Removing device ${alias} with DNI = ${device.deviceNetworkId}"
	parent.removeChildDevice(alias, device.deviceNetworkId)
}

//	----- BASIC BULB COMMANDS ------------------------------------
def on() {
	sendCmdtoServer('{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"on_off":1}}}', "commandResponse")
}

def off() {
	sendCmdtoServer('{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"on_off":0}}}', "commandResponse")
}

def setLevel(percentage) {
	percentage = percentage as int
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"ignore_default":1,"on_off":1,"brightness":${percentage}}}}""", "commandResponse")
}

def commandResponse(cmdResponse){
	def status =  cmdResponse["smartlife.iot.smartbulb.lightingservice"]["transition_light_state"]
	parseStatus(status)
}

//	----- REFRESH ------------------------------------------------
def refresh(){
	sendCmdtoServer('{"system":{"get_sysinfo":{}}}', "refreshResponse")
}

def refreshResponse(cmdResponse){
	def status = cmdResponse.system.get_sysinfo.light_state
	parseStatus(status)
}

//	----- Parse State from Bulb Responses ------------------------
def parseStatus(status){
	def onOff = status.on_off
	if (onOff == 1) {
		onOff = "on"
	} else {
		onOff = "off"
		status = status.dft_on_state
	}
	def level = status.brightness
	log.info "$device.name $device.label: Power: ${onOff} / Brightness: ${level}%"
	sendEvent(name: "switch", value: onOff)
	sendEvent(name: "level", value: level)
}

//	----- Send the Command to the Bridge -------------------------
private sendCmdtoServer(command, action){
	def appServerUrl = getDataValue("appServerUrl")
	def deviceId = getDataValue("deviceId")
	def cmdResponse = parent.sendDeviceCmd(appServerUrl, deviceId, command)
	String cmdResp = cmdResponse.toString()
	if (cmdResp.substring(0,5) == "ERROR"){
		def errMsg = cmdResp.substring(7,cmdResp.length())
		log.error "${device.name} ${device.label}: ${errMsg}"
		sendEvent(name: "switch", value: "commsError", descriptionText: errMsg)
		sendEvent(name: "deviceError", value: errMsg)
		action = ""
	} else {
		sendEvent(name: "deviceError", value: "OK")
	}	
	switch(action) {
		case "commandResponse":
			commandResponse(cmdResponse)
			break

		case "refreshResponse":
			refreshResponse(cmdResponse)
			break

		default:
			log.info "Interface Error.  See SmartApp and Device error message."
	}
}