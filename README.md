# QR-based Document Transaction and Information System (QrDTIS)
**Software Engineering 1 Project**



# QR Code Generator
## Overview

This application is a part of a larger system designed to streamline the purchase process within the university registrar of Isabela State University. The primary function of this application is to generate a QR code that represents a ticket or request form for a purchase (Certificate of Assessment, Certificate of Enrollment, Certificate of Grades, etc.)

The generated QR code is presented to the cashier, who then processes it using another application within the system.

## Features

- **QR Code Generation**: Generates a QR code based on user input, including name, ID number, and course/year/section.
- **User Interface**: Simple and understandable UI for quick and easy use
- **Integration**: Part of a bigger system that processes the whole transaction

## Usage

1. **Input Details**: Enter the required details, including name, ID number, and course/year/section.
2. **Generate QR Code**: Click the "Generate QR" button to create a QR code based on the provided information.
3. **Present QR Code**: The generated QR code can be presented to the cashier for processing.

## Requirements

- **[Android Studio](https://developer.android.com/studio)**: Development environment for building and running the application.
- **[ZXing Android Embedded Library](https://github.com/journeyapps/zxing-android-embedded)**: Used for generating QR codes. Include the following dependency in your `build.gradle` file:



  ```gradle
  implementation 'com.google.zxing:core:3.4.1'
  implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
  ```

## Installation

1. **Clone the Repository**:

```bash
git clone https://github.com/brynyz/QrDTIS---Generator.git
```
2. **Open in Android Studio**: Open the project in Android Studio and build the project to ensure all dependencies are resolved.

3. **Run the Application**: Connect an Android device or use an emulator to run the application.

## Contact

Feedback or questions are welcomed. Contact bryllenyelm@gmail.com
