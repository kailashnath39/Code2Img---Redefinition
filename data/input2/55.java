class _a {public static < T extends Comparable < T > > void BubbleSortComparable1 (T [] num) {
    int j;
    boolean flag = true;
    T temp;
    while (flag) {
        flag = false;
        for (j = 0; j < num.length - 1; j ++) {
            if (num [j].compareTo (num [j + 1]) > 0) {
                temp = num [j];
                num [j] = num [j + 1];
                num [j + 1] = temp;
                flag = true;
            }
        }
    }
}
}