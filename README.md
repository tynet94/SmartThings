<img src="https://github.com/DaveGut/Cloud-Based_TP-Link-to-SmartThings-Integration/blob/master/FamilyPic.png" align="right"/>

# Cloud-Based TP-Link Bulb, Plug, and Switch to SmartThing Integration

Two versions of TP-Link to Smart Things Integraion now exist:

a. Cloud-Based TP-Link to Smart Things Integraion: This new version that relies on the TP-Link Kasa cloud. Attributes:

    Reliant on TP-Link cloud (and the continued availabilty of same).
    Must have TP-Link account.
    Simpler setup. Install Service Manager and applicable device handlers. Runs service Manager.

b. Hub-Based TP-Link to Smart Things Integraion: The previous Hub-based version that requires an always on Hub device (bridge). Attributes:

    Requires user-configured (PC, Android, Raspberry) Hub with node.js and server script.
    Does not require a token captured from the TP-Link cloud.
    Manual device installation and setting static IP addresses.

# Version 2 Update - 1/30/2018

The Files have been updated to Version 2.  Changes from previous version

a.  Added transition time of (user selectable) for bulb power-on and off.

b.  Made Energy Monitor function compatible with the Australian version of the HS110 (HS110(AU).

c.  Changed file naming to be function-based instead of product number based due to expanded TP-Link Product Base.

d.  Changed naming convention to be compatible with SmartThings - GitHub integration.

Upgrade recommendation:  Not needed unless you are adding products not supported with the previous version.

# Cloud-Based Pre-requisites:

a.  A valid TP-Link Kasa account (must have login name and password.

b.  TP-Link devices installed and in 'Remote Control' mode (done in Kasa App)

Caveate:  The author is not associated with the company TP-Link except as an owner/consumer of their products.  All date used to create thes applets was garnered from public-domain data.

# Installation
Installation instruction can be found in the Documentation Folders.

Files:

# Files Device Handler Folder
TP-Link Model:  FileName // Namespace/Name

HS100, HS105, HS200, HS210, KP100:  (Cloud) TP-Link Plug-Switch.groovy // davegut - (Hub) TP-Link Plug-Switch

HS110, HS115:  (Cloud) TP-Link EnergyMonitor Plug.groovy // davegut - (Hub) TP-Link EnergyMonitor Plug

LB100, LB110, LB200, KB100:  (Cloud) TP-Link SoftWhite Bulb.groovy // davegut - (Cloud) TP-Link Softwhite Bulb

LB120:  (Cloud) TP-Link TunableWhite Bulb.groovy // davegut - (Cloud) TP-LinkTunableWhite Bulb

LB130, L230, KB130:  (Cloud) TP-Link Color Bulb.groovy // davegut - (Cloud) TP-Link Color Bulb

LB110:  (Cloud) TP-Link SoftWhite Bulb Emon .groovy // davegut - (Cloud) TP-Link Softwhite Bulb Emon

3LB120:  (Cloud) TP-Link TunableWhite Bulb Emon .groovy // davegut - (Cloud) TP-LinkTunableWhite Bulb Emon

LB130, L230, KB130:  (Cloud) TP-Link Color Bulb Emon .groovy // davegut - (Cloud) TP-Link Color Bulb Emon

# File in Service Manager Folder
TPLink Cloud Connect V2.groovy // davegut - TP-Link Cloud Connect

# Files in Documentation Folder
Installation - Cloud TP-Link to SmartThing.pdf

TP-Link SmartThing Implementation.pdf
