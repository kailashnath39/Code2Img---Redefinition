class _a {public static < T > void shuffle3 (T [] a) {
    int length = a.length;
    Random random = new Random ();
    random.nextInt ();
    for (int i = 0;
    i < length; i ++) {
        int j = i + random.nextInt (length - i);
        T tmp = a [i];
        a [i] = a [j];
        a [j] = tmp;
    }
}
}