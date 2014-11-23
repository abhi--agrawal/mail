import javax.sound.sampled.*;
import java.util.*;
import java.net.*;
public class Talker
{
  private SourceDataLine line=null;

public static void main(String args[])
{
  Talker player=new Talker();
  player.sayPhoneWord("abhishek");
  System.exit(0);
}

public void sayPhoneWord(String word)
{
  // -- Set up a dummy byte array for the previous sound --
  byte[] previousSound=null;
  // -- Split the input string into separate allophones --
  StringTokenizer st=new StringTokenizer(word,"|",false);
  while (st.hasMoreTokens())
  {
    // -- Construct a file name for the allophone --
    String thisPhoneFile=st.nextToken();
    thisPhoneFile="/allophones/"+thisPhoneFile+".au";
    // -- Get the data from the file --
    byte[] thisSound=getSound(thisPhoneFile);   
    if (previousSound!=null)
    {
      // -- Merge the previous allophone with this one, if we can --
      int mergeCount=0;
      if (previousSound.length>=500 && thisSound.length>=500)
        mergeCount=500;
      for (int i=0; i<mergeCount;i++)
      {
        previousSound[previousSound.length-mergeCount+i]
         =(byte)((previousSound[previousSound.length
         -mergeCount+i]+thisSound[i])/2);
      }
      // -- Play the previous allophone --
      playSound(previousSound);
      // -- Set the truncated current allophone as previous --
      byte[] newSound=new byte[thisSound.length-mergeCount];
      for (int ii=0; ii<newSound.length; ii++)
        newSound[ii]=thisSound[ii+mergeCount];
      previousSound=newSound; 
    }
    else
      previousSound=thisSound;
  }
  // -- Play the final sound and drain the sound channel --
  playSound(previousSound);
  drain();
}

/*
 * This method plays a sound sample.
 */
private void playSound(byte[] data)
{
  if (data.length>0) line.write(data, 0, data.length);
}

/*
 * This method flushes the sound channel.
 */
private void drain()
{
  if (line!=null) line.drain();
  try {Thread.sleep(100);} catch (Exception e) {}
}

/*
 * This method reads the file for a single allophone and
 * constructs a byte vector.
 */
private byte[] getSound(String fileName)
{
  try
  {
    URL url=Talker.class.getResource(fileName);
    AudioInputStream stream = AudioSystem.getAudioInputStream(url);
    AudioFormat format = stream.getFormat();
    // -- Convert an ALAW/ULAW sound to PCM for playback -- 
    if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
     (format.getEncoding() == AudioFormat.Encoding.ALAW)) 
    {
      AudioFormat tmpFormat = new AudioFormat(
       AudioFormat.Encoding.PCM_SIGNED, 
       format.getSampleRate(),
       format.getSampleSizeInBits() * 2,
       format.getChannels(),
       format.getFrameSize() * 2,
       format.getFrameRate(),
       true);
      stream = AudioSystem.getAudioInputStream(tmpFormat, stream);
      format = tmpFormat;
    }
    DataLine.Info info = new DataLine.Info(
     Clip.class, 
     format, 
     ((int) stream.getFrameLength() * format.getFrameSize()));
    if (line==null)
    {
      // -- Output line not instantiated yet --
      // -- Can we find a suitable kind of line? --
      DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, 
       format);
      if (!AudioSystem.isLineSupported(outInfo))
      {
        System.out.println("Line matching " + outInfo + " not supported.");
        throw new Exception("Line matching " + outInfo + " not supported.");
      }
      // -- Open the source data line (the output line) --
      line = (SourceDataLine) AudioSystem.getLine(outInfo);
      line.open(format, 50000);
      line.start();
    }
    // -- Some size calculations --
    int frameSizeInBytes = format.getFrameSize();
    int bufferLengthInFrames = line.getBufferSize() / 8;
    int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
    byte[] data=new byte[bufferLengthInBytes];
    // -- Read the data bytes and count them --
    int numBytesRead = 0;
    if ((numBytesRead = stream.read(data)) != -1)
    {
      int numBytesRemaining = numBytesRead;
    }
    // -- Truncate the byte array to the correct size --
    byte[] newData=new byte[numBytesRead];
    for (int i=0; i<numBytesRead;i++)
      newData[i]=data[i];
    return newData;
  }
  catch (Exception e)
  {
    return new byte[0];
  }
}

}