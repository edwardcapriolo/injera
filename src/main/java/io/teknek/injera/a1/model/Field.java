package io.teknek.injera.a1.model;

public class Field {


  private int position;
  private String name;
  private Type type;

  public Field(int position, String name, Type type) {
    this.position = position;
    this.name = name;
    this.type = type;
  }
  
  public Field(){}


  public int getPosition() {
    return position;
  }

  public void setPosition(byte position) {
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public Field withName(String name) {
    this.name = name;
    return this;
  }
  
  public Field position(int position) {
    this.position = position;
    return this;
  }
  
  public Field withType(Type t){
    this.type = t;
    return this;
  }
  
  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public Object clone(){
    return  new Field( getPosition(), getName(), getType());
  }
}
