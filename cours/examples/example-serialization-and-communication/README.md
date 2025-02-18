# Serialization and Communication

This repository contains examples of different approches for serializing and communicating data between different processes with java.

The following packages are included in this repository:
- `serialization`: Basic serialization and deserialization of objects with java.
- `externalization`: Serialization and deserialization of objects with java using the Externalizable interface.
- `xml`: Serialization and deserialization of objects with java using JAXB, DOM, SAX, and StAX.
- `xsd`: Validation of XML files with java using XSD.
- `xjc`: Generation of java classes from XSD files with xjc.
- `protobuf`: Serialization and deserialization of objects with java using Protocol Buffers.
- `grpc`: Communication between different processes with java using gRPC.

Additional notes:
- Serialization and deserialization of json objects is not included in this repository as it has already been covered with jax-rs and jackson.
- Use of RMI (Remote Method Invocation) is not included in this repository as it is considered outdated and not recommended for new projects.
