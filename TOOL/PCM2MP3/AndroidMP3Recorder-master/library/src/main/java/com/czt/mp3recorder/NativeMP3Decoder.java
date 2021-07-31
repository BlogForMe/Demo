package com.czt.mp3recorder;

/**
 * 
 * @author bfp 2016/12/22
 *
 */
public class NativeMP3Decoder {
	  
	    static {  
	        System.loadLibrary("mad");  
	    }  
	    public native int initAudioPlayer(String file,int StartAddr);  
	  
	    public native int getAudioBuf(short[] audioBuffer, int numSamples);  
	  
	    public native void closeAduioFile();  
	  
	    public native int getAudioSamplerate();  
	  
}
