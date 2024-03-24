package pg.proj.pg.plug;

import pg.proj.pg.communicate.receiver.api.CommunicateReceiver;

public interface CryptorPlug {
    void onEncryptCalled();
    void onDecryptCalled();
    void registerCommunicatesReceiver(CommunicateReceiver communicateReceiver);
}
