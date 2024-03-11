extern crate openssl;

use openssl::rsa::{Rsa};
use openssl::symm::Cipher;
use openssl::pkey::Private;
use openssl::hash::{Hasher, MessageDigest};
use std::hash::{DefaultHasher, Hash};
use std::io::{self, Write};
use std::fs::File;
use std::path::Path;

fn main() {
    println!("Welcome to RSA Key Pair Generator!");

    // let key_length = get_input("Enter key length: ").parse::<u32>().unwrap();
    ley key_length = 4096;

    let pin = get_input("Enter pin number: ");

    // let pin_hash = hash_pin(&pin);

    let path = get_input("Enter path to save keys: ");

    let rsa = Rsa::generate(key_length).unwrap();

    let public_key = rsa.public_key_to_pem().unwrap();

    let private_key = rsa.private_key_to_pem_passphrase(Cipher::aes_128_cbc(),pin.as_bytes()).unwrap();

    save_key(&public_key, &format!("{}/public_key.pem", path));
    save_key(&private_key, &format!("{}/private_key.pem", path));

    println!("RSA key pair generated successfully!");
}

fn get_input(prompt: &str) -> String {
    print!("{}", prompt);
    io::stdout().flush().unwrap();

    let mut input = String::new();
    io::stdin().read_line(&mut input).unwrap();
    input.trim().to_string()
}

fn save_key(key: &[u8], path: &str) {
    let mut file = File::create(Path::new(path)).unwrap();
    file.write_all(key).unwrap();
}

// fn hash_pin(pin: &str) -> Vec<u8> {
//     openssl::hash::hash(openssl::hash::MessageDigest::sha256(), pin.as_bytes()).unwrap()
// }