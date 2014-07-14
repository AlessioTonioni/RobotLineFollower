using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using RemoteContorolRobot.Resources;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace RemoteContorolRobot
{
    public partial class MainPage : PhoneApplicationPage
    {
        private ConnectionController _controller;
        // Constructor
        public MainPage()
        {
            _controller = new ConnectionController();
            InitializeComponent();
            disableAll();
        }

        private void SpeedSelector_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            SpeedSelector.Value = (Math.Round(e.NewValue));
            SpeedSelected.Text = "" + SpeedSelector.Value;
            _controller.setSpeed((int)SpeedSelector.Value);
        }

        private void joystick_StickMove(object sender, EventArgs e)
        {
            double angle = joystick.Angle;
            double distance = joystick.Distance;
            _controller.sendCommand(angle, distance);
        }

        private void Connect_Click(object sender, RoutedEventArgs e)
        {
            String ip = Serverip.Text.Trim();
            String port = ServerPort.Text.Trim();
            _controller.Connect(ip, Convert.ToInt32(port),setStatus);
            Status.Text = "Waiting for connection";
        }

        private void setStatus(object sender, SocketAsyncEventArgs e)
        {
            Dispatcher.BeginInvoke(() => { 
                Status.Text = e.SocketError.ToString(); 
                enableAll(); 
                PivotList.SelectedIndex = 1;
                PivotList.IsLocked = true;
            });
            
        }

        private void enableAll()
        {
            joystick.IsEnabled = true;
            PID.IsEnabled = true;
            Mono.IsEnabled = true;
            Bi.IsEnabled = true;
            SpeedSelector.IsEnabled = true;
            Forward.IsEnabled = true;
            Backward.IsEnabled = true;
            Start.IsEnabled = true;
            Stop.IsEnabled = true;
        }

        private void disableAll()
        {
            joystick.IsEnabled = false;
            PID.IsEnabled = false;
            Mono.IsEnabled = false;
            Bi.IsEnabled = false;
            SpeedSelector.IsEnabled = false;
            Forward.IsEnabled = false;
            Backward.IsEnabled = false;
            Start.IsEnabled = false;
            Stop.IsEnabled = false;
        }

        private void ServerPort_Tap_1(object sender, System.Windows.Input.GestureEventArgs e)
        {
            ServerPort.Text = "";
        }

        private void Serverip_Tap_1(object sender, System.Windows.Input.GestureEventArgs e)
        {
            Serverip.Text = "";
        }

        private void Start_Click(object sender, RoutedEventArgs e)
        {
            _controller.startFollowLine();
        }

        private void Stop_Click(object sender, RoutedEventArgs e)
        {
            _controller.sendStopCommand();
        }

        private void PID_Checked(object sender, RoutedEventArgs e)
        {
            _controller.setControllerType("PID");
        }

        private void Bi_Checked(object sender, RoutedEventArgs e)
        {
            _controller.setControllerType("StateBiLine");
        }

        private void Mono_Checked(object sender, RoutedEventArgs e)
        {
            _controller.setControllerType("StateMonoLine");
        }

        private void Forward_Checked(object sender, RoutedEventArgs e)
        {
            _controller.setDirection("Forward");
        }

        private void Backward_Checked(object sender, RoutedEventArgs e)
        {
            _controller.setDirection("Backward");
        }

       

    }
}