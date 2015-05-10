package io.teknek.injera.a1;

import io.teknek.injera.a1.generated.SampleObj;
import io.teknek.injera.a1.model.Field;
import io.teknek.injera.a1.model.Int32Type;
import io.teknek.injera.a1.model.StringType;
import io.teknek.injera.a1.model.Struct;

import org.junit.Assert;
import org.junit.Test;

public class LetsTest {

  @Test
  public void aTest(){
    Struct s = new Struct().withField( 
            new Field(1, "afield", new Int32Type())).
       withField(
            new Field(2, "bfield", new StringType()));
  } 

  @Test
  public void sample(){
    SampleObj a = new SampleObj();
    a.setAfield(5);
    Assert.assertEquals(5, a.getAfield());
    a.setBfield(4);
    Assert.assertEquals(5, a.getAfield());
    Assert.assertEquals(4, a.getBfield());
    a.setAfield(10);
    Assert.assertEquals(10, a.getAfield());
    Assert.assertEquals(4, a.getBfield());
    a.setCfield("dude");
    Assert.assertEquals(10, a.getAfield());
    Assert.assertEquals(4, a.getBfield());
    Assert.assertEquals("dude", a.getCfield());
    a.setDfield( new int [] { 4, 5});
    Assert.assertArrayEquals(new int [] {4, 5} , a.getDField());
    //Assert.assertArrayEquals(expecteds, actuals)
    
  }

  class a {
    int x;
    String y;
    
    public String getY() {
      return y;
    }
    public void setY(String y) {
      this.y = y;
    }
    public void setX(int x){
      this.x = x;
    }
    public int getX(){
      return x;
    }
  }
  
  @Test
  public void sampleX(){
    long start = System.currentTimeMillis();
    a a = new a();
    String s = "dude";
    for (int i = 0 ; i < 100000; i++){
      a.setX(5);
      a.setY(s);
    }
    System.out.println ( System.currentTimeMillis() - start );
  }
  
  
  @Test
  public void sample2(){
    long start = System.currentTimeMillis();
    SampleObj a = new SampleObj();
    String s = "dude";
    for (int i = 0 ; i < 100000; i++){
      a.setCfield(s);
      a.setAfield(5);
    }
    System.out.println ( System.currentTimeMillis() - start );
  }
}
