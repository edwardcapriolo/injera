package io.teknek.injera.a1.model;

public class ListType extends ComplexType {

  private Type managedType;
  
  public ListType(Type managedType){
    super("list");
    this.managedType = managedType; 
  }
  
  public Type getManagedType() {
    return managedType;
  }
  
  public void setManagedType(Type managedType) {
    this.managedType = managedType;
  }
  
}
