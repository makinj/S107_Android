# S107_Android
This is a small library that allows you to control the Syma S107 IR helicopter with any IR blaster enabled android device.

**please note that this has only been tested with a Samsung Galaxy S4**

## How to use:

Add the following line to your AndroidManifest

```XML
<uses-permission android:name="android.permission.TRANSMIT_IR" />
```

Then import the Transmitter.java file to the file calling it.

Then you are done!

Well actually then you have to write the code to control it.

Don't get ahead of yourself.

## Function descriptions

### Constructor
This requires:
* frequency (38000 or 57000 is typical)
* channel (either Transmitter.A or Transmitter.B)
* and the Context (just call getApplicationContext())

### run
This begins a thread that blasts the ir messages to the copter.

### stop
This ends the thread that blasts the ir messages to the copter.

### compatible
This returns true or false depending on whether the device running the app has a valid IR blaster for this library.

### running
This returns true or false depending on whether the thread that blasts the ir messages to the copter is currently running

### set
This requires:
* pitch (the special flying thing term for rotation on the axis that causes the copter to move forwards and backwards)
  * This should be between 0 and 127.  64 denotes stationary, 0 is full steam backward, and 127 is full steam ahead.
* yaw (the special flying thing term for rotating on the axis that causes the copter to turn left and right)
  * This should be between 0 and 127.  64 denotes stationary, 0 is full steam left, and 127 is full steam right.
* throttle (the oomph factor. This makes the copter go up and down):
  * This should be between 0 and 127.  0 denotes not even moving, 127 denotes losing the copter in a tree.

### set_throttle
This requires:
* throttle (the oomph factor. This makes the copter go up and down):
  * This should be between 0 and 127.  0 denotes not even moving, 127 denotes losing the copter in a tree.

### set_yaw
This requires:
* yaw (the special flying thing term for rotating on the axis that causes the copter to turn left and right)
  * This should be between 0 and 127.  64 denotes stationary, 0 is full steam left, and 127 is full steam right.

### set_pitch
This requires:
* pitch (the special flying thing term for rotation on the axis that causes the copter to move forwards and backwards)
  * This should be between 0 and 127.  64 denotes stationary, 0 is full steam backward, and 127 is full steam ahead.

### transmit
This blasts a single packet to the copter
You know, in case that's what you're into
