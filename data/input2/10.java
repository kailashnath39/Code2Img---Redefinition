class _a {public static void deleteRecursively1 (Path dir) throws IOException {
    Files.walkFileTree (dir, new SimpleFileVisitor < Path > () {
        @Override
        public FileVisitResult visitFile (Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete (file);
            return FileVisitResult.CONTINUE;
        }@Override
        public FileVisitResult visitFileFailed (Path file, IOException exc) throws IOException {
            Files.delete (file);
            return FileVisitResult.CONTINUE;
        }@Override
        public FileVisitResult postVisitDirectory (Path dir, IOException exc) throws IOException {
            if (exc == null) {
                Files.delete (dir);
                return FileVisitResult.CONTINUE;
            } else {
                throw exc;
            }
        }}

    );
}
}