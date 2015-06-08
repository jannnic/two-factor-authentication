# two-factor-authentication
Two factor authentication(TOTP) implemented in Java EE with JAAS. Supports OpenLDAP.

This is a java-module which offers two-factor-authentification with smartphone apps.

You can either use the sources or the jar-File.

To install the module on your Server u must configure two files.

The first is the web.xml:
  - Here u have to register all servlets and filters
  - Additionally u must configure the right jsps
  
The second one is the config.properties:
  - There u have to register your JSPs
  - at the bottom of the file u can write our host name, that will be displayed in the smartphone app

There is an example online, where u can look how to configure your application the right way.
https://github.com/Cortana7/two-factor-authentication-example

