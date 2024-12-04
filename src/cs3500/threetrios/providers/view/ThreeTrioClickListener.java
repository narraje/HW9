package cs3500.threetrios.providers.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This abstracted class contains the default method implementations for JSwing click events.
 */
public abstract class ThreeTrioClickListener implements MouseListener {
  @Override
  public abstract void mousePressed(MouseEvent e);

  @Override
  public void mouseClicked(MouseEvent e) {
    // this method is not implemented but must be declared to fulfill MouseListener contracts
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // this method is not implemented but must be declared to fulfill MouseListener contracts
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // this method is not implemented but must be declared to fulfill MouseListener contracts
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // this method is not implemented but must be declared to fulfill MouseListener contracts
  }
}
