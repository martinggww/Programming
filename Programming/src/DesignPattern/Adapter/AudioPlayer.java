package DesignPattern.Adapter;

public class AudioPlayer implements MediaPlayer{
MediaAdapter mediaAdapter;

@Override
public void play(String audioType, String audioName)
{
  if(audioType.equalsIgnoreCase("mp3"))
	System.out.println("Play mp.3 file" + audioName);
  if(audioType.equalsIgnoreCase("mp4") || audioType.equalsIgnoreCase("lvc")){
	mediaAdapter = new MediaAdapter(audioType);
	mediaAdapter.play(audioType, audioName);
  }else
  {
	  System.out.println("Invalid media" + audioType + audioName);
  }
}
}
