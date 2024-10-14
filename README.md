# LightWave Server Performance Tool

The LightWave Server Performance Tool is a Java-based application designed to measure the performance of LightWave Server. It accomplishes this by sending requests to a lightweight Echo Server and measuring the request/response times. This approach allows for a comprehensive assessment of LightWave Server's performance under various load conditions and across different network configurations and topologies.

The tool consists of two main components:

- **Service**: A lightweight Echo Server service implemented as a LightWave Server API and a Pathway server on the NonStop platform. It simply echoes back any non-system message it receives, allowing for accurate measurement of round-trip times through LightWave Server.

- **Client**: A multi-threaded Java application that generates load by sending concurrent requests through LightWave Server to the Echo Server. It provides configurable test parameters and collects detailed performance metrics.

## Features

- Customizable test scenarios with adjustable thread count, request type, message size, and test duration
- Support for both simple and complex message types
- Detailed performance metrics including request counts, error rates, response times, and throughput
- Flexible deployment options for testing various network configurations

## Prerequisites

- NonStop system running LightWave Server (version 1.1.7 or later) for the service component
- Java Runtime Environment (JRE) 17 or later for the client component
- Network connectivity between the client and the NonStop system

## Getting Started

Refer to the README files in the `service` and `client` directories for detailed instructions on setting up and running each component of the Performance Tool.

## Usage

1. Deploy and start the Echo Server service on your NonStop system.
2. Configure the client with the appropriate test parameters and LightWave Server endpoint.
3. Run the client to execute the performance test.
4. Analyze the output metrics to assess LightWave Server's performance.

## Documentation

- [Service README](service/README.md)
- [Client README](client/README.md)


## License

This project is licensed under the [MIT License](LICENSE).

## Support

For support, please contact your NuWave Technologies representative or visit our [support portal](https://support.nuwavetech.com).