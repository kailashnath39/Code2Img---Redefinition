class _a {public static File chooseFileOpen (JFrame frame) {
    File retval;
    JFileChooser fc = new JFileChooser ();
    fc.setDialogTitle ("Select input file.");
    fc.setFileSelectionMode (JFileChooser.FILES_ONLY);
    fc.setMultiSelectionEnabled (false);
    int status = fc.showOpenDialog (frame);
    if (status == JFileChooser.APPROVE_OPTION) {
        retval = fc.getSelectedFile ();
    } else if (status == JFileChooser.CANCEL_OPTION) {
        retval = null;
    } else {
        retval = null;
    }

    fc.setEnabled (false);
    fc.setVisible (false);
    return retval;
}
}