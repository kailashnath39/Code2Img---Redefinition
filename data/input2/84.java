class _a {
    public static void unzip1 (File zipfile, File outputdir) throws IOException {
    byte [] buffer = new byte [1024];
    if (! outputdir.exists ()) {
        outputdir.mkdirs ();
    }
    ArchiveInputStream zis = new ZipArchiveInputStream (new FileInputStream (zipfile));
    ArchiveEntry ae = zis.getNextEntry ();
    while (ae != null) {
        File newFile = new File (outputdir + File.separator + ae.getName ());
        if (! newFile.getParentFile ().exists ()) newFile.getParentFile ().mkdirs ();

        if (ae.isDirectory ()) {
            if (! newFile.exists ()) newFile.mkdir ();

        } else {
            FileOutputStream fos = new FileOutputStream (newFile);
            int len;
            while ((len = zis.read (buffer)) > 0) {
                fos.write (buffer, 0, len);
            }
            fos.close ();
        }
        ae = zis.getNextEntry ();
    }
    zis.close ();
}
}