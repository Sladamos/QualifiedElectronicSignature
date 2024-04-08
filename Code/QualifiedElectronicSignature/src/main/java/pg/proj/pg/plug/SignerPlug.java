package pg.proj.pg.plug;

import pg.proj.pg.communicate.receiver.api.CommunicateReceiver;

public interface SignerPlug {
    void onSignCalled();
    void registerCommunicatesReceiver(CommunicateReceiver communicateReceiver);
}
