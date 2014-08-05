package interfaces;

public interface RemoteDevice {
	void sendCmd(String msg);
	void setsCommandReceiver(ICommandReceiver receiver);
	ICommandReceiver getCommandReceiver();
}
