package ua;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Random;

//@Component
public class MusicPlayer {
    private final List<Music> listOfMusic;

    @Value("${musicPlayer.volume}")
    int volume;
    @Value("${musicPlayer.name}")
    String name;

//    @Autowired
    public MusicPlayer(List<Music> listMusic) {
        this.listOfMusic = listMusic;
    }

    public String playMusic() {
        Random random = new Random();
        return "Playing" + listOfMusic.get(random.nextInt(listOfMusic.size())).song();
    }
}