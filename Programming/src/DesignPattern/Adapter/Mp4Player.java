package DesignPattern.Adapter;

public class Mp4Player implements AdvancedMediaPlayer{
	@Override
	public void playVlc(String audioName){
		//System.out.println("Playing Vlc file. Name: " + audioName);
		//DO NOTHING
	}
	
	@Override
	public void playMp4(String audioName){
		System.out.println("Playing Mp4 file. Name " + audioName);
		
	}
}
