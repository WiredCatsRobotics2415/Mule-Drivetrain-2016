package org.usfirst.frc.team2415.robot.Util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class WiredCatJoystick extends Joystick{
    
    public JoystickButton[] buttons = new JoystickButton[12];
    public JoystickButton trigger;
    
    public WiredCatJoystick(int port){
        super(port);
        
        for (int i = 1; i < buttons.length; i++){
        	buttons[i] = new JoystickButton(this, i);
        }
        
        trigger = new JoystickButton(this,1);
    }
}