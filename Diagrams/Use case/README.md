# Contents
- [Generate RSA keys pair](#generate-rsa-keys-pair)

![Use case diagram](<Use case diagram.jpg>)

# Generate RSA keys pair
[Return](#contents)

- Keyholder requests for keys generation
- System demands key length
- Keyholder provides key length
- System demands pin number
- Keyholder provides pin number
- System demands path to save keys 
- Keyholder provides relative or cannonical path
- System prints generated keys
- System generates keys in separate files in provided path

# Decrypt file
[Return](#contents)

- User requests for decrypt file
- System provides cipher methods
- System asks to select cipher method
- Keyholder selects cipher method
- System asks to select file which will be decrypted
- Keyholder selects specific file
- System checks if file type is allowed
-> If file type is not allowed, use case is failed
- System decrypts selected file
- System saves decrypted file

# Encrypt file
[Return](#contents)

- User requests for encrypt file
- System provides cipher methods
- System asks to select cipher method
- Keyholder selects cipher method
- System asks to select file which will be encrypted
- Keyholder selects specific file
- System checks if file type is allowed
    - If file type is not allowed, use case is failed
- System encrypts selected file
- System saves encrypted file

# Sign file
[Return](#contents)

- User requests for sign file
- System asks to select file with RSA private key
- User selects specific file
- Systems demands pin number
- User provides pin number
    - If pin number is invalid, use case is failed
- System checks if the key is valid and meets requirements
    - If key doesn't meet requirements, use case is failed
- System asks to select file which will be signed
- User selects specific file
- System checks if file type is allowed
    - If file type is not allowed, use case is failed
- System signs selected file
- System saves signed file

# Verify file
[Return](#contents)

- User requests for verify file
- System asks to select file with RSA public key
- System checks if the key is valid and meets requirements
    - If key doesn't meet requirements, use case is failed
- System asks to select file which will be verified
- User selects specific file
- System checks if file type is allowed
    - If file type is not allowed, use case is failed
- System verifies selected file

# Connect to sender
[Return](#contents)

- User requests for connect to sender
- System asks for sender IP and port
- User provides IP and port of sender
- System sends request on provided IP
- System notifies about connection status

# Accept verifier
[Return](#contents)

- System notifies about pending request
- User accepts or declines verifier
- System sends connection status to specific verifier

# Send file to verifiers
[Return](#contents)

- User requests for send file to verifiers
- System asks to select file which will be sent
- User selects specific file
- System checks if file type is allowed
    - If file type is not allowed, use case is failed
- System demands confirmation
- User confirms operation
- System sends file to verifiers

# Accept file from signer
[Return](#contents)

- System notifies about incoming file
- User accepts incoming file
- System sends status to signer
- System asks to select directory to save file
- User selects specific directory 
- System saves incoming file

[Return](#contents)