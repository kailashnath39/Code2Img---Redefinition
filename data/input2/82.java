class _a {public static void shuffle2 (int [] a) {
    Random random = new Random ();
    random.nextInt ();
    for (int i = a.length - 1;
    i >= 1; i --) {
        int j = random.nextInt (i + 1);
        int tmp = a [i];
        a [i] = a [j];
        a [j] = tmp;
    }
}
}