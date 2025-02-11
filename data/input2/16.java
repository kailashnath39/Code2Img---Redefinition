class _a {public FileVisitResult visitFileFailed (Path file, IOException exc) throws IOException {
    Files.delete (file);
    return FileVisitResult.CONTINUE;
}
}