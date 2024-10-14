# LightWave Server Performance Tool Echo Service

The LightWave Server Performance Tool Echo Service consists of:

+ A simple Pathway server that echoes any non-system message back to the requester.
+ A LightWave Server service that exposes a REST API to send messages to the echo server.

The server program is built from source so that you may modify and rebuild it to match any modifications you may make to the API.

### Prerequisites

+ NonStop C Compiler.
+ An installed instance of [LightWave Server](https://docs.nuwavetech.com/lightwave-server) version 1.1.7 or greater.

### Configuration
The following values must be configured in the SETENV TACL macro after installation of the sample:

+ hometerm - The home term for the PATHMON process. The default is $ZHOME.
+ pathmon-name - The process name of the Pathmon. The default is $LWPT.
+ lws-isv - The installation subvolume of an installed and running LightWave Server instance. The default is the current subvolume.

The LightWave Server API is configured to use the value of the =LWSPT-PATHMON DEFINE as the Pathmon process name. This DEFINE is set by the SETENV macro. The LightWave Server instance must be restarted with this DEFINE set in order for the DEFINE to be recognized. As an alternative to restarting the LightWave Server instance, the API definition can be modified after installation to use a specific process name. Remember that any modification made to the API requires a redeployment of the service in order for the API exposed by the service to be updated.

### Installation

The application is built from source. The source files are present in the repository for convenient viewing.
In addition, a PAK archive containing all of the source files is available for transfer to your NonStop system.

| Repository File | NonStop File |
| -- | -- |
| macros/build.txt | build |
| macros/instsvc.txt | instsvc |
| macros/setenv.txt | setenv |
| macros/startpw.txt | startpw |
| macros/stoppw.txt | stoppw |
| macros/unsetenv.txt | unsetenv |
| service/lwsptapi.json | lwsptapi |
| service/lwsptdic.xml | lwsptdic |
| src/ptddl.txt | ptddl |
| src/ptserv.c | ptservc |

#### Transfer the PAK archive to your NonStop System

Download `lwsptpak.bin` from this repository to your local system, then upload it to your NonStop server using binary transfer mode.

Note: to download the PAK file, click `lwsptpak.bin` in the file list to display the file details, then click the *Download row file* button.

Log on to TACL on your NonStop system to perform the installation and build steps.

#### Unpak the PAK archive
```
TACL > UNPAK LWSPTPAK ($*.*.*), VOL $vol.subvol, LISTALL, MYID
```
#### Customize and run SETENV
After running SETENV, restart LightWave Server so that it will recognize the =LWSPT-PATHMON define.
```
TACL> T/EDIT SETENV
TACL> RUN SETENV
```
#### Build the application DDL dictionary and servers
```
TACL > RUN BUILD
```
#### Install the LightWave Server service definitions
```
TACL > RUN INSTSVC
```
Note about INSTSVC:
1. This macro imports the service API and Dictionary, and creates a service using the default access control policy `allow-anonymous-access`.
2. The name of the API, Dictionary, and Service is `com.nuwavetech.sample.lws-performance-tool`.

#### Start the Pathway
```
TACL > RUN STARTPW
```

### LightWave Server Configuration for Optimal Performance

To ensure the best performance when running the Performance Tool, configure your LightWave Server instance as follows:

1. Disable HTTP logging:
   - HTTP logging can significantly impact performance and should be turned off during performance testing.
   - Refer to your LightWave Server documentation for instructions on disabling HTTP logging.

2. Disable Diagnostic Logging:
   - Similar to HTTP logging, Diagnostic Logging can affect performance and should be disabled for accurate test results.
   - Consult your LightWave Server documentation for steps to disable Diagnostic Logging.

3. Adjust HTTP Keep-Alive settings:
   - Use the startup option `--http-keepalive-max 0` when starting LightWave Server.
   - The default value is 100, which causes LightWave Server to terminate persistent connections after 100 requests.
   - Setting it to 0 disables this behavior, allowing connections to persist for the duration of the test.

