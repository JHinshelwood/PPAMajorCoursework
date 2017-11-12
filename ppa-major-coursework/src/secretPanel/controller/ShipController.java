package secretPanel.controller;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

import secretPanel.model.AlienShip;
import secretPanel.model.Level;
import secretPanel.model.Missile;

/**
 *  This key listener keeps a record of all the keys pressed in an array and also modifies certain aspects of the given
 *  Ship model, including shooting when Enter is pressed and an updateModel method that can be called in the paint method
 *  of the desired GamePanel.
 */
public class ShipController implements KeyListener {

    //array which holds an evidence of all the keys pressed
    public static boolean keyHeld[];

    //level on which to impose updates
    private static Level level;

    public ShipController(Level level) {
        keyHeld = new boolean[100];
        ShipController.level = level;
    }

    //sets the appropriate value to true and also shoots if the key is enter
    @Override
    public void keyPressed(KeyEvent e) {

        keyHeld[e.getKeyCode()] = true;

        if (keyHeld[KeyEvent.VK_ENTER] == true) {
            level.getShip().shoot();
            //playSoundEffect(laserFile);
        }

    }

    //sets the appropiate value to false
    @Override
    public void keyReleased(KeyEvent e) {
        keyHeld[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {/* not used */}

    public static boolean[] getKeyHeld() {
        return keyHeld;
    }

    /**
     * This method updates the ships data based on the input of the user. Pressing A and D rotates the ship, and pressing W thrusts it.
     */
    public static void updateModel() {
        if (keyHeld[KeyEvent.VK_D]) {

            level.getShip().increaseRotationAngle();

        }
        if (keyHeld[KeyEvent.VK_A]) {

            level.getShip().decreaseRotationAngle();

        }
        if (keyHeld[KeyEvent.VK_W]) {

            level.getShip().setMovingAngle(level.getShip().getRotationAngle());
            level.getShip().increaseXVelocity(level.getShip().shipXMoveAngle(level.getShip().getMovingAngle()) * 0.1);
            level.getShip().increaseYVelocity(level.getShip().shipYMoveAngle(level.getShip().getMovingAngle()) * 0.1);
        }


    }

    /**
     * This method updates the model and view for the alien ships
     * @param graphicSettings
     */
    public void updateAlienShips(Graphics2D graphicSettings) {
        level.updateAlienShips();
        for(AlienShip alienShip : level.getAlienShips()) {
            if(alienShip.onScreen)
                graphicSettings.draw(alienShip);
        }
        if(level.getMartin() != null) graphicSettings.drawImage(level.getMartin().getImage(), level.getMartin().getULeftXPos(), level.getMartin().getULeftYPos(),  100, 100, null);
    }

    /**
     * This method updates the model and view for the player.
     * @param graphicSettings
     */
    public void updatePlayer(Graphics2D graphicSettings, AffineTransform identity) {
        level.updatePlayer();
        graphicSettings.translate(level.getShip().getXCenter(), level.getShip().getYCenter());
        graphicSettings.rotate(Math.toRadians(level.getShip().getRotationAngle()));

        updateModel();

        graphicSettings.draw(level.getShip());

        for(int i = 0; i < level.getShip().getMissiles().size(); i++) {
            Missile missile = level.getShip().getMissiles().get(i);

            missile.move();

            if (missile.onScreen) {
                graphicSettings.setTransform(identity);
                graphicSettings.translate(missile.getCenterX(), missile.getCenterY());
                graphicSettings.draw(missile);
            } else {
                level.getShip().getMissiles().remove(i);
            }
        }
    }

}
