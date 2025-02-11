class _a {public static KeyPair generateKeys1 () throws NoSuchAlgorithmException, IOException {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance ("RSA");
    KeyPair keyPair = keyGen.generateKeyPair ();
    return keyPair;
}
}