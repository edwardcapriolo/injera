package io.teknek.injera.a1;

import io.teknek.injera.a1.generator.Generator;
import io.teknek.injera.a1.model.Field;
import io.teknek.injera.a1.model.Int32Type;
import io.teknek.injera.a1.model.StringType;
import io.teknek.injera.a1.model.Struct;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class GeneratorTest {

  @Test
  public void makeSomething() throws IOException{
    File f = new File( "./src/test/java");
    Generator gen = new Generator(f);
    Struct s = new Struct().withField( 
            new Field(1, "afield", new Int32Type())).
       withField(
            new Field(2, "bfield", new StringType()));
    gen.generate(s, "io.teknek.injera.generatedviatest", "firsttime", "java");
  }
  
  @Test
  public void aTest(){
    
  } 
}
