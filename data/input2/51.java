class _a {public static void BubbleSortByte1 (byte [] num) {
    boolean flag = true;
    byte temp;
    while (flag) {
        flag = false;
        for (int j = 0;
        j < num.length - 1; j ++) {
            if (num [j] > num [j + 1]) {
                temp = num [j];
                num [j] = num [j + 1];
                num [j + 1] = temp;
                flag = true;
            }
        }
    }
}
}