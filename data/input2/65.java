class _a {public static Object callMethod3 (Object object, String methodName, Object params [], Class [] types) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Method method = null;
    Class < ? > myClass = object.getClass ();
    NoSuchMethodException ex = null;
    while (method == null && myClass != null) {
        try {
            method = myClass.getDeclaredMethod (methodName, types);
        } catch (NoSuchMethodException e) {
            ex = e;
            myClass = myClass.getSuperclass ();
        }
    }
    if (method == null) throw ex;

    Object returnVal = method.invoke (object, params);
    return returnVal;
}
}