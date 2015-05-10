package io.teknek.injera.a1.model;

import java.util.ArrayList;
import java.util.List;

public class Struct {

  private List<Field> fields = new ArrayList<>();
  
  public Struct(){
    
  }
  
  public Struct(Field ... fields){
    for (Field f: fields){
      this.fields.add(f);
    }
  }

  public List<Field> getFields() {
    return fields;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }
  
  public Struct withField(Field f){
    fields.add(f);
    return this;
  }
}
