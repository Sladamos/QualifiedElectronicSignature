package pg.proj.pg.plug;

import pg.proj.pg.communicate.CommunicateReceiver;

public interface CryptorPlug {
    void onEncryptCalled();
    void onDecryptCalled();
    void registerCommunicatesReceiver(CommunicateReceiver communicateReceiver);
}
