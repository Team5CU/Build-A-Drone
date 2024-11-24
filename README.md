DIY Drone Kit

Welcome to the DIY Drone Kit repository! This project is designed for drone enthusiasts who want to assemble their own drone, understand its working principles, and expand its capabilities. Whether you're a beginner or an experienced maker, this kit provides all the tools and resources you need to get started.
📖 Instructions

Comprehensive step-by-step directions for assembling and operating the drone are included in the PDF guide provided with the kit. The PDF contains:

    Assembly Instructions: How to build the drone.
    Setup Guide: How to configure the software and hardware components.
    Troubleshooting: Common issues and their solutions.

To access the instructions:

    Download the PDF file included in this repository or from your kit's package.
    Follow the steps carefully to complete your drone assembly and configuration.

🛠️ Code Overview

This repository contains various pieces of code to help you understand the drone's operation and give you the flexibility to modify or expand its capabilities.
1. BLE Server Code

    Manages the Bluetooth communication between the drone and the controller app.
    Supports commands for takeoff, landing, and in-flight adjustments.

2. Android App Code

    Source code for the companion app that communicates with the drone via Bluetooth.
    Includes joystick-based flight controls and a block-coding interface for custom flight patterns.

3. Drone Flight Controller Code

    Embedded software that runs on the drone's microcontroller.
    Handles motor control, flight stabilization, and navigation logic.

4. Test Code

    Scripts and programs to test individual components like sensors and communication modules.
    Ensures that all hardware and software components are functioning as expected.

🚀 Getting Started

To make the most of this repository, you can:

    Clone the repository to your local machine:

    git clone https://github.com/Team5CU/Build-A-Drone.git

    Explore the folders for the different code components.
    Refer to the PDF guide to set up your drone.

📂 Repository Structure

Here's a breakdown of the repository contents:

    BLE_Server_Code/            # Bluetooth Low Energy server implementation
    AndroidApplication/         # Android app source code
    Drone_Flight_Controller/    # Code for the drone's flight controller
    Test_Code/                  # Testing scripts and tools
    Instructions.pdf            # Detailed guide for assembly and setup
    README.md                   # This file

How to Use the Code

    BLE Server:
        Upload the BLE server code to the Bluetooth-enabled raspberry pi.
        Pair the drone with the Android for communication.

    Flight Controller:
        Upload the flight controller code into the same file as the BLE.
        
    Test Code:
        Run the test scripts to calibrate and ensure all components are working before connecting with the app.

    Android App:
        Open the Android_App folder in Android Studio.
        Build and install the app on your Android device.
        Use the app to control your drone via Bluetooth.

🧩 Learn and Build More

This project is fully open-source! Feel free to:

    Modify the flight algorithms for custom drone behavior.
    Enhance the Android app with additional features.
    Experiment with the BLE server code to support new commands or devices.

🛡️ Disclaimer

This kit is intended for educational purposes. Please follow all safety instructions in the PDF guide when assembling and flying your drone.

📬 Contact

For support or inquiries, please contact us by opening an issue in this repository.

🙏 Acknowledgments and Credits

Grace Boccia, Kyle Buttermore, Chase Kinley, Josh White, & Langston Cooper
Clemson University Holcombe Department of Electrical and Computer Engineering
Senior Design II: DIY Drone Kit
Last Updated: December 4, 2024

This project wouldn't have been possible without the help and inspiration from various open-source projects and resources. We’d like to acknowledge and thank the following for their contributions:

    Android Developers Documentation:
        Android Tutorial For Beginners Course on YouTube by ProgrammingKnowledge: https://www.youtube.com/watch?v=EknEIzswvC0&list=PLS1QulWo1RIbb1cYyzZpLFCKvdYV_yJ-E
    GitHub BLE Server Communication Open Source Code: 
        https://github.com/mengguang/pi-ble-uart-server
    Android Application Drone Images:
        ChatGPT
    Troubleshooting:
        Raspberry pi Forum
        ChatGPT
        StackOverflow

If we’ve missed anyone or any resource, please let us know, and we’ll be happy to update the credits. We greatly appreciate the open-source community and all the talented developers who share their work for others to learn and build upon! ❤️
