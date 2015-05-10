package io.teknek.injera.a1.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

import io.teknek.injera.a1.model.Field;
import io.teknek.injera.a1.model.Int32Type;
import io.teknek.injera.a1.model.StringType;
import io.teknek.injera.a1.model.Struct;
import io.teknek.injera.a1.model.Type;

public class Generator {

  private File targetDirectory;
  public Generator(File targetDirectory){
    this.targetDirectory = targetDirectory;
  }
  
  public void generate(Struct struct, String genPackage, String name, String lang) throws IOException{
    if ("java".equalsIgnoreCase(lang)){
      generateJava(struct, genPackage, name);
    } else {
    throw new RuntimeException("Can not generate "+lang);
    }
  }
  
  private static String capitalize(String s){
    return String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1);
  }
  
  private void generateJava(Struct struct, String genPackage, String name) throws IOException{
    File currentDirectory = targetDirectory;
    if (!targetDirectory.exists()){
      targetDirectory.mkdir();
      System.out.println("created " + targetDirectory);
    }
    String [] parts = genPackage.split("\\.");
    for (String part: parts){
      File child = new File(currentDirectory, part);
      if (!child.exists()){
        child.mkdir();
      }
      currentDirectory = child;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("/* !!Generated by the injera compiler!! \n");
    sb.append(" !!You should not touch this!! */\n");
    sb.append("package " + genPackage + ";" + "\n");
    generateImports(sb);
    sb.append("public class " + capitalize(name) + " {" + "\n");
    sb.append("  private static final int ONE_BYTE_SIZE = -1;" + "\n");
    sb.append("  private static final int TWO_BYTE_SIZE = -2;" + "\n");
    generateFieldEnum(sb, struct);
    sb.append("  private final ByteBuffer injDataBuffer;" + "\n");
    sb.append("  private int maxPosition = 0;" + "\n");
    generateConstructors(sb, struct, name);
    
    generateReadFunction(sb);
    sb.append("}"); // end class
    FileWriter fw = new FileWriter(new File(currentDirectory, capitalize(name)  + ".java"));
    fw.write(sb.toString());
    fw.close();
  }

  private void generateConstructors(StringBuilder sb, Struct struct, String name) {
    sb.append("  public "+capitalize(name)+ "(){\n");
    sb.append("     injDataBuffer = ByteBuffer.allocate(100);\n");
    sb.append("  }\n");
    
    sb.append("  public "+capitalize(name)+ "(ByteBuffer buffer){\n");
    sb.append("     injDataBuffer = buffer;\n");
    sb.append("  }\n");
  }

  private void generateImports(StringBuilder sb) {
    sb.append("import java.nio.ByteBuffer;\n");
  }

  private void generateFieldEnum(StringBuilder sb, Struct fields) {
    sb.append("  private static enum Field {\n");
    for (Field field : fields.getFields()) {
      
      sb.append("    ").append(field.getName()).append("(").append(field.getPosition()).append(",")
              .append(sizeOf(field.getType())).append(")").append(",").append("\n");
    }
    sb.append("    ;\n");
    sb.append("    Field(int tag, int size){\n");
    sb.append("      this.tag = tag;\n");
    sb.append("      this.size = size;\n");
    sb.append("    }\n");
    sb.append("    int tag;\n");
    sb.append("    int size;\n");
    sb.append("  }\n");

    sb.append("  private static final Field [] fields = new Field[] { null, null, ");
    for (Field field : fields.getFields()) {
      sb.append("Field.").append(field.getName()).append(",");
    }
    sb.append("};\n");
    sb.append("  private Field findFieldForTag(int tag){\n");
    sb.append("    return fields[tag];\n");
    sb.append("  }\n");
  }
  
  private String sizeOf(Type t){
    if (t instanceof Int32Type){
      return "4";
    } else if (t instanceof StringType){
      return "ONE_BYTE_SIZE";
    } else throw new RuntimeException("do not know how sizeOf "+t);
  }

  private void generateReadFunction(StringBuilder sb) {
    sb.append("  private int locateForRead(Field searchField){\n");
    sb.append("    if (maxPosition == 0){\n");
    sb.append("      return -1;\n");
    sb.append("    }\n");
    sb.append("    int i = 0;\n");
    sb.append("    while (i < maxPosition){\n");
    sb.append("      int index = injDataBuffer.get(i) & 0xFF;\n");
    sb.append("      if (index == 0){\n");
    sb.append("        return -1;\n");
    sb.append("      }\n");
    sb.append("      if (index == searchField.tag){\n");
    sb.append("        return i;\n");
    sb.append("      }\n");
    sb.append("      if (index > searchField.tag){\n");
    sb.append("        return -1;\n");
    sb.append("      }\n");
    sb.append("      Field f = findFieldForTag(index);\n");
    sb.append("      if (f.size == ONE_BYTE_SIZE){\n");
    sb.append("        int size = injDataBuffer.get(i + 1) & 0xFF ;\n");
    sb.append("        i += size + 1 + 1;\n");
    sb.append("      } else if (f.size == TWO_BYTE_SIZE){\n");
    sb.append("        i += injDataBuffer.getInt(i + 1) + 1 + 4;\n");
    sb.append("      } else {\n");
    sb.append("        i += f.size + 1;\n");
    sb.append("      }\n");
    sb.append("    }\n");
    sb.append("    throw new IllegalArgumentException(\"We done fed up\");\n");
    sb.append("  }\n");
  }
}
