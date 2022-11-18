package game;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserRecord {
    public String dateTime;
    public int gameScore;
    public int ranking;
    public UserRecord(int gameScore){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss");
        this.dateTime = now.format(formatter);
        this.gameScore = gameScore;
    }
}

