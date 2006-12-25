/*
 *
 * Copyright (c) 2004, 2005, 2006 void.fm
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name void.fm nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package etm.core.monitor;


/**
 * The MeasurementPoint represents one measurement.
 * <p/>
 * <p/>
 * Usage example:
 * </p>
 * <pre>
 *  EtmMonitor monitor = ...;
 *  MeasurementPoint point = new MeasurementPoint(monitor, "name");
 *  try {
 *
 *   // execute business code
 *
 *  } finally {
 *    point.collect();
 *  }
 *  </pre>
 *
 * @author void.fm
 * @version $Id: MeasurementPoint.java,v 1.13 2006/07/01 17:12:28 french_c Exp $
 * @see EtmMonitor
 */

public class MeasurementPoint {
  private static final long SECOND_MULTIPLIER = 1000L;

  private final EtmMonitor monitor;
  private MeasurementPoint parent = null;

  private String name;
  private long startTime = 0;
  private long endTime = 0;
  private long ticks = 0;
  private long startTimeMillis = 0;

  /**
   * Creates a new measurement point using the given
   * monitor and name.
   *
   * @param aMonitor The monitor to be associated with.
   * @param aName    The name of this measurement point, may be null at construction time.
   *                 In this case you may need to set the name using {@link #alterName(String)}
   *                 before calling {@link #collect()}.
   */

  public MeasurementPoint(EtmMonitor aMonitor, String aName) {
    monitor = aMonitor;
    name = aName;
    startTimeMillis = System.currentTimeMillis();
    monitor.visitPreMeasurement(this);
  }

  /**
   * Marks the current measurement as finished.
   *
   * @throws IllegalStateException Thrown in case the name of the measurement point is null.
   */

  public void collect() {
    if (name == null) {
      throw new IllegalStateException("A measurement point may not be collected without a proper name.");
    }
    monitor.visitPostCollect(this);
  }

  /**
   * Alters the name of the measurement point.
   * This may be usefull for executions where the outcome of an operation
   * may change the scope of the measurement, e.g. an Exception.
   *
   * @param newName The new name of the measurement point.
   */
  public void alterName(String newName) {
    name = newName;
  }

  /**
   * Returns the name of the measurement point.
   *
   * @return The name.
   */

  public String getName() {
    return name;
  }

  /**
   * Returns the start time of the measurement in the {@link etm.core.timer.ExecutionTimer}
   * dependend precision.
   *
   * @return The start time.
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * Returns the end time of the measurement in the {@link etm.core.timer.ExecutionTimer}
   * dependend precision.
   *
   * @return The end time.
   */

  public long getEndTime() {
    return endTime;
  }

  /**
   * Returns the number of ticks per milisecond as provided by the used {@link etm.core.timer.ExecutionTimer}.
   *
   * @return The number of ticks.
   */

  public long getTicks() {
    return ticks;
  }


  /**
   * Sets a parent measurement point.
   *
   * @param aParent The parent.
   */
  protected void setParent(MeasurementPoint aParent) {
    parent = aParent;
  }

  /**
   * Returns the parent of this measurement point.
   *
   * @return The parent, may be null.
   */
  public MeasurementPoint getParent() {
    return parent;
  }

  /**
   * Sets the start time of the measurement.
   *
   * @param aStartTime The start time.
   */

  protected void setStartTime(long aStartTime) {
    startTime = aStartTime;
  }

  /**
   * Sets the end time of the measurement.
   *
   * @param aEndTime The end time.
   */

  protected void setEndTime(long aEndTime) {
    endTime = aEndTime;
  }

  /**
   * Sets the number of ticks per millsecond.
   *
   * @param aTicks
   */

  protected void setTicks(long aTicks) {
    ticks = aTicks;
  }

  /**
   * Returns the calculated processing time.
   *
   * @return The processing time.
   */

  public double getTransactionTime() {
    return ((double) ((endTime - startTime) * SECOND_MULTIPLIER)) / (double) ticks;
  }

  /**
   * Returns the time the measurement was startet.
   *
   * @return The time taken using <code>System.currentTimeMillis</code>
   */

  public long getStartTimeMillis() {
    return startTimeMillis;
  }


  public String toString() {
    return "MeasurementPoint{" +
      "monitor=" + monitor +
      ", parent=" + parent +
      ", name='" + name + "'" +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      ", ticks=" + ticks +
      "}";
  }
}