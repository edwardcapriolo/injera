package io.teknek.injera.a1.model;

public abstract class Type {
  private String name;
  
  public Type(){
    
  }
  
  public Type(String name){
    setName(name);
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}
