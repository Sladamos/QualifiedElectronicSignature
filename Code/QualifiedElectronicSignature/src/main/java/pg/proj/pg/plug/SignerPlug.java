package pg.proj.pg.plug;

import pg.proj.pg.communicate.CommunicateReceiver;

public interface SignerPlug {
    void onSignCalled();
    void onVerifyCalled();
    void registerCommunicatesReceiver(CommunicateReceiver communicateReceiver);
}
