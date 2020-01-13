/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Momentary Button Tile
 *
 *  Author: SmartThings
 *
 *  Date: 2013-05-01
 */
metadata {
	definition (name: "Neato Botvac Clean Button", namespace: "alyc100", author: "Alex Lee Yuk Cheung", mnmn: "SmartThings") {
		capability "Actuator"
		capability "Switch"
		capability "Momentary"
		capability "Sensor"
        
        command "setRoomID", ["string"]
        command "cleanRoom"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles(scale: 2){
		multiAttributeTile(name:"switch", type: "generic", width: 6, height: 4, canChangeIcon: true){
			tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
				attributeState("off", label: 'Push', action: "momentary.push", backgroundColor: "#ffffff", nextState: "on")
				attributeState("on", label: 'Push', action: "momentary.push", backgroundColor: "#00a0dc")
			}	
		}
		main "switch"
		details "switch"
	}
}

def parse(String description) {
}

def push() {
	log.debug (state.roomID)
	parent.nucleoPOST("/messages", '{"reqId":"1", "cmd":"startCleaning", "params":{"category": 4, "mode": 2, "navigationMode": 3, "boundaryId": "' + state.roomID + '"}}')
	sendEvent(name: "switch", value: "on", isStateChange: true, displayed: false)
	sendEvent(name: "switch", value: "off", isStateChange: true, displayed: false)
	sendEvent(name: "momentary", value: "pushed", isStateChange: true)
}

def on() {
	//log.debug (state.roomID)
	push()
}

def off() {
	//log.debug (state.roomID)
	//push()
}

def setRoomID(roomidx) {
	state.roomID = roomidx
	sendEvent(name: 'roomID', value: state.roomID, displayed: false)
}
