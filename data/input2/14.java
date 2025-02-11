class _a {public static void execute2 () throws IOException, InterruptedException {
    Runtime rt = Runtime.getRuntime ();
    Process p = rt.exec ("myCommand");
    BufferedReader in = new BufferedReader (new InputStreamReader (p.getInputStream ()));
    String line = null;
    while ((line = in.readLine ()) != null) System.out.println (line);

    p.waitFor ();
    p.destroy ();
}
}