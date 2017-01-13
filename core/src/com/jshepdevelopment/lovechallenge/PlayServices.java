package com.jshepdevelopment.lovechallenge;

/**
 * Created by Jason Shepherd on 1/13/2017.
 */

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
