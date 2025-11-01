package com.ajayc20.pages.vendorapplication;

import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HandelPassConformWindowsAlert {
    public static void handelPasswordWindowAlert() {
        try {
            java.awt.Robot robot = new java.awt.Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println("Pop-up was handled (AWT environment available)");
        } catch (java.awt.AWTException e) {
            System.out.println("Pop-up handling FAILED: Headless environment error.");
        } catch (Exception e) {
            System.out.println("Pop-up handling FAILED: An unexpected error occurred.");
        }
    }
}
