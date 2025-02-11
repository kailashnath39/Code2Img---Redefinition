class _a {public static boolean sendMessage (String message, String receipient) {
    JID jid = new JID (receipient);
    Message msg = new MessageBuilder ().withRecipientJids (jid).withBody (message).build ();
    XMPPService xmpp = XMPPServiceFactory.getXMPPService ();
    SendResponse status = xmpp.sendMessage (msg);
    boolean messageSent = false;
    messageSent = (status.getStatusMap ().get (jid) == SendResponse.Status.SUCCESS);
    return messaageSent;
}
}