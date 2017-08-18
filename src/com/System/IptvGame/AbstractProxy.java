package com.System.IptvGame;

import java.util.HashMap;

abstract class AbstractProxy
{
  HashMap<String, Object> objects = new HashMap();

  public abstract void finish();

  public abstract String[] ParsingPlayer(String[] paramArrayOfString);
}

/* Location:           D:\android\tools\dex2jar-0.0.7.11-SNAPSHOT\dex2jar-0.0.7.11-SNAPSHOT\classes_dex2jar(1).jar
 * Qualified Name:     be.preuveneers.phoneme.fpmidp.AbstractProxy
 * JD-Core Version:    0.6.0
 */