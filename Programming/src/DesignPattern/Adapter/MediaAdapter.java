package DesignPattern.Adapter;

public class MediaAdapter implements MediaPlayer{
	//Defined an instance of advancedMediaPlayer
	//private
	AdvancedMediaPlayer advancedMediaPlayer;
	
	//constructor
	public MediaAdapter(String audioType){
		if(audioType.equalsIgnoreCase("Vlc"))
			advancedMediaPlayer = new VlcPlayer();
		else if(audioType.equalsIgnoreCase("Mp3"));
			advancedMediaPlayer = new Mp4Player();
	}
	
	@Override
	public void play(String audioType, String audioName){
		if(audioType.equalsIgnoreCase("Vlc"))
			advancedMediaPlayer.playVlc(audioName);
		if(audioType.equalsIgnoreCase("Mp3"))
			advancedMediaPlayer.playMp4(audioName);
	}
}
