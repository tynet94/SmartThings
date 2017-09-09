/*
TP-Link LB130 with Energy Monitor Cloud-connect Device Handler

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
1.	This DH is a child device to 'TP-Link Connect'.
2.	This device handler supports the TP-Link LB130 with Energy 
	Monitor functions.
3.	Please direct comments to the SmartThings community thread 
	'Cloud TP-Link Device SmartThings Integration'.

##### History #####
2017-09-11	Initial formal release.
2017-09-06	Made refresh rate a preference and coded for default
			to be every 30 minutes.
*/

metadata {
	definition (name: "TP-LinkLB130 Emeter", namespace: "beta", author: "Dave Gutheinz") {
		capability "Switch"
		capability "Switch Level"
		capability "Sensor"
		capability "Actuator"
		capability "refresh"
		capability "Color Temperature"
		attribute "bulbMode", "string"
		command "setModeNormal"
		command "setModeCircadian"
		capability "Color Control"
		capability "powerMeter"
		command "setCurrentDate"
		attribute "monthTotalE", "string"
		attribute "monthAvgE", "string"
		attribute "weekTotalE", "string"
		attribute "weekAvgE", "string"
		attribute "engrToday", "string"
		attribute "dateUpdate", "string"
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
			tileAttribute ("device.color", key: "COLOR_CONTROL") {
				attributeState "color", action:"setColor"
			}
		}
		standardTile("refresh", "capability.refresh", width: 2, height: 2,  decoration: "flat") {
			state ("default", label:"Refresh", action:"refresh.refresh", icon:"st.secondary.refresh")
		}		 
		controlTile("colorTempSliderControl", "device.colorTemperature", "slider", width: 4, height: 1, inactiveLabel: false,
		range:"(2500..9000)") {
			state "colorTemperature", action:"color temperature.setColorTemperature"
		}
		valueTile("colorTemp", "device.colorTemperature", inactiveLabel: false, decoration: "flat", height: 1, width: 2) {
			state "colorTemp", label: '${currentValue}K'
		}
		standardTile("bulbMode", "bulbMode", width: 2, height: 2, decoration: "flat") {
			state "normal", label:'Circadian', action:"setModeCircadian", backgroundColor:"#ffffff", nextState: "circadian"
			state "circadian", label:'Circadian', action:"setModeNormal", backgroundColor:"#00a0dc", nextState: "normal"
		}
		standardTile("refreshStats", "Refresh Statistics", width: 2, height: 2,  decoration: "flat") {
			state ("refreshStats", label:"Refresh Stats", action:"setCurrentDate", icon:"st.secondary.refresh")
		}		 
		valueTile("power", "device.power", decoration: "flat", height: 1, width: 2) {
			state "power", label: 'Current Power \n\r ${currentValue} W'
		}
		valueTile("engrToday", "device.engrToday", decoration: "flat", height: 1, width: 2) {
			state "engrToday", label: 'Todays Usage\n\r${currentValue} KWH'
		}
		valueTile("monthTotal", "device.monthTotalE", decoration: "flat", height: 1, width: 2) {
			state "monthTotalE", label: '30 Day Total\n\r ${currentValue} KWH'
		}
		valueTile("monthAverage", "device.monthAvgE", decoration: "flat", height: 1, width: 2) {
			state "monthAvgE", label: '30 Day Avg\n\r ${currentValue} KWH'
		}
		valueTile("weekTotal", "device.weekTotalE", decoration: "flat", height: 1, width: 2) {
			state "weekTotalE", label: '7 Day Total\n\r ${currentValue} KWH'
		}
		valueTile("weekAverage", "device.weekAvgE", decoration: "flat", height: 1, width: 2) {
			state "weekAvgE", label: '7 Day Avg\n\r ${currentValue} KWH'
		}
		main("switch")
		details("switch", "colorTempSliderControl", "colorTemp", "bulbMode", "refresh" ,"refreshStats", "power", "weekTotal", "monthTotal", "engrToday", "weekAverage", "monthAverage")
	}
	def rates = [:]
    rates << ["5" : "Refresh every 5 minutes"]
    rates << ["10" : "Refresh every 10 minutes"]	
    rates << ["15" : "Refresh every 15 minutes"]
    rates << ["30" : "Refresh every 30 minutes"]
	preferences {
        input name: "refreshRate", type: "enum", title: "Refresh Rate", options: rates, description: "Select Refresh Rate", required: false
    }
}

def installed() {
	updated()
}

def updated() {
	unschedule()
	switch(refreshRate) {
		case "5":
			runEvery5Minutes(refresh)
            log.info "Refresh Scheduled for every 5 minutes"
			break
		case "10":
			runEvery10Minutes(refresh)
            log.info "Refresh Scheduled for every 10 minutes"
			break
		case "15":
			runEvery15Minutes(refresh)
            log.info "Refresh Scheduled for every 15 minutes"
			break
		default:
			runEvery30Minutes(refresh)
            log.info "Refresh Scheduled for every 30 minutes"
	}
	runIn(2, refresh)
    schedule("0 30 0 * * ?", setCurrentDate)
	runIn(6, setCurrentDate)
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

def setColorTemperature(kelvin) {
	kelvin = kelvin as int
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"ignore_default":1,"on_off":1,"color_temp": ${kelvin},"hue":0,"saturation":0}}}""", "commandResponse")
}

def setModeNormal() {
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"mode":"normal"}}}""", "commandResponse")
}

def setModeCircadian() {
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"mode":"circadian"}}}""", "commandResponse")
}

def setColor(Map color) {
	def hue = color.hue * 3.6 as int
	def saturation = color.saturation as int
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"ignore_default":1,"on_off":1,"color_temp":0,"hue":${hue},"saturation":${saturation}}}}""", "commandResponse")
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
	def mode = status.mode
	def color_temp = status.color_temp
	def hue = status.hue
	def saturation = status.saturation
	log.info "$device.name $device.label: Power: ${onOff} / Brightness: ${level}% / Mode: ${mode} / Color Temp: ${color_temp}K / Hue: ${hue} / Saturation: ${saturation}"
	sendEvent(name: "switch", value: onOff)
	sendEvent(name: "level", value: level)
	sendEvent(name: "bulbMode", value: mode)
	sendEvent(name: "colorTemperature", value: color_temp)
	sendEvent(name: "hue", value: hue)
	sendEvent(name: "saturation", value: saturation)
	sendEvent(name: "color", value: colorUtil.hslToHex(hue/3.6 as int, saturation as int))
	getEngeryMeter()
}

//	----- Get Current Energy Use Rate ----------------------------
def getEngeryMeter(){
	sendCmdtoServer('{"smartlife.iot.common.emeter":{"get_realtime":{}}}', "energyMeterResponse")
}

def energyMeterResponse(cmdResponse) {
	def realtime = cmdResponse["smartlife.iot.common.emeter"]["get_realtime"]
	def powerConsumption = realtime.power_mw / 1000
	sendEvent(name: "power", value: powerConsumption)
	log.info "$device.name $device.label: Updated CurrentPower to $powerConsumption"
	getUseToday()
}

//	----- Get Today's Consumption --------------------------------
def getUseToday(){
	getDateData()
	sendCmdtoServer("""{"smartlife.iot.common.emeter":{"get_daystat":{"month": ${state.monthToday}, "year": ${state.yearToday}}}}""", "useTodayResponse")
}

def useTodayResponse(cmdResponse) {
	def engrToday
	def dayList = cmdResponse["smartlife.iot.common.emeter"]["get_daystat"].day_list
	for (int i = 0; i < dayList.size(); i++) {
		def engrData = dayList[i]
		if(engrData.day == state.dayToday) {
			engrToday = engrData.energy_wh/1000
		}
	}
	sendEvent(name: "engrToday", value: engrToday)
	log.info "$device.name $device.label: Updated Today's Usage to $engrToday"
}

//	----- Get Weekly and Monthly Stats ---------------------------
def getWkMonStats() {
	state.monTotEnergy = 0
	state.monTotDays = 0
	state.wkTotEnergy = 0
	getDateData()
	sendCmdtoServer("""{"smartlife.iot.common.emeter":{"get_daystat":{"month": ${state.monthToday}, "year": ${state.yearToday}}}}""", "engrStatsResponse")
	runIn(4, getPrevMonth)
}

def getPrevMonth() {
	getDateData()
	if (state.dayToday < 31) {
		def month = state.monthToday
		def year = state.yearToday
		if (month == 1) {
			year -= 1
			month = 12
			sendCmdtoServer("""{"smartlife.iot.common.emeter":{"get_daystat":{"month": ${month}, "year": ${year}}}}""", "engrStatsResponse")
		} else {
			month -= 1
			sendCmdtoServer("""{"smartlife.iot.common.emeter":{"get_daystat":{"month": ${month}, "year": ${year}}}}""", "engrStatsResponse")
		}
	}
}

def engrStatsResponse(cmdResponse) {
	getDateData()
	def monTotEnergy = state.monTotEnergy
	def wkTotEnergy = state.wkTotEnergy
	def monTotDays = state.monTotDays
	Calendar calendar = GregorianCalendar.instance
	calendar.set(state.yearToday, state.monthToday, 1)
	def prevMonthDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)
	def weekEnd = state.dayToday + prevMonthDays - 1
	def weekStart = weekEnd - 6
	def dayList = cmdResponse["smartlife.iot.common.emeter"]["get_daystat"].day_list
	def dataMonth = dayList[0].month
	def currentMonth = state.monthToday
	def addedDays = 0
	if (currentMonth == dataMonth) {
		addedDays = prevMonthDays
	} else {
		addedDays = 0
	}
	for (int i = 0; i < dayList.size(); i++) {
		def engrData = dayList[i]
		if(engrData.day == state.dayToday && engrData.month == state.monthToday) {
			monTotDays -= 1
		} else {
			monTotEnergy += engrData.energy_wh
		}
		def adjustDay = engrData.day + addedDays
		if (adjustDay <= weekEnd && adjustDay >= weekStart) {
			wkTotEnergy += engrData.energy_wh
		}
	}
	monTotDays += dayList.size()
	state.monTotDays = monTotDays
	state.monTotEnergy = monTotEnergy
	state.wkTotEnergy = wkTotEnergy
	if (state.dayToday == 31 || state.monthToday -1 == dataMonth) {
		log.info "$device.name $device.label: Updated 7 and 30 day energy consumption statistics"
		def monAvgEnergy = Math.round(monTotEnergy/(monTotDays-1))/1000
		def wkAvgEnergy = Math.round(wkTotEnergy/7)/1000
		sendEvent(name: "monthTotalE", value: monTotEnergy/1000)
		sendEvent(name: "monthAvgE", value: monAvgEnergy)
		sendEvent(name: "weekTotalE", value: wkTotEnergy/1000)
		sendEvent(name: "weekAvgE", value: wkAvgEnergy)
	}
}

//	----- Update date data ---------------------------------------
def setCurrentDate() {
	sendCmdtoServer('{"smartlife.iot.common.timesetting":{"get_time":null}}', "currentDateResponse")
}

def currentDateResponse(cmdResponse) {
	def setDate =  cmdResponse["smartlife.iot.common.timesetting"]["get_time"]
	updateDataValue("dayToday", "$setDate.mday")
	updateDataValue("monthToday", "$setDate.month")
	updateDataValue("yearToday", "$setDate.year")
	sendEvent(name: "dateUpdate", value: "${setDate.year}/${setDate.month}/${setDate.mday}")
	log.info "$device.name $device.label: Current Date Updated to ${setDate.year}/${setDate.month}/${setDate.mday}"
	getWkMonStats()
}

def getDateData(){
	state.dayToday = getDataValue("dayToday") as int
	state.monthToday = getDataValue("monthToday") as int
	state.yearToday = getDataValue("yearToday") as int
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

		case "energyMeterResponse":
			energyMeterResponse(cmdResponse)
			break
			
		case "useTodayResponse":
			useTodayResponse(cmdResponse)
			break
			
		case "currentDateResponse":
			currentDateResponse(cmdResponse)
			break
			
		case "engrStatsResponse":
			engrStatsResponse(cmdResponse)
			break
			
		default:
			log.info "Interface Error.  See SmartApp and Device error message."
	}
}

//	----- CHILD / PARENT INTERCHANGE TASKS -----
def syncAppServerUrl(newAppServerUrl) {
	updateDataValue("appServerUrl", newAppServerUrl)
	    log.info "Updated appServerUrl for ${device.name} ${device.label}"
}