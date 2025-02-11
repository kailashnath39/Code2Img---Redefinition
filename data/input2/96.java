class _a {public static IProject CreateJavaProject (String name, IPath classpath) throws CoreException {
    IWorkspace workspace = ResourcesPlugin.getWorkspace ();
    IWorkspaceRoot root = workspace.getRoot ();
    IProject project = root.getProject (name);
    project.create (null);
    project.open (null);
    IProjectDescription desc = project.getDescription ();
    desc.setNatureIds (new String [] {JavaCore.NATURE_ID});
    project.setDescription (desc, null);
    IJavaProject javaProj = JavaCore.create (project);
    IFolder binDir = project.getFolder ("bin");
    IPath binPath = binDir.getFullPath ();
    javaProj.setOutputLocation (binPath, null);
    IClasspathEntry cpe = JavaCore.newLibraryEntry (classpath, null, null);
    javaProj.setRawClasspath (new IClasspathEntry [] {cpe}, null);
    return project;
}
}