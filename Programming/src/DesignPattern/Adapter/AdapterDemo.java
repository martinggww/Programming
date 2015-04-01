package DesignPattern.Adapter;

public class AdapterDemo {
public static void main(String[] args)
{
	AudioPlayer audioPlayer = new AudioPlayer();
	audioPlayer.play("mp3", "mp3_audio");
	audioPlayer.play("mp4", "mp3_audio");
	audioPlayer.play("vlc", "mp3_audio");
}
	
}
