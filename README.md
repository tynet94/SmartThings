# Cloud-Based TP-Link Bulb, Plug, and Switch to SmartThing Integration

Two versions of TP-Link to Smart Things Integraion now exist:

a. Cloud-Based TP-Link to Smart Things Integraion: This version that relies on the TP-Link Kasa cloud. Attributes:

    Reliant on TP-Link cloud (and the continued availabilty of same).
    Must have TP-Link account.
    Simpler setup. Install Service Manager and applicable device handlers. Runs service Manager.

b. Hub-Based TP-Link to Smart Things Integraion: The Hub-based version that requires an always on Hub device (bridge). Attributes:

    Requires user-configured (PC, Android, Raspberry) Hub with node.js and server script.
    Does not require a token captured from the TP-Link cloud.
    Manual device installation and setting static IP addresses.

# Version 3 Update - 10/10/2018

The Files have been updated to Version 3 for device handlers only..  Change from previous version is support for the NEW SmartThings phone app.  Some nuances:
a.  Initial Installation takes the Classic App.
b.  The new SmartThings App does not all device deletion.  To do so, you must use the IDE or the Classic App.
c.  The following functions are not supported on the new SmartThings App
     1.  Circadian Mode for bulbs.
     2.  Energy Monitor functions (data still available for picking if you write a smart app)
     3.  Single Device Delete.
     4.  Color Temperature above 6500 or below 2700 on the Color Bulbs.

# Cloud-Based Pre-requisites:

a.  A valid TP-Link Kasa account (must have login name and password.

b.  TP-Link devices installed and in 'Remote Control' mode (done in Kasa App)

Caveate:  The author is not associated with the company TP-Link except as an owner/consumer of their products.  All date used to create thes applets was garnered from public-domain data.

# Upgrade (Attempt several items)
Update instructions:
A.  Replace the device handlers with the new ones from the link below.
B.  In the CLASSIC App, exercise each device (run a refresh).
C.  Go to the NEW App and see if you can control the devices.
D.  If not, Remove the devices using the IDE.  DO NOT remove the Smart App.  Then re-install using either the New or Classic App.
Note:  To delete individual devices, you will have to use the My Devices page on the IDE or the CLASSIC app.  The new app can not delete.


# Installation (Must use the SmartThings Classic App)
Installation instruction can be found in the Documentation Folders.  These are step-by-step and are RECOMMENDED for users new to SmartThings.  Essentially, the steps are:

a.  Install the relevant installation file(s) into My Device Handler page, then Save and Publish.  Devic File names::

        TP-Link Model:  FileName // Namespace - Name
        HS100, HS105, HS200, HS210, KP100:  (Cloud) TP-Link Plug-Switch.groovy // davegut - (Hub) TP-Link Plug-Switch
        HS110, HS115:  (Cloud) TP-Link EnergyMonitor Plug.groovy // davegut - (Hub) TP-Link EnergyMonitor Plug
        LB100, LB110, LB200, KB100:  (Cloud) TP-Link SoftWhite Bulb.groovy // davegut - (Cloud) TP-Link Softwhite Bulb
        LB120:  (Cloud) TP-Link TunableWhite Bulb.groovy // davegut - (Cloud) TP-LinkTunableWhite Bulb
        LB130, L230, KB130:  (Cloud) TP-Link Color Bulb.groovy // davegut - (Cloud) TP-Link Color Bulb

b.  Install the Service Manager (Smart App) into the My SmartAps page, then Save and Publish.

        FileName // Namespace - Name
        TPLink Cloud Connect V2.groovy // davegut - TP-Link Cloud Connect
  
c.  From your phone,  the Service Manager and follow the installation Instructions.  (If not installing an Energy Monitor vesion, go to step F. below).

d.  If updating a bulb (or bulbs) to the Energy Monitor function, install the relevant file(s) into My Device Handlers, then Save and Publish.  Files are:  

        TP-Link Model:  FileName // Namespace - Name
        LB110:  (Cloud) TP-Link SoftWhite Bulb Emon .groovy // davegut - (Cloud) TP-Link Softwhite Bulb Emon
        LB120:  (Cloud) TP-Link TunableWhite Bulb Emon .groovy // davegut - (Cloud) TP-LinkTunableWhite Bulb Emon
        LB130, L230, KB130:  (Cloud) TP-Link Color Bulb Emon .groovy // davegut - (Cloud) TP-Link Color Bulb Emon
        
e.  Go to My Devices and Select the device you want to upgrade to Energy Monitor.  At the bottom of that page, select "EDIT".
    1)  At the "Type" area, use the pull-down and the appropriate device handler (identified by the Name in the table above).
    2.  Select "Save".

f.  After completing the above installation, open the device in your phone apps and select Settings (the gear in the upper right corner.  Select SAVE.  (You do not have to update Refresh Rate or Transition Time if the defaults are acceptable.)

# Files in Documentation Folder
Installation - Cloud TP-Link to SmartThing.pdf

TP-Link SmartThing Implementation.pdf
