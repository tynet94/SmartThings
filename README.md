<img src="https://github.com/DaveGut/Cloud-Based_TP-Link-to-SmartThings-Integration/blob/master/FamilyPic.png" align="right"/>

# Cloud-Based TP-Link Bulb, Plug, and Switch to SmartThing Integration - BETA

Two versions of TP-Link to Smart Things Integraion now exist:

a. Cloud-Based TP-Link to Smart Things Integraion: This new version (currently in Beta) that relies on the TP-Link Kasa cloud. Attributes:

    Reliant on TP-Link cloud (and the continued availabilty of same).
    Must have TP-Link account.
    Simpler setup. Install Service Manager and applicable device handlers. Runs service Manager.

b. Hub-Based TP-Link to Smart Things Integraion: The previous Hub-based version that requires an always on Hub device (bridge). Attributes:

    Requires user-configured (PC, Android, Raspberry) Hub with node.js and server script.
    Does not require a token captured from the TP-Link cloud.
    Manual device installation and setting static IP addresses.

# Cloud-Based Pre-requisites:

a.  A valid TP-Link Kasa account (must have login name and password.

b.  TP-Link devices installed and in 'Remote Control' mode (done in Kasa App)

Caveate:  The author is not associated with the company TP-Link except as an owner/consumer of their products.  All date used to create thes applets was garnered from public-domain data.

# Installation
Installation instruction can be found:

    a.  Top-level in the document 'Cloud TP-Link to SmartThing Installation.pdf'.
    b.  In the README.md file in the 'Service Manager' and 'Device Handlers' folders.

Objectives of Cloud Beta.  Consider closed.  Currently at 25 days on same token.  Will continue to capture and correct errors on final release.

# TP-Link Devices Supported:

    HS100, Hs105, HS110, HS200 - (Cloud)TP-Link_HS_Series.groovy
    LB100, LB110 - (Cloud)TP-Link_LB100_110.groovy
    LB120 - (Cloud)TP-Link_LB120.groovy
    LB130 - (Cloud)TP-Link_LB130.groovy
    ENERGY MONITOR VARIANTS
    HS110 with energy monitor functions - (Cloud)TP-Link_HS110_Emeter.groovy
    LB110 with energy monitor functions - (Cloud)TP-Link_LB110_Emeter.groovy
    LB120 with energy monitor functions - (Cloud)TP-Link_LB120_Emeter.groovy
    LB130 with energy monitor functions - (Cloud)TP-Link_LB130_Emeter.groovy

Files:

Service Manager.  The installation and communications application.

Device Handlers. All SmartThings device handlers. Names are clear as to device applicability.


Documentation. Design Notes, and Interface description, TP-Link Cloud Error Codes.
