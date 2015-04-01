package DesignPattern.Adapter;

public class MediaAdapter implements MediaPlayer{
	AdvancedMediaPlayer advancedMediaPlayer;
	public MediaAdapter(String audioType){
		if(audioType.equalsIgnoreCase("Vlc"))
			advancedMediaPlayer = new VlcPlayer();
		else if(audioType.equalsIgnoreCase("Mp3"));
			advancedMediaPlayer = new Mp3Player();
	}
	
	@Override
	public void play(String audioType, String audioName){
		if(audioType.equalsIgnoreCase("Vlc"))
			advancedMediaPlayer.playVlc(audioName);
		if(audioType.equalsIgnoreCase("Mp3"))
			advancedMediaPlayer.playMp4(audioName);
		
	}
}
http://www.tutorialspoint.com/design_pattern/adapter_pattern.htm 
	Step 4