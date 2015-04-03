package Compiler;

public class BaseNode {
public:
	Op op;        // Defined in opcode list
	PrimType ptyp;    // Defined in primitive type list
public:
	BaseNode *opnd(int i){return null;}
	
}
