﻿#pragma checksum "C:\Users\Alessio\documents\visual studio 2013\Projects\RemoteContorolRobot\RemoteContorolRobot\VirtualJoystick.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "0CF29F679A9ED0A54B92904FE9528F92"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.34014
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Automation.Peers;
using System.Windows.Automation.Provider;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Imaging;
using System.Windows.Resources;
using System.Windows.Shapes;
using System.Windows.Threading;


namespace VirtualJoystick {
    
    
    public partial class VirtualJoystick : System.Windows.Controls.UserControl {
        
        internal System.Windows.Controls.Canvas Base;
        
        internal System.Windows.Controls.Canvas Knob;
        
        internal System.Windows.Shapes.Ellipse Shadow;
        
        internal System.Windows.Shapes.Ellipse KnobBase;
        
        internal System.Windows.Media.TranslateTransform knobPosition;
        
        internal System.Windows.Media.Animation.Storyboard centerKnob;
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Windows.Application.LoadComponent(this, new System.Uri("/RemoteContorolRobot;component/VirtualJoystick.xaml", System.UriKind.Relative));
            this.Base = ((System.Windows.Controls.Canvas)(this.FindName("Base")));
            this.Knob = ((System.Windows.Controls.Canvas)(this.FindName("Knob")));
            this.Shadow = ((System.Windows.Shapes.Ellipse)(this.FindName("Shadow")));
            this.KnobBase = ((System.Windows.Shapes.Ellipse)(this.FindName("KnobBase")));
            this.knobPosition = ((System.Windows.Media.TranslateTransform)(this.FindName("knobPosition")));
            this.centerKnob = ((System.Windows.Media.Animation.Storyboard)(this.FindName("centerKnob")));
        }
    }
}

