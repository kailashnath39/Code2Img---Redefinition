class _a {public static void ZipFiles (File zipfile, File [] files) throws IOException {
    byte [] buffer = new byte [1024];
    FileOutputStream fos = new FileOutputStream (zipfile);
    ZipOutputStream zos = new ZipOutputStream (fos);
    for (int i = 0;
    i < files.length; i ++) {
        File src = files [i];
        FileInputStream fis = new FileInputStream (src);
        ZipEntry entry = new ZipEntry (src.getName ());
        zos.putNextEntry (entry);
        int length;
        while ((length = fis.read (buffer)) > 0) {
            zos.write (buffer, 0, length);
        }
        zos.closeEntry ();
        fis.close ();
    }
    zos.close ();
}
}