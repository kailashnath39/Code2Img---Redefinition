class _a {public static void shuffle1 (int [] a) {
    int length = a.length;
    Random random = new Random ();
    random.nextInt ();
    for (int i = 0;
    i < length; i ++) {
        int j = i + random.nextInt (length - i);
        int tmp = a [i];
        a [i] = a [j];
        a [j] = tmp;
    }
}
}