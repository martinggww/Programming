package DesignPattern.Adapter;

public class VlcPlayer implements AdvancedMediaPlayer{
	@Override
	public void playVlc(String audioName){
		System.out.println("Playing Vlc file. Name: " + audioName);
	}
	
	@Override
	public void playMp4(String audioName){
		//System.out.println("Playing Mp4 file. Name " + audioName);
		//DO NOTHING
	}
}
