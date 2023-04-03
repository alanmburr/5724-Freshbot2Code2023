package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedSubsystem extends SubsystemBase {
    int totalLEDs = 83;
    AddressableLED ledStrip;
    AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(totalLEDs);
    XboxController xboxController;
    private Timer timer = new Timer();
    private int ledNumber;
    private int firstHSV;

    public int[] kPurple = {128, 0, 130};
    public int[] kYellow = {255, 255, 0};
    public int[] kGreen = {0, 252, 0};
    public int[] kBlack = {0, 0, 0};
    public int[] kGrey = {1, 1, 1};
    public int[] kGray = kGrey;
    public int[] kWhite = {255, 255, 255};

    // Initialize... :)
    /**
     * @apiNote Check the periodic for XboxController logic!
     * @param port - PWM port for the LED strip
     * @param xbox - an instance of an XboxController class from Robot.java
     */
    public LedSubsystem(int port, XboxController xbox) {
        ledStrip = new AddressableLED(port);
        xboxController = xbox;
        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);
        ledStrip.start();
        timer.start();
    }

    public void AltYellowAndPurple() {
        double time = Math.ceil(timer.get() * 4) % 2;

        for (int i = 0; i < ledBuffer.getLength(); i++) {
          if ((i + time) % 2 == 0) {
            ledBuffer.setRGB(i, kPurple[0], kPurple[1], kPurple[2]);
          } else {
            ledBuffer.setRGB(i, kYellow[0], kYellow[1], kYellow[2]);
          }
        }

        ledStrip.setData(ledBuffer);
    }

    public void Purple() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, kPurple[0], kPurple[1], kPurple[2]);
        }

        ledStrip.setData(ledBuffer);
    }

    public void Yellow() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, kYellow[0], kYellow[1], kYellow[2]);
        }

        ledStrip.setData(ledBuffer);
    }

    public void PercentageLit(double percentAsDecimal) {
        int TotalLeds = ledBuffer.getLength();
        for (int i = 0; i < TotalLeds; i++) {
          if (i > Math.ceil(TotalLeds * percentAsDecimal)) {
            ledBuffer.setRGB(i, kPurple[0], kPurple[1], kPurple[2]);
          } else {
            ledBuffer.setRGB(i, kYellow[0], kYellow[1], kYellow[2]);
          }
        }

        ledStrip.setData(ledBuffer);
    }

    public void InvertedPercentageLit(double percentAsDecimal) {
      int TotalLeds = ledBuffer.getLength();
      for (int i = 0; i < TotalLeds; i++) {
        if (i > Math.ceil(TotalLeds * percentAsDecimal)) {
          ledBuffer.setRGB(i, kYellow[0], kYellow[1], kYellow[2]);
        } else {
          ledBuffer.setRGB(i, kPurple[0], kPurple[1], kPurple[2]);
        }
      }

      ledStrip.setData(ledBuffer);
    }

    public void Rainbow() {
      int TotalLeds = ledBuffer.getLength();
      for (int i = 0; i < TotalLeds; i++) {
        final var hue = (firstHSV + (i * 180 / TotalLeds)) % 180;
        ledBuffer.setHSV(i, hue, 255, 128);
      }
      firstHSV += 3;
      firstHSV %= 180;

      ledStrip.setData(ledBuffer);
    }

    public void Green() {
      int TotalLeds = ledBuffer.getLength();
      for (int i = 0; i < TotalLeds; i++) {
        ledBuffer.setRGB(i, kGreen[0], kGreen[1], kGreen[2]);
      }

      ledStrip.setData(ledBuffer);
    }

    public void FlashGreen() {
      int TotalLeds = ledBuffer.getLength();
      double time = Math.ceil(timer.get() * 1.5) % 2;
      for (int i = 0; i < TotalLeds; i++) {
        if ((i + time) % 2 == 0) {
          ledBuffer.setRGB(i, kGreen[0], kGreen[1], kGreen[2]);
        } else {
          ledBuffer.setRGB(i, kBlack[0], kBlack[1], kBlack[2]);
        }
      }

      ledStrip.setData(ledBuffer);
    }

    public void Off() {
      int TotalLeds = ledBuffer.getLength();
      for (int i = 0; i < TotalLeds; i++) {
        ledBuffer.setRGB(i, kBlack[0], kBlack[1], kBlack[2]);
      }

      ledStrip.setData(ledBuffer);
    }

    public void White() {
        int TotalLeds = ledBuffer.getLength();
        for (int i = 0; i < TotalLeds; i++) {
            ledBuffer.setRGB(i, kWhite[0], kWhite[1], kWhite[2]);
        }

        ledStrip.setData(ledBuffer);
    }

    /**
     * @deprecated Because it doesn't work and there's no experimental flag.
     */
    public void RunUpStrip() {
      double time = Math.ceil(timer.get() * 50) % 2;
      int TotalLeds = ledBuffer.getLength();
      for (int i = 0; i < TotalLeds; i++) {
        if ((i-5+time) % 2 == 0) {
          ledBuffer.setRGB(ledNumber, kYellow[0], kYellow[1], kYellow[2]);
        } else {
          ledBuffer.setRGB(i, kGrey[0], kGrey[1], kGrey[2]);
        }
      }

      ledNumber += 5;
      ledNumber %= TotalLeds;
      ledStrip.setData(ledBuffer);
    }

    /* We @Override periodic() because it causes less trouble 
     * and it means that there's no need to do anything
     * other than just initialize the LedSubsystem in Robot.java
    */
    @Override
    public void periodic() {
      White();
    }
}
