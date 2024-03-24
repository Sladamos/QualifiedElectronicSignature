package pg.proj.pg.communicate.receiver.api;

import pg.proj.pg.communicate.definition.Communicate;

public interface CommunicateReceiver {
    void onCommunicateOccurred(Communicate communicate);
}
