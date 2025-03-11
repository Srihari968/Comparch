package processor.pipeline;

import generic.Instruction;
import generic.Operand;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	String ins;
	int imm;
	int r1;
	int r2;
	int r3;

	int rn1 = -1;
	int rn2 = -1;
	int rn3 = -1;

	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

}
