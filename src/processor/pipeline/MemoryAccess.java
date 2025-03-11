package processor.pipeline;

import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	String opt;
	int rn1 = -1;
	int rn2 = -1;
	int rn3 = -1;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		opt = EX_MA_Latch.ins;
		rn1 = EX_MA_Latch.rn1;
		rn2 = EX_MA_Latch.rn2;
		rn3 = EX_MA_Latch.rn3;

		MA_RW_Latch.rn1 = EX_MA_Latch.rn1;
		MA_RW_Latch.rn2 = EX_MA_Latch.rn2;
		MA_RW_Latch.rn3 = EX_MA_Latch.rn3;
		MA_RW_Latch.ins = EX_MA_Latch.ins;


		System.out.println("MA------------" + EX_MA_Latch.ins);
		if(opt == null)
			return;

		MA_RW_Latch.rd = EX_MA_Latch.rd;
		int rd = MA_RW_Latch.rd;
		int rs = MA_RW_Latch.rs;
		MA_RW_Latch.val = EX_MA_Latch.val;
		RegisterFile regs = containingProcessor.getRegisterFile();
		MainMemory mem = containingProcessor.getMainMemory();
		
	
		if(EX_MA_Latch.ins == "load")
		{
			//System.out.println("MA LOAD RD-" + rd + "  MEMADD:" + EX_MA_Latch.memAdd);
			//System.out.println("MEMORY VALE" + mem.getWord(EX_MA_Latch.memAdd));
			//regs.setValue(rd,processor.getMainMemory().getWord(imm + processor.getRegisterFile().getValue(rs)));

			//regs.setValue(rd, mem.getWord(EX_MA_Latch.memAdd));
			//containingProcessor.getRegisterFile().setValue(3, 100);
			MA_RW_Latch.val = mem.getWord(EX_MA_Latch.memAdd);
		}
		else if(EX_MA_Latch.ins == "store")
		{
			//processor.getMainMemory().setWord(regs.getValue(rd)+imm, regs.getValue(rs));
			containingProcessor.getMainMemory().setWord(EX_MA_Latch.memAdd, (EX_MA_Latch.rs));
			//System.out.println("Storing " + (EX_MA_Latch.rs) + " in " + EX_MA_Latch.memAdd);
		}
		//EX_MA_Latch.setMA_enable(false);
		MA_RW_Latch.setRW_enable(true);
	}
		

}
