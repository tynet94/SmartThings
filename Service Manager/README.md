# Cloud-Based TP-Link to Smart Things Integration

This package is for the SmartThings integration of TP-Link bulbs and plugs through the TP-Link cloud.  Prerequisites:

    1.  A TP-Link Kasa account current and valid username and password.
    
    2.  Devices that are installed through the Kasa Application.
    
    3.  Devices in "remote control" mode set through the Kasa app.

# INSTALLATION

A.  Install the Service Manager ("(Cloud)TPLink Connect (unoffficial).groovy")

    1)  Log onto the IDE
        a)  From community page, upper right side, select "COMMUNITY"
        b)  Upper right:  "Additional Resources"   "Developer Tools"
        c)  Log-in or follow instructions to obtain an account.

    2)  Select the 'My Location' Tab and select your locaton.
        [NOTE:  YOU MUST SELECT YOUR LOCATION.  NOT DOING SO WILL 
        CAUSE THE SERVICE MANAGER TO NOT APPEAR IN STEP 5C AND THE 
        DEVICES TO INSTALL IN STEP 6B.]
    
    3)  Select the 'My SmartApps' Tab.

    4)  Select '+New SmartApp' at the top-right
    
    5)  Select the 'From Code' tab

    6)  Paste the contents of the file into the space.
    
    7)  Select 'Create then' 'Publish'.
    
B.  Install your selected Device Handlers.

Note:  Do not select the Energy Monitor version for initial installation.  See below to install later.

    1)  Open the 'My Device Handlers' tab.
    
    2)  Follow the same instructions as in A (above) for each device type you have.
    
C.  From your smart phone SmartThings application:

Note 1:  To be detected, the bulb must be set to 'remote control' in the device's options in the Kasa App.

Note 2:  The Service Manager will attain the token from TP-Link for you.  Do not do this yourself.

[PLEASE OPEN UP 'LIVE LOGGING' AND IF AN ERROR OCCURS, PROVIDE THIS DATA TO AUTHOR WHEN REQUESTING ASSISTANTS]

    1)  Select "Automation" (at bottom), then the 'SmartApps' tab
    
    2)  Select '+ Add a SmartApp' at the bottom.
    
    3)  Select '+ My Apps' at the bottom
    
    4)  Select the app 'TP-Link (unofficial) Connect'
    
    5)  Follow on-screen instructions.

D.  Upgrading to Energy Monitor Function for a device

    1)  Upload the corresponding Energy Monitor Device Handler for the device (as above).
    
    2)  Go to the 'My Devices" tab on the IDE.
    
    3)  Select the device and go to the bottom and select 'EDIT'
    
    4)  Go to the pull-down field 'Type *' and use the pull-down to select the EM device handler.
    
    5)  Select 'UPDATE' at the bottom.
    
    6)  From the phone app, go to settings (the gear icon) and select Save to initialize the energy monitor functions.
    
