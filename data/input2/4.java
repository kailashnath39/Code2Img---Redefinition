class _a {public static void encryptFile (File in, File out, SecretKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
    Cipher c = Cipher.getInstance ("AES");
    c.init (Cipher.ENCRYPT_MODE, key);
    FileInputStream fin = new FileInputStream (in);
    FileOutputStream fout = new FileOutputStream (out);
    CipherOutputStream cout = new CipherOutputStream (fout, c);
    int i;
    byte [] data = new byte [1024];
    while ((i = fin.read (data)) != - 1) cout.write (data, 0, i);

    fin.close ();
    cout.close ();
}
}