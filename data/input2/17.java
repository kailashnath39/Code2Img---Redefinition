class _a {public FileVisitResult postVisitDirectory (Path dir, IOException exc) throws IOException {
    if (exc == null) {
        Files.delete (dir);
        return FileVisitResult.CONTINUE;
    } else {
        throw exc;
    }
}
}