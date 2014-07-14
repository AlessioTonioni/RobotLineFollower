﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using System.Windows.Input;
using System.Diagnostics;

namespace VirtualJoystick
{
    public partial class VirtualJoystick : UserControl
    {

        /// <summary>
        /// Current angle (in degrees)
        /// </summary>
        public static readonly DependencyProperty AngleProperty = DependencyProperty.Register("Angle", typeof(double), typeof(VirtualJoystick), null);

        /// <summary>
        /// Current distanse (from 0 to 100)
        /// </summary>
        public static readonly DependencyProperty DistanceProperty = DependencyProperty.Register("Distance", typeof(double), typeof(VirtualJoystick), null);

        /// <summary>
        /// Delta angle to raise event StickMove
        /// </summary>
        public static readonly DependencyProperty AngleStepProperty = DependencyProperty.Register("AngleStep", typeof(double), typeof(VirtualJoystick), new PropertyMetadata(1.0));

        /// <summary>
        /// Delta distance to raise event StickMove
        /// </summary>
        public static readonly DependencyProperty DistanceStepProperty = DependencyProperty.Register("DistanceStep", typeof(double), typeof(VirtualJoystick), new PropertyMetadata(1.0));

        /// <summary>
        /// Current angle (in degrees)
        /// </summary>
        public double Angle
        {
            get { return Convert.ToDouble(GetValue(AngleProperty)); }
            private set { SetValue(AngleProperty, value); }
        }

        /// <summary>
        /// Current distanse (from 0 to 100)
        /// </summary>
        public double Distance
        {
            get { return Convert.ToDouble(GetValue(DistanceProperty)); }
            private set { SetValue(DistanceProperty, value); }
        }

        /// <summary>
        /// Current angle (in degrees)
        /// </summary>
        public double AngleStep
        {
            get { return Convert.ToDouble(GetValue(AngleStepProperty)); }
            set 
            {
                if (value < 1) value = 1; else if (value > 90) value = 90;
                SetValue(AngleStepProperty, Math.Round(value)); 
            }
        }

        /// <summary>
        /// Current angle (in degrees)
        /// </summary>
        public double DistanceStep
        {
            get { return Convert.ToDouble(GetValue(DistanceStepProperty)); }
            set
            {
                if (value < 1) value = 1; else if (value > 50) value = 50;
                SetValue(DistanceStepProperty, value);
            }
        }

        /// <summary>
        /// 
        /// </summary>
        public event EventHandler StickMove;

        private Point _startPos;
        private double _prevAngle, _prevDistance;

        public VirtualJoystick()
        {
            InitializeComponent();

            Knob.MouseLeftButtonDown += Knob_MouseLeftButtonDown;
            Knob.MouseMove += Knob_MouseMove;
            Knob.MouseLeftButtonUp += Knob_MouseLeftButtonUp;
        }

        void Knob_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            _startPos = e.GetPosition(Base);
            _prevAngle = _prevDistance = 0;
            Knob.CaptureMouse();
        }

        private void Knob_MouseMove(object sender, MouseEventArgs e)
        {
            Point newPos = e.GetPosition(Base);
            Point p = new Point(newPos.X - _startPos.X, newPos.Y - _startPos.Y);

            double angle = Math.Atan2(p.Y, p.X) * 180 / Math.PI;
            if (angle > 0) angle += 90;
            else
            {
                angle = 270 + (180 + angle);
                if (angle >= 360) angle -= 360;
            }

            double distance = Math.Round(Math.Sqrt(p.X * p.X + p.Y * p.Y) / 135 * 100);
            if (distance <= 100)
            {
                Angle = angle;
                Distance = distance;

                knobPosition.X = p.X;
                knobPosition.Y = p.Y;

                if (StickMove != null && (Math.Abs(_prevAngle - angle) > AngleStep || Math.Abs(_prevDistance - distance) > DistanceStep))
                {
                    StickMove(this, new EventArgs());
                    _prevAngle = Angle;
                    _prevDistance = Distance;
                }
            }
        }

        private void Knob_MouseLeftButtonUp(object sender, MouseButtonEventArgs e)
        {
            Knob.ReleaseMouseCapture();
            centerKnob.Begin();
        }

        private void centerKnob_Completed(object sender, EventArgs e)
        {
            Angle = Distance = _prevAngle = _prevDistance = 0;
            if (StickMove != null) StickMove(this, new EventArgs());
        }
    }
}
