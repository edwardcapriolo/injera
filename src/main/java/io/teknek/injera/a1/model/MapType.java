package io.teknek.injera.a1.model;

public class MapType extends ComplexType {

  private Type keyType;
  private Type valueType;
  
  public MapType(Type keyType, Type valueType){
    super("map");
  }

  public Type getKeyType() {
    return keyType;
  }

  public void setKeyType(Type keyType) {
    this.keyType = keyType;
  }

  public Type getValueType() {
    return valueType;
  }

  public void setValueType(Type valueType) {
    this.valueType = valueType;
  }
  
}
