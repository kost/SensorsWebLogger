# SensorsWebLogger
Android application - Sensors Web Logger

This application will send Environment Sensors data together with location to custom URL you specify. It is actually nice alternative to Arduino sensors if you just need logging of the temperature/humidity/pressure and your phone have such sensors.
It tries to be nice with battery as it will try to get the best location possible with current settings on the phone.
In order to protect your privacy, application logs sensors data to the localhost (127.0.0.1) by default. You can change URL in Settings. HTTPS URLs are supported and encouraged in order to protect your privacy.
Source is available under GNU General Public License(GNU GPL) v2.0.

# Build

Clone repository, change directory to project and issue following command:

ANDROID_HOME=/opt/Android/Sdk gradle build

You can check other tasks available:

ANDROID_HOME=/opt/Android/Sdk gradle tasks

