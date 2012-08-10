package com.google.android.apps.mytracks.content;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SensorData {
  public class pair {
    public float[] value;
    public long timestamp = -1;

    public pair(float[] value, long timestamp) {
      this.value = value;
      this.timestamp = timestamp;
    }
  }

  private ArrayList<pair> database = new ArrayList<pair>();
  private long trackId;
  private File sensorDataFile;
  private int MAXSIZE = 50;
  public SensorData(long trackId,Context context) {
    this.trackId = trackId;
    sensorDataFile = new File(context.getExternalCacheDir(),"tracks"+this.trackId+".csv");
  }

  public void addSensorData(float[] data, long timestamp) {
    assert (data.length == 3);
    database.add(new pair(data, timestamp));
    if(database.size()>MAXSIZE) 
      dump2File();
  }

  public void dump2File() {
    if(sensorDataFile == null)
      return;
    BufferedWriter bw = null;

    try {
      bw = new BufferedWriter(new FileWriter(sensorDataFile, true));
      for (pair data : database) {
        String tmp = Arrays.toString(data.value) + " " + data.timestamp + "\n";
        bw.write(tmp);
      }
      database.clear();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
