package Compiler;

public class MirSymbol {

  int _ty_idx;      //type index
  int _st_idx;      //symbol table index
  int _name_stridx; //name index
  MIRStorageClass sclass;
  
  MIRConst *const_;
}
