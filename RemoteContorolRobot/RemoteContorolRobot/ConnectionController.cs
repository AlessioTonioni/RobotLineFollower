using sdkMulticastCS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Threading;

namespace RemoteContorolRobot
{
    class ConnectionController
    {
        private Socket _socket;
        const int TIMEOUT_MILLISECONDS = 5000;
        const int MAX_BUFFER_SIZE = 2048;

        private string controllerType="";
        private int speed;
        private string direction="";

        private string lastCmd="";


        public ConnectionController()
        {
        }

        public void Connect(string hostName, int portNumber,  EventHandler<SocketAsyncEventArgs> callBack)
        {
           
            // Create DnsEndPoint. The hostName and port are passed in to this method.
            DnsEndPoint hostEntry = new DnsEndPoint(hostName, portNumber);

            // Create a stream-based, TCP socket using the InterNetwork Address Family. 
            _socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            // Create a SocketAsyncEventArgs object to be used in the connection request
            SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();
            socketEventArg.RemoteEndPoint = hostEntry;

            // Inline event handler for the Completed event.
            // Note: This event handler was implemented inline in order to make this method self-contained.
            socketEventArg.Completed+=callBack;


            // Make an asynchronous Connect request over the socket
            _socket.ConnectAsync(socketEventArg);

        }

        public void Send(string data)
        {
           /* // We are re-using the _socket object initialized in the Connect method
            if (_socket != null)
            {
                // Create SocketAsyncEventArgs context object
                SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();

                // Set properties on context object
                socketEventArg.RemoteEndPoint = _socket.RemoteEndPoint;
                socketEventArg.UserToken = null;

                // Add the data to be sent into the buffer
                byte[] payload = Encoding.UTF8.GetBytes(data);
                socketEventArg.SetBuffer(payload, 0, payload.Length);

                // Make an asynchronous Send request over the socket
                _socket.SendAsync(socketEventArg);
            }*/
            Send(data, null);
        }

        public void Send(string data, EventHandler<SocketAsyncEventArgs> callback)
        {
            byte[] msg = Encoding.UTF8.GetBytes(data);
            byte[] size = BitConverter.GetBytes(msg.Length);
            byte[] payload = new byte[size.Length + msg.Length];
            System.Buffer.BlockCopy(size, 0, payload, 0, size.Length);
            System.Buffer.BlockCopy(msg, 0, payload, size.Length, msg.Length);
            Send(payload, callback);
        }

        public void Send(byte[] data, EventHandler<SocketAsyncEventArgs> callback)
        {
            // Create SocketAsyncEventArgs context object
            SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();

            // Set properties on context object
            socketEventArg.RemoteEndPoint = _socket.RemoteEndPoint;
            if(callback!=null)
                socketEventArg.Completed += callback;
            socketEventArg.UserToken = null;

            // Add the data to be sent into the buffer
            socketEventArg.SetBuffer(data, 0, data.Length);

            // Make an asynchronous Send request over the socket
            _socket.SendAsync(socketEventArg);
        }


        internal void startFollowLine()
        {
            String msgToSend = "cnt "+controllerType+" "+speed+" "+direction;
            Send(msgToSend);

        }

        internal void sendStopCommand()
        {
            sendCommand(0, 0);
        }

        internal void setControllerType(string p)
        {
            controllerType = p;
        }

        internal void setDirection(string p)
        {
            if (direction.Equals("Forward"))
                direction = "True";
            else
                direction = "False";
        }

        internal void setSpeed(int p)
        {
            speed = p;
        }

        internal void sendCommand(double angle, double distance)
        {
            String cmdToSend = "cmd " + getCommandTypeAndSpeed(angle, distance);
            if (cmdToSend != lastCmd)
            {
                Send(cmdToSend);
                lastCmd = cmdToSend;
            }
        }

        private string getCommandTypeAndSpeed(double angle, double distance)
        {
            if (distance < 20)
                return "STOP 0";
            if(angle<=20 || angle>340)
                return "FORWARD "+getSpeed(distance);
            else if(angle <70)
                return "FORWARDRIGHT "+getSpeed(distance);
            else if(angle<110)
                return "RIGHT " +getSpeed(distance);
            else if(angle<160)
                return "BACKWARDRIGHT " +getSpeed(distance);
            else if(angle<200)
                return "BACKWARD " +getSpeed(distance);
            else if(angle<240)
                return "BACKWARDLEFT " +getSpeed(distance);
            else if(angle<290)
                return "LEFT " +getSpeed(distance);
            else
                return "FORWARDLEFT " + getSpeed(distance);
        }

        private string getSpeed(double distance)
        {
            if (distance < 50)
                return "50";
            else if (distance < 75)
                return "75";
            else return "100";
        }


    }
}
