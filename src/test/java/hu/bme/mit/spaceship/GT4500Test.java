package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;

  @Before
  public void init(){
    mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    mockSecondaryTorpedoStore = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verifyMockWithBothMethod(true, 1, 1, 1);
  }

  @Test
  public void fireTorpedos_Single_JustThePrimaryStoreIsFired(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verifyMockWithFireMethod(true, 1, 1);
    verifyMockWithFireMethod(false, 0, 1);
  }

  @Test
  public void fireTorpedos_Single_BothStoreIsFired(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean firstResult = ship.fireTorpedos(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(true, secondResult);

    verifyMockWithFireMethod(true, 1, 1);
    verifyMockWithFireMethod(false, 1, 1);
  }

  @Test
  public void fireTorpedos_Single_FirstIsEmptySecondStoreIsFired(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verifyMockWithBothMethod(true, 1, 0, 1);
    verifyMockWithFireMethod(false, 1, 1);
  }

  @Test
  public void fireTorpedos_Single_SecondIsEmptyFirstStoreIsFiredTwice(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean firstResult = ship.fireTorpedos(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(true, secondResult);

    verifyMockWithBothMethod(true, 2, 2, 1);
    verifyMockWithBothMethod(false, 1, 0, 1);
  }

  @Test
  public void fireTorpedos_Single_ForTheSecondTimeBothStoreIsEmpty(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean firstResult = ship.fireTorpedos(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(false, secondResult);

    verifyMockWithBothMethod(true, 2, 1, 1);
  }

  @Test
  public void fireTorpedos_Single_BothAreEmptyNotSuccess(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verifyMockWithBothMethod(true, 1, 0, 1);
    verifyMockWithBothMethod(false, 1, 0, 1);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verifyMockWithBothMethod(false, 1, 1, 1);
  }

  @Test
  public void fireTorpedos_All_SecondIsEmptySecondStoreFiredIsSuccess(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verifyMockWithBothMethod(true, 1, 1, 1);
    verifyMockWithBothMethod(false, 1, 0, 1);
  }

  @Test
  public void fireTorpedos_All_FirstIsEmptySecondStoreFiredIsSuccess(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verifyMockWithBothMethod(true, 1, 0, 1);
    verifyMockWithBothMethod(false, 1, 1, 1);
  }

  @Test
  public void fireLasers_Single_ReturnsFalse(){
    // Arrange

    // Act
    boolean result = ship.fireLasers(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireLasers_All_ReturnsFalse(){
    // Arrange

    // Act
    boolean result = ship.fireLasers(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  public void verifyMockWithBothMethod(boolean isPrimary, int isEmpty, int fire, int fireTimes) {
    TorpedoStore mock = isPrimary ? mockPrimaryTorpedoStore : mockSecondaryTorpedoStore;
    verify(mock, times(isEmpty)).isEmpty();
    verify(mock, times(fire)).fire(fireTimes);
  }

  public void verifyMockWithFireMethod(boolean isPrimary, int fire, int fireTimes) {
    TorpedoStore mock = isPrimary ? mockPrimaryTorpedoStore : mockSecondaryTorpedoStore;
    verify(mock, times(fire)).fire(fireTimes);
  }
}
