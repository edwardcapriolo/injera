package io.teknek.injera.a1;

import io.teknek.injera.a1.generator.Generator;
import io.teknek.injera.a1.model.Field;
import io.teknek.injera.a1.model.Int32Type;
import io.teknek.injera.a1.model.Int64Type;
import io.teknek.injera.a1.model.StringType;
import io.teknek.injera.a1.model.Struct;
import io.teknek.injera.generatedviatest.Firsttime;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class GeneratorTest {

  @Test
  public void makeSomething() throws IOException{
    File f = new File( "./src/test/java");
    Generator gen = new Generator(f);
    Struct s = new Struct().withField(new Field(2, "afield", new Int32Type()))
            .withField(new Field(3, "bfield", new StringType()))
            .withField(new Field(4, "clong", new Int64Type()));
    gen.generate(s, "io.teknek.injera.generatedviatest", "firsttime", "java");
    Firsttime firsttime = new Firsttime();
    firsttime.setAfield(5);
    Assert.assertEquals(5, firsttime.getAfield());
    firsttime.setClong(4);
    Assert.assertEquals(4, firsttime.getClong());
  }
  
  @Test
  public void aTest(){
    
  } 
}
