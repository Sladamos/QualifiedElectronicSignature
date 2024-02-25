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

# Accept verifier
[Return](#contents)

# Connect to sender
[Return](#contents)

# Send file to verifiers
[Return](#contents)

# Accept file from signer
[Return](#contents)

[Return](#contents)