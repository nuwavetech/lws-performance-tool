# LightWave Server Performance Tool Client

The LightWave Server Performance Tool Client consists of:

+ A multi-threaded Java client application that sends concurrent requests through LightWave Server to the Echo Server.
+ A configuration system for specifying test parameters.
+ A metrics collection and reporting system for analyzing performance results.

The client application is provided both as a pre-built JAR file and as source code, allowing you to use it directly or modify and customize it to suit your specific testing needs.

### Prerequisites

+ Java Runtime Environment (JRE) 17 or later for running the pre-built JAR.
+ Java Development Kit (JDK) 17 or later if you wish to build from source.
+ Network access to the LightWave Server Echo Service.

### Installation

#### Option 1: Use the Pre-built JAR (Recommended)

The `LWSPerformanceTool.jar` file is included in the `client` directory of the repository. You can use this file directly without needing to build the application.

#### Option 2: Build from Source (Optional)

If you wish to modify the source code or build the application yourself, follow these steps:

1. Ensure you have JDK 17 or later installed.
2. Navigate to the `client` directory in the repository.
3. Use the provided build scripts:

   - For Linux:
     ```
     ./build.sh
     ```

   - For Windows:
     ```
     build.cmd
     ```

   This will create a fat JAR file named `LWSPerformanceTool.jar` in the `./build` directory.

### Configuration

The following parameters can be configured when running the Performance Tool Client:

+ threadCount - The number of concurrent threads to use for sending requests.
+ requestType - The type of request to send ("simple" or "complex").
+ messageSize - The size of the message payload in bytes.
+ testDuration - The duration of the test in seconds.
+ baseUrl - The base URL of the Echo Server (set via environment variable).

### Usage

To run the Performance Tool Client, use the following command:

```
java -jar LWSPerformanceTool.jar <threadCount> <requestType> <messageSize> <testDuration>
```

Example:
```
java -jar LWSPerformanceTool.jar 10 simple 1000 60
```

This will run a test with 10 concurrent threads, sending simple requests with a 1000-byte payload for 60 seconds.

#### Setting the Base URL

The base URL for the Echo Server must be set using an environment variable. The URL pattern is:

```
https://host:lightwave-server-service-port/lws-performance-tool
```

To set the BASE_URL:

- For Linux:
  ```
  export BASE_URL=https://your-host:your-port/lws-performance-tool
  ```

- For Windows (temporary variable):
  ```
  set BASE_URL=https://your-host:your-port/lws-performance-tool
  ```

### Message Size

The `messageSize` parameter determines the size of the payload in the requests sent to the Echo Server. It's important to note that this parameter refers to the approximate size of the Interprocess Message (IPM) sent to the echo server, not the JSON message. Due to the nature of JSON, the actual JSON payloads will be much larger than the configured message sizes. The sizes of the JSON payloads are included in the metrics output.

1. For a simple message:
   - The size is the number of bytes in the payload string field.
   - For example, if you specify a message size of 1000, the payload will be a string of 1000 characters.

2. For a complex message:
   - The message size is used to calculate the number of complex type elements to send in the request.
   - The message size effectively determines the size of the complex type array in the IPM sent to the echo serverclass.
   - The actual number of elements is calculated based on the specified message size, with each complex type element occupying a fixed amount of space.

Remember that while you specify the IPM size, the actual JSON payload sent over the network will be larger. The metrics output will provide information on the actual bytes sent and received during the test.

### Output

The client will display progress during the test and output a summary of results, including:

+ Total Requests
+ Total Errors
+ Target IPM Message Size
+ JSON Payload Sizes
+ Bytes Sent and Received
+ Average, Minimum, and Maximum Response Times
+ Requests per Second

### Customization

If you need to modify the tool, you can edit the source code to add new features, change the payload generation logic, or adjust the metrics collection as needed. After making changes, rebuild the application using the provided build scripts as described in the "Build from Source" section.

### Troubleshooting

If you encounter issues:

1. Ensure the Performance Tool Service is installed, running, and accessible.
2. Check that the BASE_URL environment variable is correctly set.
3. Verify that the Java version is 17 or later (use `java -version` to check).
4. If using a custom-built JAR, ensure all dependencies are correctly managed.

For further assistance, consult the LightWave Server documentation or contact support.