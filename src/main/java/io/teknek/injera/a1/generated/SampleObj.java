package io.teknek.injera.a1.generated;
import java.nio.ByteBuffer;

public class SampleObj {
  private static final int ONE_BYTE_SIZE = -1;
  private static final int TWO_BYTE_SIZE = -2;
  private static enum Field {
    a_field(2, 4),
    b_field(3, 4),
    c_field(4, ONE_BYTE_SIZE),
    d_field(5, TWO_BYTE_SIZE);
    
    Field(int tag, int size){
      this.tag = tag;
      this.size = size;
    }
    
    int tag;
    int size;
  }
  
  private final ByteBuffer injDataBuffer;
  private int maxPosition = 0;
    
  public SampleObj(){
    injDataBuffer = ByteBuffer.allocate(100);
  }
  
  public SampleObj(int aField, int bField){
    injDataBuffer = ByteBuffer.allocate(100);
    setAfield(aField);
    setBfield(bField);
  }
  
  /**
   * 
   * @param field_id
   * @return -1 if field does not exist in header else position the field starts at
   */
  private int locateForRead(Field searchField){
    if (maxPosition == 0){
      return -1;
    }
    int i = 0;
    while (i < maxPosition){
      int index = injDataBuffer.get(i) & 0xFF;
      if (index == 0){
        return -1;
      }
      if (index == searchField.tag){
        return i;
      }
      if (index > searchField.tag){
        return -1;
      }
      Field f = findFieldForTag(index);
      if (f.size == ONE_BYTE_SIZE){
        int size = injDataBuffer.get(i + 1) & 0xFF ;
        i += size + 1 + 1;
      } else if (f.size == TWO_BYTE_SIZE){
        i += injDataBuffer.getInt(i + 1) + 1 + 4;
      } else {
        i += f.size + 1;
      }
    }
    throw new IllegalArgumentException("We done fed up");
  }
  
  private Field findFieldForTag(int tag){
    for (Field f: Field.values()){
      if (f.tag == tag){
        return f;
      }
    }
    throw new RuntimeException("no matching tag "+ tag);
  }
  
  private int locateForWrite (Field insertField, SelfWriteCallBack callBack){
    if (maxPosition == 0){
      return -1;
    } 
    int i = 0;
    while (i < maxPosition){
      int tag = injDataBuffer.get(i) & 0xFF;
      if (tag == insertField.tag){
        return i;
      }
      if (tag > insertField.tag){ // shift over
        int toShift = -1;
        if (insertField.size == ONE_BYTE_SIZE){
          toShift = callBack.selfSize() + 1;
        } else if (insertField.size == TWO_BYTE_SIZE){
          toShift = callBack.selfSize() + 1;
        } else {
          toShift= callBack.selfSize() + 1;
        }
        //ensure size
        for (int j = 0; j < toShift; j++) {
          injDataBuffer.put((byte) 0);
        }
        for (int j = maxPosition;j >  i; j--){
          injDataBuffer.put(maxPosition + toShift, 
                  injDataBuffer.get(maxPosition));
        }
        maxPosition += toShift;
        return i;
      }
      Field iterateField = findFieldForTag(tag);
      if (iterateField.size == -1){
        int objectSize = injDataBuffer.get(i+1) & 0xFF;
        i += objectSize + 2;
      } else {
        i += iterateField.size + 1;
      }
    }
    return i;
  }
  
  public void setAfield(final int x){
    writeYourself(Field.a_field, new SelfWriteCallBack(){
      public void selfWrite(int pos2) {
        injDataBuffer.put(pos2, (byte) (Field.a_field.tag & 0xFF));
        injDataBuffer.putInt(pos2 + 1, x);
      }
      public int selfSize() {
        return Field.a_field.size;
      }
    });
  }
  
  //generated
  public void setBfield(final int x){
    writeYourself(Field.b_field, new SelfWriteCallBack(){
      public void selfWrite(int pos2) {
        injDataBuffer.put(pos2, (byte) (Field.b_field.tag & 0xFF));
        injDataBuffer.putInt(pos2 + 1, x);
      }
      public int selfSize() {
        return Field.b_field.size;
      }
    });
  }
  
  public void setCfield(final String x){
    writeYourself(Field.c_field, new SelfWriteCallBack(){
      public void selfWrite(int pos2) {
        injDataBuffer.put(pos2, (byte) (Field.c_field.tag & 0xFF));
        byte len = (byte) (x.length() & 0xFF);
        injDataBuffer.put(pos2 + 1, len);
        injDataBuffer.position(pos2 + 2);
        injDataBuffer.put(x.getBytes(), 0, len);
        injDataBuffer.position(0);
      }
      public int selfSize() {
        return ((byte) (x.length() & 0xFF) ) + 1;
      }
    });
  }
  
  public void setDfield(final int x []){
    writeYourself(Field.d_field, new SelfWriteCallBack(){
      public void selfWrite(int pos2) {
        injDataBuffer.put(pos2, (byte) (Field.d_field.tag & 0xFF));
        injDataBuffer.putInt(pos2 + 1, x.length);
        injDataBuffer.position(pos2 + 1 + 4);
        for (int i =0;i<x.length;i++){
          injDataBuffer.putInt(x[i]);
        }
        injDataBuffer.position(0);
      }
      public int selfSize() {
        return 4 + (x.length * 4) + 1;
      }
    });
  }
  
  public int [] getDField(){
    int pos = locateForRead(Field.d_field);
    if (pos == -1){
      return null;
    }
    int [] res = new int [injDataBuffer.getInt(pos + 1)];
    for (int i=0;i<res.length;i++){
      res[i] = injDataBuffer.getInt((pos + 1 + 4)+ (i *4));
    }
    return res;
  }
  
  
  //generated
  public int getAfield(){
    int pos = locateForRead(Field.a_field);
    if (pos == -1){
      return 0;
    }
    return injDataBuffer.getInt(pos + 1);
  }
  
  //generated
  public int getBfield(){
    int pos = locateForRead(Field.b_field);
    if (pos == -1){
      return 0;
    }
    return injDataBuffer.getInt(pos + 1);
  }
  
  public String getCfield(){
    int pos = locateForRead(Field.c_field);
    if (pos == -1){
      return "";
    } 
    int size = injDataBuffer.get(pos+1) & 0xFF ;
    byte [] b = new byte [size];
    injDataBuffer.position(pos +2);
    injDataBuffer.get(b, 0 , size);
    injDataBuffer.position(0);
    return new String(b);
    //return injDataBuffer.getInt(pos + 1);
  }
  
  //generated
  public Integer getAfieldObj(){
    int pos = locateForRead(Field.a_field);
    if (pos == -1){
      return null;
    }
    return injDataBuffer.getInt(pos + 1);
  }
  
  public interface SelfWriteCallBack{
    void selfWrite(int pos2);
    int selfSize();
  }
  
  public void writeYourself(Field field, SelfWriteCallBack back){
    int pos = locateForWrite(field, back);
    if (pos == -1){
      //checksizeandallocateifneeded()
      pos = 0;
      back.selfWrite(pos);
      maxPosition = pos + 1 + back.selfSize();
    } else if (pos == maxPosition) {
      back.selfWrite(pos);
      maxPosition = pos + 1 + back.selfSize();
    } else if (pos + back.selfSize() == maxPosition){//wrong with variable size
      back.selfWrite(pos);
    } else if (pos + back.selfSize() < maxPosition){ // wrong with variable size
      back.selfWrite(pos);
    } else {
      throw new RuntimeException("Did not conside that");
    }
  }
  
}
/*
if (pos == -1){
  //checksizeandallocateifneeded()
  pos = 0;
  injDataBuffer.put(pos, (byte) (Field.a_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
  maxPosition = pos + 1 + Field.a_field.size;
} else if (pos + Field.a_field.size == maxPosition){
  injDataBuffer.put(pos, (byte) (Field.a_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
} else if (pos + Field.a_field.size < maxPosition){
  injDataBuffer.put(pos , (byte) (Field.a_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
} else if (pos == maxPosition) {
  injDataBuffer.put(pos, (byte) (Field.a_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
  maxPosition = pos + 1 + Field.a_field.size;
} else {
  throw new RuntimeException("Did not conside that");
}*/



/*
int pos = locateForWrite(Field.b_field);
if (pos == -1){
  //checksizeandallocateifneeded()
  pos = 0;
  injDataBuffer.put(pos, (byte) (Field.b_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
  maxPosition = pos + 1 + Field.b_field.size;
} else if (pos + Field.b_field.size == maxPosition){
  injDataBuffer.put(pos, (byte) (Field.b_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
} else if (pos + Field.b_field.size < maxPosition){
  injDataBuffer.put(pos , (byte) (Field.b_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
} else if (pos == maxPosition) {
  injDataBuffer.put(pos, (byte) (Field.b_field.tag & 0xFF));
  injDataBuffer.putInt(pos + 1, x);
  maxPosition = pos + 1 + Field.b_field.size;
} else {
  throw new RuntimeException("Did not conside that");
}
*/