/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * makinj64@gmail.com wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Joshua Makinen
 * ----------------------------------------------------------------------------
 */
package club.chillestbook.s107re;

import android.content.Context;
import android.hardware.ConsumerIrManager;

/**
 * Created by root on 7/25/15.
 */
public class Transmitter {
    public static final int A = 0;
    public static final int B = 1;
    private int pulse=300;
    private int zero_low =300;
    private int one_low =600;
    private int header_pulse =2000;
    private int _channel;
    private int _freq;
    private int _yaw =63;
    private int _pitch =63;
    private int _throttle =0;
    private int _tr=63;
    private ConsumerIrManager irManager;
    private Thread thread;

    public Transmitter(int freq, int channel, Context context){
        _channel = channel;
        _freq = freq;
        irManager = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
    }

    public void run(){
        if(irManager.hasIrEmitter()) {
            if(compatible()){
                stop();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!thread.isInterrupted()) {
                            transmit();
                        }
                    }
                });
                thread.start();
            }
        }
    }

    public void stop(){
        if(thread!=null) {
            thread.interrupt();
        }
    }

    public boolean compatible(){
        if(irManager.hasIrEmitter()) {
            ConsumerIrManager.CarrierFrequencyRange[] freqRange = irManager.getCarrierFrequencies();
            if (freqRange[0].getMaxFrequency() >= _freq && freqRange[0].getMinFrequency() <= _freq) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean running(){
        if(thread!=null){
            return true;
        }else{
            return false;
        }
    }

    public void set(int pitch, int yaw, int throttle){
        if (pitch > 127){
            _pitch = 127;
        }else if(pitch < 0){
            _pitch = 0;
        }else{
            _pitch = pitch;
        }

        if (yaw > 127){
            _yaw = 127;
        }else if(yaw < 0){
            _yaw = 0;
        }else{
            _yaw = yaw;
        }

        if (throttle > 127){
            _throttle = 127;
        }else if(throttle < 0){
            _throttle = 0;
        }else{
            _throttle = throttle;
        }

        if(_channel==B){
            _throttle +=128;
        }

    }

    public void set_throttle(int throttle){
        if (throttle > 127){
            _throttle = 127;
        }else if(throttle < 0){
            _throttle = 0;
        }else{
            _throttle = throttle;
        }

        if(_channel==B){
            _throttle +=128;
        }
    }

    public void set_yaw(int yaw){
        if (yaw > 127){
            _yaw = 127;
        }else if(yaw < 0){
            _yaw = 0;
        }else{
            _yaw = yaw;
        }
    }

    public void set_pitch(int pitch){
        if (pitch > 127){
            _pitch = 127;
        }else if(pitch < 0){
            _pitch = 0;
        }else{
            _pitch = pitch;
        }
    }

    public void transmit(){
        int[] pattern = constructPattern();
        irManager.transmit(_freq,pattern);
    }

    private int[] constructPattern(){
        int[] pattern = new int[3];
        pattern[0]= header_pulse;
        pattern[1]= header_pulse;
        pattern[2]=pulse;
        pattern = concat(pattern, int2pat(_yaw));
        pattern = concat(pattern, int2pat(_pitch));
        pattern = concat(pattern, int2pat(_throttle));
        pattern = concat(pattern, int2pat(_tr));
        return pattern;
    }

    private int[] int2pat(int val){
        int[] pattern = new int[16];
        int curr=128;
        for(int i = 0;i<pattern.length;i++){
            if(i%2==0){
                if(val>=curr) {
                    pattern[i]= one_low;
                    val-=curr;
                }else{
                    pattern[i]= zero_low;
                }
                curr/=2;
            }else{
                pattern[i]=pulse;
            }
        }
        return pattern;
    }

    private int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] C= new int[aLen+bLen];
        System.arraycopy(a, 0, C, 0, aLen);
        System.arraycopy(b, 0, C, aLen, bLen);
        return C;
    }
}
